
# Setup

  * [Requirements](#requirements)
  * [Client Side](#client)
    * [Download Eclipse](#download-eclipse)
    * [Import the project to your workspace](#import-the-project-to-your-workspace)
    * [Run the app](#run-the-app)
    * [Troubleshooting](#troubleshooting)
  * [Server Side](#serverside)  
    * [Download Netbeans](#download-netbeans)
    * [Import Server side code to your workspace](#import-the-project-to-your-workspacex)
    * [Run the Server](#run-the-Server)

  * [Connecting client app to server](#client-server-connection) 


# Requirements

Java Development Kit (JDK) [Download](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

Android SDK [Download](http://developer.android.com/sdk/index.html)

Apache Tomcat

NetBeans 

# Client Side

## Download Eclipse
Eclipse is packaged with the [Android Developer Tools bundle](http://developer.android.com/sdk/index.html)

## Import the project to your workspace
  1. Choose File> Import...
  2. Choose Android > Existing Android Code Into Workspace
  3. Navigate to the source code/campusnav-client folder in our project
  4. You should see two projects - "campusnav-client" and "google-play-services_lib".
  5. Ensure both are selected
  6. Press "Finish"

## Run the app
  1. Ensure your phone is in plugged in, developer mode enabled and screen unlocked
  2. Press the green arrow, or choose Run>Run

## Troubleshooting
### I see a grey map

Follow the instructions under "Add your API key" and try again.

### I see the Android starter screen
  1. Choose Window>Close All Perspectives
  2. Choose Window>Open Perspective>Other...
  3. Choose Java

#Server Side

## Download netbeans with apache tomcat

## Import Server side code to your workspace
  1. Choose File > Open Project
  2. Navigate to campusnav folder in source code folder
  3. Import into workspace

## Run the server

# Connecting client app to server
  1. Open res/values/strings.xml in android project 
  2. Change "server_base_url" to your server's base url which you set up in previous step
  3. Now client app will connect to your server
