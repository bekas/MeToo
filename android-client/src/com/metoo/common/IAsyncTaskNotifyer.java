/**
 * 
 */
package com.metoo.common;

/**
 * @author Theurgist
 * Notifyes on layout preload result
 */
public interface IAsyncTaskNotifyer<ResultType, ErrorMessageType, ProgressMessageType> {

	public void onSuccess(ResultType Result);
	
	public void onError(ErrorMessageType Reason);
	
	public void onProgress(ProgressMessageType Message);
}
