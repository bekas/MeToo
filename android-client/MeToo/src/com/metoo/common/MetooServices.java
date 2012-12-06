/**
 * 
 */
package com.metoo.common;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.common.javautils.ClassChecker;
import com.metoo.srvlink.answers.EventListAnswer;
import com.metoo.srvlink.answers.MetooServerAnswer;
import com.metoo.srvlink.base.Connector;
import com.metoo.srvlink.requests.MetooServerRequest;
import com.metoo.xmlparser.INodeSerializer;
import com.metoo.xmlparser.PageParser;
import com.metoo.xmlparser.TaggedDoc;

/**
 * Статический объект, позволяющий осуществлять взаимодействие с сервером MeToo
 * и получать у него необходимые данные
 * @author theurgist
 *
 */
public final class MetooServices {
    @SuppressWarnings("unused")
	private static MetooServices INSTANCE;

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
	public MetooServices() {
		INSTANCE = new MetooServices();
		connect = new Connector("http", AppSettings.GetSrvUrl()+":"+AppSettings.GetSrvPort());
		parser = new PageParser();
	}
	
	public static <T extends MetooServerAnswer>
	void Request(MetooServerRequest request, Class<T> answerClass, IAsyncTaskNotifyer<T, String, String> callback) {
		
		DataReceiverHook<T> hookCallback = INSTANCE.new DataReceiverHook<T>(callback, answerClass);
		INSTANCE.connect.SendSimpleRequest(request, hookCallback);
	}
	
	
	/**
	 * Перехватчик ответа с сервера
	 * @author theurgist
	 */
	
	class DataReceiverHook<T extends MetooServerAnswer> implements IAsyncTaskNotifyer<String, String, String> {
		/**
		 * Обратная связь, переданная при отправке запроса
		 */
		IAsyncTaskNotifyer<T, String, String> originalCallback;
		Class<T> answerClass;
		
		/**
		 * Инициализация крючка
		 * @param originalCallback Куда дублировать события
		 * @param answerClass Какого типа был запрошен ответ
		 */
		DataReceiverHook(IAsyncTaskNotifyer<T, String, String> originalCallback, Class<T> answerClass) {
			this.originalCallback = originalCallback;
			this.answerClass = answerClass;
		}

		public void onSuccess(String Result) {
			try {
				T answer = ParseAnswer(Result);
				originalCallback.onSuccess(answer);
				
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
			originalCallback.onError(Reason);
		}
		public void onProgress(String Message) {
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
