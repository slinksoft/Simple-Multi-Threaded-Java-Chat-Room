# Simple-Multi-Threaded-Java-Chat-Room
A simple multi-threaded chat room made in Java for my Systems Programming Class.



COP 4338 Spring 2020 Assignment 1
Simple Chat Room Server
---------------------------------

IP Address: 12.0.0.7 - the local host the server is being runned on
Port: 2788
Protocols and commands:

- /QUIT, or /quit, or /LOGOUT, or /logout: logs the user out of the server
- /users or /USERS: Returns the list of active users logged into the server
/help or /HELP: Returns the basic list of available commands

Typing anything else other than the commands above will result as a message that is sent to the server and broadcasted to multiple users

Completed work:
- Server is multi-threaded
- ArrayLists of PrintWriters are synced with all users, even when users log out of the server
- Chatroom functional

Bonus part:

Only bonus part completed is rejecting a user that typed in an existing ID that is already in the server.


Tested with Windows Telnet and ChatClient.java (within source code)
