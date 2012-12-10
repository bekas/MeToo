/**
 * 
 */
package com.metoo.ui.validators;

import android.text.Editable;

/**
 * Валидация правильности ввода имени пользователя
 * @author theurgist
 *
 */
public class UsernameValidator extends BaseValidator {

	public void afterTextChanged(Editable arg0) {
		_isValid = arg0.length() > 0;
		ValidateInput();
	}
	public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
	public void onTextChanged(CharSequence s, int start, int before, int count) { }

}
