Takibat
=======

A General-purpose IRC Bot that's designed to be easily extensable
for various uses. The original implementation was just a Python
bot that went to fetch live baseball scores (hence why it's
`Takibat` and not `Takibot`).

This iteration of code is based upon Java and Jython (Yes, I am a
programming redneck, shut up). The core bot (the part coded in
Java) handles the IRC Connection and functions such as
(re)loading Python Plugins and dispatching commands to the proper
Python File.

REQUIRES JYTHON IN THE CLASSPATH

Included Commands
-----------------

+ !jing   --- Ping in Java Code
+ !ping   --- Ping in Python Code
+ !rot13  --- Rot13 the rest of the line
+ !reload --- I have not tested this, but theoretically it should
   reload all scripts and plugins.

Todo
----

+ Add !help / !list Command
+ Find the cause of the first 5 command processing threads
  throwing a Null Pointer Exception. [IRC Library]
+ Switch the IRC connection from Sockets to Writers, to prevent
  Charset problems.
+ Permit loading/unloading of individual scripts
+ Add a .jar loader for plugins and commands

License
-------

WTFPL.

