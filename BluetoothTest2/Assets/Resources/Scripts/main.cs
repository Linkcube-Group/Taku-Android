using UnityEngine;
using System.Collections;

public class main : MonoBehaviour {
	private AndroidJavaObject m_Activity;
	private int m_nState = -1;
	private int m_nToyState = 100;
	private string m_sStr = "gege";
	private string m_sToyName = "yaya";
	private bool m_bStartDiscovery = false;

	void Start()
	{
		AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		m_Activity = jc.GetStatic<AndroidJavaObject>("currentActivity");
	}

	void Update()
	{
		if (!m_bStartDiscovery) {
			m_Activity.Call ("startDiscovery");
		} else {
			m_sStr = m_Activity.Call<string>("getStr");
			m_nState = m_Activity.Call<int>("getBTState");
			m_nToyState = m_Activity.Call<int>("getToyState");
			m_sToyName = m_Activity.Call<string>("getToyName");
		}
	}

	void OnGUI()
	{
		GUI.Label(new Rect(0, 0, 400, 100), "回调函数测试：" + m_sStr);
		GUI.Label(new Rect(0,110,400,100),"蓝牙状态测试：" + m_nState.ToString());
		GUI.Label(new Rect(0,220,400,100),"玩具状态:" + m_nToyState);
		GUI.Label(new Rect(0,330,400,100),"玩具名称:" + m_sToyName);
	}
}
