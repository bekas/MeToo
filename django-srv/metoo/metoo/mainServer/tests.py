# -*- coding: utf-8 -*-
'''
Модуль с тестами для проекта
'''
from django.test import TestCase
from sessionManager import SessionManager
from userManager import UserManager
from eventManager import EventManager
from MeTooManager import MeTooManager
from errorManager import ErrorManager
from django.db import models
from models import Metoo, User, UserInterest, UserSocialNetwork, Friend, Photo, Event, Place, City, Country, EventType, MetooType
from datetime import datetime, date, time
	
class AuthTest(TestCase):
	'''
	Класс тестов авторизации пользователя
	'''
	def setUp(self):
		'''
		Метод начальной инициализации
		'''
		photo = Photo(photo = 'this is a photo, believe me ;)')
		photo.save()
		user = User(login='test',password='test',avatarId = photo, rating = 0)
		user.save()
		user = User(login='TestUser',password='test_pass',avatarId = photo, rating = 0)
		user.save()
		SessionManager.stopTimer()
		
	def testGoodData(self):
		'''
		Тест корректных данных
		'''
		test_name = "TestUser"
		test_pass = "test_pass"
		test_code = ErrorManager.Success
		real_code,result = UserManager.connectUser(test_name, test_pass)
		res = real_code == test_code
		self.assertTrue(res)

	def testBadPass(self):
		'''
		Тест плохого пароля
		'''
		test_name = "TestUser"
		test_pass = "bad_pass"
		test_code = ErrorManager.AuthBadPass
		real_code,result = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)
                
	def testBadLogin(self):
		'''
		Тест плохого логина
		'''
		test_name = "UserNotExist"
		test_pass = "test_pass"
		test_code = ErrorManager.AuthBadLogin
		real_code,result = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testEmptyLogin(self):
		'''
		Тест пустого логина
		'''
		test_name = ''
		test_pass = "test_pass"
		test_code = ErrorManager.AuthBadLogin
		real_code,result = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testEmptyPass(self):
		'''
		Тест пустого пароля
		'''
		test_name = "TestUser"
		test_pass = ''
		test_code = ErrorManager.AuthBadPass
		real_code,result = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testLoginCase(self):
		'''
		Тест Case-a
		'''
		test_name = "testuser"
		test_pass = "test_pass"
		test_code = ErrorManager.Success
		real_code,result = UserManager.connectUser(test_name, test_pass)
		self.assertTrue(test_code == real_code)

	def testPassCase(self):
		'''
		Тест Case-a
		'''
		test_name = "TestUser"
		test_pass = "Test_Pass"
		test_code = ErrorManager.Success
		real_code,result = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

class SessionTest(TestCase):
	'''
	Класс тестов сессии пользователя
	'''
	def setUp(self):
		'''
		Метод начальной инициализации
		'''
		#print("I`m SetUp and i know it")
		photo = Photo(photo = 'this is a photo, believe me ;)')
		photo.save()
		user = User(login='test',password='test',avatarId = photo, rating = 0)
		user.save()
		self.userId1 = user.pk
		#print("User1=",user.pk)
		user = User(login='TestUser',password='test_pass',avatarId = photo, rating = 0)
		user.save()
		self.userId2 = user.pk
		#print("User2=",user.pk)
		SessionManager.stopTimer()

	def testGetGoodSessionID(self):
		'''
		Тест получения ID сессии - корректные данные
		'''
		sessionId1 = SessionManager.getSessionID(self.userId1)
		sessionId2 = SessionManager.getSessionID(self.userId2)
		
		#print("sesID=",sessionId1)
		self.assertTrue(sessionId1 > 0)
		self.assertTrue(sessionId2 > 0)

	def testGetBadSessionID(self):
		'''
		Тест получения ID сессии - несуществующий юзер
		'''
		sessionId1 = SessionManager.getSessionID(self.userId2 + 1)
		#sessionId2 = SessionManager.getSessionID("number")
		self.assertTrue(sessionId1 < 0)
		#self.assertTrue(sessionId2 < 0)

	def testCheckGoodSessionID(self):
		'''
		Тест проверки существования сессии - корректные данные
		'''
		sessionId = SessionManager.getSessionID(self.userId2)
		exist = SessionManager.checkSession(sessionId)
		self.assertTrue(exist)

	def testCheckBadSessionID(self):
		'''
		Тест проверки существования сессии - несуществующая сессия
		'''
		sessionId1 = SessionManager.getSessionID(0)
		exist1 = SessionManager.checkSession(sessionId1)
		exist2 = SessionManager.checkSession(-123)
		#exist2 = SessionManager.checkSession("number")
		self.assertFalse(exist1)
		self.assertFalse(exist2)
		#self.assertFalse(exist3)

	def testGetGoodUserID(self):
		'''
		Тест получения ID юзера - существующая сессия
		'''
		goodUserID = self.userId2
		sessionId = SessionManager.getSessionID(goodUserID)
		userId = SessionManager.getUserId(sessionId)
		self.assertEqual(userId, goodUserID)
		
	def testGetBadUserID(self):
		'''
		Тест получения ID юзера - несуществующая сессия
		'''
		userId1 = SessionManager.getUserId(-1)
		userId2 = SessionManager.getUserId(0)
		#userId2 = SessionManager.getUserId("number")
		self.assertTrue(userId1 < 0)
		self.assertTrue(userId2 < 0)
		#self.assertTrue(userId3 < 0)	

class MetooManagerTest(TestCase):
	'''
	Класс тестов похода на события
	'''
	def setUp(self):
		'''
		Метод начальной инициализации
		'''
		#print("I`m SetUp and i know it")
		photo = Photo(photo = 'this is a photo, believe me ;)')
		photo.save()
		user = User(login='test',password='test',avatarId = photo, rating = 0)
		user.save()
		self.userId = user.pk
		country = Country(name = "Russia")
		country.save()
		city = City(name = "Moscow")
		city.save()
		place = Place(longitude=0, latitude=0, name='PlaceName',cityId = city,countryId = country)
		place.save()
		eventType = EventType(name = "meeting",description = "")
		eventType.save()
		event = Event(creatorId=user, name='EventName', time = datetime.now(), description = '', photoId = photo, eventTypeId = eventType, PlaceId=place)
		event.save()
		self.eventId = event.pk
		metooType = MetooType(name = "go")
		metooType.save()
		metoo = Metoo(userId=user,eventId = event, metooTypeId = metooType)
		metoo.save()
		self.metooId = metoo.pk
		SessionManager.stopTimer()

	def testGetUsersbyEventBadSession(self):
		'''
		Тест получения юзеров по событию - несуществующая сессия
		'''
		listUsers = MeTooManager.getUsersbyEvent(-1,self.eventId)
		self.assertEqual(listUsers['result'], 501)

	def testGetUsersbyBadEvent(self):
		'''
		Тест получения юзеров по событию - несуществующее событие
		'''
		sessionId = SessionManager.getSessionID(self.userId)
		listUsers = MeTooManager.getUsersbyEvent(sessionId,self.eventId+1)
		self.assertEqual(listUsers['result'], 502)

	def testGetUsersbyEventPositive(self):
		'''
		Тест получения юзеров по событию - позитивный тест
		'''
		sessionId = SessionManager.getSessionID(self.userId)
		listUsers = MeTooManager.getUsersbyEvent(sessionId,self.eventId)
		self.assertEqual(listUsers['result'], 500)

                	
class EventTest(TestCase):
	'''
	Класс тестов работы с событиями
	'''
	def setUp(self):
		'''
		Метод начальной инициализации
		'''
		photo = Photo(photo = 'this is a photo, believe me ;)')
		photo.save()
		user = User(login='test',password='test',avatarId = photo, rating = 0)
		user.save()
		user = User(login='TestUser',password='test_pass',avatarId = photo, rating = 0)
		user.save()
		self.sessionId = SessionManager.getSessionID(1)
		SessionManager.stopTimer()

	def testCreateGoodEvent(self):
		'''
		Тест получения ID события - корректные данные
		'''
		eventArgs = {'name': 'testName', 'description': 'testDescription'}
		eventId = EventManager.createEvent(self.sessionId, eventArgs)
		self.assertTrue(eventId > 0)

	def testCreateBadSessionEvent(self):
		'''
		Тест получения ID события - несуществующая сессия
		'''
		eventArgs = {'name': 'testName', 'description': 'testDescription'}
		res, eventId = EventManager.createEvent(self.sessionId+1, eventArgs)
		self.assertTrue(res == ErrorManager.AuthNothing)

	def testCreateBadArgsEvent(self):
		'''
		Тест получения ID события - некорректные агрументы
		'''
		#eventArgs0 = {'time': 'time'}
		eventArgs1 = {'time': -12}
		#eventArgs2 = {'eventTypeId': 'type'}
		eventArgs3 = {'eventTypeId': -7}
		#eventArgs4 = {'longitude': 'number'}
		eventArgs5 = {'longitude': -7}
		#eventArgs6 = {'latitude': 'number'}
		eventArgs7 = {'latitude': -7}
		res1,eventId1 = EventManager.createEvent(self.sessionId, eventArgs1)
		#eventId2 = EventManager.createEvent(1, eventArgs2)
		res2,eventId3 = EventManager.createEvent(self.sessionId, eventArgs3)
		#eventId4 = EventManager.createEvent(1, eventArgs4)
		res3,eventId5 = EventManager.createEvent(self.sessionId, eventArgs5)
		#eventId6 = EventManager.createEvent(1, eventArgs6)
		res4,eventId7 = EventManager.createEvent(self.sessionId, eventArgs7)
		self.assertTrue(res1 != ErrorManager.Success)
		#self.assertTrue(eventId2 < 0)
		self.assertTrue(res2 != ErrorManager.Success)
		#self.assertTrue(eventId4 < 0)
		self.assertTrue(res3 != ErrorManager.Success)
		#self.assertTrue(eventId6 < 0)
		self.assertTrue(res4 != ErrorManager.Success)

	def testGetBadSessionEvent(self):
		'''
		Тест получения событий - несуществующая сессия
		'''
		conditionals = {'latitude': 0, 'longtitude': 0, 'radius': 0}
		res, eventList = EventManager.createEvent(self.sessionId + 1, conditionals)
		self.assertTrue(res == ErrorManager.AuthNothing)
		
	def testGetBadArgsEvent(self):
		'''
		Тест получения событий - некорректные условия
		'''
		# не пройдут, т.к. проверка на стороне клиента и вообще я их радиус не понимаю
		conditionals1 = {}
		conditionals2 = {'latitude': -1, 'longtitude': 0, 'radius': 0}
		conditionals3 = {'latitude': 0, 'longtitude': -2, 'radius': 0}
		conditionals4 = {'latitude': 0, 'longtitude': 0, 'radius': -5}
		res1, eventList1 = EventManager.createEvent(self.sessionId, conditionals1)
		res2, eventList2 = EventManager.createEvent(self.sessionId, conditionals2)
		res3, eventList3 = EventManager.createEvent(self.sessionId, conditionals3)
		res4, eventList4 = EventManager.createEvent(self.sessionId, conditionals4)
		self.assertTrue(res1 != ErrorManager.Success)
		self.assertTrue(res2 != ErrorManager.Success)
		self.assertTrue(res3 != ErrorManager.Success)
		self.assertTrue(res4 != ErrorManager.Success)
