#-*- coding: utf8 -*-
import os, sys, inspect
 # use this if you want to include modules from a subforder
cmd_subfolder = os.path.realpath(os.path.abspath(os.path.join(os.path.split(inspect.getfile( inspect.currentframe() ))[0],"../mainServer")))
if cmd_subfolder not in sys.path:
        sys.path.insert(0, cmd_subfolder)
        
from userManager import UserManager

class Authorization:
	def login (self, name, password):
		self.msg_code = UserManager.connectUser(name, password)
                return self.msg_code
		
	def logout (self, userid, session_id):
		self.msg_code = UserManager.disconnectUser(userid, session_id)
                return self.msg_code
 
if __name__ == '__main__':
    main()
