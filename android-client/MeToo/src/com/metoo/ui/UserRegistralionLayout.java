/**
 * 
 */
package com.metoo.ui;

import android.widget.Button;
import android.widget.EditText;

import com.metoo.R;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * @author theurgist
 *
 */
public class UserRegistralionLayout extends BaseLayout {

	EditText etLogin, etPasswd, etPasswdConfirm;
	Button btnTryRegister;

	/**
	 * @param parent
	 * @param previous
	 */
	public UserRegistralionLayout(BaseActivity parent, BaseLayout previous) {
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

	}
	@Override
	public void Deactivate() {
	}

	@Override
	protected Boolean preloadRoutine() {
		return true;
	}

}
