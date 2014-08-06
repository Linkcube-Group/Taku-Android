package me.linkcube.taku.bt;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import me.linkcube.taku.R;
import me.linkcube.taku.common.ui.BaseListAdapter;
import me.linkcube.taku.core.ToyConnectTimeManager;
import me.linkcube.taku.core.bt.DeviceConnectionManager;

/**
 * 蓝牙搜索列表适配器
 * 
 * @author orange
 * 
 */
public class BTDeviceAdapter extends BaseListAdapter<BluetoothDevice> {

	private Context mContext;

	public BTDeviceAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BTDeviceListCell cell;
		if (convertView == null) {
			cell = new BTDeviceListCell(mContext);
		} else {
			cell = (BTDeviceListCell) convertView;
		}
		BluetoothDevice device = getItem(position);
		String name = device.getName();
		cell.setDeviceName(name);

		if (device.equals(DeviceConnectionManager.getInstance()
				.getDeviceConnected())) {
			if (DeviceConnectionManager.getInstance().isConnected()) {
				ToyConnectTimeManager.getInstance().startTimeStatistics();
				cell.setDeviceState(R.string.connected);
			} else {
				if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
					cell.setDeviceState(R.string.bonded);
				} else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
					cell.setDeviceState(R.string.unbond);
				} else if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
					cell.setDeviceState(R.string.bonding);
				}
			}

		} else {
			if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
				cell.setDeviceState(R.string.bonded);
			} else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
				cell.setDeviceState(R.string.unbond);
			} else if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
				cell.setDeviceState(R.string.bonding);
			}
		}

		return cell;
	}

}
