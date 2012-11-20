package com.metoo.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;



public final class AppSettings {
    @SuppressWarnings("unused")
	private static final AppSettings INSTANCE = new AppSettings();

	/**
	 * Defines structure of data which is saved into phone memory (for restoring
	 * after minimizing, for account data saving etc.)
	 */
	private static StateData _data;
	
	private AppSettings() {
		_data = new StateData();
	}
	
	/*
	 * State getters and modifyers section
	 */
	
	public static boolean IsDebugModeOn() {
		return _data.debugMode;
	}
	public static boolean IsTestingModeOn() {
		return _data.testMode;
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
	
	public static void SetSplashDelay(int delay_ms) {
		_data.msSplashDelay = delay_ms;
	}
	public static int GetSplashDelay() {
		return _data.msSplashDelay;
	}


	/**
	 * Save state in internal storage
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
			andrCtx.showToastText("FileNotFoundException: Can't save settings!");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			andrCtx.showToastText("IOException: Can't save settings!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * Load settings state from internal storage
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
			andrCtx.showToastText("ClassNotFoundException: Can't load settings!");
			e.printStackTrace();
			return false;
		} catch (InvalidClassException e) {
			// We are losing data, because class version has been changed
			andrCtx.showToastText("Settings from previous version are not supported. Settings have been reset to defaults", true);
			return false;
		} catch (IOException e) {
			andrCtx.showToastText("IOException: Can't load settings!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

/**
 * Serializeable object which holds all the settings
 * @TODO non-destroyable deserialization on settings collection update
 * @author Theurgist
 *
 */
class StateData implements Serializable{
	static final long serialVersionUID = 1L;
	static final String DEF_STATE_SETTINGS_FILE = "appstate.ser";
	String filePath;
	
	// Settings
	boolean debugMode;
	boolean testMode; 
	int msSplashDelay;
	
	// Confidentional data
	String login;
	String password;
	String session_id;
	
	// State parameters
	int nLaunches;
	
	/**
	 * Setting up default values
	 */
	StateData() {
		debugMode = true;
		testMode = true;
		filePath = DEF_STATE_SETTINGS_FILE;
		msSplashDelay = 2000;
		
		login = password = session_id = "";
		
		nLaunches = 0;
	}
};