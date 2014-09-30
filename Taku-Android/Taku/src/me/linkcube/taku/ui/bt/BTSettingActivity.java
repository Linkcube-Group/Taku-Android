package me.linkcube.taku.ui.bt;

import java.util.List;

import com.ervinwang.bthelper.BTHelper;
import com.ervinwang.bthelper.BTManager;
import com.ervinwang.bthelper.core.DeviceBroadcastReceiver;
import com.ervinwang.bthelper.core.DeviceConnectionManager;
import com.ervinwang.bthelper.core.OnBTDiscoveryListener;
import com.unity3d.player.f;

import custom.android.app.DialogActivity;
import custom.android.util.AlertUtils;
import custom.android.util.PreferenceUtils;
import custom.android.util.Timber;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import me.linkcube.taku.R;
import static com.ervinwang.bthelper.Const.Device.*;

public class BTSettingActivity extends DialogActivity implements
		OnClickListener, OnDeviceItemClickListener, OnBTDiscoveryListener {

	private ImageButton back_imgBtn;
	private ToggleButton bluetoothTb;

	private Button discoverDevicesBtn, bluetoothHelpBtn;

	private BTDeviceListView deviceLv;

	private List<BluetoothDevice> deviceList;

	private BTDeviceAdapter deviceAdapter;

	private DeviceBroadcastReceiver deviceDiscoveryReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bt_setting_activity);
		deviceDiscoveryReceiver = new DeviceBroadcastReceiver(this);
		deviceList = BTHelper.getBondedDevices();
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		bluetoothTb.setChecked(BTHelper.isBluetoothEnabled());
		bluetoothTb.setOnCheckedChangeListener(switchListener);
		BTHelper.regiserDeviceReceiver(mActivity, deviceDiscoveryReceiver);
		deviceAdapter = new BTDeviceAdapter(mActivity, deviceList);
		deviceLv.setAdapter(deviceAdapter);

	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BTHelper.unregisterDeviceReceiver(mActivity, deviceDiscoveryReceiver);
		deviceList.clear();
	}

	private void initViews() {
		back_imgBtn = (ImageButton) findViewById(R.id.back_imgBtn);
		back_imgBtn.setOnClickListener(this);
		bluetoothTb = (ToggleButton) findViewById(R.id.bluetooth_tb);
		deviceLv = (BTDeviceListView) findViewById(R.id.device_lv);
		deviceLv.setOnDeviceItemClickListener(this);
		discoverDevicesBtn = (Button) findViewById(R.id.discover_devices_btn);
		discoverDevicesBtn.setOnClickListener(this);
		if (BTHelper.isBluetoothEnabled()) {
			showBondedDevices();
		}
		bluetoothHelpBtn = (Button) findViewById(R.id.bluetooth_help_btn);
		bluetoothHelpBtn.setOnClickListener(this);
	}

	private void showBondedDevices() {
		if (BTHelper.getBondedDevices() != null) {
			deviceLv.showDeviceListView();
		}
	}

	private void clearDeviceList() {
		deviceLv.showTipTextView();
		deviceLv.setTip(R.string.switch_on_bluetooth_to_search_toy);
		deviceList.clear();
		deviceAdapter.notifyDataSetChanged();
	}

	private void startDiscoverBluetoothDevices() {
		BluetoothAdapter madapter = BluetoothAdapter.getDefaultAdapter();
		madapter.startDiscovery();
		discoverDevicesBtn.setText(R.string.searching);
	}

	private void finishDiscoverBluetoothDevices() {
		discoverDevicesBtn.setText(R.string.search_toy);
	}

	private void bondDevice(BluetoothDevice device, int position) {
		if (BTManager.getInstance().bondDevice(device)) {
			showProgressDialog(getResources().getString(
					R.string.dialog_bonding_bluetooth));
		} else {
			Timber.d("绑定拉玩具失败");
			AlertUtils.showToast(mActivity,
					getResources().getString(R.string.toast_toy_unbonded));
		}
	}

	private OnCheckedChangeListener switchListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			BTHelper.setBluetoothEnabled(isChecked);
			if (!isChecked) {
				discoverDevicesBtn.setText(R.string.search_toy);
			} else {
				showBondedDevices();
			}
		}
	};

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.discover_devices_btn:
			if (BTHelper.isBluetoothEnabled()) {
				startDiscoverBluetoothDevices();
			} else {
				AlertUtils.showToast(
						mActivity,
						getResources().getString(
								R.string.toast_pls_open_bluetooth));
			}
			break;
		case R.id.bluetooth_help_btn:
			break;
		case R.id.back_imgBtn:
			if (isToyConnected()) {
				setResult(RESULT_OK);
				this.finish();
			}
			break;
		default:
			break;
		}

	}

	private boolean isToyConnected() {
		return BTManager.getInstance().getDeviceService().checkConnection();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// TODO

	}

	@Override
	public void onBTStateTuringOn() {
		Timber.i("正在打开蓝牙");
		bluetoothTb.setClickable(false);
		deviceLv.setTip(R.string.switching_on_bluetooth);
		AlertUtils.showToast(this,
				getResources().getString(R.string.toast_open_bluetooth_wait));

	}

	@Override
	public void onBTStateTuringOff() {
		Timber.i("正在关闭蓝牙");
		bluetoothTb.setClickable(false);
		deviceLv.setTip(R.string.switching_off_bluetooth);
		AlertUtils.showToast(this,
				getResources().getString(R.string.toast_close_bluetooth_wait));
	}

	@Override
	public void onBTStateOn() {
		Timber.i("蓝牙已打开");
		bluetoothTb.setClickable(true);
		AlertUtils.showToast(this,
				getResources().getString(R.string.toast_bluetooth_open));
		showBondedDevices();
		startDiscoverBluetoothDevices();
	}

	@Override
	public void onBTStateOff() {
		Timber.i("蓝牙已关闭");
		bluetoothTb.setClickable(true);
		AlertUtils.showToast(this,
				getResources().getString(R.string.toast_bluetooth_closed));
		clearDeviceList();
	}

	@Override
	public void onBTDiscoveryOne(BluetoothDevice device) {
		deviceLv.showDeviceListView();
		Timber.d("发现一个设备:" + device.getName());
		filterDevices(device);
		if (deviceList.size() > 0) {
			deviceAdapter.notifyDataSetChanged();
			deviceLv.showDeviceListView();
		} else {
			deviceLv.showTipTextView();
		}

	}

	@Override
	public void onBTDiscoveryFinished() {
		Timber.d("搜索蓝牙设备完毕！");
		finishDiscoverBluetoothDevices();
		AlertUtils.showToast(this,
				getResources().getString(R.string.toast_searching_toy_over));
		if (deviceList.size() == 0) {
			deviceLv.setTip(getResources().getString(
					R.string.toast_no_device_nearby));
			deviceLv.showTipTextView();
		}
	}

	@Override
	public void onBondDeviceClick(BluetoothDevice device, int position) {
		bondDevice(device, position);
	}

	@Override
	public void onConnectDeviceClick(BluetoothDevice device, int position) {
		new ConnectDeviceAsyncTask(device, position).execute();
	}

	private class ConnectDeviceAsyncTask extends
			AsyncTask<BluetoothDevice, Void, Boolean> {

		private BluetoothDevice mDevice;

		public ConnectDeviceAsyncTask(BluetoothDevice device, int position) {
			this.mDevice = device;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Timber.d("准备连接设备");
			showProgressDialog(getResources().getString(
					R.string.toast_connecting_toy));
		}

		@Override
		protected Boolean doInBackground(BluetoothDevice... params) {
			Timber.d("正在连接设备");
			boolean success = false;
			success = BTManager.getInstance().getDeviceService()
					.connectDevice(mDevice.getName(), mDevice.getAddress());
			return success;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			super.onPostExecute(success);
			Timber.d("连接设备完毕");
			dismissProgressDialog();
			if (success) {
				DeviceConnectionManager.getInstance().startCheckConnetionTask();
				AlertUtils.showToast(mActivity,
						R.string.toast_connect_toy_success);
				// 保存连接上的设备名和状态
				PreferenceUtils.setString(DEVICE_NAME, mDevice.getName());
				PreferenceUtils.setString(DEVICE_ADDRESS, mDevice.getAddress());
				deviceAdapter.notifyDataSetChanged();
			} else {
				AlertUtils.showToast(mActivity,
						R.string.toast_connect_toy_failure);
			}

		}

	}

	private List<BluetoothDevice> filterDevices(BluetoothDevice device) {
		for (int i = 0; i < deviceList.size(); i++) {
			if (deviceList.get(i).getAddress().equals(device.getAddress())) {
				return deviceList;
			}
		}
		deviceList.add(device);
		return deviceList;
	}

	@Override
	public void onBTStateBonded() {
		Timber.d("onReceive:bluetooth bond state changed -> " + "BONDED");
		Timber.d("玩具配对成功");
		dismissProgressDialog();
		AlertUtils.showToast(mActivity,
				getResources().getString(R.string.toast_bond_toy_success));
		deviceAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBTStateBondNone() {
		Timber.d("onReceive:bluetooth bond state changed -> " + "BOND_NONE");
		deviceAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBTStateBonding() {
		Timber.d("onReceive:bluetooth bond state changed -> " + "BONDING");
		Timber.d("正在绑定玩具");
	}

}
