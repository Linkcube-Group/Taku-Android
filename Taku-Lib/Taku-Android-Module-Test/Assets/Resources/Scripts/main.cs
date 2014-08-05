using UnityEngine;
using System.Collections;

public class main : MonoBehaviour {
	
	// Use this for initialization
	private AndroidJavaObject activity;
	private int  bstate=-1;
	
	private int  toystate = -1;
	private string str = "gege";
	private string toyname = "yaya";
	
	private bool bStateMinus1, bState0;
	private bool bToyState0, bToyState3;
	private int nCount = 0;
	// private string result="http://blog.csdn.net/dingxiaowei2013";  //result用于接收二维码的返回值
	void Start()
	{
		AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		activity = jc.GetStatic<AndroidJavaObject>("currentActivity");
		
		bStateMinus1 = bState0 = false;
		bToyState0 = bToyState3 = false;
		str = activity.Call<string>("startDiscovery");
	}
	
	void Update()
	{
		
		// activity.Call("bondAndConnectDevice");
		// activity.Call("startDiscovery");
		//state =  activity.Call<int>("getBTState");
		/***str = activity.Call<string>("getStr");
	   bstate = activity.Call<int>("getBTState");
	    toystate = activity.Call<int>("getToyState");
       toyname =  activity.Call<string>("getToyName");
       ***/
		//activity.Call("startDiscovery");
		bstate = activity.Call<int>("getBTState");	   
		toystate = activity.Call<int>("getToyState");
		//toyname =  activity.Call<string>("getToyName");
		//str = nCount.ToString();
		++nCount;
		if(nCount > 0x7FFFFFFE)
			nCount = 0;
		/*if(!bState0)
		{
			activity.Call("bond");
			bState0 = true;
		}*/
		/*switch(bstate)
	   {
		case -1:
			if(!bStateMinus1)
			{
				activity.Call("setBluetoothEnabled", true);
				bStateMinus1 = true;
			}
			break;
		case 0: //蓝牙已打开
			if(!bState0)
			{
				activity.Call("bond");
				bState0 = true;
			}
			break;
		default:
			if(!bState0)
			{
				activity.Call("bond");
				bState0 = true;
			}
			break;
	   };*/
		
		/*if(toystate != 0)
	   {
			if(!bToyState0)
			{
				activity.Call("connect");
				bToyState0 = true;
			}
	   }*/
		/*switch(toystate)
	   {
		case 0: //已配对
			if(!bToyState0)
			{
				activity.Call("connect");
				bToyState0 = true;
			}
			break;
		case 3: //已连接
			if(!bToyState3)
			{
				bToyState3 = true;
				toyname =  activity.Call<string>("getToyName");
			}
			break;
	   }*/
		
	}
	void OnGUI()
	{
		
		GUI.Button(new Rect(0, 0, 400, 100), "回调函数测试："+str);
		GUI.Button(new Rect(100,100,400,100),"蓝牙状态测试："+bstate.ToString());
		GUI.Button(new Rect(200,200,400,100),"玩具状态:"+toystate.ToString());
		//GUI.Button(new Rect(300,300,400,100),"玩具名称:"+toyname);
		if(bState0)
			GUI.Button(new Rect(0, 420, 400, 100), "Start bond: true");
		else
			GUI.Button(new Rect(0, 420, 400, 100), "Start bond: false");
	}
	
}