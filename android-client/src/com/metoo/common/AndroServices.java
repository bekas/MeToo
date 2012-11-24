/**
 * 
 */
package com.metoo.common;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.util.Log;
import android.view.Window;

/**
 * @author Theurgist
 *
 */
public class AndroServices
{
	/** Инкапсулирует мои методы GUI */
	
	private Context Cntxt;
	/*
	 *  Вещи, необходимые для пробуждения устройства при установке
	 *  и дебаге приложения (в релизной версии ьудут отключены)
	 */
	private PowerManager mPowerManager;
	private WakeLock mWakeLock;
	private KeyguardManager mKeyguardManager;
	private KeyguardLock mKeyguardLock;
	
	SharedPreferences prefsVault;
	SharedPreferences.Editor prefsEditor;
	
	public Context getContext() {
		return Cntxt;
	}

	public AndroServices(Context base)
	{
		Cntxt = base;
		// Настройка механизмов управления питанием и экраном
		mPowerManager = (PowerManager) Cntxt.getSystemService(Context.POWER_SERVICE);
		mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, getClass().getName());
		mKeyguardManager = (KeyguardManager) Cntxt.getSystemService(Context.KEYGUARD_SERVICE);
		mKeyguardLock = mKeyguardManager.newKeyguardLock("Author");
		// Получение механизмов для доступа к хранилищу настроек
        prefsVault = PreferenceManager.getDefaultSharedPreferences(Cntxt);
        prefsEditor = prefsVault.edit();
	}
	
// Работа со временем
	public void	post_delayed_action	(Handler H, Runnable Actn, long msec, boolean RemoveCallbacks) {
		if (RemoveCallbacks)
			H.removeCallbacks(Actn);
		H.postDelayed(Actn, msec);
	}
	public void	post_planned_action	(Handler H, Runnable Actn, long msec, boolean RemoveCallbacks) {
		if (RemoveCallbacks)
			H.removeCallbacks(Actn);
		H.postAtTime(Actn, msec);
	}
	public void	remove_planned_action (Handler H, Runnable Actn) {
		H.removeCallbacks(Actn);
	}
	
// Работа с экраном
	public void set_wake_policy(Window Wnd, boolean ScreenOn) {
		if (ScreenOn)
			mWakeLock.acquire();
		else
			mWakeLock.release();
/*		if (ScreenOn)
		{
			Wnd.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			Wnd.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		}
		else
		{
			Wnd.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			Wnd.clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		}*/
	}
	public void set_keyg_policy(Window Wnd, boolean DisableKeyg) {
		if (DisableKeyg)
		{
			 if (mKeyguardManager.inKeyguardRestrictedInputMode()){ 
				 mKeyguardManager.exitKeyguardSecurely(new KeyguardManager.OnKeyguardExitResult(){ 
                         public void onKeyguardExitResult(boolean success) { 
                                 if (success){ 
                                         Log.d("FLIS DEBUGS", "onKeyguardExitResult success"); 
                                 } else { 
                                         Log.d("FLIS DEBUGS", "onKeyguardExitResult ! success"); 
                                 } 
                         } 
                 }); 
                 Log.d("FLIS DEBUGS", "inKeyguardRestrictedInputMode - disableKeyguard"); 
         }
			mKeyguardLock.disableKeyguard();
		}
		else
			mKeyguardLock.reenableKeyguard();
/*		if (DisableKeyg)
		{
			Wnd.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			Wnd.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		}
		else
		{
			Wnd.clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			Wnd.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		}*/
		
	}
	
// Различные сообщения
	public void showToastText(String Msg)
	{
        Toast.makeText(Cntxt, Msg, Toast.LENGTH_SHORT).show();
	}
	public void showToastText(String Msg, boolean Long)
	{
        Toast.makeText(Cntxt,
        			   Msg, Long ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
