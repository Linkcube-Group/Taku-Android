#pragma strict


private var tEnemy : Transform;
private var tPlayer : Transform;

private var iEnemyState : int = 0;
private var fDeathRotation : float = 0.0;
private var fCosLerp : float = 0.0;

private var hInGameScript : InGameScript;
private var hControllerScript : ControllerScript;
private var hSoundManager : SoundManager;

private var fEnemyPosition : float = 0.0;
private var fEnemyPositionX : float = -5;
private var fEnemyPositionY : float = 0;
private var fStumbleStartTime : float;
private var fChaseTime : float = 5;

private var aEnemAnim : Animation;

private var bShowUp : boolean;
private var fHideTime : float;
private var fShowTime : float;
private var iCurrentPlayerLane : int;
private var fCurrentDistanceOnPath : float;
private var fDistanceToPlayer : float;
private var fLocation : float;
private var fShowTimeLine : float = 0.0;

function Start()
{	
	tPlayer = GameObject.Find("Player").transform;
	tEnemy = this.transform;
	aEnemAnim = this.transform.Find("Chaser").GetComponent(Animation) as Animation;
	
	hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
	hControllerScript = GameObject.Find("Player").GetComponent(ControllerScript) as ControllerScript;
	hSoundManager = GameObject.Find("SoundManager").GetComponent(SoundManager) as SoundManager;	
	
	aEnemAnim.Play("Run");
	bShowUp = false;
	fHideTime = 0.0;
	this.transform.position = tPlayer.position + Vector3(-100,0,0);
}

public function launchEnemy():void
{
    //iEnemyState = 2;
    aEnemAnim.Play("Run");
}

function OnGUI()
{
	//GUI.Box(Rect(0,0,300,100), ""+fDistanceToPlayer + "  " + fShowTime);
}

function FixedUpdate ()
{	
	if(hInGameScript.isGamePaused()==true)
		return;
	fShowTimeLine += Time.deltaTime;
	if(fShowTimeLine < 5.0 || hControllerScript.getCurrentWalkSpeed() < 100)
	{
		return;
	}
	if(!bShowUp)
	{
		fHideTime += Time.deltaTime;
		if(fHideTime >= 10){
		
		}
		
		var fRand : float = Random.Range(0, 1.0);
		if(fRand > 0.3 && fRand < 0.305)
		{
			bShowUp = true;
			aEnemAnim.Play("Run");
			Debug.Log(fRand);
			fShowTime = 0.0;
			fDistanceToPlayer = -0.11;
		}
		else{
			bShowUp = false;
		}
		this.transform.Find("Chaser").gameObject.SetActive(bShowUp);
	}
	
	if(bShowUp)
	{
		aEnemAnim.Play("Run");
		fDistanceToPlayer += (200 - hControllerScript.getCurrentWalkSpeed())*Time.deltaTime*0.002;
		this.transform.position = tPlayer.transform.position + Vector3(fDistanceToPlayer,0,0);
		fShowTime += Time.deltaTime;
		if(fDistanceToPlayer < -1)
		{
			bShowUp = false;
			this.transform.Find("Chaser").gameObject.SetActive(bShowUp);
		}
		else if(fShowTime > 5.0 || fDistanceToPlayer >= -0.1)
		{
			bShowUp = false;
			hInGameScript.showAddScore(-150);
		}
	}
		
		//tEnemy.position.x = Mathf.Lerp(tEnemy.position.x, (tPlayer.position.x - fEnemyPosition), Time.deltaTime*10);
			
		//if (!hControllerScript.isInAir())
		//	tEnemy.position.y = Mathf.Lerp(tEnemy.position.y, tPlayer.position.y + fEnemyPositionY, Time.deltaTime*8);
	
	/*
	if (iEnemyState < 4)
	{
		tEnemy.position.z = Mathf.Lerp(tEnemy.position.z, tPlayer.position.z, Time.deltaTime*10);
		tEnemy.localEulerAngles.y = -hControllerScript.getCurrentPlayerRotation();
	}
	
	if (iEnemyState == 1)
	{
		fCosLerp += (Time.deltaTime/10);
		fEnemyPosition = Mathf.Lerp(fEnemyPosition, fEnemyPositionX + 45, Mathf.Cos(fCosLerp)/1000);
		
		if (fCosLerp >= 0.7)
		{
			fCosLerp = 0.0;
			iEnemyState = 0;
			
			//hSoundManager.stopSound(EnemySounds.Siren);
		}
	}
	else if (iEnemyState == 2)
	{
		aEnemAnim.Play("Run");
		hSoundManager.playSound(EnemySounds.Siren);
		
		fCosLerp += (Time.deltaTime/4);
		fEnemyPosition = Mathf.Lerp(fEnemyPosition, fEnemyPositionX, Mathf.Cos(fCosLerp));
		
		if (fCosLerp >= 1.5)
		{
			fCosLerp = 0.0;
			iEnemyState = 3;
		}
	}
	else if (iEnemyState == 3)
	{
		if ( (Time.time - fStumbleStartTime)%60 >= fChaseTime)
			iEnemyState = 1;
	}
	
	else if (iEnemyState == 4)
	{	
		//tEnemy.localEulerAngles.y = 350;
		//hSoundManager.stopSound(EnemySounds);
		hSoundManager.stopSound(BackgroundSounds.Background);
		hSoundManager.playSound(EnemySounds.TiresSqueal);
		iEnemyState = 5;
	}
	else if (iEnemyState == 5)
	{
		fEnemyPosition = Mathf.Lerp(fEnemyPosition, fEnemyPositionX+10, Time.fixedDeltaTime*50);
		tEnemy.position.z = Mathf.Lerp(tEnemy.position.z, tPlayer.position.z + 20, Time.deltaTime*10);
		
		//tEnemy.localEulerAngles = Vector3.Lerp(tEnemy.localEulerAngles, Vector3(0,260,0), Time.deltaTime*10);
		aEnemAnim.Play("Attack");
		if (tEnemy.localEulerAngles.y <= 261)
			iEnemyState = 6;
	}
	else if (iEnemyState == 6)
	{
		//hSoundManager.stopSound(EnemySounds.Siren);
	}
	*/
}

public function processStumble():boolean
{
	/*if (isEnemyActive())
	{
		iEnemyState = 0;		
		return true;
	}
	else
	{
		fStumbleStartTime = Time.time;
		iEnemyState = 2;		
		return false;
	}*/
	return true;
}

public function playDeathAnimation():void { iEnemyState = 4; }
public function hideEnemy() { iEnemyState = 1; }

public function isEnemyActive():boolean
{
	return true;
	if (iEnemyState == 2 || iEnemyState == 3)
		return true;
	else
		return false;
}