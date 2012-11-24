/**
 * 
 */
package com.metoo.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.metoo.R;
import com.metoo.activities.MainActivity;
import com.metoo.common.AppLog;
import com.metoo.common.IAsyncTaskNotifyer;
import com.metoo.srvlink.Connector;
import com.metoo.srvlink.PostRequest;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;
import com.metoo.ui.base.LayoutManager;

/**
 * @author Theurgist
 *
 */
public class TestingLayout extends BaseLayout {
	// Service connection object
    private Connector connect;
    private PostRequest _initial;
    
    // UI
	WebView webView;
	ProgressBar progrBar;
	EditText editText;
	Button btnTestLogin;
	Button btnTestMap;

	public TestingLayout(BaseActivity parent, BaseLayout previous, PostRequest initialRequest) {
		super(parent, previous);
		_initial = initialRequest;
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
        btnTestLogin = (Button)activity.findViewById(R.id.btnTestLogin);
        btnTestMap = (Button)activity.findViewById(R.id.btnTestMap);

        btnTestLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	LayoutManager.RequestSwitch(new LoginLayout(activity, TestingLayout.this));
            	AppLog.W("'TestLogin' button pressed");
            }
        });
        btnTestMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	LayoutManager.RequestSwitch(MainActivity.screenMap);
            	AppLog.W("'TestMap' button pressed");
            }
        });

        // Ui initial tweaks
        editText.setEnabled(false);
        //editText.setInputType(InputType.TYPE_NULL); -- doesn't work, Android bug!! :(
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
		connect = new Connector( activity.getResources().getString(R.string.metoo_srv_base_uri));
		try {
			connect.SendSimpleRequest(_initial, new FirstTimeLoader());
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
			Toast.makeText(activity.getApplicationContext(), "Received!", Toast.LENGTH_LONG).show();
			WebView webView = (WebView)activity.findViewById(R.id.webView1);
			webView.loadDataWithBaseURL("fakeURI", (String)Result, "text/html", "utf-8", "");
			editText.setText((String)Result);
			progrBar.setVisibility(View.GONE);
		}

		public void onError(String Reason) {
			Toast.makeText(activity.getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
			webView.loadDataWithBaseURL("fakeURI", Reason, "text/html", "utf-8", "");
			editText.setText(Reason);
			progrBar.setVisibility(View.GONE);
		}

		public void onProgress(String Message) {
		}
    	
    }
    
    class FirstTimeLoader extends RequestLogUpdater {

		public void onSuccess(String Result) {
			_isPreloaded = true;
			LayoutManager.SwitchImmediately(TestingLayout.this);
			super.onSuccess(Result);
		}

		public void onError(String Reason) {
			_isPreloaded = true;
			LayoutManager.SwitchImmediately(TestingLayout.this);
			super.onError(Reason);
		}
    }
}
