#!/usr/bin/python

from ninja.pelirrojo.takibat.bot import BotCommand

class Rot13Command(BotCommand):
    provides = [ "rot13" ]
    def getProvides(self):
        return [ "rot13" ]
    def onCommand(self,user,chan,cmd,args,raw,out,err):
        out.println(" ".join(args).encode("rot13"))


