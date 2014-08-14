using UnityEngine;
using System.Collections;

public class MainMenu : MonoBehaviour {

	void OnUpdate()
	{
		//当用户按下手机的返回键或home键退出游戏
		if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.Home) )
		{
			Application.Quit();
		}
	}
	
	
	void OnGUI() {
		
		if (GUI.Button(new Rect(220, 200, 150, 100), "Bluetooth Setting"))
		{
			AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
			AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
			jo.Call("startBTSettingActivity");
		}
		
		if (GUI.Button(new Rect(220, 320, 150, 100), "Play"))
		{
			Application.LoadLevel("main");
		}
	}
}
