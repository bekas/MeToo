# -*- coding: utf-8 -*-
'''
Модуль, отвечающий за работу с ошибками.
'''
from django.db import models
from timeManager import TimeManager, Worker
from configurationManager import ConfigurationManager
from sessionManager import SessionManager

'''
Здесь мог бы быть ваш код
'''
class ErrorManager:
	'''
	Класс работы с ошибками
	'''
	Success = 100
	UserExists = 201
	UserError = 202
	
	AuthBadLogin = 101 # неверный логи
	AuthBadPass = 102 # неверный пароль
	AuthError = 103 #ошибка при получении session_id
	
	AuthNothing = 110
	
	EventCreateError = 301
	EventGetError = 302
	
	ErrorMessage = {100: "Все ок!", 101: "Пользователя с таким логином не существует", 102: "Неверный пароль",103: "Ошибка при создании сессии", 110 : "Пожалуйста, авторизуйтесь!", 201 : "Пользователь с таким именем уже существует!", 202: "Ошибка при создании пользователя", 301 : "Ошибка при создании события", 302 : "Ошибка при выполнения запроса событий" }
	
	@staticmethod
	def getError(code):
		'''
		Метод получения сообщения ошибки
		'''
		if ErrorManager.ErrorMessage.has_key(code):
			return ErrorManager.ErrorMessage[code]
		else:
			return ""
	
