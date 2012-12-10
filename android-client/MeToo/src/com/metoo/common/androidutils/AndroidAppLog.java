package com.metoo.common.androidutils;

import android.util.Log;

/**
 * Журнал приложения для Android
 * @author theurgist
 *
 */
public class AndroidAppLog
{ 
	private static String tag = "AppLog";

	/**
	 * Verbose-level
	 * @param message
	 */
	public static void V(String message)
	{
		Log.v(tag, message);
	}
	/**
	 * Info-level
	 * @param message
	 */
	public static void I(String message)
	{
		Log.i(tag, message);
	}
	/**
	 * Warning-level
	 * @param message
	 */
	public static void W(String message)
	{
		Log.w(tag, message);
	}
	/**
	 * Error-level
	 * @param message
	 */
	public static void E(String message)
	{
		Log.e(tag, message);
	}
	/**
	 * Debug-level
	 * @param message
	 */
	public static void D(String message)
	{
		Log.d(tag, message);
	}
	/**
	 * What-a-Terrible-Failure-level
	 * @param message
	 */
	public static void WTF(String message)
	{
		Log.wtf(tag, message);
	}
}
