using UnityEngine;
using System;

/**
 * 蓝牙控制器，负责蓝牙部分的接收数据，和蓝牙的相关操作,主要是开启蓝牙界面，开始接收数据，停止接收数据。
 * 
 * 目前没有写成单例模式，但是希望只在游戏中的主页面进行此类的调用，保证我们只有一个数据源。
 * 
 * */
public class BluetoothController 
{
	public BluetoothController ()
	{

	}

	/**
	 * 获取蓝牙传输数据，返回为十六进制字符串
	 * */
	public String GetBluetoothData()
	{
		AndroidJavaObject jo = GetAndroidJavaObject();
		String data = jo.Call<String> ("getData");
		return data;
	}

	/**
	 * 获取调用Android 方法的实例
	 * */
	private AndroidJavaObject GetAndroidJavaObject()
	{
		AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
		return jo;
	}

	/**
	 * 判断蓝牙是否和玩具连接上
	 * */
	public Boolean IsBluetoothConncted()
	{	AndroidJavaObject jo = GetAndroidJavaObject ();
		Boolean isConnected = jo.Call<Boolean>("isToyConnected");
		return isConnected;
	}

	/**
	 * 开始接收数据
	 * */
	public void StartReceiveData()
	{
		AndroidJavaObject jo = GetAndroidJavaObject ();
		jo.Call ("startReceiveData");
	}

	/**
	 * 停止接收数据
	 * */
	public void StopReceiveData()
	{
		AndroidJavaObject jo = GetAndroidJavaObject ();
		jo.Call ("stopReceiveData");
	}

	/**
	 * 开启蓝牙界面
	 * */
	public void StartBTSettingActivity()
	{
		AndroidJavaObject jo = GetAndroidJavaObject ();
		jo.Call ("startBTSettingActivity");
	}
}


