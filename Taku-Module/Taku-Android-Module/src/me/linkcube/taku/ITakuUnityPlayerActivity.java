package me.linkcube.taku;

public interface ITakuUnityPlayerActivity {

	public String getData();

	public void clearBufferData();

	public boolean isBluetoothEnabled();

	public boolean isToyConnected();
	
	public void startReceiveData();
	
	public void stopReceiveData();

}