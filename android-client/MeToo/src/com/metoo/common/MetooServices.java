/**
 * 
 */
package com.metoo.common;

import java.lang.reflect.InvocationTargetException;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.common.javautils.ClassChecker;
import com.metoo.model.Event;
import com.metoo.srvlink.answers.LoginAnswer;
import com.metoo.srvlink.answers.MetooServerAnswer;
import com.metoo.srvlink.answers.UserRegistrationAnswer;
import com.metoo.srvlink.base.Connector;
import com.metoo.srvlink.requests.LoginRequest;
import com.metoo.srvlink.requests.MetooServerRequest;
import com.metoo.srvlink.requests.UserRegistrationRequest;
import com.metoo.xmlparser.PageParser;

/**
 * Статический объект, позволяющий осуществлять взаимодействие с сервером MeToo
 * и получать у него необходимые данные
 * @author theurgist
 *
 */
public final class MetooServices {
	static MetooServices INSTANCE = new MetooServices();

    /**
     * Связь с сервером осуществляется через этот объект
     */
	protected Connector connect;
	/**
	 * Механизм разбора ответов
	 */
	protected PageParser parser;

	/**
	 * Инициализация сервисов Metoo
	 */
	public static void Initialize() {
		INSTANCE.connect = new Connector("http", AppSettings.GetSrvUrl()+":"+AppSettings.GetSrvPort());
		INSTANCE.parser = new PageParser();
	}
	
	/**
	 * Отправка запроса на сервер
	 * @param request Объект-описатель запроса
	 * @param answerClass Тип объекта-описателя ответа
	 * @param callback Обратная связь
	 */
	public static <T extends MetooServerAnswer>
	void Request(MetooServerRequest request, Class<T> answerClass, IAsyncTaskNotifyer<T, String, String> callback) {
		
		DataReceiverHook<T> hookCallback = INSTANCE.new DataReceiverHook<T>(request, callback, answerClass);
		hookCallback.SendRequest();
	}

	/**
	 * Запускает авторизацию пользователя. Имя и пароль берутся из настроек.
	 * @param callback Обратная связь
	 * @return True, если запрос отправлен, либо False, если логин-пароль пусты
	 */
	public static boolean Authorize(IAsyncTaskNotifyer<LoginAnswer, String, String> callback) {
		if (AppSettings.GetLogin().isEmpty() || AppSettings.GetPassword().isEmpty()) {
			if (callback != null)
				callback.onError("Credentials are empty!");
			return false;
		}
		
		LoginRequest request = new LoginRequest(AppSettings.GetLogin(), AppSettings.GetPassword());
		AppSettings.SetIsLoggedIn(true);

		DataReceiverHook<LoginAnswer> hookCallback = INSTANCE.new DataReceiverHook<LoginAnswer>(request, callback, LoginAnswer.class);
		hookCallback.SendRequest();
		
		return true;
	}

	/**
	 * Запускает регистрацию пользователя. Имя и пароль берутся из настроек.
	 * @param callback Обратная связь
	 * @return True, если запрос отправлен, либо False, если логин-пароль пусты
	 */
	public static boolean Registrate(IAsyncTaskNotifyer<UserRegistrationAnswer, String, String> callback) {
		if (AppSettings.GetLogin().isEmpty() || AppSettings.GetPassword().isEmpty()) {
			if (callback != null)
				callback.onError("Credentials are empty!");
			return false;
		}
		
		UserRegistrationRequest request = new UserRegistrationRequest(AppSettings.GetLogin(), AppSettings.GetPassword());
		AppSettings.SetIsLoggedIn(true);

		DataReceiverHook<UserRegistrationAnswer> hookCallback = INSTANCE.new DataReceiverHook<UserRegistrationAnswer>(request, callback, UserRegistrationAnswer.class);
		hookCallback.SendRequest();
		
		return true;
	}
	
	/**
	 * Запускает переавторизацию пользователя
	 * @param callback Обратная связь
	 * @return True, если запрос отправлен, либо False, если логин-пароль пусты, либо автоматическая авторизация отключена
	 */
	public static boolean ReAuthorize(IAsyncTaskNotifyer<LoginAnswer, String, String> callback) {
		if (!AppSettings.GetIsLoggedIn()) {
			if (callback != null)
				callback.onError("Can't reauthorize because user was not authorized!");
			return false;
		}
		return Authorize(callback);
	}

	
	/*
	public static boolean RequestEventInfo(int Id, IAsyncTaskNotifyer<Event, String, String> callback) {
		
	}*/
	
	
	
	/**
	 * Перехватчик ответа с сервера
	 * @author theurgist
	 */
	class DataReceiverHook<T extends MetooServerAnswer> implements IAsyncTaskNotifyer<String, String, String> {

		/**
		 * Запрос, на который ожидается ответ
		 */
		MetooServerRequest request;
		/**
		 * Обратная связь, переданная при отправке запроса
		 */
		IAsyncTaskNotifyer<T, String, String> originalCallback;
		/**
		 * Объект, способный сериализовать ответ на запрос
		 */
		Class<T> answerClass;

		/**
		 * True, если на данный момент запущено перелогинивание
		 */
		boolean isRelogining;
		/**
		 * True, если для данного запроса уже был выполнен релогин
		 */
		boolean reloginWasAlreadyMade;
		
		/**
		 * Инициализация крючка
		 * @param originalCallback Куда дублировать события
		 * @param answerClass Какого типа был запрошен ответ
		 */
		DataReceiverHook(MetooServerRequest request, IAsyncTaskNotifyer<T, String, String> originalCallback, Class<T> answerClass) {
			this.request = request;
			this.originalCallback = originalCallback;
			this.answerClass = answerClass;
			this.isRelogining = false;
			this.reloginWasAlreadyMade = false;
		}

		/**
		 * Отправка перехватываемого запроса
		 */
		public void SendRequest() {
			INSTANCE.connect.SendSimpleRequest(request, this);
		}
		
		public void onSuccess(String Result) {
			try {
				
				if (isRelogining) {
					// Пришёл ответ на перелогин
					LoginAnswer loginAns = new LoginAnswer(Result, parser);
					if (loginAns.GetSessionId() > 0) {
						// Успешно перелогинились! Сохраняем id сессии и перезапускаем изначальный запрос
						reloginWasAlreadyMade = true;
						AppSettings.SetSessionId(loginAns.GetSessionId());
						request.AddParam("session_id", loginAns.GetSessionId().toString());
						
						isRelogining = false;
						reloginWasAlreadyMade = true;
						INSTANCE.connect.SendSimpleRequest(request, this);
					}
					else {
						// Попытка перелогина потрачена - сообщаем об ошибке
						onError("Недействительная пара логин-пароль");
					}
				}
				else {
					T answer = ParseAnswer(Result);
					if (answer.GetError() != null) {
						// Не смогли нормально распарсить
						onError(answer.GetError());
					}
					
					else if (answer.GetRequestResult() == 110) {
						// Ошибка: сессия недействительна
						
						// Если попытка перелогинивания не была потрачена - отправляем запрос!
						if (!reloginWasAlreadyMade) {
							isRelogining = true;
							LoginRequest reloginReq = new LoginRequest(AppSettings.GetLogin(), AppSettings.GetPassword());
							INSTANCE.connect.SendSimpleRequest(reloginReq, this);
						} else {
							// Попытка перелогина потрачена - сообщаем об ошибке
							onError("Reauthorization failed! (this point shouldn't be ever reached)");
						}
					} else if (originalCallback != null) {
						// Всё прошло удачно - отправляем пользователю результат
						originalCallback.onSuccess(answer);
					}
				}
				
			} catch (ClassCastException e) {
				onError("MetooServices: " + e.getMessage());
			} catch (InstantiationException e) {
				onError("MetooServices: " + e.getMessage());
			} catch (IllegalAccessException e) {
				onError("MetooServices: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				onError("MetooServices: " + e.getMessage());
			} catch (SecurityException e) {
				onError("MetooServices: " + e.getMessage());
			} catch (InvocationTargetException e) {
				onError("MetooServices: " + e.getMessage());
			} catch (NoSuchMethodException e) {
				onError("MetooServices: " + e.getMessage());
			}
		}
		public void onError(String Reason) {
			if (originalCallback != null)
				originalCallback.onError(Reason);
		}
		public void onProgress(String Message) {
			if (originalCallback != null)
				originalCallback.onProgress(Message);
		}
		
		/**
		 * Парсер приходящего ответа
		 * @param source Полученный от сервера XML-документ
		 * @return Объект ответа ожидаемого типа
		 * @throws IllegalAccessException 
		 * @throws InstantiationException 
		 * @throws NoSuchMethodException 
		 * @throws InvocationTargetException 
		 * @throws SecurityException 
		 * @throws IllegalArgumentException 
		 */
		T ParseAnswer(String source) 
				throws InstantiationException, IllegalAccessException, ClassCastException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException {
			
			if (!ClassChecker.CheckInheritance(answerClass, MetooServerAnswer.class)) 
				throw new ClassCastException("Class '"+ answerClass.getSimpleName() +
						"' is not derived from '" + MetooServerAnswer.class.getSimpleName() +
						"': cannot parse answer from server");
			
			T answer = answerClass.getDeclaredConstructor
					(String.class, PageParser.class).newInstance(source, parser);
			
			return answer;
		}
	}

}

/**
 * Перехватчик авторизации. При успехе записывает номер текущей сессии в память
 * @author theurgist
 *
 */
class LoginHook implements IAsyncTaskNotifyer<LoginAnswer, String, String> {
	
	/**
	 * Переданный пользователем объект обратной связи
	 */
	IAsyncTaskNotifyer<LoginAnswer, String, String> originalCallback;
	
	/**
	 * Создание объекта-крючка
	 * @param originalCallback Обратная связь с пользовательским кодом
	 */
	LoginHook(IAsyncTaskNotifyer<LoginAnswer, String, String> originalCallback) {
		this.originalCallback = originalCallback;
	}
	
	@Override
	public void onSuccess(LoginAnswer Result) {
		if (Result.GetError() == null) {
			AppSettings.SetSessionId(Result.GetSessionId());

			if (originalCallback != null)
				originalCallback.onSuccess(Result);
		}
		else
			onError(Result.GetError());

	}

	@Override
	public void onError(String Reason) {
		if (originalCallback != null)
			originalCallback.onError(Reason);
	}

	@Override
	public void onProgress(String Message) {
		if (originalCallback != null)
			originalCallback.onProgress(Message);
	}
}
	


/**
 * Перехватчик авторизации. При успехе записывает номер текущей сессии в память
 * @author theurgist
 *
 */
class RegistrationHook implements IAsyncTaskNotifyer<UserRegistrationAnswer, String, String> {
	
	/**
	 * Переданный пользователем объект обратной связи
	 */
	IAsyncTaskNotifyer<UserRegistrationAnswer, String, String> originalCallback;
	
	/**
	 * Создание объекта-крючка
	 * @param originalCallback Обратная связь с пользовательским кодом
	 */
	RegistrationHook(IAsyncTaskNotifyer<UserRegistrationAnswer, String, String> originalCallback) {
		this.originalCallback = originalCallback;
	}
	
	@Override
	public void onSuccess(UserRegistrationAnswer Result) {
		if (Result.GetError() == null) {
			AppSettings.SetSessionId(Result.GetSessionId());

			if (originalCallback != null)
				originalCallback.onSuccess(Result);
		}
		else
			onError(Result.GetError());

	}

	@Override
	public void onError(String Reason) {
		if (originalCallback != null)
			originalCallback.onError(Reason);
	}

	@Override
	public void onProgress(String Message) {
		if (originalCallback != null)
			originalCallback.onProgress(Message);
	}
}
	