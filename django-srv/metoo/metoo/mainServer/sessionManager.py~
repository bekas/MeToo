from django.db import models
from models import Session, User
from timeManager import TimeManager, Worker
from configurationManager import ConfigurationManager

# Module sessionManager. This module work with sessions: create new session,
# get user by session and 

class CheckSessionWorker(Worker):
	test = 0
	def work(self):
		currentTime = TimeManager.getTime()
		deleteInterval = ConfigurationManager.sessionDeleteInterval()
		time = currentTime + deleteInterval
		Session.objects.filter(referenceTime__lt = time).delete()
		self.test = self.test + 1
		
class SessionManager:	
	checkSessionWorker = CheckSessionWorker()
	
	@staticmethod
	def __init__():
		SessionManager.checkSessionWorker.doWork(ConfigurationManager.loopDeleteSessionInterval())

	@staticmethod
	def getSessionID(userId):
		return SessionManager.createSession(User.objects.get(pk = userId)) 
	
	@staticmethod
	def createSession(user):
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
		return Session.objects.filter(pk = sessionId).exists()
	

	# method to get user by session 
	@staticmethod
	def getUserId(sessionId):
		userId = -1	
		#userId = 0 - SessionManager.checkSessionWorker.test
		if SessionManager.checkSession(sessionId):
			reqSession = Session.objects.get(pk = sessionId)
			reqSession.referenceTime = TimeManager.getTime()
			reqSession.save()
			userId = reqSession.userId.pk
		return userId
		
	# method to get user by session 
	@staticmethod
	def getUser(sessionId):
		user = -1	
		#userId = 0 - SessionManager.checkSessionWorker.test
		if SessionManager.checkSession(sessionId):
			reqSession = Session.objects.get(pk = sessionId)
			reqSession.referenceTime = TimeManager.getTime()
			reqSession.save()
			user = reqSession.userId
		return user
	
