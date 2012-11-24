/**
 * 
 */
package com.metoo.ui.base;

import java.util.Timer;
import java.util.TimerTask;

import android.os.AsyncTask;

/**
 * Used to switch between layouts async-way
 * @author Theurgist
 */
public final class LayoutManager {
	static BaseActivity _launcher = null;
	static BaseLayout _current = null;
	static LayoutLoader _next = null;
	
	public static void Initialize(BaseActivity launcher, BaseLayout initial) {
		_launcher = launcher;
		_current = initial;
		_launcher.SetCurrentLayout(initial);
		_current.Activate();
	}

	/**
	 * Start background layout switching process
	 * @param nextLayout
	 */
	public static void RequestSwitch(BaseLayout nextLayout) {		
		RequestSwitch(nextLayout, 0);
	}
	/**
	 * Start background layout switching process
	 * @param nextLayout
	 * @param minDelay
	 */
	public static void RequestSwitch(BaseLayout nextLayout, int minDelay) {		
		if (_next != null) {
			_next.AbortLoad();
			_next = null;
		}
		
		_next = new LayoutLoader(nextLayout, minDelay);
		_next.execute();
	}
	
	/**
	 * Force switch
	 * @param layout
	 */
	public static void SwitchImmediately(BaseLayout layout) {
		LoadIsCompleted(layout);
	}
	
	/**
	 * Is invoked on successful background load
	 * @param layout
	 */
 	static void LoadIsCompleted(BaseLayout layout) {
 		if (_current == layout)
 			return;
 		
		_current.Deactivate();
		_current = layout;
		_next = null;

		_launcher.SetCurrentLayout(_current);
		_current.Activate();
	}
	
}


/**
 * Inner notifyer
 * @author Theurgist
 */
class LayoutLoader extends AsyncTask<Void, String, Boolean> {
	private BaseLayout _layout;
	private Timer _timer;
	private StatusUpdater _updater;

	private int _startingDelay;
	private int _extraLoadTime;
	private static int _extraLoadTimeStep = 1000;
	
	private boolean _extraTimeStarted;
	private boolean _aborted;
	
	public LayoutLoader(BaseLayout layout, int minDelay) {
		_layout = layout;
		
		_timer = new Timer();
		_updater = new StatusUpdater();
		_extraTimeStarted = false;
		_aborted = false;
		
		_startingDelay = minDelay;
		_extraLoadTime = 0;
	}
	
	public void AbortLoad() {
		_aborted = true;
		_timer.cancel();
		_timer.purge();
	}
	

	@Override
	protected Boolean doInBackground(Void... arg0) {
		_timer.scheduleAtFixedRate(_updater, _startingDelay, _extraLoadTimeStep);
		long millis_start = System.currentTimeMillis();
		
		Boolean result = _layout.preloadRoutine();
		long millis_end = System.currentTimeMillis();
		if (millis_end - millis_start < _startingDelay)
			try {
				Thread.sleep(_startingDelay - (millis_end - millis_start));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		while (!_layout.IsPreloaded()) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	@Override
	protected void onProgressUpdate (String... values) {
		LayoutManager._current.NextLoadingLayoutOnProgress(values[0]);
	}
	
	@Override
	protected void onPostExecute (Boolean result) {
		_timer.cancel();
		_timer.purge();
		LayoutManager.LoadIsCompleted(_layout);
	}
	
	/**
	 * Notifyes if load takes too long
	 * @author Theurgist
	 *
	 */
	class StatusUpdater extends TimerTask {
		
		@Override
		public void run() {
			if (_aborted)
			{
				cancel();
				return;
			}
			else if (_layout.IsPreloaded())
			{
				cancel();
				return;
			}
			else if (!_extraTimeStarted) {
				_extraTimeStarted = true;
				_extraLoadTime = 0;
				publishProgress("Загрузка длится дольше ожидаемого...");
			}
			else  {
				_extraLoadTime += _extraLoadTimeStep;
				publishProgress("Загрузка длится дольше ожидаемого на " + _extraLoadTime/1000 + " секунд");
			}
		}
	}
}
