/**
 * 
 */
package com.metoo.common.androidutils;

/**
 * Сообщает о прогрессе выполнения асинхронных операций
 * @author Theurgist
 */
public interface IAsyncTaskNotifyer<ResultType, ErrorMessageType, ProgressMessageType> {

	/**
	 * Метод, вызываемый при удачном завершении асинхронного вызова
	 * @param Result
	 */
	public void onSuccess(ResultType Result);
	
	/**
	 * Метод, вызываемый при завершении асинхронного вызова с ошибкой
	 * @param Reason
	 */
	public void onError(ErrorMessageType Reason);
	
	/**
	 * Метод, вызываемый асинхронной рутиной с помощью функции publishProgress().
	 * Выполняется в ui-процессе!
	 * @param Message
	 */
	public void onProgress(ProgressMessageType Message);
}
