### Create a service that stores audi-Trail information

### Overview:

A service that receives audit trail information about user activity etc. and stores it in a secure and tamper proof manner. 
A simple frontend application that shows the audit trail info

## Detail requirements

### Audit trail service
* Java
* A RESTful endpoint that receives following data
    * Type
    * Subtype
    * Timestamp
    * Message
    * User
* Store data in a database (use managed database of a free tier e.g. from AWS)
* Secure the data of being tampered with a simple block chain mechanism:
    * Every message contains a hash that consists of the data of the message + the hash of the previous message
    * As several instances of this service can be running and receive data in parallel a mechanism needs to be established that ensures rowing up the messages without conflict
    * Do not care for decentral aspects
* Services that support the frontend application

### Audit trail frontend
* frontend technology of your choice (e.g. react)
* shows the audit trail entries
* pagination or infinitive scrolling
* sorting by type, subtype, timestamp, user (and any combination)
* Filter by type, subtype, time range user (and any combination)
* Give alert if data has been tampered with

### We will judge the task by (not a specific order)
* code quality
* simple, creative solutions that avoid unnecessary complexities
* meeting requirements
* presentation (no slides required)
* QA session