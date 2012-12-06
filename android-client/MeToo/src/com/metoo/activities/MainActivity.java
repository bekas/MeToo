package com.metoo.activities;

import com.metoo.R;

import android.os.Bundle;
import android.view.Menu;
import com.metoo.common.AppSettings;
import com.metoo.common.MetooServices;
import com.metoo.common.androidutils.AndroidAppLog;
import com.metoo.srvlink.requests.LoginRequest;
import com.metoo.ui.MainLayout;
import com.metoo.ui.MapLayout;
import com.metoo.ui.SplashLayout;
import com.metoo.ui.TestingLayout;
import com.metoo.ui.base.BaseActivity;

/**
 * Главная точка входа в приложение
 * @author Theurgist
 */
public class MainActivity extends BaseActivity {
	
    SplashLayout screenSplash;
	static public TestingLayout screenTester;
	static public MainLayout screenMain;
	static public MapLayout screenMap;
    
    /** 
     * Вызывается при холодном запуске приложения
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        AppSettings.LoadFromDisk(services);
        MetooServices.Initialize();

        if (AppSettings.GetEmulationMode()) {
        	
			LoginRequest loginreq = new LoginRequest(AppSettings.GetLogin(), AppSettings.GetPassword());
            
            screenTester = new TestingLayout(this, null, loginreq);
            screenSplash = new SplashLayout(this, screenTester, AppSettings.GetSplashDelay(), 3000);
            
            // Заставить устройство держать дисплей включённым и отключить на время
            // работы приложения блокировщик клавиатуры - так удобнее тестировать
	       services.SetWakePolicy(this.getWindow(), true);
	       services.SetKeygPolicy(this.getWindow(), true);
        }
        else {
        	screenMain = new MainLayout(this, null);
        	screenSplash = new SplashLayout(this, screenMain, AppSettings.GetSplashDelay(), 3000);
        }

	    switcher.SwitchImmediately(screenSplash);
        AndroidAppLog.I("Application started");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }  
        
}
