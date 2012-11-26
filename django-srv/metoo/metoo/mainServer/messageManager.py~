# -*- coding: utf-8 -*-
from eventManager import EventManager
from sessionManager import SessionManager
from userManager import UserManager
from eventManager import EventManager

#Менедер пакетов - ответов сервера.
class MessageManager:	
	@staticmethod
	def createContext(agentMessage):		
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
			
					'modifyEvent':
						lambda x: 
							MessageManager.modifyEventContext(x),
							
					'modifyProfile':
						lambda x: 
							MessageManager.modifyProfileContext(x),
					'test':
						lambda x:
							MessageManager.testContext(x),
						
					}[agentMessage['type']](agentMessage)
		
		
		if 'request_id' in agentMessage:
			context['request_id'] = agentMessage['request_id'] 
		return context
	
	
	# Страницы Веб-приложения
	@staticmethod	
	def mainContext(agentMessage):
		context = {}
		context['title'] = 'Welcome!'
		context['type'] = 'main'
		return context	
	
	@staticmethod	
	def aboutContext(agentMessage):
		context = {}
		context['title'] = 'About us'
		context['type'] = 'about'
		context['data'] = 'Some words about us...'
		return context	
		
	@staticmethod	
	def whatContext(agentMessage):
		context = {}
		context['title'] = 'What is it?'
		context['type'] = 'what'
		context['data'] = 'This is a new geo-service, that can help you to meet with your friends.'
		return context	
	
	@staticmethod	
	def downloadContext(agentMessage):
		context = {}
		context['title'] = 'Download MeToo there!'
		context['type'] = 'download'
		context['data'] = 'You can download Android-client there.'
		return context	
	
	@staticmethod	
	def devContext(agentMessage):
		context = {}
		context['title'] = '<<Developer page>>'
		context['type'] = 'dev'
	
		
		devcontext={}
		devcontext['type'] = 'events'
		devcontext['sessionid'] = 15
		devcontext['latitude'] = 55.5
		devcontext['longitude'] = 37.4
		devcontext['radius'] = 0.3
		'''
		devcontext['type'] = 'createEvent'
		devcontext['sessionid'] = 15
		devcontext['latitude'] = 700
		devcontext['longitude'] = 700
		devcontext['name'] = 'TEST_EVENT'
		'''

		context['data'] = devcontext
		
		return context	
			
			
	# Обработчики протокола обмена с приложением
	@staticmethod	
	def pingContext(agentMessage):
		context = {}
		context['type'] = 'ping'
		return context
	
	@staticmethod
	def authoriseContext(agentMessage):
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
		context = {}
		context['type'] = 'registrate'
		return context
	
	@staticmethod	
	def eventsContext(agentMessage):
		context = {}
		context['type'] = 'events'	
		context['events'] = EventManager.getEvents(agentMessage['sessionid'], agentMessage)
		context['count'] = len(context['events'])
		context['result'] = 300
		return context
	
	@staticmethod
	def createEventContext(agentMessage):
		context = {}
		context['type'] = 'eventcreate'
		context['result'] = EventManager.createEvent(agentMessage['sessionid'],agentMessage)
		return context
	
	@staticmethod
	def modifyEventContext(agentMessage):
		context = {}
		context['type'] = 'eventmodify'
		return context
	
	@staticmethod	
	def modifyProfileContext(agentMessage):
		context = {}
		context['type'] = 'profilemodify'
		return context
		
	@staticmethod
	def testContext(agentMessage):
		context = {}
		context['type'] = 'test'
		context['get'] = agentMessage
		return context
