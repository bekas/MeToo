"""
This file demonstrates writing tests using the unittest module. These will pass
when you run "manage.py test".

Replace this with more appropriate tests for your application.
"""

from django.test import TestCase
from sessionManager import SessionManager
from userManager import UserManager
from django.db import models
from models import User, UserInterest, UserSocialNetwork, Friend, Photo

class SessionTest(TestCase):
	def setUp(self):
		photo = Photo(photo = 'this is a photo, believe me ;)')
		photo.save()
		user = User(login='test',password='test',avatarId = photo, rating = 0)
		user.save()
		user = User(login='TestUser',password='test_pass',avatarId = photo, rating = 0)
		user.save()
        
	def test_get_sessionID(self):
		print "get"
		#fixtures = ['test_data.json','moar_data.json'] 	
		sessionId = SessionManager.getSessionID(2);
		self.assertTrue(sessionId > 0)
		
	def testGoodData(self):
		test_name = "TestUser"
		test_pass = "test_pass"
		test_code = 0
		real_code = UserManager.connectUser(test_name, test_pass)
		res = real_code > test_code
		self.assertTrue(res)

	def testBadPass(self):
		test_name = "TestUser"
		test_pass = "bad_pass"
		test_code = -102
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)
                
	def testBadLogin(self):
		test_name = "UserNotExist"
		test_pass = "test_pass"
		test_code = -101
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testEmptyLogin(self):
		test_name = ''
		test_pass = "test_pass"
		test_code = -101
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testEmptyPass(self):
		test_name = "TestUser"
		test_pass = ''
		test_code = -102
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)

	def testLoginCase(self):
		test_name = "testuser"
		test_pass = "test_pass"
		test_code = 0
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertTrue(test_code < real_code)

	def testPassCase(self):
		test_name = "TestUser"
		test_pass = "Test_Pass"
		test_code = -102
		real_code = UserManager.connectUser(test_name, test_pass)
		self.assertEqual(test_code, real_code)
