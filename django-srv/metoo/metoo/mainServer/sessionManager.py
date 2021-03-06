# -*- coding: utf-8 -*-
'''
Модуль работы с сессиями
'''
from django.db import models
from models import Session, User
from timeManager import TimeManager, Worker
from configurationManager import ConfigurationManager

class CheckSessionWorker(Worker):
	'''
	Класс, переодически подчиющий таблицу сессий
	'''
	test = 0
	def work(self):
		'''
		Вызываемый по таймеру метод
		'''
		currentTime = TimeManager.getTime()
		deleteInterval = ConfigurationManager.sessionDeleteInterval()
		time = currentTime + deleteInterval
		Session.objects.filter(referenceTime__lt = time).delete()
		self.test = self.test + 1
		
class SessionManager:	
	'''
	Класс для работы с сессиями.Позволяет:\n
	- Получить сессию для указанного пользователя
	- Создать сессию для пользователя
	- Проверить, существует ли сессия
	- Получить userId по сессии
	'''
	checkSessionWorker = CheckSessionWorker()
	
	@staticmethod
	def __init__():
		'''
		Конструктор.Запускает очистку сессий.
		'''
		SessionManager.checkSessionWorker.doWork(ConfigurationManager.loopDeleteSessionInterval())

	@staticmethod
	def getSessionID(userId):
		'''
		Метод для получения id сессии по id пользователя
		'''
		return SessionManager.createSession(User.objects.get(pk = userId)) 
	
	@staticmethod
	def createSession(user):
		'''
		Метод для создания сессии для пользователя
		'''
		sessionId = -1
		try:
			newSession = Session(userId = user, referenceTime = TimeManager.getTime())
			newSession.save()
			sessionId = newSession.pk
		except:
			sessionId = -1
		return sessionId

	@staticmethod
	def checkSession(sessionId):
		'''
		Метод для проверки существования сессии 
		'''
		return Session.objects.filter(pk = sessionId).exists()
	
	@staticmethod
	def getUserId(sessionId):
		'''
		Метод для получения userId по sessionId
		'''
		userId = -1	
		#userId = 0 - SessionManager.checkSessionWorker.test
		if SessionManager.checkSession(sessionId):
			reqSession = Session.objects.get(pk = sessionId)
			reqSession.referenceTime = TimeManager.getTime()
			reqSession.save()
			userId = reqSession.userId.pk
		return userId
		
	@staticmethod
	def getUser(sessionId):
		'''
		Метод для получения user по sessionId
		'''
		user = None	
		#userId = 0 - SessionManager.checkSessionWorker.test
		if SessionManager.checkSession(sessionId):
			reqSession = Session.objects.get(pk = sessionId)
			reqSession.referenceTime = TimeManager.getTime()
			reqSession.save()
			user = reqSession.userId
		return user
		
		
	
