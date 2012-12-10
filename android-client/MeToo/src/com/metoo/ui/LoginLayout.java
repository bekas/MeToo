package com.metoo.ui;

import com.metoo.R;
import com.metoo.common.AppSettings;
import com.metoo.common.MetooServices;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.srvlink.answers.LoginAnswer;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;
import com.metoo.ui.validators.PasswordValidator;
import com.metoo.ui.validators.UsernameValidator;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Model of login screen
 * @author Theurgist
 */
public class LoginLayout extends BaseLayout {
	EditText etUsername;
	EditText etPasswd;
	Button btnLogin;
	ProgressBar pbLogin;
	TextView lblLoginProblem;
	TextView lblLoginOk;
	ImageView imgLoginLogo;

	UsernameValidator usernameValidator;
	PasswordValidator passwdValidator;
	
	
	public LoginLayout(BaseActivity parent, BaseLayout previous) {
		super(parent, previous);
	}

	public void onSuccess(String Result) { }
	public void onError(String Reason) { }
	public void onProgress(String Message) { }

	/**
	 * @see com.metoo.ui.BaseLayout#Activate()
	 */
	@Override
	public void Activate() {
		activity.setContentView(R.layout.screen_login);

        // Get UI objects aliases
        etUsername = (EditText)activity.findViewById(R.id.etUsername);
        etPasswd = (EditText)activity.findViewById(R.id.etPasswd);
        btnLogin = (Button)activity.findViewById(R.id.btnLogin);
        pbLogin = (ProgressBar)activity.findViewById(R.id.pbLogin);
        lblLoginProblem = (TextView)activity.findViewById(R.id.lblLoginProblem);
        lblLoginOk = (TextView)activity.findViewById(R.id.lblLoginOk);
        imgLoginLogo = (ImageView)activity.findViewById(R.id.imgLoginLogo);
        
        usernameValidator = new UsernameValidator();
        passwdValidator = new PasswordValidator();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	sendLoginData();
            }
        });

        etUsername.addTextChangedListener(usernameValidator);
		etUsername.setEnabled(true);
        etPasswd.addTextChangedListener(passwdValidator);
		etPasswd.setEnabled(true);
        
        btnLogin.setVisibility(View.GONE);
        pbLogin.setVisibility(View.GONE);
        lblLoginProblem.setVisibility(View.GONE);
		lblLoginOk.setVisibility(View.GONE);
		

		etUsername.setText(AppSettings.GetLogin());
		etPasswd.setText(AppSettings.GetPassword());
	}

	/**
	 * @see com.metoo.ui.BaseLayout#Deactivate()
	 */
	@Override
	public void Deactivate() {
		etUsername.removeTextChangedListener(usernameValidator);
		etPasswd.removeTextChangedListener(passwdValidator);

        btnLogin.setVisibility(View.GONE);
        pbLogin.setVisibility(View.GONE);
	}

	/**
	 * @see com.metoo.ui.BaseLayout#preloadRoutine()
	 */
	@Override
	protected Boolean preloadRoutine() {
		_isPreloaded = true;
		return true;
	}
	
	/**
	 * Вызывается валидатором при изменении полей
	 */
	void ValidateInput() {
		lblLoginProblem.setVisibility(View.GONE);
		if (usernameValidator.IsValid() && passwdValidator.IsValid()) {
			btnLogin.setVisibility(View.VISIBLE);
		}
		else {
			btnLogin.setVisibility(View.GONE);
		}
	}
	
	void proceedError(String msg) {
		lblLoginProblem.setText(msg);
		lblLoginProblem.setVisibility(View.VISIBLE);
		lblLoginOk.setVisibility(View.GONE);
		pbLogin.setVisibility(View.GONE);
		
		etUsername.setEnabled(true);
		etPasswd.setEnabled(true);
		btnLogin.setVisibility(View.VISIBLE);
	}
	void proceedOk(Integer session_id) {
		//_parent.services.showToastText("Success! Logged in with session_id=" + session_id);
		lblLoginOk.setText("Success! Logged in with session_id=" + session_id);
		lblLoginOk.setVisibility(View.VISIBLE);
		lblLoginProblem.setVisibility(View.GONE);
		pbLogin.setVisibility(View.GONE);
		
		AppSettings.SetSessionId(session_id);
		AppSettings.SetCreditials(etUsername.getText().toString(), etPasswd.getText().toString());
		AppSettings.SaveOnDisk(activity.services);
		//LayoutManager.SwitchImmediately(_previous);
		
		imgLoginLogo.setImageResource(R.drawable.button_ok);
	}
	
	protected void sendLoginData() {
		etUsername.setEnabled(false);
		etPasswd.setEnabled(false);
		btnLogin.setVisibility(View.GONE);
		pbLogin.setVisibility(View.VISIBLE);

		try {
			AppSettings.SetCreditials(etUsername.getText().toString(), etPasswd.getText().toString());
			MetooServices.Authorize(new LoginRequestProceeder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**выполнять
	 * Notifyes about login request results
	 * @author Theurgist
	 *
	 */
    class LoginRequestProceeder implements IAsyncTaskNotifyer<LoginAnswer, String, String>
    {
		public void onSuccess(LoginAnswer Result) {
			if (Result.GetError() != null)
				proceedError("Ошибка чтения ответа сервера: " + Result.GetError());
				
			else if (Result.GetSessionId() > 0)
				proceedOk(Result.GetSessionId());

			else
				proceedError("Ошибка аутентификации: " + Result.GetRequestResult().toString());
		}

		public void onError(String Reason) {
			proceedError("Ошибка подключения: " + Reason);
		}

		public void onProgress(String Message) { }
    	
    }
}
