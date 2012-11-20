# -*- coding: utf8 -*-

import unittest
import test_authorization
#импорт тестов
 
suiteAuth = test_authorization.suite()
#заводим сьют для каждого набора тестов
 
suite = unittest.TestSuite()
suite.addTest(suiteAuth)
#добавляем к главному
 
unittest.TextTestRunner(verbosity=2).run(suite)
