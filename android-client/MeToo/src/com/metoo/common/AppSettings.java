package com.metoo.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.metoo.common.androidutils.AndroServices;

import android.content.Context;


/**
 * Предоставляет статический доступ к настройкам приложения
 * @author Theurgist
 */
public final class AppSettings {
    @SuppressWarnings("unused")
	private static final AppSettings INSTANCE = new AppSettings();

	/**
	 * Структура, в которой хранятся все настройки
	 */
	private static StateData _data;
	
	private AppSettings() {
		_data = new StateData();
	}
	
	/*
	 * Статические геттеры и сеттеры параметров
	 */
	
	public static Boolean IsDebugModeOn() {
		return _data.debugMode;
	}
	public static Boolean GetEmulationMode() {
		return _data.emulationMode;
	}
	public static void SetEmulationMode(Boolean emulation) {
		_data.emulationMode = emulation;
	}

	public static void SetSrvUrl(String url) {
		_data.srvURL = url;
	}
	public static String GetSrvUrl() {
		return _data.srvURL;
	}
	public static void SetSrvTestUrl(String url) {
		_data.srvTestURL = url;
	}
	public static String SetSrvTestUrl() {
		return _data.srvTestURL;
	}
	public static void SetSrvPort(int port) {
		_data.srvPort = port;
	}
	public static Integer GetSrvPort() {
		return _data.srvPort;
	}

	
	public static String GetLogin() {
		return _data.login;
	}
	public static String GetPassword() {
		return _data.password;
	}
	public static void SetCreditials(String login, String passw) {
		_data.login = login;
		_data.password = passw;
	}

	public static void SetSessionId(String session_id) {
		_data.session_id = session_id;
	}
	public static String GetSessionId() {
		return _data.session_id;
	}
	

	public static void SetLastZoomLevel(int zoomLevel) {
		_data.lastZoomLevel = zoomLevel;
	}
	public static int GetLastZoomLevel() {
		return _data.lastZoomLevel;
	}
	public static void SetLastLatitude(double latitude) {
		_data.lastLatitude = latitude;
	}
	public static double GetLastLatitude() {
		return _data.lastLatitude;
	}
	public static void SetLastLongitude(double longitude) {
		_data.lastLongitude = longitude;
	}
	public static double GetLastLongitude() {
		return _data.lastLongitude;
	}

	public static void SetSplashDelay(int delay_ms) {
		_data.msSplashDelay = delay_ms;
	}
	public static Integer GetSplashDelay() {
		return _data.msSplashDelay;
	}
	public static void SetSplashMaxDelay(int delay_ms) {
		_data.msSplashMaxDelay = delay_ms;
	}
	public static Integer GetSplashMaxDelay() {
		return _data.msSplashMaxDelay;
	}


	/**
	 * Сохранить состояние во внутренней памяти устройства
	 * @param Android Context
	 * @return Operation successfulness
	 */
	public static boolean SaveOnDisk(AndroServices andrCtx) {
		FileOutputStream fos = null;
		ObjectOutputStream objout = null;
		try {
			fos = andrCtx.getContext().openFileOutput(_data.filePath, Context.MODE_PRIVATE);
			objout = new ObjectOutputStream(fos);
			objout.writeObject(_data);
			objout.close();
		} catch (FileNotFoundException e) {
			andrCtx.ShowToast("FileNotFoundException: Can't save settings!");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			andrCtx.ShowToast("IOException: Can't save settings!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * Загрузить состояние из внутренней памяти устройства
	 * @param Android Context
	 * @return Operation successfulness
	 */
	public static boolean LoadFromDisk(AndroServices andrCtx) {
		FileInputStream fis = null;
		ObjectInputStream objin = null;
		try {
			fis = andrCtx.getContext().openFileInput(_data.filePath);
		} catch (FileNotFoundException e) {
			return false;
		}
		try {
			objin = new ObjectInputStream(fis);
			_data = (StateData)objin.readObject();
			objin.close();
		} catch (ClassNotFoundException e) {
			// Should never happen
			andrCtx.ShowToast("ClassNotFoundException: Can't load settings!");
			e.printStackTrace();
			return false;
		} catch (InvalidClassException e) {
			// We are losing data, because class version has been changed
			andrCtx.ShowToast("Settings from previous version are not supported. Settings have been reset to defaults", true);
			return false;
		} catch (IOException e) {
			andrCtx.ShowToast("IOException: Can't load settings!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean ResetSettings(AndroServices andrCtx) {
		_data = new StateData();
		return SaveOnDisk(andrCtx);
	}
}

/**
 * Объект, поддерживающий сериализацию и хранящий в себе все настройки
 * @TODO non-destroyable deserialization on settings collection update
 * @author Theurgist
 */
class StateData implements Serializable{
	static final long serialVersionUID = 4L;
	static final String DEF_STATE_SETTINGS_FILE = "appstate.ser";
	String filePath;
	
	// Базовые настройки
	boolean debugMode;
	boolean emulationMode; 
	int msSplashDelay;
	int msSplashMaxDelay;

	String srvURL;
	int srvPort;
	String srvTestURL;
	
	// Конфиденциальные данные
	String login;
	String password;
	String session_id;
	
	// Предыдущее состояние навигации
	int lastZoomLevel;
	double lastLatitude;
	double lastLongitude;
	
	// Общие параметры
	int nLaunches;
	
	/**
	 * Установка значений по умолчанию
	 */
	StateData() {
		debugMode = true;
		emulationMode = true;
		filePath = DEF_STATE_SETTINGS_FILE;
		msSplashDelay = 2000;
		msSplashMaxDelay = 4000;
		
		srvURL = srvTestURL = "10.0.2.2";
		srvPort = 8000;
		
		login = password = session_id = "";
		
		lastZoomLevel = 10;
		lastLatitude = 55.5;
		lastLongitude = 37.4;
		
		nLaunches = 0;
	}
};