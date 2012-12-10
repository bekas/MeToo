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
	
	def __init__(self):
		a = 1+1
		#self.doWork(ConfigurationManager.loopDeleteSessionInterval())
	
	def work(self):
		'''
		Вызываемый по таймеру метод
		'''
		currentTime = TimeManager.getTime()
		deleteInterval = ConfigurationManager.sessionDeleteInterval()
		time = currentTime - deleteInterval
		Session.objects.filter(referenceTime__lt = time).delete()
		#Session.objects.filter().delete()
		self.test = self.test + 3
		#s = Session(userId_id = 1, referenceTime = TimeManager.getTime())
		#s.save()
		print("Session delete works")
		
class SessionManager:	
	'''
	Класс для работы с сессиями.Позволяет:\n
	- Получить сессию для указанного пользователя
	- Создать сессию для пользователя
	- Проверить, существует ли сессия
	- Получить userId по сессии
	'''
	checkSessionWorker = CheckSessionWorker()
	
	timerWorks = False
	
	@staticmethod
	def stopTimer():
		SessionManager.checkSessionWorker.stop()
	
	@staticmethod
	def startTimer(delay):
		SessionManager.checkSessionWorker.start(delay)
	
	@staticmethod
	def startTimerIfNot():
		if not SessionManager.timerWorks:
			SessionManager.timerWorks = True
			SessionManager.startTimer(ConfigurationManager.loopDeleteSessionInterval())	
	
	@staticmethod
	def __init__():
		'''
		Конструктор.Запускает очистку сессий.
		'''
		s = Session(userId_id = 1, referenceTime = TimeManager.getTime())
		s.save()
		SessionManager.checkSessionWorker.doWork(ConfigurationManager.loopDeleteSessionInterval())

	@staticmethod
	def getSessionID(userId):
		'''
		Метод для получения id сессии по id пользователя
		'''
		SessionManager.startTimerIfNot()
		if(User.objects.filter(pk = userId).exists()):
			#print("Exists")
			return SessionManager.createSession(User.objects.get(pk = userId)) 
		else:
			#print("Not exisits")
			return -1
	
	@staticmethod
	def createSession(user):
		'''
		Метод для создания сессии для пользователя
		'''
		sessionId = -1
		try:
			newSession = Session(userId = user, referenceTime = TimeManager.getTime())
			#print("create")
			newSession.save()
			#print("save")
			sessionId = newSession.pk
			#print("pk")
		except:
			sessionId = -1
		return sessionId
	
	@staticmethod 
	def freeSession(sessionId):
		userId = SessionManager.getUserId(sessionId)
		if userId > 0:
			Session.objects.filter(pk = sessionId).delete()
		return userId
		
	@staticmethod
	def checkSession(sessionId):
		'''
		Метод для проверки существования сессии 
		'''
		if Session.objects.filter(pk = sessionId).exists():
			Session.objects.filter(pk = sessionId).referenceTime = TimeManager.getTime()
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
		
	@staticmethod	
	def	isUserOnline(user):
		'''
		Метод для проверки находится ли пользователь онлайн
		'''
		return Session.objects.filter(userId = user).exists()

	@staticmethod
	def	isUserIdOnline(userId):
		'''
		Метод для проверки находится ли пользователь онлайн по id
		'''
		return Session.objects.filter(userId_id = userId).exists()




		
	
