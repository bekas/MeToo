# -*- coding: utf-8 -*-
'''
Модуль, отвечающий за работу с событиями.
'''
from django.db import models
from models import Session, User, Event, Place, City, Country, Photo
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
		userId = SessionManager.getUser(sessionId)
		eName = 'Noname event'
		eTime = '2012-12-31'
		eDescription = 'No defenition'
		photo = Photo.objects.get(pk = 1)
		eEventTypeId = 1
		eLatitude = 0
		eLongitude = 0
		eCountryId = Country.objects.get(pk = 1)
		eCityId = City.objects.get(pk = 1)
		eNamePlace = 'Noname place'
		
		if eventArgs.has_key('name'):
			eName = eventArgs['name']
			
		if eventArgs.has_key('time'):
			eTime = eventArgs['time']
			
		if eventArgs.has_key('description'):
			eDescription = eventArgs['description']
			
		if eventArgs.has_key('photo'):
			photo = Photo(photo = eventArgs['photo'])
			
		if eventArgs.has_key('eventTypeId'):
			eEventTypeId = eventArgs['eventTypeId']
			
		if eventArgs.has_key('longitude'):
			eLatitude = eventArgs['longitude']
			
		if eventArgs.has_key('eventTypeId'):
			eLongitude = eventArgs['longitude']	
		
		
		place = Place(cityId = eCityId, countryId = eCountryId, name = eNamePlace, latitude = eLatitude, longitude = eLongitude)
		place.save()
		newEvent = Event(creatorId = userId, name = eName, time = eTime, description = eDescription, photoId = photo, eventTypeId_id = eEventTypeId, PlaceId = place)
		newEvent.save()
		return newEvent.pk
	
	@staticmethod
	def getEvents(sessionId, conditionals):
		'''
		Метод для запроса событий (по сессии и запросу)
		'''
		eventList = []
		userId = SessionManager.getUserId(sessionId)
		#TODO Обработка user-a;
		if userId > 0:
			latitudeR = conditionals['latitude']
			longitudeR = conditionals['longitude']
			radius = conditionals['radius']
		
			events =  Event.objects.filter(PlaceId__latitude__range=(latitudeR-radius,latitudeR+radius),PlaceId__longitude__range=(longitudeR-radius,longitudeR+radius))
			for event in events:
				addEvent = {}			
				addEvent['id'] = event.pk
				addEvent['creatorId'] = event.creatorId
				addEvent['name'] = event.name
				addEvent['time'] = event.time
				addEvent['description'] = event.description
				addEvent['photo'] = event.photoId.photo
				addEvent['type'] = event.eventTypeId.name
				addEvent['latitude'] = event.PlaceId.latitude
				addEvent['longitude'] = event.PlaceId.longitude
				eventList.append(addEvent)
		return eventList
	
	@staticmethod
	def modifyEvent(sessionId,eventId,eventArgs):
		'''
		Метод для редактирования событий (по сессии и списку аргументов)
		'''
		userId = SessionManager.getUser(sessionId)
		modEvent = Event.objects.get(pk=eventId)
		
		eName = modEvent.name
		eTime = modEvent.time
		eDescription = modEvent.description
		photo = modEvent.photoId
		eEventTypeId = modEvent.eventTypeId
		eLatitude = modEvent.PlaceId.latitude
		eLongitude = modEvent.PlaceId.longitude
		eCountryId = modeEvent.PlaceId.countryId
		eCityId = modeEvent.PlaceId.cityId
		eNamePlace = modeEvent.PlaceId.name
		
		if eventArgs.has_key('name'):
			eName = eventArgs['name']
		if eventArgs.has_key('time'):
			eTime = eventArgs['time']
		if eventArgs.has_key('description'):
			eDescription = eventArgs['description']
		if eventArgs.has_key('photo'):
			photo = Photo(photo = eventArgs['photo'])
		if eventArgs.has_key('eventTypeId'):
			eEventTypeId = eventArgs['eventTypeId']
		if eventArgs.has_key('longitude'):
			eLatitude = eventArgs['longitude']
		if eventArgs.has_key('eventTypeId'):
			eLongitude = eventArgs['longitude']	
		
		#TODO: Редактирование подредактировать
		place = Place(cityId = eCityId, countryId = eCountryId, name = eNamePlace, latitude = eLatitude, longitude = eLongitude)
		newEvent = Event(creatorId = userId, name = eName, time = eTime, description = eDescription, photoId = photo, eventTypeId = eEventTypeId, PlaceId = place)
		newEvent.save()
		return null
	
	@staticmethod	
	def deleteEvent(sessionId,):
		'''
		Метод для редактирования событий (по сессии и списку аргументов)
		'''
		return null

