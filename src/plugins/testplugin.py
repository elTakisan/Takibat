#!/usr/bin/python

from ninja.pelirrojo.takibat.bot import BotPlugin

from time import time

class ExamplePlugin(BotPlugin):
    periodicInterval = 60*5
    def onLine(self,user,chan,line,out,err):
        print "[{}] <{}> {}".format(chan,user.getNick(),line)
    def onSlashMe(self,user,chan,line,out,err):
        pass
    def onJoin(self,user,chan,out,err):
        pass
    def onPart(self,user,chan,message,out,err):
        pass
    def onPriv(self,user,message,out,err):
        pass
    def onUnknown(self,line,out,err):
        """
        This is run whenever something that I don't know exists
        is sent into the IRC Bot.
        """
        pass
    def periodic(self,chan,out,err):
        """
        Periodic Execution in a seperate thread.
        """
        print time()
