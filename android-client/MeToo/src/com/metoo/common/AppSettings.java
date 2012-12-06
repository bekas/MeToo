package com.metoo.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.Semaphore;

import com.metoo.common.androidutils.AndroServices;

import android.content.Context;


/**
 * Предоставляет статический доступ к настройкам приложения
 * @author Theurgist
 */
public final class AppSettings {
	private static final AppSettings INSTANCE = new AppSettings();

	/**
	 * Структура, в которой хранятся все настройки
	 */
	private static StateData data;
	/**
	 * Примитив для запрета одновременной записи настроек
	 */
	Semaphore sync;
	
	private AppSettings() {
		data = new StateData();
		sync = new Semaphore(1);
	}
	
	/*
	 * Статические геттеры и сеттеры параметров
	 */
	
	public static Boolean IsDebugModeOn() {
		return data.debugMode;
	}
	public static Boolean GetEmulationMode() {
		return data.emulationMode;
	}
	public static void SetEmulationMode(Boolean emulation) {
		data.emulationMode = emulation;
	}

	public static void SetSrvUrl(String url) {
		data.srvURL = url;
	}
	public static String GetSrvUrl() {
		return data.srvURL;
	}
	public static void SetSrvTestUrl(String url) {
		data.srvTestURL = url;
	}
	public static String SetSrvTestUrl() {
		return data.srvTestURL;
	}
	public static void SetSrvPort(Integer port) {
		data.srvPort = port;
	}
	public static Integer GetSrvPort() {
		return data.srvPort;
	}

	
	public static String GetLogin() {
		return data.login;
	}
	public static String GetPassword() {
		return data.password;
	}
	public static void SetCreditials(String login, String passw) {
		data.login = login;
		data.password = passw;
	}

	public static void SetSessionId(Integer session_id) {
		data.session_id = session_id;
	}
	public static Integer GetSessionId() {
		return data.session_id;
	}
	public static void SetIsLoggedIn(Boolean isLoggedIn) {
		data.isLoggedIn = isLoggedIn;
	}
	public static Boolean GetIsLoggedIn() {
		return data.isLoggedIn;
	}

		
	public static void SetLastZoomLevel(Integer zoomLevel) {
		data.lastZoomLevel = zoomLevel;
	}
	public static Integer GetLastZoomLevel() {
		return data.lastZoomLevel;
	}
	public static void SetLastLatitude(Double latitude) {
		data.lastLatitude = latitude;
	}
	public static Double GetLastLatitude() {
		return data.lastLatitude;
	}
	public static void SetLastLongitude(Double longitude) {
		data.lastLongitude = longitude;
	}
	public static Double GetLastLongitude() {
		return data.lastLongitude;
	}

	public static void SetSplashDelay(Integer delay_ms) {
		data.msSplashDelay = delay_ms;
	}
	public static Integer GetSplashDelay() {
		return data.msSplashDelay;
	}
	public static void SetSplashMaxDelay(Integer delay_ms) {
		data.msSplashMaxDelay = delay_ms;
	}
	public static Integer GetSplashMaxDelay() {
		return data.msSplashMaxDelay;
	}


	/**
	 * Сохранить состояние во внутренней памяти устройства
	 * @param Android Context
	 * @return Operation successfulness
	 */
	public static Boolean SaveOnDisk(AndroServices andrCtx) {
		boolean allOk = true;
		FileOutputStream fos = null;
		ObjectOutputStream objout = null;
		
		try {
			INSTANCE.sync.acquire();
			
			fos = andrCtx.getContext().openFileOutput(data.filePath, Context.MODE_PRIVATE);
			objout = new ObjectOutputStream(fos);
			objout.writeObject(data);
			objout.close();
			
		} catch (FileNotFoundException e) {
			andrCtx.ShowToast("FileNotFoundException: Can't save settings!");
			e.printStackTrace();
			allOk = false;
		} catch (IOException e) {
			andrCtx.ShowToast("IOException: Can't save settings!");
			e.printStackTrace();
			allOk = false;
		} catch (InterruptedException e) {
		} finally {
			INSTANCE.sync.release();
		}
		return allOk;
	}
	/**
	 * Загрузить состояние из внутренней памяти устройства
	 * @param Android Context
	 * @return Operation successfulness
	 */
	public static Boolean LoadFromDisk(AndroServices andrCtx) {
		boolean allOk = true;
		FileInputStream fis = null;
		ObjectInputStream objin = null;
		
		
		try {
			INSTANCE.sync.acquire();
			fis = andrCtx.getContext().openFileInput(data.filePath);
			objin = new ObjectInputStream(fis);
			data = (StateData)objin.readObject();
			objin.close();
			
		} catch (ClassNotFoundException e) {
			// Should never happen
			andrCtx.ShowToast("ClassNotFoundException: Can't load settings!");
			e.printStackTrace();
			allOk = false;
		} catch (InvalidClassException e) {
			// We are losing data, because class version has been changed
			andrCtx.ShowToast("Settings from previous version are not supported. Settings have been reset to defaults", true);
			allOk = false;
		} catch (IOException e) {
			andrCtx.ShowToast("IOException: Can't load settings!");
			e.printStackTrace();
			allOk = false;
		} catch (InterruptedException e) {
		} finally {
			INSTANCE.sync.release();
		}
		return allOk;
	}
	
	public static Boolean ResetSettings(AndroServices andrCtx) {
		data = new StateData();
		return SaveOnDisk(andrCtx);
	}
}

/**
 * Объект, поддерживающий сериализацию и хранящий в себе все настройки
 * @TODO non-destroyable deserialization on settings collection update
 * @author Theurgist
 */
class StateData implements Serializable{
	static final long serialVersionUID = 6L;
	static final String DEF_STATE_SETTINGS_FILE = "appstate.ser";
	String filePath;
	
	// Базовые настройки
	Boolean debugMode;
	Boolean emulationMode; 
	Integer msSplashDelay;
	Integer msSplashMaxDelay;

	String srvURL;
	Integer srvPort;
	String srvTestURL;
	
	// Конфиденциальные данные
	String login;
	String password;
	Integer session_id;
	boolean isLoggedIn;
	
	// Предыдущее состояние навигации
	Integer lastZoomLevel;
	Double lastLatitude;
	Double lastLongitude;
	
	// Общие параметры
	Integer nLaunches;
	
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
		
		login = password = "";
		session_id = 0;
		isLoggedIn = true;
		
		lastZoomLevel = 10;
		lastLatitude = 55.5;
		lastLongitude = 37.4;
		
		nLaunches = 0;
	}
};