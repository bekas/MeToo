package com.metoo.ui.base;

import android.view.KeyEvent;

import com.metoo.common.androidutils.IAsyncTaskNotifyer;

/**
 * Базовый класс модели вида. Потомки инкапсулируют всю логику работы интерфейса.
 * @author theurgist
 *
 */
public abstract class BaseLayout implements IAsyncTaskNotifyer<String, String, String>
{	
	protected BaseActivity activity = null;
	protected BaseLayout _previous = null;
	
	public boolean IsPreloaded() {return _isPreloaded;}
	protected boolean _isPreloaded = false;
	
	/**
	 * Запускается при переходе вида в активный режим
	 */
	public abstract void Activate();
	/**
	 * Вызывается для остановки всех процессов, не нужных в фоновом режиме
	 */
	public abstract void Deactivate();
	/**
	 * Содержит всю логику предзагрузки
	 * @return Успешность предзагрузки
	 */
	protected abstract Boolean preloadRoutine();
	
	
	public BaseLayout(BaseActivity parent, BaseLayout previous) {
		activity = parent;
		_previous = previous;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        if (_previous != null) {
	        	activity.switcher.SwitchImmediately(_previous);
		        return true;
	        }
	    }
	    return activity.onKeyDown(keyCode, event, true);
	}
	
	public void NextLoadingLayoutOnProgress(String Message) { }
	public void NextLoadingLayoutOnError(String Reason) { }
}
