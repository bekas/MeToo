# -*- coding: utf-8 -*-
'''
Модуль, отвечающий за работу с событиями.
'''
from eventManager import EventManager
from sessionManager import SessionManager
from userManager import UserManager
from datetime import datetime, date, time

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
		if not 'session_id' in agentMessage:
			agentMessage['session_id'] = -1
		
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
					
					'logout':
						lambda x: 
							MessageManager.logoutContext(x),				
					
					'registrate':
						lambda x: 
							MessageManager.registrateContext(x),
							
					'friend_add':
						lambda x: 
							MessageManager.friendAddContext(x),

					'friend_delete':
						lambda x: 
							MessageManager.friendDeleteContext(x),
					
					'friends':
						lambda x: 
							MessageManager.getFriendsContext(x),
				
					'events':
						lambda x: 
							MessageManager.eventsContext(x),
		
					'event_create':
						lambda x: 
							MessageManager.createEventContext(x),
							
					'event_delete':
						lambda x: 
							MessageManager.deleteEventContext(x),		
			
					'event_modify':
						lambda x: 
							MessageManager.modifyEventContext(x),
							
					'profile_modify':
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
					'metoo_modify':
						lambda x:
							MessageManager.modMetooContext(x),
					'metoo_delete':
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
		#User
		'''
		devcontext['type'] = 'auth'
		devcontext['login'] = 'new_test_log'
		devcontext['password'] = 'new_test_pass'
		'''
		'''
		devcontext['type'] = 'registrate'
		devcontext['login'] = 'new_test_log'
		devcontext['password'] = 'new_test_pass'
		'''
		'''
		devcontext['type'] = 'profile_modify'
		devcontext['avatar'] = 'new_photo'
		devcontext['gender'] = 'M'
		devcontext['description'] = 'Test modify user'
		devcontext['interest'] = ['girls','food']
		devcontext['sn'] = [1,2]
		devcontext['session_id'] = 90		
		'''
		'''
		devcontext['type'] = 'logout'
		devcontext['session_id'] = '93'
		#devcontext['password'] = 'test'
		'''
		'''
		devcontext['type'] = 'friend_add'
		devcontext['session_id'] = '94'
		devcontext['friends'] = '4;2;5;3;1;1'
		'''
		'''
		devcontext['type'] = 'friend_delete'
		devcontext['session_id'] = '94'
		devcontext['friends'] = '1;5'
		'''
		
		devcontext['type'] = 'friends'
		devcontext['session_id'] = '94'
	
		
		#Event
		'''
		devcontext['type'] = 'events'
		devcontext['session_id'] = '94'
		devcontext['longitude'] = '37.6'
		devcontext['latitude'] = '55.6'
		devcontext['radius'] = '0.3'
		'''
		'''
		devcontext['type'] = 'event_modify'
		devcontext['session_id'] = 15
		devcontext['eventid'] = 8
		devcontext['latitude'] = 55.0
		devcontext['longitude'] = 37.0
		devcontext['name'] = 'TEST_EVENT2'
		'''
		'''
		devcontext['type'] = 'event_create'
		devcontext['session_id'] = '94'
		
		devcontext['latitude'] = '55.6'
		devcontext['longitude'] = '37.6'
		devcontext['name'] = 'New Event-2'
		devcontext['time'] = '31-12-12 23:55'		
		devcontext['description'] = 'Some words about..'	
		devcontext['photo'] = 'new photo'	
		devcontext['event_type_id'] = '1'			
		'''
		'''
		devcontext['type'] = 'event_delete'
		devcontext['session_id'] = 15
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
		context['session_id'] = sessionRes
		return context
	
	@staticmethod
	def logoutContext(agentMessage):
		'''
		Контекст пакета отключения пользователя от сервера
		'''
		context = {}
		context['type'] = 'logout'	
		#sessionRes = 1
		sessionRes = UserManager.disconnectUser(int(agentMessage['session_id']))
		context['result'] = sessionRes
		return context
		
	@staticmethod
	def registrateContext(agentMessage):
		'''
		Контекст пакета регистрации
		'''
		context = {}
		context['type'] = 'registrate'
		context['result'] = UserManager.createAccount(agentMessage['login'],agentMessage['password'])
		if int(context['result']) > 0:
			context['session_id'] = context['result']
		return context
	#################################################################################################3	
	@staticmethod
	def friendAddContext(agentMessage):
		'''
		Контекст пакета добавления друзей
		'''
		context = {}
		context['type'] = 'friend_add'
		context['result'] = UserManager.addFriends(int(agentMessage['session_id']),agentMessage['friends'])
		return context
		
	@staticmethod
	def friendDeleteContext(agentMessage):
		'''
		Контекст пакета удаления друзей
		'''
		context = {}
		context['type'] = 'friend_delete'
		context['result'] = UserManager.deleteFriends(int(agentMessage['session_id']),agentMessage['friends'])
		return context
				
	@staticmethod
	def getFriendsContext(agentMessage):
		'''
		Контекст пакета получения списка друзей
		'''
		context = {}
		context['type'] = 'friends'
		context['result'],context['friends'] = UserManager.getListFriends(int(agentMessage['session_id']))
		return context
	#########################################################################################################
	@staticmethod	
	def eventsContext(agentMessage):
		'''
		Контекст пакета запроса событий
		'''
		context = {}
		context['type'] = 'events'
		agentMessage['radius'] = float(agentMessage['radius'])
		agentMessage['latitude'] = float(agentMessage['latitude'])
		agentMessage['longitude'] = float(agentMessage['longitude'])
		context['events'] = EventManager.getEvents(int(agentMessage['session_id']),agentMessage)
		context['count'] = len(context['events'])
		return context
	
	@staticmethod
	def createEventContext(agentMessage):
		'''
		Контекст пакета создания событий
		'''
		context = {}
		context['type'] = 'event_create'
		agentMessage['latitude'] = float(agentMessage['latitude'])
		agentMessage['longitude'] = float(agentMessage['longitude'])
		agentMessage['time'] = datetime.strptime(agentMessage['time'], "%d-%m-%y %H:%M")
		
		context['result'] = EventManager.createEvent(int(agentMessage['session_id']),agentMessage)
		return context
	
	@staticmethod
	def modifyEventContext(agentMessage):
		'''
		Контекст пакета изменения событий
		'''
		context = {}
		context['type'] = 'event_modify'
		context['result'] = EventManager.modifyEvent(int(agentMessage['session_id']),agentMessage)
		return context
	
	@staticmethod
	def deleteEventContext(agentMessage):
		'''
		Контекст пакета удаления событий
		'''
		context = {}
		context['type'] = 'event_delete'
		context['result'] = EventManager.deleteEvent(int(agentMessage['session_id']),agentMessage['eventid'])
		return context
	
	@staticmethod	
	def modifyProfileContext(agentMessage):
		'''
		Контекст пакета редактирования профиля
		'''
		context = {}
		context['type'] = 'profile_modify'
		context['result'] = UserManager.editAccount(int(agentMessage['session_id']), agentMessage)
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
		result = MeTooManager.getUsersbyEvent(int(agentMessage['session_id']), agentMessage['eventId'])
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
		result = MeTooManager.meToo(int(agentMessage['session_id']), int(agentMessage['event_id']), int(agentMessage['metooType_id']))
		context['result'] = result
		return context		

	@staticmethod	
	def delMetooContext(agentMessage):
		'''
		Контекст пакета запроса отказа на событие
		'''
		context = {}
		context['type'] = 'metoo'	
		result = MeTooManager.delMeToo(int(agentMessage['session_id']), int(agentMessage['event_id']))
		context['result'] = result
		return context
		
	@staticmethod	
	def modMetooContext(agentMessage):
		'''
		Контекст пакета запроса изменения типа похода на событие
		'''
		context = {}
		context['type'] = 'metoo'	
		result = MeTooManager.modMeToo(int(agentMessage['session_id']), int(agentMessage['event_id']), int(agentMessage['metooType_id']))
		context['result'] = result
		return context
