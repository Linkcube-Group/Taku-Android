package com.example.demo;

import com.unity3d.player.UnityPlayerNativeActivity;

import me.linkcube.library.core.bluetooth.LinkcubeBT;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

public class MainActivity extends UnityPlayerNativeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinkcubeBT.onCreate(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LinkcubeBT.onResume(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LinkcubeBT.onDestroy(this);
	}

	/**
	 * 蓝牙是否开启
	 * 
	 * @return 如果开启返回true，否则false
	 */
	public boolean isBluetoothEnabled() {
		return LinkcubeBT.isBluetoothEnabled();
	}

	/**
	 * 打开/关闭蓝牙设备
	 * 
	 * @param enabled
	 */
	public void setBluetoothEnable(boolean enabled) {
		LinkcubeBT.setBluetoothEnable(enabled);
	}

	/**
	 * 开始扫描设备
	 */
	public void startDiscover() {
		LinkcubeBT.startDiscovery();
	}

	/**
	 * 取消扫描设备
	 */
	public void cancelDiscovery() {
		LinkcubeBT.cancelDiscovery();
	}

	/**
	 * 绑定指定Mac地址设备
	 * 
	 * @param macAdrress
	 */
	public void bond(String address) {
		LinkcubeBT.bond(address);
	}

	/**
	 * 连接指定Mac地址设备
	 * 
	 * @param macAdrress
	 */
	public void connect(String adrress) {
		LinkcubeBT.connect(adrress);
	}

	/**
	 * 获取已连接设备，名称规则为Name@MacAdrress;若无绑定设备，则返回null
	 * 
	 * @return Name@MacAdrress
	 */
	public String getConnectedDevice() {
		return LinkcubeBT.getConnectedDevice();
	}

	/**
	 * 获取绑定设备名称列表，名称规则为Name@MacAdrress，多个设备则以'|'来分割。如Name1@MacAdrress1|Name2@MacAdrress2
	 * 
	 * @return Name@MacAdrress
	 */
	public String getBondedToyNameList() {
		return LinkcubeBT.getBondedToyNameList();
	}

	/**
	 * 获取绑定设备名称列表，名称规则为Name@MacAdrress，多个设备则以'|'来分割。如Name1@MacAdrress1|Name2@MacAdrress2
	 * 
	 * @return Name@MacAdrress
	 */
	public String getUnbondedToyNameList() {
		return LinkcubeBT.getUnbondedToyNameList();
	}

	/**
	 * 返回蓝牙状态：0-蓝牙已打开；1-蓝牙已关闭；2-蓝牙正在打开中；3-蓝牙正在关闭中；4-正在扫描设备中；6-扫描设备过程结束
	 * 
	 * @return
	 */
	public int getBTState() {
		return LinkcubeBT.getBTState();
	}

	/**
	 * 返回和玩具的连接状态：0-已配对；1-配对中；2-配对完成；3-已连接；4-连接失败；5-连接失败；6-其他原因失去连接
	 * 
	 * @return
	 */
	public int getToyState() {
		return LinkcubeBT.getToyState();
	}

}
