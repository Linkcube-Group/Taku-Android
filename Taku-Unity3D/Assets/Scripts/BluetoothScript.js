#pragma strict

var RunnerGUI : GUISkin;

private var activity : AndroidJavaObject = null;
private var hControllerScript : ControllerScript;
private var aniPlayer : Animation;

private var strBluetoothData : String = "Data";
private var arrBTDataSplitter : char[] = [(" ")[0]];
private var arrBTData : String[];
private var strDeviceNames : String = "";
private var arrDeviceNameSplitter : char[] = [("|")[0]];
private var arrDeivceNameMacSplitter : char[] = [(" ")[0]];
private var arrDevices : String[];
private var nSwitchStatusCount : int = 7;
private var arrSwitchStatus : int[] = [
	0x01, 
	0x02, 
	0x04,
	0x03,
	0x05,
	0x06,
	0x07
	];
private var arrPlayerMotion : String[] = [
	"left",
	"run",
	"right",
	"left",
	"jump",
	"right",
	"jump"
	];
private var nSignal : int = 0;
private var sTestMotion : String = "";

private var nState : int = -2;
private var bSearchCalled : boolean = false;
private var bConnectCalled : boolean = false;
private var bGetDeviceName : boolean = false;
private var bDeviceSelected : boolean = false;

private var bStartTiming : boolean = false;
private var fPrevBTTime : float = 0.0f;
private var fCurrBTTime : float = 0.0f;
private var fElapsedBTTime : float = 0.0f;
private var fLastRunTime : float;
private var fCurrentRunTime : float;
private var fDeltaRunTime : float;
private var bBTRunning : boolean = false;
private var fNotRunningTime : float = 0.0f;
private var fSpeedWhenStop : float = 0.0f;

private var iLeftRight : int = 0;
private var eSwipeDirection : SwipeDirection = SwipeDirection.Null;

private var i : int;

function Start()
{
	var jc : AndroidJavaClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
	activity = jc.GetStatic.<AndroidJavaObject>("currentActivity");
	hControllerScript = this.GetComponent(ControllerScript) as ControllerScript;
	aniPlayer = this.transform.Find("PlayerRotation/PlayerMesh/boy").GetComponent(Animation) as Animation;
	fDeltaRunTime = 5;
}

function Update()
{
	fNotRunningTime += Time.deltaTime;
	
	nState = activity.Call.<int> ("GetState");	
	strDeviceNames = activity.Call.<String>("GetDeviceNames");
	arrDevices = strDeviceNames.Split(arrDeviceNameSplitter);
		
	strBluetoothData = activity.Call.<String> ("GetData");
	
	if(!strBluetoothData.Equals("") && strBluetoothData.Length > 3)
	{
		strBluetoothData = strBluetoothData.Substring(1, strBluetoothData.Length - 2);
		var arrBTData = strBluetoothData.Split(arrBTDataSplitter);
		
		i = 0;		
		while(i<arrBTData.Length)
		{
			if(int.TryParse(arrBTData[i], nSignal))
			{
				if(nSignal == 0x25) //0x25开头的信号
				{
					i+=2;
					if(i>=arrBTData.Length)
					{
						break;
					}
					
					//检测第三个信号
					if(int.TryParse(arrBTData[i], nSignal))
					{
						if(0x02 == nSignal) //run
						{
							fNotRunningTime = 0;
							if(!bBTRunning)
							{
								fLastRunTime = Time.time;
								bBTRunning = true;
							}
							else
							{
								fCurrentRunTime = Time.time;
								fDeltaRunTime = (fCurrentRunTime - fLastRunTime) * 1000;					
							}							
						}
						else if(0x01 == nSignal) //left
						{
							eSwipeDirection = SwipeDirection.Left;
						}
						else if(0x04 == nSignal) //right
						{
							eSwipeDirection = SwipeDirection.Right;
						}
						else if(0x05 == nSignal) //jump
						{
							eSwipeDirection = SwipeDirection.Jump;
						}
						
						//根据信号播放动画
						for(var j : int = 0; j < nSwitchStatusCount; ++j)
						{
							if(nSignal == arrSwitchStatus[j])
							{
								aniPlayer.Play(arrPlayerMotion[j]);
								sTestMotion = arrPlayerMotion[j];
								break;
							}
						}
						i += 6;
					}
				}
				else //信号非法
				{
					break;
				}
			}
			else //无信号
			{
				break;
			}
		}
		
		if(bBTRunning) //如果在跑步，更新一下时间
		{
			fLastRunTime = fCurrentRunTime;
		}
		fPrevBTTime = fCurrBTTime;
	}
	else if(fNotRunningTime > 1.0f)  //如果空闲的时间大于1秒，
	{
		if(bBTRunning) //记录下停止时的速度
		{
			fSpeedWhenStop = hControllerScript.getCurrentWalkSpeed();
		}
		
		hControllerScript.setCurrentWalkSpeed(Mathf.Lerp(fSpeedWhenStop, 0, (fNotRunningTime-1.0)/2.0));
		fDeltaRunTime += 5;
		if(fDeltaRunTime > 50000)
		{
			fDeltaRunTime = 50000;
		}
		bBTRunning = false;
	}
	
	//如果信号的间隔时间小于50，仍置为50，防止间隔时间过小
	if(fDeltaRunTime < 50)
	{
		fDeltaRunTime = 50;
	}
	
	if(bBTRunning)
	{
		hControllerScript.setCurrentWalkSpeed(100000.0/fDeltaRunTime);
	}
	else
	{
		if(hControllerScript.getCurrentWalkSpeed() <= 1)
		{
			if(aniPlayer.IsPlaying("jump") || 
				aniPlayer.IsPlaying("left") || 
				aniPlayer.IsPlaying("right"))
			{
			}
			else
			{
				aniPlayer.Play("death");
			}
		}
	}
}

function OnGUI(){
	GUI.skin = RunnerGUI;
	//GUI.Box(Rect(0, 400, 500, 100), strDeviceNames);
	if(GUI.Button(Rect(50, Screen.height-120, 200, 100), "Search Bluetooth")){
		activity.Call("Search");
		bDeviceSelected = false;
	}
	if(GUI.Button(Rect(300, Screen.height-120, 200, 100), "Stop Search")){
		activity.Call("StopSearch"); 
	}
	//GUI.Box(Rect(200, Screen.height-360, 400, 100), "State: " + nState);
	//GUI.Box(Rect(50, Screen.height-480, 600, 100), arrDevices.Length + " Devices: " + strDeviceNames);
	GUI.Button(Rect(200, Screen.height - 240, 400, 100), strBluetoothData);
	GUI.Button(Rect(200, Screen.height - 360, 400, 100), sTestMotion + "  " + fDeltaRunTime + "  " + iLeftRight);
	if(!bDeviceSelected){
		for(var i : int=0; i<arrDevices.Length; ++i){
			if(GUI.Button(Rect(100, 200+75*i, 500, 70), arrDevices[i])){
				activity.Call("SelectDevice", i);
				activity.Call("Connect");
				bDeviceSelected = true;
			}
		}
	}	
}

public function GetDirection()
{
	var eRes = eSwipeDirection;
	eSwipeDirection = SwipeDirection.Null;
	return eRes;
}