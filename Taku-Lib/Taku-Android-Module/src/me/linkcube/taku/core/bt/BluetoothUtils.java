package me.linkcube.taku.core.bt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.linkcube.taku.common.utils.Timber;
import me.linkcube.taku.core.bt.BluetoothConst.TOY_TYPE;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import static android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED;
import static android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED;
import static android.bluetooth.BluetoothDevice.ACTION_BOND_STATE_CHANGED;
import static me.linkcube.taku.core.bt.BluetoothConst.DEVICE_DEFAULT_NAMES;

public class BluetoothUtils {

	/**
	 * 向给定Activity注册寻找蓝牙设备接收器
	 * 
	 * @param activity
	 * @param receiver
	 */
	public static void regiserDeviceReceiver(Activity activity,
			BluetoothDeviceReceiver receiver) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(ACTION_BOND_STATE_CHANGED);
		filter.addAction(ACTION_DISCOVERY_FINISHED);
		filter.addAction(ACTION_STATE_CHANGED);
		activity.registerReceiver(receiver, filter);
	}

	/**
	 * 向给定Activity注销寻找蓝牙设备接收器
	 * 
	 * @param activity
	 * @param receiver
	 */
	public static void unregisterDeviceReceiver(Activity activity,
			BluetoothDeviceReceiver receiver) {
		activity.unregisterReceiver(receiver);
	}

	public static boolean isHaveBluetooth() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter == null)
			return false;
		else
			return true;
	}

	public static boolean isBluetoothEnabled() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		return adapter.isEnabled();
	}

	public static void setBluetoothEnabled(boolean enabled) {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (enabled) {
			adapter.enable();
		} else {
			adapter.disable();
		}
	}

	public static List<BluetoothDevice> getBondedDevices() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
		List<BluetoothDevice> devlist = new ArrayList<BluetoothDevice>();
		for (BluetoothDevice device : pairedDevices) {
			for (int nc = 0; nc < DEVICE_DEFAULT_NAMES.length; nc++) {
				if (device.getName().equalsIgnoreCase(DEVICE_DEFAULT_NAMES[nc])) {
					devlist.add(device);
					break;
				}
			}
		}
		return devlist;
	}

	public static boolean bondDevice(BluetoothDevice dev) {
		Method createBondMethod;
		Timber.d("createBondMethod--bondDevice");
		try {
			createBondMethod = dev.getClass().getMethod("createBond");
			try {
				createBondMethod.invoke(dev);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				return false;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean isLinkCubeDevice(BluetoothDevice device) {

		for (int nc = 0; nc < DEVICE_DEFAULT_NAMES.length; nc++) {
			Timber.d("get address:" + device.getAddress());
			Timber.d("get name:" + device.getName());
			if (device.getName() == null) {
				return false;
			}
			if (device.getName().equalsIgnoreCase(DEVICE_DEFAULT_NAMES[nc])) {
				return true;
			}
		}
		return false;
	}

	public static TOY_TYPE getDeviceType(String name) {
		if (name.equals(DEVICE_DEFAULT_NAMES[0])) {
			return TOY_TYPE.MARS;
		} else {
			return TOY_TYPE.VENUS;
		}
	}

	private BluetoothUtils() {

	}

}
