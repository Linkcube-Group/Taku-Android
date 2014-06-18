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
	 * �����Ƿ���
	 * 
	 * @return �����������true������false
	 */
	public boolean isBluetoothEnabled() {
		return LinkcubeBT.isBluetoothEnabled();
	}

	/**
	 * ��/�ر������豸
	 * 
	 * @param enabled
	 */
	public void setBluetoothEnable(boolean enabled) {
		LinkcubeBT.setBluetoothEnable(enabled);
	}

	/**
	 * ��ʼɨ���豸
	 */
	public void startDiscover() {
		LinkcubeBT.startDiscovery();
	}

	/**
	 * ȡ��ɨ���豸
	 */
	public void cancelDiscovery() {
		LinkcubeBT.cancelDiscovery();
	}

	/**
	 * ��ָ��Mac��ַ�豸
	 * 
	 * @param macAdrress
	 */
	public void bond(String address) {
		LinkcubeBT.bond(address);
	}

	/**
	 * ����ָ��Mac��ַ�豸
	 * 
	 * @param macAdrress
	 */
	public void connect(String adrress) {
		LinkcubeBT.connect(adrress);
	}

	/**
	 * ��ȡ�������豸�����ƹ���ΪName@MacAdrress;���ް��豸���򷵻�null
	 * 
	 * @return Name@MacAdrress
	 */
	public String getConnectedDevice() {
		return LinkcubeBT.getConnectedDevice();
	}

	/**
	 * ��ȡ���豸�����б����ƹ���ΪName@MacAdrress������豸����'|'���ָ��Name1@MacAdrress1|Name2@MacAdrress2
	 * 
	 * @return Name@MacAdrress
	 */
	public String getBondedToyNameList() {
		return LinkcubeBT.getBondedToyNameList();
	}

	/**
	 * ��ȡ���豸�����б����ƹ���ΪName@MacAdrress������豸����'|'���ָ��Name1@MacAdrress1|Name2@MacAdrress2
	 * 
	 * @return Name@MacAdrress
	 */
	public String getUnbondedToyNameList() {
		return LinkcubeBT.getUnbondedToyNameList();
	}

	/**
	 * ��������״̬��0-�����Ѵ򿪣�1-�����ѹرգ�2-�������ڴ��У�3-�������ڹر��У�4-����ɨ���豸�У�6-ɨ���豸���̽���
	 * 
	 * @return
	 */
	public int getBTState() {
		return LinkcubeBT.getBTState();
	}

	/**
	 * ���غ���ߵ�����״̬��0-����ԣ�1-����У�2-�����ɣ�3-�����ӣ�4-����ʧ�ܣ�5-����ʧ�ܣ�6-����ԭ��ʧȥ����
	 * 
	 * @return
	 */
	public int getToyState() {
		return LinkcubeBT.getToyState();
	}

}
