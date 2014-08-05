package me.linkcube.taku.service;

import java.util.List;
import java.util.Map;

interface IToyServiceCall {
	
	boolean connectToy(String deviceName, String macAddress);
	
	boolean disconnectToy(String deviceName, String macAddress);
	
	boolean checkData();
}

