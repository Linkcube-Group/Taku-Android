#pragma strict

public enum PreyLevelEnum {pleEasy = 0, pleNormal = 1, pleHard = 2};
public var oRunnerSkin : GUISkin;

private var tPreyTransform : Transform;
private var tPlayerMesh : Transform;
private var tPlayer : Transform;
private var hControllerScript : ControllerScript;
private var hCheckPointsMain : CheckPointsMain;
private var hInGameScript : InGameScript;
private var hPowerupsMainController : PowerupsMainController;
private var hConfigScript : ConfigScript;

private var aPlayer : Animation;
private var aPreyAnim : Animation;

private var arrSpeedLimits : float[] = [900f, 1100f, 1200f];
public var eLevel : PreyLevelEnum;
private var fSpeedLimit : float;
private var fRelativeSpeed : float;
private var fPlayerSpeed : float;
private var fDistanceToPlayer : float;
private var fCurrentDistanceOnPath : float;
private var fLocation : float;
private var fChangeLaneDistance : float = 700.0f;
private var fRandom : float = 0;
private var iLaneRandom : float = 0;
private var fLaneKeepTime : float;
private var arrLaneStayMinTime : float[] = [2.0f, 1.0f, 0.5f];
private var iLastPlayerLane : int = 0;
private var iCurrentPlayerLane : int;
private var fMinDistanceToPlayer : float = 20.0f;
private var bIsCaught : boolean;
private var bIsCatching : boolean = true;
private var fCaughtAnimTime : float = 0.0f;
private var nScore : int = 100;
private var nCatchTime : int;
private var fElapsedTime : float = 0.0f;

private var fStartZ : float = 0.0f;
private var fEndZ : float = 0.0f;
private var fChangeLaneTime : float = 0.0f;
private var iChangeDir : int = 0; //1-Left, 2-Right

function Start () {
    tPlayer = GameObject.Find("Player").transform;
    tPreyTransform = GameObject.Find("Prey").transform;
    tPlayerMesh = GameObject.Find("PlayerRotation/PlayerMesh").transform;
    hControllerScript = GameObject.Find("Player").GetComponent(ControllerScript) as ControllerScript;
    hCheckPointsMain = GameObject.Find("Player").GetComponent(CheckPointsMain) as CheckPointsMain;
    hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
    hPowerupsMainController = GameObject.Find("Player").GetComponent(PowerupsMainController) as PowerupsMainController;
    hConfigScript = GameObject.Find("Player").GetComponent(ConfigScript) as ConfigScript;

    aPlayer = GameObject.Find("Player").transform.Find("PlayerRotation/PlayerMesh/boy").GetComponent(Animation) as Animation;
    aPreyAnim = this.transform.Find("Prey_01").GetComponent(Animation) as Animation;
    fRelativeSpeed = 0;
    fDistanceToPlayer = 400.0f;
    fLaneKeepTime = 0.0f;
    //SetLevel(PreyLevelEnum.pleEasy);
    bIsCaught = false;
    aPreyAnim["Run"].speed = 1.0f;
    nCatchTime = hConfigScript.TimeToCatchNextPrey;
    aPreyAnim.Play("Idle");
}

function Update () {
	var fDeltaTime : float = Time.deltaTime;
    fElapsedTime += fDeltaTime;
    
    if(nCatchTime > 0){
        nCatchTime = hConfigScript.TimeToCatchNextPrey - Mathf.Floor(fElapsedTime);
        if(nCatchTime <= 0){
            nScore -= hConfigScript.ScoreLooseNextPrey; 
            bIsCatching = true;
        }
    }
    if(bIsCaught){ 
        if(fCaughtAnimTime < 1.0f){
            fCaughtAnimTime += Time.deltaTime;
            return;
        }else{
            fDistanceToPlayer = hConfigScript.DistanceOfNewPrey;//0;
            bIsCatching = true;
            bIsCaught = false;
        }

        return;
    }else{
        
    }
}

function FixedUpdate(){
    if(bIsCaught){
        fDistanceToPlayer = 0;
        return;
    }
    else{
        var fCurrentPlayerDistanceOnPath : float = hControllerScript.getCurrentDistanceOnPath();
        //iCurrentPlayerLane = hControllerScript.getCurrentLane();
        fCurrentDistanceOnPath = fCurrentPlayerDistanceOnPath + fDistanceToPlayer;
        fLocation = fCurrentDistanceOnPath / 3000.0f;
        if(fLocation < 1.0f){
            tPreyTransform.position = hCheckPointsMain.getCurrentWSPointBasedOnPercent(fLocation);//getNextWSPointBasedOnPercent(fLocation);
        }else{
            fLocation -= 1.0f;
            tPreyTransform.position = hCheckPointsMain.getNextWSPointBasedOnPercent(fLocation);
        }
        
        if(fChangeLaneTime < 1.0f && iLastPlayerLane != iLaneRandom) //正变道的过程中
        {
	        if(iLastPlayerLane == -1)
			{
				tPreyTransform.position.z += 15.0f;
			}
			else if(iLastPlayerLane == 1)
			{
				tPreyTransform.position.z -= 15.0f;
			}
			tPreyTransform.position.z = 
				Mathf.Lerp(tPreyTransform.position.z, tPreyTransform.position.z + (iLastPlayerLane - iLaneRandom) * 15.0f, fChangeLaneTime);
			fChangeLaneTime += Time.fixedDeltaTime;
		}
		else
		{
			if(iLaneRandom == -1)
			{
				tPreyTransform.position.z += 15.0f;
			}
			else if(iLaneRandom == 1)
			{
				tPreyTransform.position.z -= 15.0f;
			}
		}

        fPlayerSpeed = hControllerScript.getCurrentWalkSpeed();//getCurrentForwardSpeed();
        if(fPlayerSpeed > fSpeedLimit){
            fRelativeSpeed = fPlayerSpeed - fSpeedLimit;
            switch(eLevel){
                case PreyLevelEnum.pleEasy:
                    fDistanceToPlayer -= fRelativeSpeed * Time.deltaTime;
                    break;
                case PreyLevelEnum.pleNormal:
                    fDistanceToPlayer -= fRelativeSpeed * Time.deltaTime * 0.6f;
                    break;
                case PreyLevelEnum.pleHard:
                    fDistanceToPlayer -= fRelativeSpeed * Time.deltaTime * 0.4f;
                    break;
            }
        }
        else if(fPlayerSpeed < 50)
        {
        	if(fDistanceToPlayer < 600)
        	{
        		fRelativeSpeed = fPlayerSpeed - fSpeedLimit;
	            switch(eLevel){
	                case PreyLevelEnum.pleEasy:
	                    fDistanceToPlayer -= fRelativeSpeed * Time.deltaTime * 0.4;
	                    break;
	                case PreyLevelEnum.pleNormal:
	                    fDistanceToPlayer -= fRelativeSpeed * Time.deltaTime * 0.4 * 0.6f;
	                    break;
	                case PreyLevelEnum.pleHard:
	                    fDistanceToPlayer -= fRelativeSpeed * Time.deltaTime * 0.4 * 0.4f;
	                    break;
	            }
        	}
        }
        
        if(fDistanceToPlayer < fChangeLaneDistance){
	        if(fLaneKeepTime > arrLaneStayMinTime[eLevel]){
	            fLaneKeepTime = 0.0f;
	            fRandom = Random.Range(0,4);
	            iChangeDir = 0;
	            iLastPlayerLane = iLaneRandom;
	            if(iLaneRandom == 0){
	                if(fRandom <= 1) // 0 -> -1 left
	                {
	                	iLaneRandom = -1;
	                	iChangeDir = 1;
	                }
	                else // 0 -> 1 right
	                {
	                	iLaneRandom = 1;
	                	iChangeDir = 2;
	                }
	                fChangeLaneTime = 0.0f;
	            }else{
	            	if(fRandom < 1)
	            	{
	            		if(iLaneRandom == -1)  //-1 -> 0 right
	            		{
	            			iChangeDir = 2;
	            		}
	            		else // 1 -> 0 left
	            		{
	            			iChangeDir = 1;
	            		}
	            		iLaneRandom = 0;
	            		fChangeLaneTime = 0.0f;
	            	}
	            	else
	            	{	            		
	            		iChangeDir = 0;
	            		fChangeLaneTime = 0.0f;
	            	}
	            }
	        }
	        fLaneKeepTime += Time.deltaTime;            
	    }

        if(iCurrentPlayerLane == iLaneRandom){
            if(fDistanceToPlayer < fMinDistanceToPlayer){
                bIsCaught = true;
                bIsCatching = false;
                fCaughtAnimTime = 0.0f;
                aPreyAnim.Play("BackwardDeath");
                var nAddScore : int = 0;
                nAddScore += hConfigScript.ScoreCatchPrey;
                if(fRelativeSpeed > hConfigScript.SpeedBurstThanPrey)
                {
                	nAddScore += hConfigScript.ScoreBurst;
                }
                hInGameScript.showAddScore(nAddScore);
                nScore += nAddScore;
                nCatchTime = hConfigScript.TimeToCatchNextPrey;
                fElapsedTime = 0.0f;
            }
        }
        
        var Groundhit : boolean = false;
        var hitInfo : RaycastHit;
        var DownPos : Vector3 = Vector3(0,-100,0) + tPreyTransform.position;
        var layerMask = 1<<9;//地形在第9个层
        Groundhit = Physics.Linecast(tPreyTransform.position,DownPos,hitInfo,layerMask);    
        tPreyTransform.position.y = hitInfo.point.y;
        
       	if(hControllerScript.getCurrentWalkSpeed() <= 1)
       	{
       		//if(fDistanceToPlayer >= 600)
       		//{
       			aPreyAnim.Play("Idle");
       		//}
       	}	    
       	else
       	{
       		aPreyAnim.Play("Run");
       	}
    }
}

function OnGUI(){
    if(hInGameScript.isGamePaused() == true)
        return;
    GUI.skin = oRunnerSkin;
    
    if(nCatchTime > 0){
        GUI.Box(Rect((Screen.width - 100) / 2, 10, 100, 40), "时间 " + nCatchTime);
    }

    //GUI.Box(Rect(10, 50, 160, 40), "金币 " + hPowerupsMainController.getCurrencyUnits().ToString());
    GUI.Box(Rect(Screen.width - 160 - 10, 10, 160, 40), "分数 " + nScore);
    GUI.Box(Rect(Screen.width - 160 - 10, 50, 160, 40), "速度 " + hControllerScript.GetCurrSpeed().ToString("0"));
    //GUI.Box(Rect(Screen.width - 300 - 10, 90, 300, 40), "跑道 " + iLaneRandom + " " + (iChangeDir == 1 ? "left" : (iChangeDir == 2 ? "right" : "")) +"  " + fStartZ + "_" + fEndZ	);
}

public function LaunchPrey()
{
	aPreyAnim.Play("Run");
}

public function SetLevel(pLevel : PreyLevelEnum){
    eLevel = pLevel;
    fSpeedLimit = arrSpeedLimits[eLevel];
}

public function GetLevel(){
    return eLevel;
}

public function GetDistanceToPlayer(){
    return fDistanceToPlayer;
}

public function GetMinDistanceToPlayer(){
    return fMinDistanceToPlayer;
}

public function GetSpeedLimit(){
    return fSpeedLimit;
}

public function AddScore(pScore : int){
    nScore += pScore;
}