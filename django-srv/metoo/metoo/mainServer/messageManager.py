# -*- coding: utf-8 -*-
'''
Модуль, отвечающий за работу с событиями.
'''
from eventManager import EventManager
from sessionManager import SessionManager
from userManager import UserManager

#Менедер пакетов - ответов сервера.
class MessageManager:	
	'''
	Класс, обрабатывающий входные сообщения и формирующий ответы.\n
	Основной метод - createContext.Создает словарь, содержащий необходимую\n 
	информацию\n
	'''
	@staticmethod
	def createContext(agentMessage):		
		'''
		Метод для создания контекста для генерации ответных пакетов/страниц
		'''
		#ИМХО это решение не очень. Надо бы подумать ка лучше сделать
		context = {		
					'main':
						lambda x:
							MessageManager.mainContext(x),
		
					'about':
						lambda x:
							MessageManager.aboutContext(x),	
											
					'what':
						lambda x:
							MessageManager.whatContext(x),
				
					'dev':
						lambda x:
							MessageManager.devContext(x),

					'download':
						lambda x:
							MessageManager.downloadContext(x),

					'ping': 
						lambda x: 
							MessageManager.pingContext(x),
							
					'auth':
						lambda x: 
							MessageManager.authoriseContext(x),
		
					'registrate':
						lambda x: 
							MessageManager.registrateContext(x),
		
					'events':
						lambda x: 
							MessageManager.eventsContext(x),
		
					'createEvent':
						lambda x: 
							MessageManager.createEventContext(x),
							
					'deleteEvent':
						lambda x: 
							MessageManager.deleteEventContext(x),		
			
					'modifyEvent':
						lambda x: 
							MessageManager.modifyEventContext(x),
							
					'modifyProfile':
						lambda x: 
							MessageManager.modifyProfileContext(x),
					'test':
						lambda x:
							MessageManager.testContext(x),				
					'users':
						lambda x:
							MessageManager.usersContext(x),
					'metoo':
						lambda x:
							MessageManager.metooContext(x),
					'modmetoo':
						lambda x:
							MessageManager.modMetooContext(x),
					'delmetoo':
						lambda x:
							MessageManager.delMetooContext(x),						
					}[agentMessage['type']](agentMessage)	
		
		if 'request_id' in agentMessage:
			context['request_id'] = agentMessage['request_id'] 
		return context
	
	
	# Страницы Веб-приложения
	@staticmethod	
	def mainContext(agentMessage):
		'''
		Контекст основной страницы сайта
		'''
		context = {}
		context['title'] = 'Welcome!'
		context['type'] = 'main'
		return context	
	
	@staticmethod	
	def aboutContext(agentMessage):
		'''
		Контекст страницы сайта "About"
		'''
		context = {}
		context['title'] = 'About us'
		context['type'] = 'about'
		context['data'] = 'Some words about us...'
		return context	
		
	@staticmethod	
	def whatContext(agentMessage):
		'''
		Контекст cтраницы сайта "What is it?"
		'''
		context = {}
		context['title'] = 'What is it?'
		context['type'] = 'what'
		context['data'] = 'This is a new geo-service, that can help you to meet with your friends.'
		return context	
	
	@staticmethod	
	def downloadContext(agentMessage):
		'''
		Контекст cтраницы сайта "Download"
		'''
		context = {}
		context['title'] = 'Download MeToo there!'
		context['type'] = 'download'
		context['data'] = 'You can download Android-client there.'
		return context	
	
	@staticmethod	
	def devContext(agentMessage):
		'''
		Контекст cтраницы сайта для разработки и тестирования
		'''
		context = {}
		context['title'] = '<<Developer page>>'
		context['type'] = 'dev'
	
		
		devcontext={}
		
		'''
		devcontext['type'] = 'auth'
		devcontext['login'] = 'test'
		devcontext['password'] = 'test'
		'''
		'''
		devcontext['type'] = 'events'
		devcontext['sessionid'] = 15
		devcontext['latitude'] = 55.6
		devcontext['longitude'] = 37.5
		devcontext['radius'] = 0.4
		'''
		'''
		devcontext['type'] = 'modifyEvent'
		devcontext['sessionid'] = 15
		devcontext['eventid'] = 8
		devcontext['latitude'] = 55.0
		devcontext['longitude'] = 37.0
		devcontext['name'] = 'TEST_EVENT2'
		'''
		
		devcontext['type'] = 'createEvent'
		devcontext['sessionid'] = 15
		
		devcontext['latitude'] = 55.5
		devcontext['longitude'] = 37.5
		devcontext['name'] = 'New Event'
		devcontext['time'] = '2012-12-21'		
		devcontext['description'] = 'Some words about..'	
		#devcontext['photo'] = 'new photo'	
		devcontext['eventTypeId'] = 1			
		
		'''
		devcontext['type'] = 'deleteEvent'
		devcontext['sessionid'] = 15
		devcontext['eventid'] = 10
		'''
		context['data'] = devcontext
		
		return context	
			
			
	# Обработчики протокола обмена с приложением
	@staticmethod	
	def pingContext(agentMessage):
		'''
		Контекст пингующего пакета 
		'''
		context = {}
		context['type'] = 'ping'
		return context
	
	@staticmethod
	def authoriseContext(agentMessage):
		'''
		Контекст пакета авторизации
		'''
		context = {}
		context['type'] = 'auth'	
		#sessionRes = 1
		sessionRes = UserManager.connectUser(agentMessage['login'], agentMessage['password'])
		context['result'] = sessionRes
		context['description'] = 'Nothing'
		context['session_id'] = sessionRes
		return context
		
	@staticmethod
	def registrateContext(agentMessage):
		'''
		Контекст пакета регистрации
		'''
		context = {}
		context['type'] = 'registrate'
		return context
	
	@staticmethod	
	def eventsContext(agentMessage):
		'''
		Контекст пакета запроса событий
		'''
		context = {}
		context['type'] = 'events'
		context['events'] = EventManager.getEvents(agentMessage['sessionid'],agentMessage)
		return context
	
	@staticmethod
	def createEventContext(agentMessage):
		'''
		Контекст пакета создания событий
		'''
		context = {}
		context['type'] = 'eventcreate'
		context['result'] = EventManager.createEvent(agentMessage['sessionid'],agentMessage)
		return context
	
	@staticmethod
	def modifyEventContext(agentMessage):
		'''
		Контекст пакета изменения событий
		'''
		context = {}
		context['type'] = 'eventmodify'
		context['result'] = EventManager.modifyEvent(agentMessage['sessionid'],agentMessage['eventid'],agentMessage)
		return context
	
	@staticmethod
	def deleteEventContext(agentMessage):
		'''
		Контекст пакета удаления событий
		'''
		context = {}
		context['type'] = 'eventdelete'
		context['result'] = EventManager.deleteEvent(agentMessage['sessionid'],agentMessage['eventid'])
		return context
	
	@staticmethod	
	def modifyProfileContext(agentMessage):
		'''
		Контекст пакета редактирования профиля
		'''
		context = {}
		context['type'] = 'profilemodify'
		return context
		
	@staticmethod
	def testContext(agentMessage):
		'''
		Контекст пакета тестирования
		'''
		context = {}
		context['type'] = 'test'
		context['get'] = agentMessage
		return context
		
	@staticmethod	
	def usersContext(agentMessage):
		'''
		Контекст пакета запроса пользователей, подписавшихся на событие
		'''
		context = {}
		context['type'] = 'users'	
		result = MeTooManager.getUsersbyEvent(agentMessage['sessionid'], agentMessage['eventId'])
		context['users'] = result['users']
		context['count'] = len(context['events'])
		context['result'] = result['result']
		return context		
		
	@staticmethod	
	def metooContext(agentMessage):
		'''
		Контекст пакета запроса подписывания на событие
		'''
		context = {}
		context['type'] = 'metoo'	
		result = MeTooManager.meToo(agentMessage['sessionid'], agentMessage['eventId'], agentMessage['metooTypeId'])
		context['result'] = result
		return context		

	@staticmethod	
	def delMetooContext(agentMessage):
		'''
		Контекст пакета запроса отказа на событие
		'''
		context = {}
		context['type'] = 'metoo'	
		result = MeTooManager.delMeToo(agentMessage['sessionid'], agentMessage['eventId'])
		context['result'] = result
		return context
		
	@staticmethod	
	def modMetooContext(agentMessage):
		'''
		Контекст пакета запроса изменения типа похода на событие
		'''
		context = {}
		context['type'] = 'metoo'	
		result = MeTooManager.modMeToo(agentMessage['sessionid'], agentMessage['eventId'], agentMessage['metooTypeId'])
		context['result'] = result
		return context
