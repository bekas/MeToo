package com.metoo.activities;

import java.io.InputStream;

import com.metoo.R;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import com.metoo.common.AndroServices;
import com.metoo.common.AppLog;
import com.metoo.common.AppSettings;
import com.metoo.srvlink.PostRequest;
import com.metoo.srvlink.XmlAnswer;
import com.metoo.ui.MainLayout;
import com.metoo.ui.MapLayout;
import com.metoo.ui.SplashLayout;
import com.metoo.ui.TestingLayout;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.LayoutManager;

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

        if (AppSettings.IsTestingModeOn()) {
        	
//    		XmlAnswer parser = new XmlAnswer();
//    		String error = parser.ParseMessage(
//    				Helper.readRawTextFile(getApplicationContext(), 
//    				R.raw.fake_auth));
        	
            //PostRequest testRequest = new PostRequest(getResources().getString(R.string.metoo_srv_base_uri));
            PostRequest testRequest = new PostRequest("http://www.google.ru");
            testRequest.AddParam("q", "HELLO");
            testRequest.SetPreambula("search");
            testRequest.AddParam("sclient", "psy");
            testRequest.AddParam("hl", "ru");
            testRequest.AddParam("newwindow", "1");
            
            screenTester = new TestingLayout(this, null, testRequest);
            screenSplash = new SplashLayout(this, screenTester, AppSettings.GetSplashDelay());
            screenMap = new MapLayout(this, screenTester);
            
	        // Force device to power on display and to disable keyguard - 
	        // it makes testing more comfortable
	        services.set_wake_policy(this.getWindow(), true);
	        services.set_keyg_policy(this.getWindow(), true);
        }
        else {
        	screenMain = new MainLayout(this, null);
        	screenSplash = new SplashLayout(this, screenMain, AppSettings.GetSplashDelay());
        }

	    LayoutManager.Initialize(this, screenSplash);
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
