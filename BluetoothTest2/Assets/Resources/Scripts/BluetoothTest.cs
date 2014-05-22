using UnityEngine;
using System.Collections;

public class BluetoothTest : MonoBehaviour {
	AndroidJavaClass oJC;// = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
	AndroidJavaObject oJO;// = oJC.GetStatic<AndroidJavaObject>("currentActivity");

	string oReturnStr = "@@";
	string sDebugInfo = "Debug: ";

	// Use this for initialization
	void Start () {

	}
	
	// Update is called once per frame
	void Update () {
		if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.Home) )
		{
			Application.Quit();
		}
	}

	void OnGUI(){
		if(GUILayout.Button("OPEN Activity01",GUILayout.Height(100)))
		{
			using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
			{
				using( AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
				{
					if(jo != null){
						sDebugInfo += "init success";
					}
					//调用Android插件中UnityTestActivity中StartActivity0方法，stringToEdit表示它的参数
					jo.Call("startDiscovery");

				}
				
			}
			/*
			AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
			AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
			jo.Call("StartActivity0","第一个Activity");
			*/
		}

		/*
		if(GUILayout.Button("OPEN Activity02",GUILayout.Height(100)))
		{
			//AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
			//AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
			//jo.Call("StartActivity1","第二个Activit");
			oJC = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
			if (oJC == null) {
				sDebugInfo += "null AndroidJavaClass\n";
			} else {
				sDebugInfo += "success new AndroidJavaClass\n";
			}
			oJO = oJC.GetStatic<AndroidJavaObject>("currentActivity");
			if (oJO == null) {
				sDebugInfo += "Debug: null AndroidJavaObject\n";
			} else {
				sDebugInfo += "Debug: sucess AndroidJavaObject\n";
			}
			
			int nRes = oJO.Call<int>("Max", new object[]{10, 20});
			oReturnStr = nRes.ToString ();
		}


		//if (GUI.Button (new Rect (100, 200, 60, 40), "hehe")) {
		//	oJO.Call("FindBluetoothDevice");
		//}
		GUI.Box (new Rect (100, 100, 200, 60), oReturnStr);*/
		GUI.Box (new Rect (0, Screen.height - 120, Screen.width, 100), sDebugInfo);

	}
}
