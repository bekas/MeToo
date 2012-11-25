# -*- coding: utf-8 -*-
'''
Модуль, отвечающий за работу с событиями.
'''
from django.db import models
from models import Session, User
from timeManager import TimeManager, Worker
from configurationManager import ConfigurationManager
from sessionManager import SessionManager

class EventManager:
	'''
	Класс, предоставлящий возможности:\n
	-Создания событий;\n
	-Запроса событий по критерию;\n
	-Изменения событий;\n
	-Удаления событий;\n
	'''
	@staticmethod
	def createEvent(sessionId,eventArgs):
		'''
		Метод для создания событий (по сессии и списку аргументов)
		'''
		userId = SessionManager.getUserId(sessionId)
		
		#newEvent = Event(creatorId = userId, name = )
		return null
	
	@staticmethod
	def getEvents(sessiobId, conditionals):
		'''
		Метод для запроса событий (по сессии и запросу)
		'''
		userId = SessionManager.getUserId(sessionId)
		latitudeR = conditionals['latitude']
		longitudeR = conditionals['longitude']
		radius = conditionals['radius']
		
		events =  Events.objects.filter(latitude__range=(latitudeR-radius,latitudeR+radius),longitude__range=(longitudeR-radius,longitudeR+radius))
		
		eventList = []
		for event in events:
			addEvent = {}
			addEvent['id'] = event.pk
			addEvent['creatorId'] = event.creatorId
			addEvent['name'] = event.name
			addEvent['time'] = event.time
			addEvent['description'] = event.description
			addEvent['photo'] = event.photoId.photo
			addEvent['type'] = event.eventTypeId.name
			addEvent['latitude'] = event.placeId.latitude
			addEvent['longitude'] = event.placeId.longitude
			eventList.append(addEvent)
		
		return eventList
	
	@staticmethod
	def modifyEvent(name,description,date,x,y,photo,creator):
		'''
		Метод для редактирования событий (по сессии и списку аргументов)
		'''
		return null
	
	@staticmethod	
	def deleteEvent(sessionId,):
		'''
		Метод для редактирования событий (по сессии и списку аргументов)
		'''
		return null

