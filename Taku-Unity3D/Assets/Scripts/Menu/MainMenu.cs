using UnityEngine;
using System.Collections;

public class MainMenu : MonoBehaviour {

	void OnStart()
	{
	
	}

	void OnUpdate()
	{
		//当用户按下手机的返回键或home键退出游戏
		if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.Home) )
		{
			Application.Quit();
		}
	}
	
	
	void OnGUI() {

		BluetoothController btController = new BluetoothController ();

		if (GUI.Button(new Rect(220, 200, 150, 100), "Bluetooth Setting"))
		{	
			btController.StartBTSettingActivity();
		}

		if (GUI.Button(new Rect(220, 320, 150, 100), "Play"))
		{

			if(btController.IsBluetoothConncted)
			{
				Application.LoadLevel("main");
			}
			else
			{
				btController.StartBTSettingActivity();
			}

		}
	}
}
