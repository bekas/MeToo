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
import com.metoo.common.IAsyncTaskNotifyer;
import com.metoo.srvlink.XmlAnswer;
import com.metoo.srvlink.base.Connector;
import com.metoo.srvlink.requests.MetooServerRequest;
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
	CheckBox chbIsNewEventPrivate;

	Connector connect;
	
	GeoPoint locationOfNewEvent;

	/**
	 * @param parent
	 * @param previous
	 */
	public NewEventLayout(BaseActivity parent, BaseLayout previous, GeoPoint crd) {
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
		MetooServerRequest req = new MetooServerRequest();
		req.AddParam("type", "create_event");
		req.AddParam("name", etNewEventName.getText().toString());
		req.AddParam("description", etNewEventDescr.getText().toString());
		req.AddParam("latitude", String.format("%.8f", locationOfNewEvent.getLatitudeE6()/1E6));
		req.AddParam("longitude", String.format("%.8f", locationOfNewEvent.getLongitudeE6()/1E6));
		
		try {
			connect.SendSimpleRequest(req, new SaveEventAnswerReceiver());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}}

	
	class SaveEventAnswerReceiver implements IAsyncTaskNotifyer<String, String, String> {

		public void onSuccess(String Result) {
			XmlAnswer ans = new XmlAnswer();
			ans.ParseMessage(Result);
			
			
			if (ans.type == "eventcreate") {
				if (ans.result == 0) {
					activity.services.ShowToast("Событие создано!");
					onEventCreated();
				}
				else {
					activity.services.ShowInfoAlert("Ошибка создания события", "Код ошибки: " + ans.result);
					onEventNotCreated();
				}
			}
			else
				activity.services.ShowInfoAlert("Ошибка", "SaveEventAnswerReceiver: пришел ответ типа" + ans.type);
			
			
		}
		public void onError(String Reason) {
			activity.services.ShowToast("Ошибка в модуле MapDataReceiver: " + Reason);
		}
		public void onProgress(String Message) {
		}
	}
}
