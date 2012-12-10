/**
 * 
 */
package com.metoo.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.metoo.R;
import com.metoo.common.AppSettings;
import com.metoo.common.MetooServices;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.srvlink.answers.LoginAnswer;
import com.metoo.srvlink.answers.UserRegistrationAnswer;
import com.metoo.ui.LoginLayout.LoginRequestProceeder;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;
import com.metoo.ui.validators.BaseValidator;
import com.metoo.ui.validators.PasswordValidator;
import com.metoo.ui.validators.UsernameValidator;

/**
 * @author theurgist
 *
 */
public class UserRegistrationLayout extends BaseLayout {

	EditText etLogin, etPasswd, etPasswdConfirm;
	Button btnTryRegister;
	TextView lblError;
	ProgressBar pbLogin;

	UsernameValidator usernameValidator;
	PasswordValidator passwdValidator, passwdConfirmValidator;

	/**
	 * @param parent
	 * @param previous
	 */
	public UserRegistrationLayout(BaseActivity parent, BaseLayout previous) {
		super(parent, previous);
	}

	@Override
	public void onSuccess(String Result) {}
	@Override
	public void onError(String Reason) {}
	@Override
	public void onProgress(String Message) {}


	@Override
	public void Activate() {
		activity.setContentView(R.layout.screen_registration);

		btnTryRegister = (Button)activity.findViewById(R.id.btnRegister);
		etLogin = (EditText)activity.findViewById(R.id.etUsername);
		etPasswd = (EditText)activity.findViewById(R.id.etPasswd);
		etPasswdConfirm = (EditText)activity.findViewById(R.id.etConfirmPasswd);
		lblError = (TextView)activity.findViewById(R.id.lblError);
        pbLogin = (ProgressBar)activity.findViewById(R.id.pbLogin);


        Validator postValidator = new Validator();
        usernameValidator = new UsernameValidator();
        usernameValidator.SetOnContentChangedListener(postValidator);
        etLogin.addTextChangedListener(usernameValidator);
        
        passwdValidator = new PasswordValidator();
        passwdValidator.SetOnContentChangedListener(postValidator);
        etPasswd.addTextChangedListener(passwdValidator);
        
        passwdConfirmValidator = new PasswordValidator();
        passwdConfirmValidator.SetOnContentChangedListener(postValidator);
        etPasswdConfirm.addTextChangedListener(passwdConfirmValidator);
        
        
        btnTryRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	sendLoginData();
            }
        });
        

		lblError.setVisibility(View.GONE);
		pbLogin.setVisibility(View.GONE);
		
		etLogin.setEnabled(true);
		etPasswd.setEnabled(true);
		etPasswdConfirm.setEnabled(true);
		btnTryRegister.setVisibility(View.GONE);
	}
	@Override
	public void Deactivate() {
	}

	@Override
	protected Boolean preloadRoutine() {
		return true;
	}


	protected void sendLoginData() {
		etLogin.setEnabled(false);
		etPasswd.setEnabled(false);
		etPasswdConfirm.setEnabled(false);
		btnTryRegister.setVisibility(View.GONE);
		pbLogin.setVisibility(View.VISIBLE);

		try {
			AppSettings.SetCreditials(etLogin.getText().toString(), etPasswd.getText().toString());
			MetooServices.Registrate(new RegistrationRequestProceeder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	void proceedError(String msg) {
		lblError.setText(msg);
		lblError.setVisibility(View.VISIBLE);
		pbLogin.setVisibility(View.GONE);
		
		etLogin.setEnabled(true);
		etPasswd.setEnabled(true);
		etPasswdConfirm.setEnabled(true);
		btnTryRegister.setVisibility(View.VISIBLE);
	}
	void proceedOk(Integer session_id) {
		pbLogin.setVisibility(View.GONE);
		
		AppSettings.SetSessionId(session_id);
		AppSettings.SetCreditials(etLogin.getText().toString(), etPasswd.getText().toString());
		AppSettings.SaveOnDisk(activity.services);
		
		
		activity.services.ShowToast("Пользователь успешно зарегистрирован", true);
		activity.finish();
	}
	

	/**
	 * Сообщает о результатах авторизации
	 * @author Theurgist
	 */
    class RegistrationRequestProceeder implements IAsyncTaskNotifyer<UserRegistrationAnswer, String, String>
    {
		public void onSuccess(UserRegistrationAnswer Result) {
			if (Result.GetError() != null)
				proceedError("Ошибка чтения ответа сервера: " + Result.GetError());
			
			else if (Result.GetRequestResult() == 201) {
				// Уже существует
				proceedError("Такой пользователь уже существует");
			}
			
			else if (Result.GetRequestResult() == 202) {
				proceedError("Не удалось создать запись для нового пользователя");
			}
				
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
	
	/**
	 * Вызывается валидатором при изменении полей
	 */
	private class Validator implements BaseValidator.IOnContentChanged {

		@Override
		public void onContentChanged() {
			if (usernameValidator.IsValid() && 
				passwdValidator.IsValid() && 
				passwdConfirmValidator.IsValid()) {
				btnTryRegister.setVisibility(View.VISIBLE);
			}
			else {
				btnTryRegister.setVisibility(View.GONE);
			}
		}
		
	}
}
