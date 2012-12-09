# -*- coding: utf-8 -*-
"""
Модуль с тестами для проекта
"""

from django.test import TestCase
from sessionManager import SessionManager
from userManager import UserManager
from eventManager import EventManager
from MeTooManager import MeTooManager
from django.db import models
from models import Metoo, User, UserInterest, UserSocialNetwork, Friend, Photo, Event, Place

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
		
	def testGoodData(self):
		'''
		Тест корректных данных
		'''
		test_name = "TestUser"
		test_pass = "test_pass"
		test_code = 0
		real_code = UserManager.connectUser(test_name, test_pass)
		res = real_code > test_code
		self.assertTrue(res)

	def testBadPass(self):
		'''
		Тест плохого пароля
		'''
		test_name = "TestUser"
		test_pass = "bad_pass"
		test_code = -102
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)
                
	def testBadLogin(self):
		'''
		Тест плохого логина
		'''
		test_name = "UserNotExist"
		test_pass = "test_pass"
		test_code = -101
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testEmptyLogin(self):
		'''
		Тест пустого логина
		'''
		test_name = ''
		test_pass = "test_pass"
		test_code = -101
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testEmptyPass(self):
		'''
		Тест пустого пароля
		'''
		test_name = "TestUser"
		test_pass = ''
		test_code = -102
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testLoginCase(self):
		'''
		Тест Case-a
		'''
		test_name = "testuser"
		test_pass = "test_pass"
		test_code = 0
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertTrue(test_code < real_code)

	def testPassCase(self):
		'''
		Тест Case-a
		'''
		test_name = "TestUser"
		test_pass = "Test_Pass"
		test_code = -102
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

class SessionTest(TestCase):
	'''
	Класс тестов сессии пользователя
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

	def testGetGoodSessionID(self):
		'''
		Тест получения ID сессии - корректные данные
		'''
		sessionId1 = SessionManager.getSessionID(1)
		sessionId2 = SessionManager.getSessionID(2)
		self.assertTrue(sessionId1 > 0)
		self.assertTrue(sessionId2 > 0)

	def testGetBadSessionID(self):
		'''
		Тест получения ID сессии - несуществующий юзер
		'''
		sessionId1 = SessionManager.getSessionID(4)
		#sessionId2 = SessionManager.getSessionID("number")
		self.assertTrue(sessionId1 < 0)
		#self.assertTrue(sessionId2 < 0)

	def testCheckGoodSessionID(self):
		'''
		Тест проверки существования сессии - корректные данные
		'''
		sessionId = SessionManager.getSessionID(2)
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
		goodUserID = 2
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

class MeTooTest(TestCase):
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
		place = Place(y=0, x=0, name='PlaceName')
		place.save()
		event = Event(creatorId=user, name='EventName', time = '25-01-12 12:23', description = '', photoId = photo, eventTypeId_id = 1, PlaceId=place)
                event.save()
                metoo = Metoo(userId=user,eventId_id = event, metooTypeId_id = 1)
		metoo.save()

	def getUsersbyEventBadSession(self):
                '''
		Тест получения юзеров по событию - несуществующая сессия
		'''
                listUsers = MeTooManager.getUsersbyEvent(145,1)
                self.assertEqual(listUsers['result'], 501)

        def getUsersbyBadEvent(self):
                '''
		Тест получения юзеров по событию - несуществующее событие
		'''
                sessionId = SessionManager.createSessionID(1)
                listUsers = MeTooManager.getUsersbyEvent(sessionId,14)
                self.assertEqual(listUsers['result'], 502)

        def getUsersbyEventPositive(self):
                '''
		Тест получения юзеров по событию - позитивный тест
		'''
                sessionId = SessionManager.createSessionID(1)
                listUsers = MeTooManager.getUsersbyEvent(sessionId,1)
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
		sessionId = SessionManager.createSessionID(1)

	def testCreateGoodEvent(self):
		'''
		Тест получения ID события - корректные данные
		'''
		eventArgs = {'name': 'testName', 'description': 'testDescription'}
		eventId = EventManager.createEvent(1, eventArgs)
		self.assertTrue(eventId > 0)

	def testCreateBadSessionEvent(self):
		'''
		Тест получения ID события - несуществующая сессия
		'''
		eventArgs = {'name': 'testName', 'description': 'testDescription'}
		eventId = EventManager.createEvent(17, eventArgs)
		self.assertTrue(eventId < 0)

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
		eventId1 = EventManager.createEvent(1, eventArgs1)
		#eventId2 = EventManager.createEvent(1, eventArgs2)
		eventId3 = EventManager.createEvent(1, eventArgs3)
		#eventId4 = EventManager.createEvent(1, eventArgs4)
		eventId5 = EventManager.createEvent(1, eventArgs5)
		#eventId6 = EventManager.createEvent(1, eventArgs6)
		eventId7 = EventManager.createEvent(1, eventArgs7)
		self.assertTrue(eventId1 < 0)
		#self.assertTrue(eventId2 < 0)
		self.assertTrue(eventId3 < 0)
		#self.assertTrue(eventId4 < 0)
		self.assertTrue(eventId5 < 0)
		#self.assertTrue(eventId6 < 0)
		self.assertTrue(eventId7 < 0)

	def testGetBadSessionEvent(self):
		'''
		Тест получения событий - несуществующая сессия
		'''
		conditionals = {'latitude': 0, 'longtitude': 0, 'radius': 0}
		eventList = EventManager.createEvent(111, conditionals)
		self.assertTrue(eventList == [])
		
        def testGetBadArgsEvent(self):
		'''
		Тест получения событий - некорректные условия
		'''
		# не пройдут, т.к. проверка на стороне клиента и вообще я их радиус не понимаю
		conditionals1 = {}
		conditionals2 = {'latitude': -1, 'longtitude': 0, 'radius': 0}
		conditionals3 = {'latitude': 0, 'longtitude': -2, 'radius': 0}
		conditionals4 = {'latitude': 0, 'longtitude': 0, 'radius': -5}
		eventList1 = EventManager.createEvent(1, conditionals1)
		eventList2 = EventManager.createEvent(1, conditionals2)
		eventList3 = EventManager.createEvent(1, conditionals3)
		eventList4 = EventManager.createEvent(1, conditionals4)
		self.assertTrue(eventList1 == [])
		self.assertTrue(eventList2 == [])
		self.assertTrue(eventList3 == [])
		self.assertTrue(eventList4 == [])
