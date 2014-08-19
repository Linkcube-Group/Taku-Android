using System;
using UnityEngine;

/**
 * 数据中心，接收蓝牙数据之后进行处理，并且全局保持唯一，是单例的。
 * */
public class DataController : MonoBehaviour
{
	private Fighter fighter;

	private Enemy enemy;

	private String bluetoothData;

	private BluetoothController btController;

	public DataController ()
	{
		btController = new BluetoothController ();
	}

	void OnStart ()
	{
		
	}

	void OnUpdate ()
	{
		if (btController.IsBluetoothConncted)
				bluetoothData = btController.GetBluetoothData ();
		else
			//TODO 提示蓝牙断开
			;
			
	}


	private void ProcessFighterData (String data)
	{

	}

	/**
	 * 判断数据是否合法
	 * */
	private Boolean IsBluetoothDataLegal ()
	{
		if(bluetoothData!=null && !bluetoothData.Equals(""))
		{
			String signal = "";
			signal = bluetoothData.Substring(0, 2);
			if(signal.Equals("25"))
				return true;
			else 
				return false;
		}
		return false;
			
	}

	/**
	 * 计算两次信号相差时间
	 * */
	private float GetRelativeTime ()
	{
		//TODO 计算两次信号相差时间
		return 1;
	}

	/**
	 * 获取信号状态
	 * */
	private Charactor.STATE GetCharactorState (String bluetoothData)
	{
		String state = bluetoothData.Substring(2,4);
		if(state.Equals("02")) //run
		{
			return Charactor.STATE.RUN;							
		}
		if(state.Equals("01")) //left
		{
			return Charactor.STATE.LEFT;	
		}
		if(state.Equals("04")) //right
		{
			return Charactor.STATE.RIGHT;
		}
		if(state.Equals("05")) //jump
		{
			return Charactor.STATE.JUMP;
		}
		return 0;
	}



}
