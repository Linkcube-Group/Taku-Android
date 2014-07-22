#pragma strict

private var CurrentEnergy : int = 100;
private var iLevelScore : int = 0;

private var hMenuScript : MenuScript;
private var hControllerScript : ControllerScript;
private var hSoundManager : SoundManager;
private var hPowerupsMainController : PowerupsMainController;
private var hEnemyController : EnemyController;
private var hCameraController : CameraController;
private var hPreyScript : PreyScript;
private var hConfigScript : ConfigScript;

private var iPauseStatus : int = 0;
private var iDeathStatus : int = 0;
private var iMenuStatus : int;

private var bGameOver : boolean = false;
private var bGamePaused : boolean = false;

private var goPlayer : GameObject;
private var goScore : GameObject;
private var bShowScore : boolean;
private var fScoreTime : float;

function Start()
{
	Application.targetFrameRate = 60;
	
	RenderSettings.fog = true;
		
	hSoundManager = GameObject.Find("SoundManager").GetComponent(SoundManager) as SoundManager;
	hMenuScript = GameObject.Find("MenuGroup").GetComponent(MenuScript) as MenuScript;
	hControllerScript = this.GetComponent(ControllerScript) as ControllerScript;
	hPowerupsMainController = this.GetComponent(PowerupsMainController) as PowerupsMainController;
	hCameraController = GameObject.Find("Main Camera").GetComponent(CameraController) as CameraController;
	hEnemyController = this.GetComponent(EnemyController) as EnemyController;
	hPreyScript = GameObject.Find("Prey").GetComponent(PreyScript) as PreyScript;
	hConfigScript = GameObject.Find("Player").GetComponent(ConfigScript) as ConfigScript;
	goScore = GameObject.Find("Score");
	goPlayer = GameObject.Find("Player");
	fScoreTime = 0.0;

	CurrentEnergy = 100;
	iPauseStatus = 0;
	iMenuStatus = 1;
	
	bGameOver = false;
	bGamePaused = true;
	
	bShowScore = false;
	goScore.SetActive(false);
}

function Update()
{	
	var fDeltaTime : float = Time.deltaTime;
	if(bShowScore){
		fScoreTime += fDeltaTime;
		if(fScoreTime > 1.0){
			bShowScore = false;
			fScoreTime = 0.0;
			goScore.SetActive(false);
		}
	}
	
	if (iMenuStatus == 0)
		;
	else if (iMenuStatus == 1)
	{
		hMenuScript.setMenuScriptStatus(true);
				
		bGamePaused = true;
		iMenuStatus = 2;
	}
	
	if(iPauseStatus==0)
		;
	else if(iPauseStatus==1)
	{	
		hMenuScript.setMenuScriptStatus(true);		
		hMenuScript.displayPauseMenu();
		
		iPauseStatus = 2;
	}
	else if(iPauseStatus==3)
	{		
		bGamePaused = false;		
		hMenuScript.setMenuScriptStatus(false);
		
		iPauseStatus = 0;
	}
	
	if(iDeathStatus==0)
		;
	else if(iDeathStatus==1)
	{
		hPowerupsMainController.deactivateAllPowerups();
		
		iDeathStatus = 2;
	}
	else if (iDeathStatus == 2)
	{		
		hMenuScript.setMenuScriptStatus(true);
		hMenuScript.displayGameOverMenu();
		
		iDeathStatus = 0;
	}
	
	if (bGamePaused == true)
		return;
	
}

function FixedUpdate(){
	goScore.transform.position = goPlayer.transform.position + Vector3(0, 20 + 40 * fScoreTime, 0);
}

public function pauseGame()
{
	hControllerScript.togglePlayerAnimation(false);
	bGamePaused = true;
	iPauseStatus = 1;
	
	hSoundManager.stopAllSounds();
}

public function launchGame()
{	
	iMenuStatus = 0;
	bGamePaused = false;	
	hMenuScript.showHUDElements();	
	hControllerScript.launchGame();
	hCameraController.launchGame();
}

public function setupDeathMenu()
{	
	bGameOver = true;
	bGamePaused = true;	
	iDeathStatus = 1;
}

public function processClicksPauseMenu(index : PauseMenuEvents)
{
	if (index == PauseMenuEvents.MainMenu)
	{	
		Application.LoadLevel(0);
		
		hMenuScript.ShowMenu(MenuIDs.MainMenu);
		iMenuStatus = 1;
	}
	else if (index == PauseMenuEvents.Resume)
	{
		hMenuScript.showHUDElements();
		hControllerScript.togglePlayerAnimation(true);
		iPauseStatus = 3;
	}
}

public function procesClicksDeathMenu(index : GameOverMenuEvents)
{
	if (index == GameOverMenuEvents.Play)
	{
		hMenuScript.showHUDElements();
		Application.LoadLevel(0);
		launchGame();
	}
	else if (index == GameOverMenuEvents.Back)
	{
		Application.LoadLevel(0);
		
		hMenuScript.ShowMenu(MenuIDs.MainMenu);
		iMenuStatus = 1;
	}
}

public function collidedWithObstacle()
{
	showAddScore(-hConfigScript.ScoreOnBarrier);
    hPreyScript.AddScore(-hConfigScript.ScoreOnBarrier);
	//decrementEnergy(100);
    hCameraController.setCameraShakeImpulseValue(5);
    hControllerScript.MultiCurrSpeed(0.8f);
}

function OnApplicationPause (pause : boolean) : void
{
	Debug.Log("Application Paused : "+pause);
	if(Application.isEditor==false)
	{
		if(bGamePaused==false&&pause==false)
		{
			pauseGame();
		}
	}	
}

//显示分数
public function showAddScore(iScore:int)
{
	var txtScore : TextMesh = goScore.GetComponent(TextMesh) as TextMesh;
	if(iScore > 0){
		txtScore.text = "+" + iScore;
	}
	else if(iScore < 0){
		txtScore.text = "" + iScore;
	}
	else{
		txtScore.text = "";
	}
	bShowScore = true;
	goScore.SetActive(false);
	fScoreTime = 0.0;
	goScore.SetActive(true);
}

public function isGamePaused() { return bGamePaused; }

public function getLevelScore() { return iLevelScore; }
public function incrementLevelScore(iValue:int) { iLevelScore += iValue; }

public function getCurrentEnergy() { return CurrentEnergy; }
public function isEnergyZero():boolean { return (CurrentEnergy <= 0 ? true : false); }
public function zeroEnergy(){CurrentEnergy = 0;}
public function decrementEnergy(iValue:int) { CurrentEnergy -= iValue; }
