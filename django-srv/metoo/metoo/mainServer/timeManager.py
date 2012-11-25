import sched, time, datetime
from configurationManager import ConfigurationManager
from threading import Timer

class TimeManager:
	@staticmethod
	def getTime():
		return datetime.datetime.now()
			

class Worker:
	#s = sched.scheduler(time.time, time.sleep)
	
	def doWork(self, delay):
		self.work()
		Timer(delay, self.doWork, (delay)).start()
		#self.s.enter(delay, 1, self.doWork, (delay))
		#self.s.run()		
		
	def work(self):	
		x = 1 + 1
