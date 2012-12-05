package com.metoo.activities;

import java.io.InputStream;

import com.metoo.R;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import com.metoo.common.AppLog;
import com.metoo.common.AppSettings;
import com.metoo.srvlink.base.ServerRequest;
import com.metoo.srvlink.requests.LoginRequest;
import com.metoo.ui.MainLayout;
import com.metoo.ui.MapLayout;
import com.metoo.ui.SplashLayout;
import com.metoo.ui.TestingLayout;
import com.metoo.ui.base.BaseActivity;

/**
 * Startup activity
 * @author Theurgist
 */
public class MainActivity extends BaseActivity {
	
    SplashLayout screenSplash;
	static public TestingLayout screenTester;
	static public MainLayout screenMain;
	static public MapLayout screenMap;
    
    /** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        AppSettings.LoadFromDisk(services);

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
        AppLog.I("Application started");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }  
        
}



class Helper {
	public static String readRawTextFile(Context ctx, int resId)
	{
	    try {
	        Resources res = ctx.getResources();
	        InputStream in_s = res.openRawResource(resId);

	        byte[] b = new byte[in_s.available()];
	        in_s.read(b);
	        return new String(b);
	    } catch (Exception e) {
	        // e.printStackTrace();
	        return null;
	    }
	}
}
