#pragma strict

private var tPlayer : Transform;	
private var tCamera : Transform;
private var tPlayerMesh : Transform;

private var tChaserMesh : Transform;

private var hInGameScript : InGameScript;
private var hControllerScript : ControllerScript;

private var fCameraLerpValue : float;

private var fCameraDistance : float = 30;
private var v3CamDirection : Vector3;
private var fCurrentCamDir : float = 90.0;
private var fCameraRotationX : float = 0.0;
private var fCameraRotationZ : float = 0.0;
private var fCameraPositionY : float = 35;
private var fCameraPositionX : float = -10;

private var iCameraState : int = 0;
private var fCamShakeImpulse : float = 0.0;

function Start()
{
	tCamera = camera.main.transform;
	tPlayerMesh = GameObject.Find("PlayerRotation/PlayerMesh").transform;
	tPlayer = GameObject.Find("Player").transform;
	tChaserMesh = GameObject.Find("Enemy/Chaser").transform;
	
	hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
	hControllerScript = GameObject.Find("Player").GetComponent(ControllerScript) as ControllerScript;
			
	fCameraRotationX = tCamera.localEulerAngles.x;
	fCameraRotationZ = tCamera.localEulerAngles.z;
	
	iCameraState = 0;	
	fCamShakeImpulse = 0.0;
}

public function launchGame()
{	
	iCameraState = 1;
}

function Update ()
{
	if(hInGameScript.isGamePaused()==true)		
		return;
	
	// When player's energy is empty, the game is over.
	
	if (hInGameScript.isEnergyZero())
		iCameraState = 2;
}

function FixedUpdate()
{
	CameraMain();
}

//Initial camera
private function CameraMain()
{
	fCameraDistance = Mathf.Lerp(fCameraDistance,fCameraLerpValue,Time.deltaTime*1.5);
	fCurrentCamDir = Mathf.Lerp(fCurrentCamDir,-hControllerScript.getCurrentPlayerRotation()+90.0,Time.deltaTime*4.0);
	tCamera.localEulerAngles = Vector3(fCameraRotationX, fCurrentCamDir, fCameraRotationZ);
	v3CamDirection = rotateAlongY(Vector3(-1,0,0),-hControllerScript.getCurrentPlayerRotation());
		
	if (iCameraState == 1)
	{
		fCameraLerpValue = 35;
		tCamera.position.x = tPlayerMesh.position.x/*tChaserMesh.position.x*/ + v3CamDirection.x*fCameraDistance + fCameraPositionX;
		tCamera.position.z = Mathf.Lerp(tCamera.position.z, (tPlayerMesh.position.z/*tChaserMesh.position.z*/ + v3CamDirection.z*fCameraDistance), Time.deltaTime*50);		
		tCamera.position.y = Mathf.Lerp(tCamera.position.y, tPlayerMesh.position.y/*tChaserMesh.position.y*/ + fCameraPositionY, Time.deltaTime*70);		
	}	
	else if(iCameraState == 2)
	{	
		fCameraLerpValue = 60;
		tCamera.position = tPlayerMesh.position/*tChaserMesh.position*/ + v3CamDirection*fCameraDistance;
		tCamera.position.y+= 30.0;
		
		tCamera.localEulerAngles.x = Mathf.Lerp(tCamera.localEulerAngles.x, 40, Time.deltaTime*25);
	}
	
	if(fCamShakeImpulse>0.0)
		shakeCamera();
}

private function rotateAlongY(inputVector : Vector3, angletoRotate : float):Vector3
{
	var FinalVector : Vector3 = Vector3.zero;
	angletoRotate = angletoRotate/57.3;
	FinalVector.x = Mathf.Cos(-angletoRotate) * inputVector.x - Mathf.Sin(-angletoRotate) * inputVector.z;
	FinalVector.z = Mathf.Sin(-angletoRotate) * inputVector.x + Mathf.Cos(-angletoRotate) * inputVector.z;
	
	return FinalVector;
}

public function setCameraShakeImpulseValue(iShakeValue : int)
{
	if(iShakeValue==1)
		fCamShakeImpulse = 1.0;
	else if(iShakeValue==2)
		fCamShakeImpulse = 2.0;
	else if(iShakeValue==3)
		fCamShakeImpulse = 1.3;
	else if(iShakeValue==4)
		fCamShakeImpulse = 1.5;
	else if(iShakeValue==5)
		fCamShakeImpulse = 1.3;
}

private function shakeCamera()
{
	tCamera.position.y+=Random.Range(-fCamShakeImpulse,fCamShakeImpulse);
	tCamera.position.z+=Random.Range(-fCamShakeImpulse,fCamShakeImpulse);
	fCamShakeImpulse-=Time.deltaTime * fCamShakeImpulse*4.0;
	if(fCamShakeImpulse<0.01)
		fCamShakeImpulse = 0.0;
}
