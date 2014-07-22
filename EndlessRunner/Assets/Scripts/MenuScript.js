#pragma strict

public enum MenuIDs
{
	MainMenu = 0, 
	PauseMenu = 1,
	GameOverMenu = 2,
	InstructionsMenu = 3
}

public enum PauseMenuEvents
{
	Resume = 0,
	MainMenu = 1
}

public enum GameOverMenuEvents
{
	Back = 0,
	Play = 1
}

private var tMenuGroup : Transform;
private var CurrentMenu:int = -1;

private var aspectRatio : float = 0.0;
private var fResolutionFactor : float;
public function getResolutionFactor():float { return fResolutionFactor; }

private var HUDCamera : Camera;

private var hControllerScript : ControllerScript;
private var hSoundManagerScript : SoundManager;
private var hInGameScript : InGameScript;
private var hPreyScript : PreyScript;

private var tMenuTransforms : Transform[];
//Main Menu
private var tMainMenuButtons:Transform[];
private var iMainMenuButtonsCount:int = 2;
//Pause Menu
private var tPauseButtons:Transform[];
private var iPauseButtonsCount:int = 2;
//Game Over Menu
private var tGameOverButtons:Transform[];
private var iGameOverButtonsCount:int = 2;
//instructions menu
private var tInstructionsButtons : Transform[];
private var iInstructionsButtonsCount : int = 4;
private var mrSwipeControls : MeshRenderer;
private var mrGyroControls : MeshRenderer;
private var mrEasyControls : MeshRenderer;

//resume game countdown
private var iResumeGameState : int = 0;
private var iResumeGameStartTime: int = 0;
private var tmPauseCountdown : TextMesh;


function Start ()
{  	
	HUDCamera = GameObject.Find("HUDMainGroup/HUDCamera").GetComponent(Camera) as Camera;
	hControllerScript = GameObject.Find("Player").GetComponent(ControllerScript) as ControllerScript;
	hSoundManagerScript = GameObject.Find("SoundManager").GetComponent(SoundManager) as SoundManager;
	hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
	hPreyScript = GameObject.Find("Prey").GetComponent(PreyScript) as PreyScript;
	
	aspectRatio = ( (Screen.height * 1.0)/(Screen.width * 1.0) - 1.77);	
	fResolutionFactor = (43.0 * (aspectRatio));
		
	tMenuGroup = GameObject.Find("MenuGroup").transform;
	tMenuTransforms = new Transform[MenuIDs.GetValues(MenuIDs).Length];
	
	//main menu initialization
	tMenuTransforms[MenuIDs.MainMenu] = tMenuGroup.Find("MainMenu").GetComponent(Transform) as Transform;
	tMainMenuButtons = new Transform[iMainMenuButtonsCount];
	tMainMenuButtons[0] = tMenuTransforms[MenuIDs.MainMenu].Find("Buttons/Button_TapToPlay");
	tMainMenuButtons[1] = tMenuTransforms[MenuIDs.MainMenu].Find("Buttons/Button_Instructions");
	
	//pause menu initialization
	tMenuTransforms[MenuIDs.PauseMenu] = tMenuGroup.Find("PauseMenu").GetComponent(Transform) as Transform;
	tPauseButtons = new Transform[iPauseButtonsCount];
	tPauseButtons[0] = tMenuTransforms[MenuIDs.PauseMenu].Find("Buttons/Button_Back");
	tPauseButtons[1] = tMenuTransforms[MenuIDs.PauseMenu].Find("Buttons/Button_Resume");
	
	//game over menu initialization
	tMenuTransforms[MenuIDs.GameOverMenu] = tMenuGroup.Find("GameOver").GetComponent(Transform) as Transform;
	tGameOverButtons = new Transform[iGameOverButtonsCount];
	tGameOverButtons[0] = tMenuTransforms[MenuIDs.GameOverMenu].Find("Buttons/Button_Back");
	tGameOverButtons[1] = tMenuTransforms[MenuIDs.GameOverMenu].Find("Buttons/Button_Play");
	
	//instructions menu initialization
	tMenuTransforms[MenuIDs.InstructionsMenu] = tMenuGroup.Find("Instructions").GetComponent(Transform) as Transform;
	tInstructionsButtons = new Transform[iInstructionsButtonsCount];
	tInstructionsButtons[0] = tMenuTransforms[MenuIDs.InstructionsMenu].Find("Buttons/Button_Back");
	tInstructionsButtons[1] = tMenuTransforms[MenuIDs.InstructionsMenu].Find("Buttons/Button_Swipe/RadioButton_Background").GetComponent(Transform) as Transform;
	tInstructionsButtons[2] = tMenuTransforms[MenuIDs.InstructionsMenu].Find("Buttons/Button_Gyro/RadioButton_Background").GetComponent(Transform) as Transform;
	tInstructionsButtons[3] = tMenuTransforms[MenuIDs.InstructionsMenu].Find("Buttons/Button_Easy/RadioButton_Background").GetComponent(Transform) as Transform;
	
	mrSwipeControls = tMenuTransforms[MenuIDs.InstructionsMenu].Find("Buttons/Button_Swipe/RadioButton_Foreground").GetComponent(MeshRenderer) as MeshRenderer;
	mrGyroControls = tMenuTransforms[MenuIDs.InstructionsMenu].Find("Buttons/Button_Gyro/RadioButton_Foreground").GetComponent(MeshRenderer) as MeshRenderer;
	mrEasyControls = tMenuTransforms[MenuIDs.InstructionsMenu].Find("Buttons/Button_Easy/RadioButton_Foreground").GetComponent(MeshRenderer) as MeshRenderer;
	///////HUD//////
	(GameObject.Find("HUDMainGroup/HUDPauseCounter").GetComponent(MeshRenderer) as MeshRenderer).enabled = false;
	
	//(GameObject.Find("HUDMainGroup/HUDGroup/HUDCurrencyGroup").GetComponent(Transform) as Transform).transform.Translate(-fResolutionFactor,0,0);
	//(GameObject.Find("HUDMainGroup/HUDGroup/HUDScoreGroup").GetComponent(Transform) as Transform).transform.Translate(-fResolutionFactor,0,0);
	(GameObject.Find("HUDMainGroup/HUDGroup/HUDPause").GetComponent(Transform) as Transform).transform.Translate(fResolutionFactor,0,0);
		
	ShowMenu(MenuIDs.MainMenu);

	mrSwipeControls.enabled = false;
	mrGyroControls.enabled = false;
	mrEasyControls.enabled = true;
}

public function displayPauseMenu()
{
	ShowMenu(MenuIDs.PauseMenu);
}

public function displayGameOverMenu()
{	
	ShowMenu(MenuIDs.GameOverMenu);	
}

function FixedUpdate()
{	
	listenerClicks();
	
	if (iResumeGameState == 0)
		;
	else if (iResumeGameState == 1)
	{
		tmPauseCountdown = GameObject.Find("HUDMainGroup/HUDPauseCounter").GetComponent("TextMesh") as TextMesh;
		(GameObject.Find("HUDMainGroup/HUDPauseCounter").GetComponent(MeshRenderer) as MeshRenderer).enabled = true;
		
		iResumeGameStartTime = Time.time;		
		iResumeGameState = 2;
	}
	else if (iResumeGameState == 2)
	{
		tmPauseCountdown.text = Mathf.Round(4 - (Time.time - iResumeGameStartTime)).ToString();
		
		if ( (Time.time - iResumeGameStartTime) >= 3)
		{
			tmPauseCountdown.text = String.Empty;
			hInGameScript.processClicksPauseMenu(PauseMenuEvents.Resume);
			iResumeGameStartTime = 0;			
			iResumeGameState = 0;
		}
	}	
}

private var hit : RaycastHit;
private function listenerClicks()
{
	if (Input.GetMouseButton(0))
	{	
		if (Physics.Raycast(HUDCamera.ScreenPointToRay(Input.mousePosition),hit))
		{
			if (CurrentMenu == MenuIDs.MainMenu)
				handlerMainMenu(hit.transform);
			else if (CurrentMenu == MenuIDs.PauseMenu)
				handlerPauseMenu(hit.transform);
			else if (CurrentMenu == MenuIDs.GameOverMenu)
				handlerGameOverMenu(hit.transform);
			else if (CurrentMenu == MenuIDs.InstructionsMenu)
				handlerInstructionsMenu(hit.transform);
		}
	}
}

private function handlerMainMenu(buttonTransform : Transform)
{		
	if (tMainMenuButtons[0] == buttonTransform)
	{
		CloseMenu(MenuIDs.MainMenu);
		
		hInGameScript.launchGame();
		setMenuScriptStatus(false);
	}
	else if (tMainMenuButtons[1] == buttonTransform)
	{
		CloseMenu(MenuIDs.MainMenu);
		ShowMenu(MenuIDs.InstructionsMenu);
	}	
}

private function handlerPauseMenu(buttonTransform : Transform)
{
	if (tPauseButtons[0] == buttonTransform)
	{
		hInGameScript.processClicksPauseMenu(PauseMenuEvents.MainMenu);
		
		CloseMenu(MenuIDs.PauseMenu);		
		ShowMenu(MenuIDs.MainMenu);
	}
	else if (tPauseButtons[1] == buttonTransform)
	{
		CloseMenu(MenuIDs.PauseMenu);
		iResumeGameState = 1;
	}
}

private function handlerGameOverMenu(buttonTransform : Transform)
{
	if (tGameOverButtons[0] == buttonTransform)
	{
		hInGameScript.procesClicksDeathMenu(GameOverMenuEvents.Back);
		CloseMenu(MenuIDs.GameOverMenu);
		ShowMenu(MenuIDs.MainMenu);
		CurrentMenu = MenuIDs.MainMenu;
	}
	else if (tGameOverButtons[1] == buttonTransform)
	{
		hInGameScript.procesClicksDeathMenu(GameOverMenuEvents.Play);		
		CloseMenu(CurrentMenu);
	}	
}

private function handlerInstructionsMenu(buttonTransform : Transform)
{
	if (tInstructionsButtons[0] == buttonTransform)
	{
		CloseMenu(MenuIDs.InstructionsMenu);
		ShowMenu(MenuIDs.MainMenu);
		CurrentMenu = MenuIDs.MainMenu;
	}
	else if (tInstructionsButtons[1] == buttonTransform)//Normal
	{		
		if (mrSwipeControls.enabled == false)
		{
			mrSwipeControls.enabled = true;
			mrGyroControls.enabled = false;
			mrEasyControls.enabled = false;
			hPreyScript.SetLevel(PreyLevelEnum.pleNormal);
			//hControllerScript.toggleSwipeControls(true);
		}		
	}
	else if (tInstructionsButtons[2] == buttonTransform) //Hard
	{		
		if (mrGyroControls.enabled == false)
		{
			mrGyroControls.enabled = true;
			mrSwipeControls.enabled = false;
			mrEasyControls.enabled = false;
			hPreyScript.SetLevel(PreyLevelEnum.pleHard);
			//hControllerScript.toggleSwipeControls(false);
		}		
	}
	else if (tInstructionsButtons[3] == buttonTransform) //Easy
	{		
	    if (mrEasyControls.enabled == false)
	    {
	        mrSwipeControls.enabled = false;
	        mrGyroControls.enabled = false;
	        mrEasyControls.enabled = true;
	        hPreyScript.SetLevel(PreyLevelEnum.pleEasy);
	        //hControllerScript.toggleSwipeControls(false);
	    }		
	}
}

public function ShowMenu(index : int)
{
	if (MenuIDs.MainMenu == index)
	{		
		tMenuTransforms[MenuIDs.MainMenu].position.y = 0;
	}
	else if (MenuIDs.PauseMenu == index)
	{
		tMenuTransforms[MenuIDs.PauseMenu].position.y = 0;
	}
	else if (MenuIDs.GameOverMenu == index)
	{
		tMenuTransforms[MenuIDs.GameOverMenu].position.y = 0;
	}
	else if (MenuIDs.InstructionsMenu == index)
	{
		tMenuTransforms[MenuIDs.InstructionsMenu].position.y = 0;
	}
	
	CurrentMenu = index;
	hideHUDElements();	//hide the HUD
	hSoundManagerScript.playSound(MenuSounds.ButtonTap);
}

private function CloseMenu(index : int)
{
	if (index == MenuIDs.MainMenu)
	{	
	    var eLevel : PreyLevelEnum = hPreyScript.GetLevel();
	    mrSwipeControls.enabled = eLevel == PreyLevelEnum.pleNormal;
	    mrGyroControls.enabled = eLevel == PreyLevelEnum.pleHard;
	    mrEasyControls.enabled = eLevel == PreyLevelEnum.pleEasy;
		//if ( hControllerScript.isSwipeControlEnabled() )
		//{
		//	mrSwipeControls.enabled = true;
		//	mrGyroControls.enabled = false;
		//}
		//else
		//{
		//	mrSwipeControls.enabled = false;
		//	mrGyroControls.enabled = true;
		//}
		
		tMenuTransforms[MenuIDs.MainMenu].position.y = 1000;
	}
	else if (index == MenuIDs.PauseMenu)
	{	
		tMenuTransforms[MenuIDs.PauseMenu].position.y = 1000;
	}
	else if (index == MenuIDs.GameOverMenu)
	{
		tMenuTransforms[MenuIDs.GameOverMenu].position.y = 1000;
	}
	else if (MenuIDs.InstructionsMenu == index)
	{
		tMenuTransforms[MenuIDs.InstructionsMenu].position.y = 1000;
	}
	
	CurrentMenu = -1;
}

public function hideHUDElements()
{
	(GameObject.Find("HUDMainGroup/HUDGroup").GetComponent(Transform) as Transform).position.y = 1000;
}

public function showHUDElements()
{	
	(GameObject.Find("HUDMainGroup/HUDGroup").GetComponent(Transform) as Transform).position.y = 0;
}

public function setMenuScriptStatus(flag:boolean)
{
	if (flag != this.enabled)
		this.enabled = flag;
}