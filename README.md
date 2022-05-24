# Welcome to FirstLane!
### Developed by me (Alec)!


## Info
### What does FirstLane do?
FirstLane is a simple, secure, and fast password manager that lets you keep your data safe, accessible only by you. From passwords to other info, FirstLane is the simple solution for protecting all your data. 
### What are all these files?
Because FirstLane is an open-source project, all of our files are available on GitHub at this page. Lets go through the important ones one by one.

#### Startup.java
Startup.java is used to start the program. Either run this or run the Jar file using the instructions below to start the program and open the Login/Register window.
#### Client.java
This is the interface between the client and the server. The client sends requests through here and parses server responses for use inside of the app.
#### PasswordUtils.java and AESUtils.java
These handle the encryption and decryption, which is all done on the client side.
#### Login.java and Register.java
These are pretty self-explanatory.
#### APIRestController.java
This is the server-side REST API controller that receives and parses all requests.
#### DBLink.java
This is the connection between the REST API and the database in which all of the data is stored.

### Dependencies
You can find all of the dependencies used by this project in the Maven pom.xml


### Running the app
To start the server, run the following command in the terminal (only do this when self-hosting):\
```java -jar firstlane-v1.0.0.jar```

To start the client, run the following command in the terminal:\
```java -cp firstlane-v1.0.0.jar -Dloader.main=client.app.Startup org.springframework.boot.loader.PropertiesLauncher```

###### An [Infiniton](https://infiniton.xyz) Product
