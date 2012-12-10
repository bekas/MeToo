/**
 * 
 */
package com.metoo.ui.validators;

import android.text.TextWatcher;

/**
 * Базовый класс унифицированного валидатора
 * @author theurgist
 *
 */
public abstract class BaseValidator implements TextWatcher {
	
	/**
	 * Является ли текущее состояние допустимым
	 */
	public boolean IsValid() { return _isValid; }
	protected boolean _isValid = false;
	
	public void SetOnContentChangedListener(IOnContentChanged listener) {
		this.changeListener = listener;
	}
	IOnContentChanged changeListener;
	
	
	/**
	 * Делегат изменения содержимого
	 * @author theurgist
	 *
	 */
	public interface IOnContentChanged {
		/**
		 * Вызывается после фильтрации валидатором ввода
		 */
		void onContentChanged();
	}

	protected void ValidateInput() {
		if (changeListener != null)
			changeListener.onContentChanged();
	}

}
