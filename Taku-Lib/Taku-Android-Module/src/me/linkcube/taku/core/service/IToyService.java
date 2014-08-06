package me.linkcube.taku.core.service;

interface IToyService {

	boolean connectToy(String deviceName, String macAddress);

	boolean disconnectToy(String deviceName, String macAddress);

	boolean checkData();

	void startReadData();

	void clearDataBuffer();

	void stopReadData();
}
