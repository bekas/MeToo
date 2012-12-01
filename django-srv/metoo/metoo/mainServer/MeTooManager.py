# -*- coding: utf-8 -*-
'''
Модуль, отвечающий за работу с походами на события.
'''
from django.db import models
from models import Session, User, Event, Place, City, Country, Photo
from timeManager import TimeManager, Worker
from configurationManager import ConfigurationManager
from sessionManager import SessionManager
from eventManager import EventManager

class MeTooManager:
	'''
	Класс, позволяющий узнать, кто идет на конкретное событие,\n
	пойти самому, изменить тип похода или отказаться от события
	'''
	def getUsersbyEvent(sessionId,eventId):
		'''
		Метод, позволяющий узнать, кто идет на конкретное событие
		'''
		result = {}
		userId = SessionManager.getUser(sessionId)
		if(userId != -1):
			if(MeToo.objects.get(userId=userId,eventId = eventId).exist):
				metoo = MeToo.objects.filter(eventId = eventId)
				result['list'] = []
				for met in metoo:
					addUser = {}
					addUser['id'] = met.userId.pk
					addUser['login'] = met.userId.login
					addUser['avatar'] = met.userId.avatarId.photo
					addUser['rating'] = met.userId.rating
					result['list'].append(addUser)
				result['result'] = 500
			else:
				result['result'] = 502
		else:
			result['result'] = 501
		return result
		
	def meToo(sessionId,eventId,metooTypeId):
		'''
		Метод, для того, чтобы пойти на событие
		'''
		userId = SessionManager.getUser(sessionId)
		if(userId != -1):
			metoo = MeToo(userId=userId,eventId = eventId, metooTypeId = metooTypeId)
			metoo.save()
			result = 500
		else:
			result = 501
		return result
		
	def delMeToo(sessionId,eventId):
		'''
		Метод отказа от события
		'''
		userId = SessionManager.getUser(sessionId)
		if(userId != -1):
			metoo = MeToo.objects.get(userId=userId,eventId = eventId)
			metoo.delete()
			result = 500
		else:
			result = 501
		return result
		
	def modMeToo(sessionId,eventId,metooTypeId):
		'''
		Метод для измения типа похода
		'''
		userId = SessionManager.getUser(sessionId)
		if(userId != -1):
			metoo = MeToo.objects.get(userId=userId,eventId = eventId)
			metoo.metooTypeId = metooTypeId
			result = 500
		else:
			result = 501
		return result
