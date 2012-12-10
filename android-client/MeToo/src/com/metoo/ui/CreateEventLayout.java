/**
 * 
 */
package com.metoo.ui;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.maps.GeoPoint;
import com.metoo.R;
import com.metoo.common.AppSettings;
import com.metoo.common.MetooServices;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.srvlink.answers.CreateEventAnswer;
import com.metoo.srvlink.base.Connector;
import com.metoo.srvlink.requests.CreateEventRequest;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * Модель окна создания события
 * @author theurgist
 */
public class CreateEventLayout extends BaseLayout {
	EditText etNewEventName;
	EditText etNewEventDescr;
	Button btnCreateEvent;
	CheckBox chbIsNewEventPrivate;

	Connector connect;
	
	GeoPoint locationOfNewEvent;

	/**
	 * @param parent
	 * @param previous
	 */
	public CreateEventLayout(BaseActivity parent, BaseLayout previous, GeoPoint crd) {
		super(parent, previous);
		locationOfNewEvent = crd;
		
		connect = new Connector("http", AppSettings.GetSrvUrl()+":"+AppSettings.GetSrvPort());
	}


	@Override
	public void onSuccess(String Result) { }
	@Override
	public void onError(String Reason) { }
	@Override
	public void onProgress(String Message) { }
	
	
	private void onEventCreated() {
		activity.services.ShowToast("Событие создано!");
		activity.finish();
	}
	
	private void onEventNotCreated() {
		
	}
	

	@Override
	public void Activate() {
		activity.setContentView(R.layout.screen_create_event);

		btnCreateEvent = (Button)activity.findViewById(R.id.btnCreateEvent);
		etNewEventName = (EditText)activity.findViewById(R.id.etNewEventName);
		etNewEventDescr = (EditText)activity.findViewById(R.id.etNewEventDescr);
        chbIsNewEventPrivate = (CheckBox)activity.findViewById(R.id.chbIsNewEventPrivate);
        
        btnCreateEvent.setOnClickListener(new onSaveEvent());
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
		CreateEventRequest req = new CreateEventRequest(
				etNewEventName.getText().toString(),
				etNewEventDescr.getText().toString(),
				locationOfNewEvent.getLatitudeE6()/1E6,
				locationOfNewEvent.getLongitudeE6()/1E6);
		
		try {
			MetooServices.Request(req, CreateEventAnswer.class, new SaveEventAnswerReceiver());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}}

	
	class SaveEventAnswerReceiver implements IAsyncTaskNotifyer<CreateEventAnswer, String, String> {

		public void onSuccess(CreateEventAnswer Result) {

			if (Result.GetError() != null)
				onError("Ошибка чтения ответа сервера: " + Result.GetError());
				
			else if (Result.GetRequestResult() == 0)
				onEventCreated();

			else
				onError("Событие не создано! Код ошибки: " + Result.GetRequestResult());
			
		}
		public void onError(String Reason) {
			activity.services.ShowErrorAlert("Ошибка", Reason);
			onEventNotCreated();
		}
		public void onProgress(String Message) {
		}
	}
}
