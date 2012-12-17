# -*- coding: utf-8 -*-
'''
Модуль работы с представлениями.
'''
from django.shortcuts import render_to_response
from django.utils import simplejson
from django.http import HttpResponse
import re
from messageManager import MessageManager

# MAIN CALLBACK >>
def mainPage(request):
	'''
	Метод, возвращающий основную страницу сайта
	'''
	packet = processRequest(request,'main')
	context = MessageManager.createContext(packet['get'])
	return render(context,packet)

def aboutPage(request):
	'''
	Метод, возвращающий страницу сайта "About"
	'''
	packet = processRequest(request,'about')
	context = MessageManager.createContext(packet['get'])
	return render(context,packet)

def downloadPage(request):
	'''
	Метод, возвращающий страницу сайта "Download"
	'''
	packet = processRequest(request,'download')
	context = MessageManager.createContext(packet['get'])
	return render(context,packet)

def downloadClientPage(request):
	'''
	Метод, возвращающий страницу сайта "Download Client For Android"
	'''
	packet = processRequest(request,'download_client')
	context = MessageManager.createContext(packet['get'])
	return render(context,packet)


def whatPage(request):
	'''
	Метод, возвращающий страницу сайта "What is it"
	'''
	packet = processRequest(request,'what')
	context = MessageManager.createContext(packet['get'])
	return render(context,packet)

def mapPage(request):
	'''
	Метод, возвращающий страницу сайта "What is it"
	'''
	packet = processRequest(request,'map')
	context = MessageManager.createContext(packet['get'])
	#return render_to_response('markertest.html',context)
	return render(context,packet)


def devPage(request):
	'''
	Метод, возвращающий страницу сайта "Developer page" для отладки
	'''
	packet = processRequest(request,'dev')
	context = MessageManager.createContext(packet['get'])
	context['data'] = render(MessageManager.createContext(context['data']),packet,True)
	return render(context,packet)

def statPage(request):
	'''
	Метод, возвращающий страницу сайта "Statistic"
	'''
	packet = processRequest(request,'stat')
	context = MessageManager.createContext(packet['get'])
	return render(context,packet)
# Service functions


def render(context,packet,mode = False):
	'''
	Метод выполняющий рендер страницы
	'''
	if not mode:
		return render_to_response(processAgent(packet['agent']),context)
	else:
		return render_to_response('agentMainAnswer.xml',context)

def processAgent(userAgent):
	'''
	Метод анализирующий агента
	'''
	answerType = 'mainPage.html'
	if re.match('MeToo.*', userAgent):
		answerType = 'agentMainAnswer.xml'
	return answerType
	
def getAgentVersion(userAgent):	
	'''
	Метод - заглушка, возвращающий версию агента
	'''
	agentVersion = 'v.1'
	return agentVersion
	
def processRequest(request,typePacket):
	'''
	Метод обработки запроса
	'''
	request.encoding = 'utf-8'
	packet = {}
	packet['agent'] = request.META['HTTP_USER_AGENT']
	packet['get'] = request.GET.copy()
	packet['post'] = request.POST.copy()
	
	#TODO >>>>>>>>>
	#packet['get'] = {}
	if not 'type' in packet['get'] :
		packet['get'] = {}
		packet['get']['type'] = typePacket
	
	return packet	
	

#this is code has been copy-pasted from my mail.ru project. Don`t care with it. 
#This is 'little example' for Post-Processing


#def download(request):
#    context = {}
#    context['message'] = 'fail'
#    
#    if request.method == 'POST':
#        form = UploadFileForm(request.POST, request.FILES)
#        if form.is_valid():
#            handle_uploaded_file(request.FILES['file'], request.POST['title'])
#            context['message'] = 'success!'
# 	context['name'] =  request.POST['title'] 
#        return render_to_response('answer.html', context)
#    else:
#        form = UploadFileForm()
#    context['form'] = form
#    return render_to_response('index.html', context)
#    
#def handle_uploaded_file(f,title):
#    destination = open('files/'+title, 'w')
#    for chunk in f.chunks():
#        destination.write(chunk)
#    destination.close()

