/**
 * 
 */
package com.metoo.ui;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.metoo.R;
import com.metoo.common.AppSettings;
import com.metoo.common.androidutils.AndroidAppLog;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * Модель окна настроек
 * @author Theurgist
 */
public class SettingsLayout extends BaseLayout {
	Button btnSettingsResetCred;
	EditText etSettingsSplashTime;
	EditText etSettingsSrvURL;
	Button btnResetSettings;
	CheckBox chbEmulationMode;

	public SettingsLayout(BaseActivity parent, BaseLayout previous) {
		super(parent, previous);
	}
	
	public void SaveSettings() {
		AppSettings.SetSrvUrl(etSettingsSrvURL.getText().toString());
		AppSettings.SetSplashDelay(Integer.parseInt(etSettingsSplashTime.getText().toString()));
	}

	public void onSuccess(String Result) { }
	public void onError(String Reason) { }
	public void onProgress(String Message) { }

	@Override
	public void Activate() { 
		activity.setContentView(R.layout.screen_settings);

		btnSettingsResetCred = (Button)activity.findViewById(R.id.btnSettingsResetCred);
		etSettingsSplashTime = (EditText)activity.findViewById(R.id.etSettingsSplashTime);
		etSettingsSrvURL = (EditText)activity.findViewById(R.id.etSettingsSrvURL);
        btnResetSettings = (Button)activity.findViewById(R.id.btnResetSettings);
        chbEmulationMode = (CheckBox)activity.findViewById(R.id.chbEmulationMode);
		
		btnSettingsResetCred.setOnClickListener(new onResetCred());

        btnResetSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AppSettings.ResetSettings(activity.services);
            	activity.services.ShowInfoAlert("Уведомление", "Все настрйки сброшены на настройки по-умолчанию");
            	AndroidAppLog.W("Настройки сброшены");
            }
        });
        chbEmulationMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AppSettings.SetEmulationMode(chbEmulationMode.isChecked());
            	AppSettings.SaveOnDisk(activity.services);
            	AndroidAppLog.W("'TestMode switched to' " + chbEmulationMode.isChecked());
            }
        });
        

        // Инициализация содержания UI
		etSettingsSrvURL.setText(AppSettings.GetSrvUrl());
		etSettingsSplashTime.setText(AppSettings.GetSplashDelay().toString());
        chbEmulationMode.setChecked(AppSettings.GetEmulationMode());
	}

	@Override
	public void Deactivate() { }


	@Override
	protected Boolean preloadRoutine() {
		return true;
	}

	// Listeners
	class onResetCred implements View.OnClickListener { public void onClick(View arg0) {
        	AppSettings.SetCreditials("", "");
        	AppSettings.SaveOnDisk(activity.services);
		}}
}
