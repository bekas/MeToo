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
	Класс, позволяющиййй узнать, кто идет на конкретное событие,\n
	пойти самому, изменить тип похода или отказаться от события
	'''
	def getUsersbyEvent(eventId):
		'''
		Узнать, кто идет на конкретное событие
		'''
		
	def meToo(sessionId,eventId,metooTypeId):
		'''
		Пойти на событие
		'''
		userId = SessionManager.getUser(sessionId)
		if(userId != -1):
			metoo = MeToo(userId=userId,eventId = eventId, metooTypeId = metooTypeId)
			metoo.save()
		
	def delMeToo(sessionId,eventId):
		'''
		Отказаться от события
		'''
	def modMeToo(sessionId,eventId,metooTypeId):
		'''
		Изменить тип похода
		'''
		
