import time,datetime
class ConfigurationManager:
	
	@staticmethod
	def serverVersion():
		return 'v.0.1'
	
	@staticmethod	
	def sessionDeleteInterval():
		return datetime.timedelta(minutes=1)
	
	@staticmethod	
	def loopDeleteSessionInterval():
		return 5
