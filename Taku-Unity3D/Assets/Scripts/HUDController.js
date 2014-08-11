#pragma strict

private var tPlayer : Transform;

private var hInGameScript : InGameScript;
private var hPowerupsMainController : PowerupsMainController;
private var hControllerScript : ControllerScript;

private var tmHUDCurrencyText : TextMesh;
private var tmHUDScoreText : TextMesh;
private var tHUDScoreContainerMid : Transform;
private var tHUDCurrencyContainerMid : Transform;

private var fPreviousDistance : float = 0.0;
private var fCurrentDistance : float = 0.0;
private var fCurrentTime : float = 0.0;
private var fPreviousTime : float = 0.0;

private var iDivisorScore : int;
private var iDivisorCurrency : int;
private var iDivisorMultiplier : int;

function Start()
{		
	tPlayer = this.transform;
	hInGameScript = this.GetComponent(InGameScript) as InGameScript;	
	hControllerScript = this.GetComponent(ControllerScript) as ControllerScript;
	hPowerupsMainController = this.GetComponent(PowerupsMainController) as PowerupsMainController;

	//tmHUDCurrencyText = GameObject.Find("HUDMainGroup/HUDGroup/HUDCurrencyGroup/HUD_Currency_Text").GetComponent("TextMesh") as TextMesh;
	//tmHUDScoreText = GameObject.Find("HUDMainGroup/HUDGroup/HUDScoreGroup/HUD_Score_Text").GetComponent("TextMesh") as TextMesh;
		
	//tHUDScoreContainerMid = GameObject.Find("HUDMainGroup/HUDGroup/HUDScoreGroup/HUD_Score_BG").GetComponent(Transform) as Transform;	//	HUD Score Container	
	//tHUDCurrencyContainerMid = GameObject.Find("HUDMainGroup/HUDGroup/HUDCurrencyGroup/HUD_Currency_BG").GetComponent(Transform) as Transform;	//	HUD Currency Container
	
	fCurrentTime = Time.time;
	fPreviousTime = Time.time;
	
	fPreviousDistance = 0;
	fCurrentDistance = 0;
	fCurrentTime = 0;
	fPreviousTime = 0;
	
	iDivisorScore = 10;
	iDivisorCurrency = 10;
	iDivisorMultiplier = 10;
	
	//tHUDScoreContainerMid.localScale.z = 0.45;
	//tHUDCurrencyContainerMid.localScale.z = 0.45;
	
	InvokeRepeating("resizeDigitContainer", 1, 0.5);
	resizeDigitContainer();
}

function FixedUpdate()
{	
	if(hInGameScript.isGamePaused()==true)
		return;

	UpdateHUDStats();
	
}

private function UpdateHUDStats()
{	
	yield WaitForEndOfFrame();
	
	if ( (fCurrentTime - fPreviousTime) >= 0.1 )
	{
		var iCurrentFrameScore = (fCurrentDistance - fPreviousDistance);
		hInGameScript.incrementLevelScore(iCurrentFrameScore);
		
		fPreviousDistance = fCurrentDistance;
		fCurrentDistance = hControllerScript.getCurrentMileage();
		
		fPreviousTime = fCurrentTime;
		fCurrentTime = Time.time;
	}
	else
	{
		fCurrentDistance = hControllerScript.getCurrentMileage();	//get the current mileage
		fCurrentTime = Time.time;
	}	
		
	//tmHUDCurrencyText.text = hPowerupsMainController.getCurrencyUnits().ToString();
	//tmHUDScoreText.text = "Speed:" + hControllerScript.GetCurrSpeed().ToString("0.00");//hInGameScript.getLevelScore().ToString();				//update Score on HUD
}

private function resizeDigitContainer()
{
	var fScore : int = hInGameScript.getLevelScore();
	var fCurrency : int = hPowerupsMainController.getCurrencyUnits();
		
	if ( (fScore / iDivisorScore) >= 1 )
	{
		//tHUDScoreContainerMid.localScale.z += 0.4;
		iDivisorScore *= 10;
	}
	
	if ( (fCurrency / iDivisorCurrency) >= 1 )
	{
		//tHUDCurrencyContainerMid.localScale.z += 0.4;
		iDivisorCurrency *= 10;
	}
}