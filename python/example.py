#!/usr/bin/python

from ninja.pelirrojo.takibat.bot import BotPlugin,BotCommand

# The Difference between a Command and a Plugin is that a Command
# is only handed execution when it is invoked, and a Plugin is
# Event-driven; running execution on every one of the listed
# events.

class ExampleCommand(BotCommand):
    # provides = command that runs this code
    provides = [ "example" ]
    def onCommand(self,user,chan,cmd,args,raw,out,err):
        """
        Arguments:

        user === The User as an object
        chan === The Channel that was sent to
        cmd  === The Command the user ran
        args === The Arguments to the command
        raw  === The Raw String Line
        out  === Standard Output (Java PrintStream with a .slashMe(String))
        err  === Standard Error  (Java PrintStream with a .slashMe(String))
        """
        ### out and err don't work right now, please use chan.msg(String) or user.msg(String)
        if user.isOp():
            chan.msg("Hi {}! You're an Operator!".format(user.name))
        else:
            chan.msg("You're not an Operator!")

class ExamplePlugin(BotPlugin):
    periodicInterval = -1
    ## The Time interval to run the `Periodic()` method.
    ## Code will also throw a tizzy if periodicInterval XOR
    ##  .periodic().
    def onLine(self,user,chan,line,out,err):
        pass
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
        pass

