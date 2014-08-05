package me.linkcube.taku.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import me.linkcube.taku.common.utils.Timber;
import me.linkcube.taku.core.bt.BluetoothUtils;
import me.linkcube.taku.core.bt.DeviceConnectionManager;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

public class ToyServiceCallImpl extends android.os.Binder implements
		IToyServiceCall {

	private BluetoothDevice curDevice = null;

	private BluetoothSocket curSocket = null;

	private byte[] checkData = { 0x35, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x35 };

	@Override
	public IBinder asBinder() {
		return this;
	}

	@SuppressLint("NewApi")
	private boolean connectDevice() {
		UUID suuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		if (curDevice == null) {
			return false;
		}
		if (curSocket != null) {
			try {
				curSocket.close();
				curSocket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BluetoothSocket tmp = null;
		try {
			if (Build.VERSION.SDK_INT >= 10) {
				tmp = curDevice
						.createInsecureRfcommSocketToServiceRecord(suuid);
			} else {
				// Method m;
				// m = curDevice.getClass()
				// .getMethod("createRfcommSocket",
				// new Class[] { int.class });
				// tmp = (BluetoothSocket) m.invoke(curDevice, 1);
				tmp = curDevice.createRfcommSocketToServiceRecord(suuid);
			}

		} catch (IOException e) {
			return false;
		}

		curSocket = tmp;

		try {
			BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			adapter.cancelDiscovery();
			curSocket.connect();
		} catch (IOException e) {
			try {
				curSocket.close();
				curSocket = null;
			} catch (IOException e2) {
				return false;
			}
			e.printStackTrace();
			return false;
		}
		DeviceConnectionManager.getInstance().setmIsConnected(true, curDevice);
		return true;
	}

	@Override
	public boolean connectToy(String name, String macaddr)
			throws RemoteException {

		Timber.d("connectToy:");
		curDevice = null;

		if (!BluetoothUtils.isBluetoothEnabled()) {
			return false;
		}

		Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter()
				.getBondedDevices();

		if (devices.size() <= 0) {
			return false;
		}

		for (Iterator<BluetoothDevice> iterator = devices.iterator(); iterator
				.hasNext();) {
			BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
			String deviceName = bluetoothDevice.getName();
			Timber.d("device mac address = %s and name = %s",
					bluetoothDevice.getAddress(), deviceName);
			if (deviceName.contains(name)) {
				if (bluetoothDevice.getAddress().equalsIgnoreCase(macaddr)) {
					curDevice = bluetoothDevice;
					return connectDevice();
				}
			}

		}

		return false;
	}

	@Override
	public boolean disconnectToy(String name, String macaddr)
			throws RemoteException {
		if (curDevice == null || curSocket == null) {
			return false;
		}
		try {
			curSocket.close();
			curSocket = null;
		} catch (IOException e2) {
			return false;
		}
		DeviceConnectionManager.getInstance().setmIsConnected(false, curDevice);
		return true;
	}

	@Override
	public boolean checkData() {
		if (curDevice == null || curSocket == null) {
			return false;
		}

		OutputStream tmpOut = null;
		try {
			tmpOut = curSocket.getOutputStream();
		} catch (IOException e) {
			Timber.e(e, "sockets not created");
			return false;
		}

		try {
			tmpOut.write(checkData);
		} catch (IOException e) {
			e.printStackTrace();
			Timber.i("Toy is disconnected.");
			return false;
		}

		Timber.i("Toy is connected.");

		return true;
	}

}
