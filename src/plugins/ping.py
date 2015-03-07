#!/usr/bin/python
# -*- coding: utf8 -*-

from ninja.pelirrojo.takibat.bot import BotCommand

class PingCommand(BotCommand):
    provides = "ping"
    def onCommand(self,user,chan,cmd,args,raw,out,err):
        """
        Arguments:

        user === The User Options as a Dict
        chan === The Channel that was sent to
        cmd  === The Command the user ran
        args === The Arguments to the command
        raw  === The Raw String Line
        out  === Standard Output (Java PrintStream with a .slashMe(String))
        err  === Standard Error  (Java PrintStream with a .slashMe(String))
        """
        chan.msg("Pong!")

