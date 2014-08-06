package me.linkcube.taku.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.linkcube.taku.TakuApplication;
import me.linkcube.taku.core.service.ToyService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class BTManager {

	public ToyService toyService;

	private Timer timer;

	private List<BluetoothDevice> bondedDevices;

	private List<BluetoothDevice> unbondedDevices = new ArrayList<BluetoothDevice>();

	private String currentDevice;

	private static BTManager instance;

	private int toyState;

	public static BTManager getInstance() {
		if (instance == null) {
			synchronized (BTManager.class) {
				if (instance == null) {
					instance = new BTManager();
				}
			}
		}

		return instance;
	}

	private BTManager() {
		toyService = new ToyService();
	}

	public ToyService getToyService() {
		return toyService;
	}

	/**
	 * 开始扫描设备
	 */
	public void startDiscovery() {
		BluetoothAdapter madapter = BluetoothAdapter.getDefaultAdapter();
		madapter.startDiscovery();
	}

	/**
	 * 取消扫描设备
	 */
	public void cancelDiscovery() {
		BluetoothAdapter madapter = BluetoothAdapter.getDefaultAdapter();
		madapter.cancelDiscovery();
	}

	public int getToyState() {
		return toyState;
	}

	public String getConnectedDevice() {
		return currentDevice;
	}

	public class ConnectToyThread implements Runnable {

		private BluetoothDevice device;

		public ConnectToyThread(BluetoothDevice device) {
			this.device = device;
		}

		@Override
		public void run() {
			toyState = Const.TOY_STATE.CONNECTING;
			boolean success;
			success = TakuApplication.toyService.connectToy(device.getName(),
					device.getAddress());
			if (success) {
				toyState = Const.TOY_STATE.CONNECTED;
				currentDevice = device.getName() + "@" + device.getAddress();
				;
			} else {
				toyState = Const.TOY_STATE.CONNECT_FAIL;
				currentDevice = null;
			}
		}

	}

	public class CheckConnectionTask extends TimerTask {

		@Override
		public void run() {

			if (toyState == Const.TOY_STATE.CONNECTED) {
				if (!TakuApplication.toyService.checkConnection()) {
					toyState = Const.TOY_STATE.INTERRUPTED;
					cancelCheckConnectionTask();
				}
			}
		}

	}

	public void startCheckConnetionTask() {
		timer = new Timer();
		timer.schedule(new CheckConnectionTask(), 3000, 3000);
	}

	public void cancelCheckConnectionTask() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public BluetoothDevice convertBondedAddressToDevice(String address) {
		for (int i = 0; i < bondedDevices.size(); i++) {
			BluetoothDevice temp = bondedDevices.get(i);
			if (temp.getAddress().equals(address))
				return temp;
		}
		// XXX
		return null;
	}

	public BluetoothDevice convertUnbondedAddressToDevice(String address) {
		for (int i = 0; i < unbondedDevices.size(); i++) {
			BluetoothDevice temp = unbondedDevices.get(i);
			if (temp.getAddress().equals(address))
				return temp;
		}
		// XXX
		return null;
	}
}
