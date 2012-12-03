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
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * Модель окна создания события
 * @author theurgist
 */
public class NewEventLayout extends BaseLayout {
	EditText etNewEventName;
	EditText etNewEventDescr;
	Button btnCreateEvent;
	CheckBox chbIsPrivate;

	/**
	 * @param parent
	 * @param previous
	 */
	public NewEventLayout(BaseActivity parent, BaseLayout previous) {
		super(parent, previous);
	}


	@Override
	public void onSuccess(String Result) { }
	@Override
	public void onError(String Reason) { }
	@Override
	public void onProgress(String Message) { }
	

	@Override
	public void Activate() {
		activity.setContentView(R.layout.screen_create_event);

		btnCreateEvent = (Button)activity.findViewById(R.id.btnCreateEvent);
		etNewEventName = (EditText)activity.findViewById(R.id.etNewEventName);
		etNewEventDescr = (EditText)activity.findViewById(R.id.etNewEventDescr);
        chbIsPrivate = (CheckBox)activity.findViewById(R.id.chbIsPrivate);
	}
	@Override
	public void Deactivate() {
	}

	@Override
	protected Boolean preloadRoutine() {
		return true;
	}
	

	// Listeners
	class onSaveEvent implements View.OnClickListener { public void onClick(View arg0) {
        	
		}}

}
