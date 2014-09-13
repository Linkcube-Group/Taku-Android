#pragma strict

enum StrafeDirection {left = 0, right = 1}

private var tPlayer : Transform;
private var tPlayerRotation : Transform;
private var aPlayer : Animation;

private var tPlayerSidesCollider : Transform;
private var tFrontCollider : Transform;
private var v3BNCDefaultScale : Vector3;
private var v3BFCDefaultScale : Vector3;

//Variables
public var fCurrentWalkSpeed : float;

private var tCurrentAngle : float = 0.0;
private var fJumpForwardFactor : float = 0.0;
private var fCurrentUpwardVelocity : float = 0.0;
private var fCurrentHeight : float = 0.0;
private var fContactPointY : float = 0.0;

//player state during gameplay
private var bInAir : boolean = false;
private var bJumpFlag : boolean = false;
private var bInJump : boolean = false;
private var bInDuck : boolean = false;
private var bDiveFlag : boolean = false;
private var bExecuteLand : boolean = false;
private var bInStrafe : boolean = false;

private var fForwardAccleration : float = 0.0;
private var tBlobShadowPlane : Transform;
private var CurrentDirection : Vector3;

private var hPatchesRandomizer : PatchesRandomizer;
private var hCheckPointsMain : CheckPointsMain;
private var hInGameScript : InGameScript;
private var hPitsMainController : PitsMainController;
private var hSoundManager : SoundManager;
private var hCameraController : CameraController;
private var hPowerupScript : PowerupsMainController;
private var hEnemyController : EnemyController;
private var hMenuScript : MenuScript;
private var hPlayerFrontColliderScript : PlayerFrontColliderScript;
private var hPlayerSidesColliderScript : PlayerSidesColliderScript;
private var hPreyScript : PreyScript;
private var hBluetoothScript : BluetoothScript;

private var hitInfo : RaycastHit;
private var bGroundhit : boolean = false;
private var fHorizontalDistance : float = 0.0;

private var fCurrentForwardSpeed : float = .5;
public var fCurrentDistance : float = 0.0;
private var fCurrentMileage : float = 0.0;

private var fPitFallLerpValue : float = 0.0;
private var fPitFallForwardSpeed : float = 0.0;
private var fPitPositionX : float = 0.0;
private var iDeathAnimStartTime : int = 0;
private var iDeathAnimEndTime : int = 3;

private var JumpAnimationFirstTime : boolean = true;
private var HUDCamera : Camera;

private var tPauseButton : Transform;
private var tHUDGroup : Transform;
private var swipeLogic : SwipeControls;
private var iLanePosition : int;
private var iLastLanePosition : int;
private var bMouseReleased : boolean = true;
private var bControlsEnabled : boolean = true;

private var directionQueue : SwipeDirection;
private var bDirectionQueueFlag : boolean = false;

private var fStartingWalkSpeed : float = 0.0;//150.0;
private var fEndingWalkSpeed : float = 400.0;//230.0;
private var fCurrentWalkAccleration : float = 0.5;

private var fJumpPush : float = 185;
private function getAccleration() { return 500; }

public var fCurrentDistanceOnPath : float = 0.0;

private var swipeControlsEnabled : boolean = true;
public function isSwipeControlEnabled() { return swipeControlsEnabled; }

private var bIsLeft : boolean = true;
private var fPrevTouchTime : float = -10.0f;
private var fTouchTime : float = 0.0f;
private var fElapseTouchTime : float = 0.0f;
private var fTouchFrequency : float = 0.0f;
private var fPrevAccelerationRatio : float = 1.0f;
private var fAccelarationRatio : float = 1.0f;

private var eLastSwipeDirection : SwipeDirection = SwipeDirection.Null;

public function toggleSwipeControls(state:boolean)
{
	swipeControlsEnabled = state;

	PlayerPrefs.SetInt("ControlsType", (state == true ? 1 : 0));
	PlayerPrefs.Save();
}

function Start()
{
	hMenuScript = GameObject.Find("MenuGroup").GetComponent(MenuScript) as MenuScript;
	hPatchesRandomizer = this.GetComponent(PatchesRandomizer) as PatchesRandomizer;
	hPlayerSidesColliderScript = GameObject.Find("PlayerSidesCollider").GetComponent(PlayerSidesColliderScript) as PlayerSidesColliderScript;
	hPlayerFrontColliderScript = GameObject.Find("PlayerFrontCollider").GetComponent(PlayerFrontColliderScript) as PlayerFrontColliderScript;
	hSoundManager = GameObject.Find("SoundManager").GetComponent(SoundManager) as SoundManager;
	hInGameScript = this.GetComponent(InGameScript) as InGameScript;	
	hPitsMainController = this.GetComponent(PitsMainController) as PitsMainController;
	hCheckPointsMain = this.GetComponent(CheckPointsMain) as CheckPointsMain;
	hPowerupScript = this.GetComponent(PowerupsMainController) as PowerupsMainController;
	hEnemyController = GameObject.Find("Enemy").GetComponent(EnemyController) as EnemyController;
	hPowerupScript = this.GetComponent(PowerupsMainController) as PowerupsMainController;
	hCameraController = camera.main.gameObject.GetComponent(CameraController) as CameraController;
	swipeLogic = transform.GetComponent(SwipeControls) as SwipeControls;
	hPreyScript = GameObject.Find("Prey").GetComponent(PreyScript) as PreyScript;
	hBluetoothScript = this.GetComponent(BluetoothScript) as BluetoothScript;

	tPlayer = transform;
	tPlayerRotation = transform.Find("PlayerRotation");

	//get the animation component of the player character
	aPlayer = this.transform.Find("PlayerRotation/PlayerMesh/boy").GetComponent(Animation) as Animation;
	tBlobShadowPlane = transform.Find("BlobShadowPlane");

	tPlayerSidesCollider = GameObject.Find("PlayerSidesCollider").transform;	
	tFrontCollider = GameObject.Find("PlayerFrontCollider").transform;
	tHUDGroup = GameObject.Find("HUDMainGroup/HUDGroup").transform;
	tPauseButton = GameObject.Find("HUDMainGroup/HUDGroup/HUDPause").transform;

	HUDCamera = GameObject.Find("HUDCamera").camera;

	v3BNCDefaultScale = tFrontCollider.localScale;	
	v3BFCDefaultScale = tPlayerSidesCollider.localScale;

	bInAir = false;
	fCurrentDistanceOnPath = 50.0;
	fCurrentDistance = 0.0;
	fCurrentMileage = 0.0;
	tCurrentAngle = 0.0;	
	fPitFallLerpValue = 0.0;
	fPitFallForwardSpeed = 0.0;
	fPitPositionX = 0.0;
	iDeathAnimStartTime = 0;
	bGroundhit = false;	
	bJumpFlag = false;
	bInJump = false;
	fCurrentUpwardVelocity = 0;
	fCurrentHeight = 0;

	bDirectionQueueFlag = false;
	directionQueue = SwipeDirection.Null;
	iLanePosition = 0;	//set current lane to mid	
	fCurrentWalkSpeed = fStartingWalkSpeed;

	
	if (PlayerPrefs.HasKey("ControlsType"))
	    swipeControlsEnabled = PlayerPrefs.GetInt("ControlsType") == 1 ? true : false;
	else
	    PlayerPrefs.SetInt("ControlsType", (swipeControlsEnabled == true ? 1 : 0));

	togglePlayerAnimation(false);	
	hSoundManager.stopSound(CharacterSounds.Footsteps);
	hPreyScript.SetLevel(PreyLevelEnum.pleEasy);
}
    
public function launchGame()
{
	hEnemyController.launchEnemy();
	hPreyScript.LaunchPrey();

	togglePlayerAnimation(true);
	if(fStartingWalkSpeed > 0.0f){
		aPlayer["run"].speed = Mathf.Clamp( (fCurrentWalkSpeed/fStartingWalkSpeed)/1.1, 0.8, 1.2 );
		aPlayer.Play("run");
	}

	hSoundManager.playSound(CharacterSounds.Footsteps);
}

function Update()
{
	if(hInGameScript.isGamePaused()==true)
		return;

	if (hInGameScript.isEnergyZero())
		if(DeathScene())
			return;

	getClicks();

	if (bControlsEnabled)
		SwipeMovementControl();
	if(fStartingWalkSpeed > 0.0f){
		//aPlayer.CrossFade("run");
	}
	if (Input.GetMouseButtonDown(0))
	{
		if(Input.touchCount == 2){
			if(Input.mousePosition.y < Screen.height * 0.2)
			{
				aPlayer.CrossFade("jump");
			}
		}          
	}
}

//function OnGUI(){
	//GUI.Box(Rect(0, 0, 400, 30), fAccelarationRatio + " --- "  + fElapseTouchTime + " ** " + fCurrentWalkSpeed);
	//GUI.Box(Rect(0, 40, 400, 30), Input.touchCount.ToString());
	/*if(GUI.Button(Rect(0, Screen.height*(1-0.2f), Screen.width, Screen.height * (1-0.2f)), ""))
	{
		if(Input.touchCount <= 1)
		{
			fTouchTime = Time.time;
			if (fPrevTouchTime > 0.0f)
			{
				fElapseTouchTime = fTouchTime - fPrevTouchTime;  
				if(fElapseTouchTime < 0.4f)
				{
					fTouchFrequency = 1.0f / fElapseTouchTime;
					fPrevAccelerationRatio = fAccelarationRatio;
					if(fTouchFrequency >= 7.0f)
					{
						fAccelarationRatio = 1.0f + fTouchFrequency * 2;// / 5.0f;   //fCurrentWalkSpeed    
					}
					else
					{
						fAccelarationRatio = 1.0f + fTouchFrequency;
					}
					fStartingWalkSpeed = 70.0f;
					fCurrentWalkSpeed = fStartingWalkSpeed * fAccelarationRatio;// * 0.3f;//(fAccelarationRatio / fPrevAccelerationRatio);
				}
				else if(fElapseTouchTime < 0.6f)
				{
					fStartingWalkSpeed = 50.0f;
					fCurrentWalkSpeed = 50.0f;
				}
				else
				{
					fStartingWalkSpeed = 0.0f;
					fCurrentWalkSpeed = 0.0f;
				}
			}
        

            fPrevTouchTime = fTouchTime;
            aPlayer.CrossFade("run");
        }
    }*/
//}

public function GetCurrSpeed(){
    return fCurrentWalkSpeed;
}

public function MultiCurrSpeed(pMultiplier : float){
    fCurrentWalkSpeed *= pMultiplier;
}

function FixedUpdate()
{
	if(hInGameScript.isGamePaused() == true)
		return;
	if(hPreyScript.GetDistanceToPlayer() < hPreyScript.GetMinDistanceToPlayer())
	{
	    fStartingWalkSpeed = hPreyScript.GetSpeedLimit();
	    fCurrentWalkSpeed = fStartingWalkSpeed;
	}
	setForwardSpeed();
	SetTransform();
	setShadow();
		
	if(!bInAir)
	{
		if(bExecuteLand)
		{
			hSoundManager.playSound(CharacterSounds.JumpLand);
			bExecuteLand = false;
			JumpAnimationFirstTime = true;
		}
	}
	else
	{		
		if(JumpAnimationFirstTime&&bInJump==true)
		{
			aPlayer.Rewind("jump");
			JumpAnimationFirstTime = false;
			bInDuck = false;
						
			aPlayer.CrossFade("jump", 0.1);
		}
	}

	if(bJumpFlag==true)
	{		
		bJumpFlag = false;
		bExecuteLand = true;
		bInJump = true;
		bInAir = true;
		fCurrentUpwardVelocity = fJumpPush;
		fCurrentHeight = tPlayer.position.y;
	}
		
	if(fCurrentWalkSpeed<fEndingWalkSpeed)
	{
		fCurrentWalkSpeed += (fCurrentWalkAccleration * Time.fixedDeltaTime);
	}
		
	if(fStartingWalkSpeed > 0.0f && fCurrentWalkSpeed > 60.0f)
	{
	    aPlayer["run"].speed = Mathf.Clamp( (fCurrentWalkSpeed/fStartingWalkSpeed)/1.1, 0.8, 1.2 );
	}
}

private function getClicks()
{
	if(Input.GetMouseButtonUp(0) && bMouseReleased==true)
	{
		var screenPoint : Vector3;
		var buttonSize : Vector2;
		var Orb_Rect : Rect;
		
		if(tHUDGroup.localPosition.z==0)
		{
			buttonSize = Vector3(Screen.width/6,Screen.width/6,0.0);
			screenPoint = HUDCamera.WorldToScreenPoint( tPauseButton.position );
			
			Orb_Rect = Rect (screenPoint.x - ( buttonSize.x * 0.5 ), screenPoint.y - ( buttonSize.y * 0.5 ), buttonSize.x, buttonSize.y);
			if(Orb_Rect.Contains(Input.mousePosition))
			{				
				hInGameScript.pauseGame();
			}
		}
		
		Orb_Rect = Rect (screenPoint.x - ( buttonSize.x * 0.5 ), screenPoint.y - ( buttonSize.y * 0.5 ), buttonSize.x, buttonSize.y);
	}		
}

private function setShadow()
{	
	tBlobShadowPlane.up = hitInfo.normal;
	tBlobShadowPlane.position.y = fContactPointY+0.7;
	tBlobShadowPlane.localEulerAngles.y = tPlayerRotation.localEulerAngles.y;
	
	tPlayerSidesCollider.position = tPlayer.position + Vector3(0,5,0);
	tPlayerSidesCollider.localEulerAngles = tBlobShadowPlane.localEulerAngles;
	
	tFrontCollider.position = tPlayer.position + Vector3(7,5,0);
	tFrontCollider.localEulerAngles = tBlobShadowPlane.localEulerAngles;
}

function SetTransform()
{
	if (bControlsEnabled)
		var iStrafeDirection : int = getLeftRightInput();
	
	fCurrentDistanceOnPath = hCheckPointsMain.SetNextMidPointandRotation(fCurrentDistanceOnPath, fCurrentForwardSpeed);
	fCurrentDistance = fCurrentDistanceOnPath + hPatchesRandomizer.getCoveredDistance();
	fCurrentMileage = fCurrentDistance/12.0;
	
	tCurrentAngle = hCheckPointsMain.getCurrentAngle();
	tPlayerRotation.localEulerAngles.y = -tCurrentAngle;
	
	CurrentDirection = hCheckPointsMain.getCurrentDirection();
	var Desired_Horinzontal_Pos : Vector3 = calculateHorizontalPosition(iStrafeDirection);	
	
	bGroundhit = Physics.Linecast(Desired_Horinzontal_Pos + Vector3(0,20,0),Desired_Horinzontal_Pos + Vector3(0,-100,0),hitInfo,(1<<9));	
	
	if(bGroundhit && hPitsMainController.isFallingInPit()==false)
		fContactPointY = hitInfo.point.y;
	else
	{
		fContactPointY = -10000.0;
		if(!bInAir)
		{
			if(!bInJump)
			{
				if(reConfirmPitFalling(Desired_Horinzontal_Pos,iStrafeDirection)==true)
				{
					hPitsMainController.setPitValues();
				}
			}
			bInAir = true;
			fCurrentUpwardVelocity = 0;
			fCurrentHeight = tPlayer.position.y;
		}
	}
	
	if(!bInAir)
	{
		tPlayer.position.y = fContactPointY+0.6;
	}
	else
	{
		if (bDiveFlag)
		{
			setCurrentDiveHeight();
			tPlayer.position.y = fCurrentHeight;
		}
		else
		{
			setCurrentJumpHeight();
			tPlayer.position.y = fCurrentHeight;
		}
	}
	
	tPlayer.position.x = Desired_Horinzontal_Pos.x;//set player position in x-axis
	tPlayer.position.z = Desired_Horinzontal_Pos.z;//set player position in y-axis	
}

private function setCurrentJumpHeight()
{
	fCurrentUpwardVelocity-=Time.fixedDeltaTime*getAccleration();
	fCurrentUpwardVelocity = Mathf.Clamp(fCurrentUpwardVelocity,-fJumpPush,fJumpPush);
	fCurrentHeight+=fCurrentUpwardVelocity*(Time.fixedDeltaTime/1.4);
	
	if(fCurrentHeight<fContactPointY)
	{
		fCurrentHeight = fContactPointY;
		bInAir = false;
		bInJump = false;
		
		if (bDiveFlag)
			return;
				
		if (!hInGameScript.isEnergyZero())
		{		
			aPlayer.CrossFade("run", 0.1);
		}
	}
}

private function setCurrentDiveHeight()
{
	fCurrentUpwardVelocity-=Time.fixedDeltaTime*2000;
	fCurrentUpwardVelocity = Mathf.Clamp(fCurrentUpwardVelocity,-fJumpPush,fJumpPush);
	if(hPitsMainController.isFallingInPit() == false)
	{
		fCurrentHeight+=fCurrentUpwardVelocity*Time.fixedDeltaTime;
	}
	else
	{
		fCurrentHeight-=40.0*Time.fixedDeltaTime;
		hMenuScript.hideHUDElements();
	}	
	
	if(fCurrentHeight<=fContactPointY)
	{
		fCurrentHeight = fContactPointY;
			
		bInAir = false;
		bInJump = false;
		
		duckPlayer();
		bDiveFlag = false;
	}
}

private function reConfirmPitFalling(Desired_Horinzontal_Pos : Vector3, iStrafeDirection : float) : boolean
{
	var bGroundhit : boolean = false;
	
	if(iStrafeDirection>=0)
		bGroundhit = Physics.Linecast(Desired_Horinzontal_Pos + Vector3(1,20,5),Desired_Horinzontal_Pos + Vector3(0,-100,5),hitInfo,1<<9);
	else
		bGroundhit = Physics.Linecast(Desired_Horinzontal_Pos + Vector3(1,20,-5),Desired_Horinzontal_Pos + Vector3(0,-100,-5),hitInfo,1<<9);
	
	if(!bGroundhit)
		return true;
	else
		return false;
}

function DeathScene() : boolean
{
	bInAir = false;
	tPlayerRotation.localEulerAngles = Vector3(0,0,0);
	
	if (iDeathAnimStartTime == 0)
	{
		hSoundManager.stopSound(CharacterSounds.Footsteps);
		bControlsEnabled = false;
				
		var v3EffectPosition = this.transform.position;
		v3EffectPosition.x += 15;
		v3EffectPosition.y += 5;		
	
		aPlayer.CrossFade("death",0.1);
		hEnemyController.playDeathAnimation();
		
		hMenuScript.hideHUDElements();
		iDeathAnimStartTime = Time.time;	
	}	
	else if (iDeathAnimStartTime != 0 && (Time.time - iDeathAnimStartTime) >= iDeathAnimEndTime)
	{		
		hInGameScript.setupDeathMenu();
		return true;
	}
	
	return false;
}

public function processStumble()
{
	hCameraController.setCameraShakeImpulseValue(1);
	iLanePosition = iLastLanePosition;
		
	if (hEnemyController.processStumble())
	{	
		hInGameScript.collidedWithObstacle();
	}
	else
	{
		aPlayer.PlayQueued("run", QueueMode.CompleteOthers);
		
		hPlayerFrontColliderScript.activateFrontCollider();
		hPlayerSidesColliderScript.activateSidesCollider();
	}	
}

private function getLeftRightInput()
{
    if(swipeControlsEnabled == true)
    {
        return iLanePosition;
    }
    else//gyro direction
    {
        var fMovement : float = 0.0;
        var fSign : float = 1.0;
		
        if(Screen.orientation == ScreenOrientation.Portrait)
        	fSign = 1.0;
        else
        	fSign = -1.0;
		
		if(Application.isEditor)
		{
			fMovement = (Input.mousePosition.x - (Screen.height/2.0))/(Screen.height/2.0) * 4.5;
		}
		else
		{
			//fMovement = (fSign * Input.acceleration.x * 4.5);			
			//fMovement = fSign*hBluetoothScript.GetLeftRight()*4.5;
		}
		
		return fMovement;
	}
}

private function setForwardSpeed()
{
	if(hPitsMainController.isFallingInPit() == true)
	{		
		if(transform.position.x>fPitPositionX)
			fCurrentForwardSpeed = 0.0;
		else
			fCurrentForwardSpeed = Mathf.Lerp(fPitFallForwardSpeed,0.01,(Time.time-fPitFallLerpValue)*3.5);
		return;
	}
	
	if (hInGameScript.isEnergyZero())
	{
		fCurrentForwardSpeed = 0;
		return;
	}
	
	if(bInAir)
		fForwardAccleration = 1.0;
	else
		fForwardAccleration = 2.0;
		
	if(fCurrentWalkSpeed <= 0.1)
	{
		fJumpForwardFactor = 0;
	}
	else
	{
		fJumpForwardFactor = 1 + ((1/fCurrentWalkSpeed)*50);
	}
		
	if(bInJump==true)
		fCurrentForwardSpeed = Mathf.Lerp(fCurrentForwardSpeed,fCurrentWalkSpeed*Time.fixedDeltaTime*fJumpForwardFactor,Time.fixedDeltaTime*fForwardAccleration);
	else
		fCurrentForwardSpeed = Mathf.Lerp(fCurrentForwardSpeed,(fCurrentWalkSpeed)*Time.fixedDeltaTime,Time.fixedDeltaTime*fForwardAccleration);
}

private var fCurrentStrafePosition : float = 0.0;
private var fSpeedMultiplier : float = 5.0;
private function calculateHorizontalPosition(iStrafeDirection : int)
{
	if (swipeControlsEnabled == true)
	{
		var SideDirection_Vector2 : Vector2 = rotateAlongZAxis(Vector2(CurrentDirection.x,CurrentDirection.z),90.0);
		SideDirection_Vector2.Normalize();
			
		if(iStrafeDirection==-1)
		{
			if(fCurrentStrafePosition>-1)
			{
				fCurrentStrafePosition-= fSpeedMultiplier*Time.fixedDeltaTime;
				if(fCurrentStrafePosition<=-1.0)
				{
					fCurrentStrafePosition = -1.0;
					switchStrafeToSprint();
				}
			}
		}
		else if(iStrafeDirection==1)
		{
			if(fCurrentStrafePosition<1)
			{
				fCurrentStrafePosition+= fSpeedMultiplier*Time.fixedDeltaTime;
				if(fCurrentStrafePosition>=1.0)
				{
					fCurrentStrafePosition = 1.0;
					switchStrafeToSprint();
				}
			}
		}
		else if(iStrafeDirection==0&&fCurrentStrafePosition!=0.0)//strafe from left or right lane to center
		{	
			if(fCurrentStrafePosition<0)
			{
				fCurrentStrafePosition+= fSpeedMultiplier*Time.fixedDeltaTime;
				if(fCurrentStrafePosition>=0.0)
				{
					fCurrentStrafePosition = 0.0;
					switchStrafeToSprint();
				}
			}
			else if(fCurrentStrafePosition>0)
			{
				fCurrentStrafePosition-= fSpeedMultiplier*Time.fixedDeltaTime;
				if(fCurrentStrafePosition<=0.0)
				{
					fCurrentStrafePosition = 0.0;
					switchStrafeToSprint();
				}
			}
		}
			
		fHorizontalDistance = -fCurrentStrafePosition*16.0;	
		fHorizontalDistance = Mathf.Clamp(fHorizontalDistance,-20.0,20.0);
		
		var fHorizontalPoint : Vector2 = hCheckPointsMain.getCurrentMidPoint() + SideDirection_Vector2*fHorizontalDistance;
			
		return Vector3(fHorizontalPoint.x,tPlayerRotation.position.y,fHorizontalPoint.y);
	}
	else
	{
		SideDirection_Vector2 = rotateAlongZAxis(Vector2(CurrentDirection.x,CurrentDirection.z),90.0);
		SideDirection_Vector2.Normalize();
		
		fHorizontalDistance = Mathf.Lerp(fHorizontalDistance,-iStrafeDirection * 40.0, 0.05*fCurrentForwardSpeed);		
		fHorizontalDistance = Mathf.Clamp(fHorizontalDistance,-20.0,20.0);		
		fHorizontalPoint = hCheckPointsMain.getCurrentMidPoint() + SideDirection_Vector2*fHorizontalDistance;
				
		return Vector3(fHorizontalPoint.x,tPlayerRotation.position.y,fHorizontalPoint.y);
	}
}

private function rotateAlongZAxis(inputVector : Vector2, angletoRotate : float)
{
	var FinalVector : Vector2 = Vector2.zero;
	angletoRotate = angletoRotate/57.3;
	FinalVector.x = Mathf.Cos(angletoRotate) * inputVector.x - Mathf.Sin(angletoRotate) * inputVector.y;
	FinalVector.y = Mathf.Sin(angletoRotate) * inputVector.x + Mathf.Cos(angletoRotate) * inputVector.y;
	
	return FinalVector;
}

private function switchStrafeToSprint():void
{
	if (!hInGameScript.isEnergyZero() && !isInAir())
	{
		aPlayer.CrossFade("run", 0.1);
		bInStrafe = false;
	}	
}

function SwipeMovementControl()
{	
	if (bDirectionQueueFlag)
	{
		if(!bInAir && directionQueue == SwipeDirection.Jump)
		{
			bJumpFlag = true;			
			bDirectionQueueFlag = false;
		}
		if (directionQueue == SwipeDirection.Duck && !bInDuck)
		{
			duckPlayer();			
			bDirectionQueueFlag = false;
		}
	}

	var direction = swipeLogic.getSwipeDirection();
	Debug.Log("direction = " + direction);
	if (direction != SwipeDirection.Null)
	{
	    bMouseReleased = false;
		
	    if (direction == SwipeDirection.Jump)
	    {
	        if(!bInAir)
	        {					
	            bJumpFlag = true;
	        }
			
	        if (bInAir)
	        {
	            bDirectionQueueFlag = true;
	            directionQueue = SwipeDirection.Jump;
	        }
	    }
	    
	    if (direction == SwipeDirection.Right && swipeControlsEnabled == true)
	    {
	        if (iLanePosition != 1) 
	        {
	            iLastLanePosition = iLanePosition;
	            iLanePosition++;				
	            strafePlayer(StrafeDirection.right);				
	        }
	    }
	    
	    if (direction == SwipeDirection.Left && swipeControlsEnabled == true)
	    {
	        if (iLanePosition != -1) 
	        {
	            iLastLanePosition = iLanePosition;
	            iLanePosition--;				
	            strafePlayer(StrafeDirection.left);				
	        }
	    }
	    
	    if (direction == SwipeDirection.Duck && bInDuck)
	    {
	        bDirectionQueueFlag = true;
	        directionQueue = SwipeDirection.Duck;
	    }
	    
	    if (direction == SwipeDirection.Duck && !bInAir && !bInDuck)
	    {
	        duckPlayer();
	    }
	    
	    if (direction == SwipeDirection.Duck && bInAir && !bInDuck)
	    {				
	        bDiveFlag = true;
	    }
	}
	
	if (Input.GetMouseButtonUp(0))
	{
		bMouseReleased = true;
	}
		
	if (!isPlayingDuck() && bInDuck == true)
	{
		hSoundManager.playSound(CharacterSounds.Footsteps);
		
		tPlayerRotation.localEulerAngles.z = 0;
		tBlobShadowPlane.localPosition.x = 0;
	
		bInDuck = false;
		tFrontCollider.localScale = v3BNCDefaultScale;
		tPlayerSidesCollider.localScale = v3BFCDefaultScale;
		
		if (bDiveFlag)
			return;
				
		aPlayer.CrossFadeQueued("run", 0.5, QueueMode.CompleteOthers);
	}
	
	if (Input.GetKeyDown(KeyCode.W) || Input.GetKeyDown(KeyCode.UpArrow))
	{
		if(!bInAir)
		{					
			bJumpFlag = true;
		}
		if (bInAir)
		{
			bDirectionQueueFlag = true;
			directionQueue = SwipeDirection.Jump;
		}
	}
	else if (Input.GetKeyDown(KeyCode.D) || Input.GetKeyDown(KeyCode.RightArrow))
	{
		if (iLanePosition != 1) 
		{
			iLastLanePosition = iLanePosition;
			iLanePosition++;			
			strafePlayer(StrafeDirection.right);			
		}
	}
	else if (Input.GetKeyDown(KeyCode.A) || Input.GetKeyDown(KeyCode.LeftArrow))
	{
		if (iLanePosition != -1) 
		{
			iLastLanePosition = iLanePosition;
			iLanePosition--;			
			strafePlayer(StrafeDirection.left);			
		}
	}
	else if ( (Input.GetKeyDown(KeyCode.S) || Input.GetKeyDown(KeyCode.DownArrow)) && bInDuck)
	{
		bDirectionQueueFlag = true;
		directionQueue = SwipeDirection.Duck;
	}
	else if ((Input.GetKeyDown(KeyCode.S) || Input.GetKeyDown(KeyCode.DownArrow)) && !bInAir && !bInDuck)
	{
		duckPlayer();
	}
	else if ((Input.GetKeyDown(KeyCode.S) || Input.GetKeyDown(KeyCode.DownArrow)) && bInAir && !bInDuck)
	{
		bDiveFlag = true;
	}	
}

function duckPlayer()
{
	bInDuck = true;
	hSoundManager.stopSound(CharacterSounds.Footsteps);
	
	aPlayer["slide"].speed = 1.4;
	aPlayer.CrossFade("slide", 0.1);
	
	tFrontCollider.localScale = v3BNCDefaultScale/2;
	tPlayerSidesCollider.localScale = v3BFCDefaultScale/2;
}

function isPlayingDuck():boolean
{
	if (hInGameScript.isEnergyZero())
		return false;
	
	if (aPlayer.IsPlaying("slide"))
		return true;
	else
		return false;
}

function strafePlayer(strafeDirection : StrafeDirection)
{
	if (isInAir())
	{	
		aPlayer[strafeDirection.ToString()].speed = 2;
		aPlayer.Play(strafeDirection.ToString());
		aPlayer.CrossFade("glide", 0.5);
	}
	else if (aPlayer.IsPlaying(strafeDirection.ToString()))
	{
		aPlayer.Stop(strafeDirection.ToString());		
		aPlayer[strafeDirection.ToString()].speed = 1.75;
		aPlayer.CrossFade(strafeDirection.ToString(),0.01);		
		bInStrafe = true;
	}
	else
	{
		aPlayer[strafeDirection.ToString()].speed = 1.75;
		aPlayer.CrossFade(strafeDirection.ToString(),0.01);		
		bInStrafe = true;
	}
}

public function getCurrentMileage():float { return fCurrentMileage; }
public function getCurrentForwardSpeed():float { return fCurrentForwardSpeed; }
public function getCurrentLane():int { return iLanePosition; }
public function getCurrentPlayerRotation():float { return tCurrentAngle; }
public function getCurrentWalkSpeed():float { return fCurrentWalkSpeed; }
public function isInAir():boolean
{
	if (bInAir || bJumpFlag || bInJump || bDiveFlag)
		return true;
	else
		return false;
}

public function getCurrentDistanceOnPath(){return fCurrentDistanceOnPath;}

public function setCurrentDistanceOnPath(iValue:float) { fCurrentDistanceOnPath = iValue; }
public function setPitFallLerpValue(iValue:float) { fPitFallLerpValue = iValue; }
public function setPitFallForwardSpeed(iValue:float) { fPitFallForwardSpeed = iValue; }

public function setDeathAnimStartTime(iValue:int){iDeathAnimStartTime = iValue;}
public function setCurrentWalkSpeed(fValue: float){fCurrentWalkSpeed = fValue;}

/*
*	FUNCTION: Turn player animations On or Off
*/
public function togglePlayerAnimation(bValue:boolean):void { aPlayer.enabled = bValue; }