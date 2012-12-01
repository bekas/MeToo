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
	protected BaseActivity _launcher = null;
	protected BaseLayout _current = null;
	protected LayoutLoader _next = null;
	
	public LayoutManager(BaseActivity launcher, BaseLayout initial) {
		_launcher = launcher;
		_current = initial;
		
		if (initial != null)
			_launcher.SetLayoutAndActivate(_current);
	}

	/**
	 * Start background layout switching process
	 * @param nextLayout
	 */
	public void RequestSwitch(BaseLayout nextLayout) {		
		RequestSwitch(nextLayout, 0, 0);
	}
	/**
	 * Start background layout switching process
	 * @param nextLayout
	 * @param minDelay
	 */
	public void RequestSwitch(BaseLayout nextLayout, int minDelay, int maxDelay) {		
		if (_next != null) {
			_next.AbortLoad();
			_next = null;
		}
		
		_next = new LayoutLoader(this, nextLayout, minDelay, maxDelay);
		_next.execute();
	}
	
	/**
	 * Force switch
	 * @param layout
	 */
	public void SwitchImmediately(BaseLayout layout) {
		LoadIsCompleted(layout);
	}
	
	/**
	 * Is invoked on successful background load
	 * @param layout
	 */
 	void LoadIsCompleted(BaseLayout layout) {
 		if (_current == layout)
 			return;
 		
		if (_current != null)
			_current.Deactivate();
		_current = layout;
		_next = null;

		_launcher.SetLayoutAndActivate(_current);
	}
	
}


/**
 * Inner notifyer
 * @author Theurgist
 */
class LayoutLoader extends AsyncTask<Void, String, Boolean> {
	private LayoutManager _mgr;
	private BaseLayout _layout;
	private Timer _timer;
	private StatusUpdater _updater;

	private int _startingDelay;
	private int _extraLoadTime;
	private int _maxDelay;
	private static int _extraLoadTimeStep = 1000;
	
	private boolean _extraTimeStarted;
	private boolean _aborted;
	
	public LayoutLoader(LayoutManager switcher, BaseLayout layout, int minDelay, int maxDelay) {
		_mgr = switcher;
		_layout = layout;
		
		_timer = new Timer();
		_updater = new StatusUpdater();
		_extraTimeStarted = false;
		_aborted = false;
		
		_startingDelay = minDelay;
		_extraLoadTime = 0;
		_maxDelay = maxDelay;
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

		millis_end = System.currentTimeMillis();
		while (!_layout.IsPreloaded()) {
			if ((_maxDelay > 0) && (millis_end - millis_start > _maxDelay))
				break;
			try {
				Thread.sleep(250);
				millis_end = System.currentTimeMillis();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		AbortLoad();
		return result;
	}
	
	
	@Override
	protected void onProgressUpdate (String... values) {
		_mgr._current.NextLoadingLayoutOnProgress(values[0]);
	}
	
	@Override
	protected void onPostExecute (Boolean result) {
		_timer.cancel();
		_timer.purge();
		_mgr.LoadIsCompleted(_layout);
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
