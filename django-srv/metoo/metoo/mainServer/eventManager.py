# -*- coding: utf-8 -*-
from django.db import models
from models import Session, User, Event, Place, City, Country, Photo
from timeManager import TimeManager, Worker
from configurationManager import ConfigurationManager
from sessionManager import SessionManager
from errorManager import ErrorManager
'''
Модуль, предоставляющий инструментарий для работы с событиями
'''

class EventManager:
	'''
	Класс, предоставлящий возможности:\n
	- Создания событий;\n
	- Запроса событий по критерию;\n
	- Изменения событий;\n
	- Удаления событий;\n
	'''
	@staticmethod
	def createEvent(sessionId,eventArgs):
		'''
		Метод для создания событий (по сессии и списку аргументов)
		'''
		result = ErrorManager.Success
		eventId = -1
		if SessionManager.checkSession(sessionId):
			try:
				userId = SessionManager.getUser(sessionId)
		
				userId = User.objects.get(pk = 1)
				eName = u'Noname event'
				eTime = '31-12-12'
				eDescription = u'No defenition'
				photo = Photo.objects.get(pk = 1)
				eEventTypeId = 1
				eLatitude = 0
				eLongitude = 0
				eCountryId = Country.objects.get(pk = 1)
				eCityId = City.objects.get(pk = 1)
				eNamePlace = 'Noname place'
				
				print "Start create\n"

				if eventArgs.has_key('name'):
					print "if\n"
					eName = eventArgs['name']
					
	
				if eventArgs.has_key('time'):
					eTime = eventArgs['time']
	
				if eventArgs.has_key('description'):
					eDescription = eventArgs['description']
	
				if eventArgs.has_key('photo'):
					photo = Photo(photo = eventArgs['photo'])
					photo.save()
				else:
					photo = Photo.objects.get(pk = 1)
	
				if eventArgs.has_key('event_type_id'):
					eEventTypeId = int(eventArgs['event_type_id'])
	
				if eventArgs.has_key('longitude'):
					eLongitude = float(eventArgs['longitude'])
	
				if eventArgs.has_key('latitude'):
					eLatitude = float(eventArgs['latitude'])	

				
				print "==\n"
				
				#eName = eName.decode('utf-8').replace('_',' ')
				eName = eName.replace('_',' ')
				print "decode\n"
				#eDescription = eDescription.decode('utf-8').replace('_',' ')
				eDescription = eDescription.replace('_',' ')
				print "decode\n"
				print(eName)
				print (eDescription)
				
				place = Place(cityId = eCityId, countryId = eCountryId, name = eNamePlace, latitude = eLatitude, longitude = eLongitude)
				place.save()
				newEvent = Event(creatorId = userId, name = eName, time = eTime, description = eDescription, photoId = photo, eventTypeId_id = eEventTypeId, PlaceId = place)
				newEvent.save()
				eventId = newEvent.pk
			except:
				result = ErrorManager.EventCreateError			
		else:
			result = ErrorManager.AuthNothing
		return result, eventId
	
	@staticmethod
	def getEvents(sessionId, conditionals):
		'''
		Метод для запроса событий (по сессии и запросу)
		'''
		eventList = []
		if sessionId > 0:
			userId = SessionManager.getUserId(sessionId)
		else:
			userId = 1
		result = ErrorManager.Success
		#TODO Обработка user-a;
		if userId > 0:
			try:
				latitudeR = conditionals['latitude']
				longitudeR = conditionals['longitude']
				radius = conditionals['radius']
		
				events =  Event.objects.filter(PlaceId__latitude__range=(latitudeR-radius,latitudeR+radius),PlaceId__longitude__range=(longitudeR-radius,longitudeR+radius))
				#events =  Event.objects.filter()
				for event in events:
					addEvent = {}			
					addEvent['id'] = event.pk
					addEvent['creator_id'] = event.creatorId.pk
					addEvent['creator_name'] = event.creatorId.login
					addEvent['name'] = event.name
					addEvent['date'] = event.time.strftime('%d-%m-%y %H:%M')
					addEvent['description'] = event.description
					addEvent['photo'] = event.photoId.photo
					addEvent['type'] = event.eventTypeId.pk
					addEvent['type_name'] = event.eventTypeId.name
					addEvent['latitude'] = str(event.PlaceId.latitude)
					addEvent['longitude'] = str(event.PlaceId.longitude)
					eventList.append(addEvent)
			except:
				result = ErrorManager.EventGetError
		else:
			result = ErrorManager.AuthNothing
		return result, eventList
	
	@staticmethod
	def modifyEvent(sessionId,eventId,eventArgs):
		'''
		Метод для редактирования событий (по сессии и списку аргументов)
		'''
		#TODO Обработка Юзера!
		userId = SessionManager.getUser(sessionId)
		modEvent = Event.objects.get(pk=eventId)
		
		#eName = modEvent.name
		#eTime = modEvent.time
		#eDescription = modEvent.description
		#photo = modEvent.photoId
		#eEventTypeId = modEvent.eventTypeId
		#place = modEvent.PlaceId
		#eLatitude = modEvent.PlaceId.latitude
		#eLongitude = modEvent.PlaceId.longitude
		#eCountryId = modEvent.PlaceId.countryId
		#eCityId = modEvent.PlaceId.cityId
		#eNamePlace = modEvent.PlaceId.name
		
		if eventArgs.has_key('name'):
			modEvent.name = eventArgs['name']
		if eventArgs.has_key('time'):
			modEvent.time = eventArgs['time']
		if eventArgs.has_key('description'):
			modEvent.description = eventArgs['description']
		
		#TODO: Подумать насчет изменений. Где они олжны происходить?
		if eventArgs.has_key('photo'):
			modEvent.photoId.photo = eventArgs['photo']
		if eventArgs.has_key('event_type_id'):
			modEvent.eventTypeId_id = eventArgs['event_type_id']
		if eventArgs.has_key('longitude'):
			modEvent.PlaceId.longitude = eventArgs['longitude']
		if eventArgs.has_key('latitude'):
			modEvent.PlaceId.latitude = eventArgs['latitude']	
		
		modEvent.photoId.save()
		modEvent.eventTypeId.save()
		modEvent.PlaceId.save()
		modEvent.save()
		
		#TODO Обработка ошибок
		return modEvent.pk
	
	@staticmethod	
	def deleteEvent(sessionId,eventId):
		'''
		Метод для удаления событий (по сессии и Id сессии)
		'''
		#TODO Удалять ли события?
		result = -1
		userId = SessionManager.getUser(sessionId)
		if EventManager.checkEvent(eventId):
			delEvent = Event.objects.get(pk=eventId)
			#delEvent.photo.delete()
			delEvent.delete()
			#TODO Вернуть норм ошибку
			result = 300
		return result
	
	@staticmethod
	def getEvent(sessionId,eventId):
		'''
		Метод получения события по Id
		'''
		resEvent = {}
		if sessionId > 0:
			userId = SessionManager.getUserId(sessionId)
		else:
			userId = 1
		result = ErrorManager.Success
		if userId > 0:
			try:
				if EventManager.checkEvent(eventId):
					event = Event.objects.get(pk = eventId)			
					resEvent['id'] = event.pk
					resEvent['creator_id'] = event.creatorId.pk
					resEvent['creator_name'] = event.creatorId.login
					resEvent['name'] = event.name
					resEvent['date'] = event.time.strftime('%d-%m-%y %H:%M')
					resEvent['description'] = event.description
					resEvent['photo'] = event.photoId.photo
					resEvent['type'] = event.eventTypeId.pk
					resEvent['type_name'] = event.eventTypeId.name
					resEvent['latitude'] = event.PlaceId.latitude
					resEvent['longitude'] = event.PlaceId.longitude
				else:
					result = ErrorManager.EventGetError
			except:
				result = ErrorManager.EventGetError 
		else:
			result = ErrorManager.AuthNothing
		return result, resEvent
		
		
		
	@staticmethod
	def checkEvent(eventId):
		'''
		Метод для проверки существованияя события(по id события)
		'''
		return Event.objects.filter(pk = eventId).exists()
