#-*- coding: utf8 -*-
import os, sys, inspect

 # use this if you want to include modules from a subforder
cmd_subfolder = os.path.realpath(os.path.abspath(os.path.join(os.path.split(inspect.getfile( inspect.currentframe() ))[0],"../mainServer")))
if cmd_subfolder not in sys.path:
        sys.path.insert(0, cmd_subfolder)
                    
from authorization import Authorization
import unittest
 
class TestAuth (unittest.TestCase):      

        def setUp(self):
                pass

        def tearDown(self):
                pass

        def testGoodData(self): # корректные логин и пароль
                test_name = "TestUser"
                test_pass = "test_pass"
                test_code = 0
                real_code = Authorization.login(test_name, test_pass)
                res = real_code > test_code
                self.assertTrue(res)

        def testBadPass(self): # неправильный пароль
                test_name = "TestUser"
                test_pass = "bad_pass"
                test_code = -102
                real_code = Authorization.login(test_name, test_pass)
                self.assertEqual(test_code, real_code)
                    
        def testBadLogin(self): # неправильный логин
                test_name = "UserNotExist"
                test_pass = "test_pass"
                test_code = -101
                real_code = Authorization.login(test_name, test_pass)
                self.assertEqual(test_code, real_code)

        def testEmptyLogin(self): # пустой логин
                test_name = ''
                test_pass = "test_pass"
                test_code = -101
                real_code = Authorization.login(test_name, test_pass)
                self.assertEqual(test_code, real_code)

        def testEmptyPass(self): # пустой пароль
                test_name = "TestUser"
                test_pass = ''
                test_code = -102
                real_code = Authorization.login(test_name, test_pass)
                self.assertEqual(test_code, real_code)

        def testLoginCase(self): # правильный логин - в другом регистре
                test_name = "testuser"
                test_pass = "test_pass"
                test_code = -101
                real_code = Authorization.login(test_name, test_pass)
                self.assertEqual(test_code, real_code)

        def testPassCase(self): # неправильный пароль - в другом регистре
                test_name = "TestUser"
                test_pass = "Test_Pass"
                test_code = -102
                real_code = Authorization.login(test_name, test_pass)
                self.assertEqual(test_code, real_code)
 
def suite():
        suite = unittest.TestSuite()
        suite.addTest(unittest.makeSuite(TestAuth))
        return suite
