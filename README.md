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

License
-------

I'm going to be very weird and make up a new license, called the
Wheaton-Green License. It says this:

> Don't be a dick.
>
> Don't forget to be awesome.

Todo
----

+ Add autodownloader for Jython Jar.
+ Permit loading/unloading of individual scripts
+ Add a .jar loader for plugins and commands
+ Add a Brainfuck interpreter for plugins and commands

