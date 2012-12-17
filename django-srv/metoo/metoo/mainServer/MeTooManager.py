# -*- coding: utf-8 -*-
'''
Модуль, отвечающий за работу с походами на события.
'''
from django.db import models
from models import Session, User, Event, Place, City, Country, Photo, Metoo
from timeManager import TimeManager, Worker
from configurationManager import ConfigurationManager
from sessionManager import SessionManager
from eventManager import EventManager
from errorManager import ErrorManager

class MeTooManager:
	'''
	Класс, позволяющий узнать, кто идет на конкретное событие,\n
	пойти самому, изменить тип похода или отказаться от события
	'''
	@staticmethod
	def getUsersbyEvent(sessionId,eventId):
		'''
		Метод, позволяющий узнать, кто идет на конкретное событие
		'''
		result = {}
		userId = SessionManager.getUser(sessionId)
		if(userId != None):
			if(Metoo.objects.filter(userId=userId,eventId = eventId).exists()):
				metoo = Metoo.objects.filter(eventId = eventId)
				result['users'] = []
				for met in metoo:
					addUser = {}
					addUser['id'] = met.userId.pk
					addUser['login'] = met.userId.login
					addUser['avatar'] = met.userId.avatarId.photo
					addUser['rating'] = met.userId.rating
					result['users'].append(addUser)
				result['result'] = 500
			else:
				result['result'] = 502
		else:
			result['result'] = 501
		return result
	
	@staticmethod
	def meTooUser(sessionId,userId,eventId):
		'''
		Метод для того, чтобы узнать, идет ли данный юзер на событие
		'''
		userIdL = SessionManager.getUser(sessionId)
		metooType = -1
		result = ErrorManager.Success
		if(userIdL != None):
			if EventManager.checkEvent(eventId):
				try:
					if(Metoo.objects.filter(userId = userId,eventId_id = eventId).exists()):
						result = ErrorManager.Success
					else:
						result = ErrorManager.MeTooNoSuccess
				except:
					result = ErrorManager.MeTooError
			else:
				result = ErrorManager.EventExistError	
		else:
			result = ErrorManager.AuthNothing
		return result
		
	@staticmethod
	def meTooI(sessionId,eventId):	
		'''
		Метод для того, чтобы узнать, идет ли данный юзер на событие
		'''
		userId = SessionManager.getUser(sessionId)
		return MeTooManager.meTooUser(sessionId,userId,eventId)
		
	
	@staticmethod	
	def meToo(sessionId,eventId,metooTypeId):
		'''
		Метод, для того, чтобы пойти на событие
		'''
		userId = SessionManager.getUser(sessionId)
		if(userId != None):
			if EventManager.checkEvent(eventId):
				try:
					if(not Metoo.objects.filter(userId=userId,eventId_id = eventId).exists()):
						metoo = Metoo(userId=userId,eventId_id = eventId, metooTypeId_id = metooTypeId)
						metoo.save()
					result = ErrorManager.Success
				except:
					result = ErrorManager.MeTooError
			else:
				result = ErrorManager.EventExistError	
		else:
			result = ErrorManager.AuthNothing
		return result
	
	@staticmethod	
	def delMeToo(sessionId,eventId):
		'''
		Метод отказа от события
		'''
		userId = SessionManager.getUser(sessionId)
		if(userId != -1):
			if(Metoo.objects.filter(userId=userId,eventId_id = eventId).exists()):
				metoo = Metoo.objects.filter(userId=userId,eventId_id = eventId)
				metoo.delete()
			result = ErrorManager.Success
		else:
			result = ErrorManager.MeTooError
		return result

	@staticmethod	
	def modMeToo(sessionId,eventId,metooTypeId):
		'''
		Метод для измения типа похода
		'''
		userId = SessionManager.getUser(sessionId)
		if(userId != -1):
			if(Metoo.objects.filter(userId=userId,eventId_id = eventId).exists()):
				metoo = Metoo.objects.get(userId=userId,eventId_id = eventId)
				metoo.metooTypeId_id = metooTypeId
				metoo.save()
			result = ErrorManager.Success
		else:
			result = ErrorManager.MeTooError
		return result
