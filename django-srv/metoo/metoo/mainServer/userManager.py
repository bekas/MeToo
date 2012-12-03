# -*- coding: utf-8 -*-
'''
Модуль работы с пользователями
'''
from django.db import models
from models import User, UserInterest,Interest, UserSocialNetwork,SocialNetwork, Friend
from sessionManager import SessionManager

# создание/редактирование/удаление аккаунта, логин/логаут юзера, добавление/удаление друзей, получение списка друзей

class UserManager:
	'''
	Класс предоставляющий методы работы с пользователями\n
	- Создание аккаунта\n
	- Редактирование аккаунта\n
	- Подключение\n
	- Отключение\n
	- Добавить друзей\n
	- Удалить друзей\n
	- Выдать список друзей\n
	'''
	
	@staticmethod
	def createAccount(new_login, new_password):
		'''
		Метод для создания аккаунта
		'''
		if User.objects.filter(login__iexact=new_login).exists():
			msg_code = -201
		else:
			u = User.objects.create(login=new_login, password=new_password,avatarId_id = 1,rating = 0)
		session_id = UserManager.connectUser(new_login, new_password)
		if session_id == -101:
			msg_code = -202
		else:
			msg_code = session_id
		return msg_code # возвращает айди сессии или код ошибки

#    def deleteAccount(userid, session_id):
#        if SessionManager.getUserId(session_id) > 0:
#            msg_code = self.disconnectUser(userid, session_id)
#            if msg_code > 0:
#                # удаляются записи из Photo, UserInterest, UserSocialNetwork, Friend, User
#                if models.User.objects.exists(pk=userid):
#                    u = models.User.objects.get(pk=userid)
#                    u.delete() # жаль, ничего не возвращает
#                    # как удалять запись из Photo?
#                    msg_code = 200
#                else:
#                    msg_code = -203
#        else:
#            msg_code = -204
#        return msg_code # возвращает 200 или код ошибки

	@staticmethod
	def editAccount(session_id, userArgs):
		'''
		Метод для редактирования аккаунта
		'''
		userid = SessionManager.getUserId(session_id)
		if userid > 0:
			try:
				u = User.objects.get(pk=userid)
				if userArgs.has_key('avatar'):
					u.avatarId.photo = userArgs['avatar']
				
				if userArgs.has_key('gender'):
					u.gender = userArgs['gender']
					
				if userArgs.has_key('description'):
					u.description = userArgs['description']
				u.save()
				
				if userArgs.has_key('interest'):
					UserInterest.objects.filter(userId__pk = userid).delete()
					for interest in userArgs['interest']:
						#Проверка на существование 
						if Interest.objects.filter(name = interest).exists():
							uinterest = Interest.objects.get(name = interest)
						else:
							uinterest = Interest(name = interest)
							uinterest.save()
						
						new_ui = UserInterest(userId=User.objects.get(pk=userid), interestId=uinterest)
						new_ui.save()
				if userArgs.has_key('sn'):
					UserSocialNetwork.objects.filter(userId=userid).delete() 
					for networkId in userArgs['sn']:
						new_usn = UserSocialNetwork(userId=User.objects.get(pk=userid), socialNetworkId=SocialNetwork.objects.get(pk=networkId))
						new_usn.save()
				msg_code = 200
			except:
				msg_code = -205              
			#else:
			#	msg_code = -204
		else:
			msg_code = -203
		return msg_code

	@staticmethod
	def connectUser(login, password):
		'''
		Метод для подключения пользователя к серверу
		'''
		u = User.objects
		if u.filter(login__iexact=login, password__exact=password).exists():
			# не уверена, что в таком виде сожрет Оо
			userid = u.get(login__iexact=login, password__exact=password).pk
			session_id = SessionManager.getSessionID(userid) #заглушка для метода выдачи хэндла
			if session_id > 0:
				msg_code = session_id
			else:
				msg_code = -103
		elif u.filter(login__iexact=login).exists():
			msg_code = -102
		else:
			msg_code = -101
		return msg_code # возвращает айди сессии или код ошибки

	@staticmethod
	def disconnectUser(session_id):
		'''
		Метод для отключения пользователя от сервера
		'''
		userid2 = SessionManager.freeSession(session_id) 
		if userid2 > 0:
			#if userid == userid2: # и не надо мне говорить, что такого быть не может
			msg_code = userid2
			#else:
			#	msg_code = -104
		else:
			msg_code = -105
		return msg_code # возвращает айди юзера или код ошибки

	@staticmethod
	def addFriends(session_id, list_newfriends): # !не проверяет правильность id друзей
		'''
		Метод добавления друзей к пользователю
		'''
		userid2 = SessionManager.getUserId(session_id)
		if userid2 > 0:
			#if userid == userid2:
				try:
					f = Friend.objects.get(pk=userid)
					list_friends = list(set(f.friend.split(";")) | set(list_newfriends))
					f.friend = ";".join(list_friends)
					f.save()
					msg_code = 200
				except:
					msg_code = -206
			#else:
			#	msg_code = -204
		else:
			msg_code = -203
		return msg_code # возвращает 200 или код ошибки

	@staticmethod
	def deleteFriends(userid, session_id, list_exfriends):
		'''
		Метод удаления друзей у пользователя
		'''
		userid2 = SessionManager.getUserId(session_id)
		if userid2 > 0:
			if userid == userid2:
				try:
					f = models.Friend.objects.get(pk=userid)
					friendline = f.friend
					list_friends = friendline.split(";")
					list_friends = list(set(list_friends).difference(list_exfriends))
					friendline = ";".join(list_friends)

					msg_code = 200
				except:
					msg_code = -206
			else:
				msg_code = -204
		else:
			msg_code = -203
		return msg_code  # возвращает 200 или код ошибки

	@staticmethod
	def getListFriends(userid, session_id):
		'''
		Метод получения списка друзей
		'''
		userid2 = SessionManager.getUserId(session_id)
		if userid2 > 0:
			if userid == userid2:
				try:
					f = models.Friend.objects.get(pk=userid)
					friendline = f.friend
					if friendline != '':
						list_friends = friendline.split(";")
					else:
						list_friends = list()
                    
					msg_code = 200
				except:
					msg_code = -207
			else:
				msg_code = -204
		else:
			msg_code = -203
		return msg_code, list_friends # возвращает список id друзей, 200 или код ошибки
