/**
 * 
 */
package com.metoo.ui;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.metoo.R;
import com.metoo.activities.NavActivity;
import com.metoo.activities.SettingsActivity;
import com.metoo.common.AppSettings;
import com.metoo.common.androidutils.AndroServices;
import com.metoo.common.androidutils.AndroidAppLog;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.srvlink.base.Connector;
import com.metoo.srvlink.base.ServerRequest;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * @author Theurgist
 *
 */
public class TestingLayout extends BaseLayout {
	// Service connection object
    private Connector connect;
    private ServerRequest _initialRequest;
    
    // UI
	WebView webView;
	ProgressBar progrBar;
	EditText editText;
	Button btnTestSettings;
	Button btnTestLogin;
	Button btnTestMap;
	
	String loadResult;

	public TestingLayout(BaseActivity parent, BaseLayout previous, ServerRequest initialRequest) {
		super(parent, previous);
		_initialRequest = initialRequest;
		loadResult = "Nothing loaded";
	}

	/**
	 * @see ui.BaseLayout#Activate()
	 */
	@Override
	public void Activate() {
		activity.setContentView(R.layout.screen_test);

        // Get UI alias objects
        webView = (WebView)activity.findViewById(R.id.webView1);
        progrBar = (ProgressBar)activity.findViewById(R.id.progressBar1);
        editText = (EditText)activity.findViewById(R.id.editText1);
        btnTestSettings = (Button)activity.findViewById(R.id.btnTestSettings);
        btnTestLogin = (Button)activity.findViewById(R.id.btnTestLogin);
        btnTestMap = (Button)activity.findViewById(R.id.btnTestMap);

        btnTestSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(activity, SettingsActivity.class);
           	 	activity.startActivity(myIntent);
            	AndroidAppLog.W("'TestSettings' button pressed");
            }
        });
        btnTestLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	activity.RequestSwitch(new LoginLayout(activity, TestingLayout.this));
            	AndroidAppLog.W("'TestLogin' button pressed");
            }
        });
        btnTestMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(activity, NavActivity.class);
           	 	activity.startActivity(myIntent);
            	AndroidAppLog.W("'TestMap' button pressed");
            }
        });

        // Ui initial tweaks
        editText.setEnabled(false);
        //editText.setInputType(InputType.TYPE_NULL); -- doesn't work, Android bug!! :(
        
		webView.loadDataWithBaseURL("fakeURI", loadResult, "text/html", "utf-8", "");
		editText.setText(loadResult);
		progrBar.setVisibility(View.GONE);
	}

	/**
	 * @see ui.BaseLayout#Deactivate()
	 */
	@Override
	public void Deactivate() {
		btnTestLogin.setOnClickListener(null);
	}

	/**
	 * @see ui.BaseLayout#preloadRoutine()
	 */
	@Override
	protected Boolean preloadRoutine() {
		//connect = new Connector( activity.getResources().getString(R.string.metoo_srv_base_uri));
		connect = new Connector("http", AppSettings.GetSrvUrl()+":"+AppSettings.GetSrvPort());
		try {
			connect.SendSimpleRequest(_initialRequest, new FirstTimeLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
		
	}

	public void onSuccess(String Result) { }
	public void onError(String Reason) { }
	public void onProgress(String Message) { }

	
    class RequestLogUpdater implements IAsyncTaskNotifyer<String, String, String>
    {
		public void onSuccess(String Result) {
			loadResult = Result;
			activity.services.ShowToast("Received!", true);
		}

		public void onError(String Reason) {
			loadResult = Reason;
			activity.services.ShowToast("Error!", true);
		}

		public void onProgress(String Message) {
		}
    	
    }
    
    class FirstTimeLoader extends RequestLogUpdater {

		public void onSuccess(String Result) {
			_isPreloaded = true;
			super.onSuccess(Result);
		}

		public void onError(String Reason) {
			_isPreloaded = true;
			super.onError(Reason);
		}
    }
}
