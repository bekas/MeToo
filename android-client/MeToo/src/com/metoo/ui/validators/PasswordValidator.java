/**
 * 
 */
package com.metoo.ui.validators;

import android.text.Editable;

/**
 * Валидация правильности ввода пароля
 * @author theurgist
 *
 */
public class PasswordValidator extends BaseValidator {

	@Override
	public void afterTextChanged(Editable arg0) {
		_isValid = arg0.length() > 0;
		ValidateInput();
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) { }
}
