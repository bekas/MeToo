# -*- coding: utf-8 -*-
import sched, time, datetime
from configurationManager import ConfigurationManager
from threading import Timer
'''
Модуль для работы со временем и переодическими действиями
'''

class TimeManager:
	'''
	Класс, предоставляющий методы для работы со временем 
	'''
	@staticmethod
	def getTime():
		'''
		Метод для получения текущего времени
		'''
		return datetime.datetime.now()
			

class Worker:
	'''
	Базовый класс для выполнения переодических действий
	'''
	#s = sched.scheduler(time.time, time.sleep)
	
	def doWork(self, delay):
		'''
		Метод, вызываемый переодически и запускающий метод выполняемого действия
		'''
		self.work()
		Timer(delay, self.doWork, [delay]).start()
		#self.s.enter(delay, 1, self.doWork, (delay))
		#self.s.run()		
		
	def work(self):	
		'''
		Абстрактный метод переодически выполняемого действия
		'''
		x = 1 + 1
