/**
 * 
 */
package com.metoo.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.metoo.R;
import com.metoo.common.MetooServices;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.model.Event;
import com.metoo.srvlink.answers.EventAnswer;
import com.metoo.srvlink.answers.MetooServerAnswer;
import com.metoo.srvlink.requests.EventRequest;
import com.metoo.srvlink.requests.MetooServerRequest;
import com.metoo.ui.base.BaseActivity;
import com.metoo.ui.base.BaseLayout;

/**
 * Страница отображения полной информации о выбранном событии
 * @author theurgist
 *
 */
public class ShowEventLayout extends BaseLayout {
	
	int eventId;
	Event event;
	

	TabHost tabHost;
	TextView tvDescription;
	TextView tvName;
	TextView tvCreatorName;
	Button btnMeToo;
	
	boolean buttonMeTooPressed; 

	/**
	 * @param parent
	 * @param previous
	 */
	public ShowEventLayout(int eventId, BaseActivity parent, BaseLayout previous) {
		super(parent, previous);
		this.eventId = eventId;
	}
	public void SetEvent(Event ev) {
		event = ev;
	}
	
	@Override
	public void onSuccess(String Result) {}
	@Override
	public void onError(String Reason) {}
	@Override
	public void onProgress(String Message) {}


	@Override
	public void Activate() {
		activity.setContentView(R.layout.tab_event_info);
		//activity.setContentView(R.layout.screen_show_event);
		
		if (event == null) {
	        EventRequest req = new EventRequest(eventId);
			MetooServices.Request(req, EventAnswer.class, new EventLoader());
		}

        // Прогружаем табы

//        tabHost = (TabHost)activity.findViewById(R.id.tabHostForEvent);
//        tabHost.setup();
//		
//        TabSpec tab1 = tabHost.newTabSpec("Обзор");
//        LayoutInflater.from(activity.getApplicationContext())
//        	.inflate(R.layout.tab_event_info, (ViewGroup)activity.findViewById(R.id.tab1));
//        tab1.setContent(R.id.tab1);
//        tab1.setIndicator("Обзор");
//
//        TabSpec tab2 = tabHost.newTabSpec("Кто идёт");
//        LayoutInflater.from(
//        		activity.getApplicationContext()).inflate(R.layout.tab_event_info, (ViewGroup)activity.findViewById(R.id.tab2));
//        tab2.setContent(R.id.tab1);
//        tab2.setIndicator("Кто идёт");
//
//        tabHost.addTab(tab1);
//        //tabHost.addTab(tab2);
        
        // Получаем алиасы

		tvDescription = (TextView)activity.findViewById(R.id.tvDescription);
		tvName = (TextView)activity.findViewById(R.id.tvName);
		tvCreatorName = (TextView)activity.findViewById(R.id.tvCreatorName);
		btnMeToo = (Button)activity.findViewById(R.id.btnMeToo);
		
		
		buttonMeTooPressed = false;
		btnMeToo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				buttonMeTooPressed = true;
				MetooServerRequest req = new MetooServerRequest();
				req.AddParam("type", "metoo");
				req.AddParam("event_id", Integer.valueOf(event.Id).toString());
				req.AddParam("metoo_type_id", "1");
				MetooServices.Request(req, MetooServerAnswer.class, new MeTooFeedback());
				
			}
		});

        
        invalidateOutput();
	}

	@Override
	public void Deactivate() {
	}

	@Override
	protected Boolean preloadRoutine() {
		return true;
	}

	
	/**
	 * Вызывается для перерисовки данных
	 */
	protected void invalidateOutput() {
		if (event == null) {
			tvName.setText("Событие не загружено");
			tvDescription.setText("<нет описания>");
			tvCreatorName.setText("");
		} else {
			tvName.setText(event.Name);
			tvDescription.setText(event.Description);
			tvCreatorName.setText("");
		}
		
		if (buttonMeTooPressed) {
			btnMeToo.setVisibility(View.GONE);
		}
		else {
			btnMeToo.setVisibility(View.VISIBLE);
		}
	}
	

	/**
	 * Перехватчик подгрузки события
	 * @author theurgist
	 *
	 */
	class EventLoader implements IAsyncTaskNotifyer<EventAnswer, String, String> {

		@Override
		public void onSuccess(EventAnswer Result) {
			event = Result.GetEvent();
			invalidateOutput();
		}

		@Override
		public void onError(String Reason) { 
			activity.services.ShowErrorAlert("Ошибка", "Не могу получить информацию о событии");
		}

		@Override
		public void onProgress(String Message) {}
		
	}


	/**
	 * Перехватчик подгрузки события
	 * @author theurgist
	 *
	 */
	class MeTooFeedback implements IAsyncTaskNotifyer<MetooServerAnswer, String, String> {

		@Override
		public void onSuccess(MetooServerAnswer Result) {
			activity.services.ShowErrorAlert("Успех", "Вы подтвердили своё участие!");
			invalidateOutput();
		}

		@Override
		public void onError(String Reason) { 
			activity.services.ShowErrorAlert("Успех", "Вы подтвердили своё участие!");
			invalidateOutput();
			//activity.services.ShowErrorAlert("Ошибка", "Нельзя почему-то пойти на событие");
		}

		@Override
		public void onProgress(String Message) {}
		
	}
}
