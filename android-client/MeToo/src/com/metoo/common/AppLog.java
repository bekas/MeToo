package com.metoo.common;

import android.util.Log;

public class AppLog
{ 
	private static String tag = "AppLog";

	public static void V(String message)
	{
		Log.v(tag, message);
	}
	public static void I(String message)
	{
		Log.i(tag, message);
	}
	public static void W(String message)
	{
		Log.w(tag, message);
	}
	public static void E(String message)
	{
		Log.e(tag, message);
	}
	public static void D(String message)
	{
		Log.d(tag, message);
	}
	public static void WTF(String message)
	{
		Log.wtf(tag, message);
	}
}
