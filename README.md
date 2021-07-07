# firstmicroservice

Steps To Run MicroService Project

Download zipkin server jar from below path 

https://zipkin.io/pages/quickstart.html

Select Java latest relase

After downloading jar goto command prompt 
and run command 
> java -jar jarfilename

you can see last line in command prompt like 

zipkin server start on so and so port.
 http://127.0.0.1:9411/
 
 Please verify in department and user service with port.
 
 For Configuration Server you need to create one repository in github
 Goto https://github.com/ and login if not logged in.
 Then Goto new Repository and create new file with name application.yml
 
 please paste below content in file
 
 eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      default-zone: http://localhost:8761/eureka/
  instance:
    hostname: localhost
 

First Open all projects in IDE.
Then start in below sequence
1) Registry Server
2) Config Server
3) Gateway API (Port : 9191)
4) Department Service
5) User Service
6) Hystrix Dashboard

now will access all api through gateway only 

You can check eureka server also that number of service are up and running.
http://localhost:8761/


1) You can start with adding new department
http://localhost:9191/departments/save with post method and with below payload
URL :http://localhost:9191/departments/save
Method type: POST
Data:

{
	"departmentName":"IT",
	"departmentAddress":"3rd cross, First Street",
	"departmentCode":"IT-006"
	}

2) Retriving Department 
URL:http://localhost:9191/departments/1
Method type : GET 
1 is id of department and passing as path varriable parameter

3) now move to user service and adding new user 
URL:http://localhost:9191/users/save
Method Type:POST
Data:

{
	"firstName":"IT",
	"lastName":"3rd cross, First Street",
	"email":"IT-006",
	"departmentId":"1"
}

4) Fetching User 
URL: http://localhost:9191/users/1
Method Type:GET

It should return as below
{
    "user": {
        "userId": 1,
        "firstName": "IT",
        "lastName": "3rd cross, First Street",
        "email": "IT-006",
        "departmentId": 1
    },
    "department": {
        "departmentId": 1,
        "departmentName": "IT",
        "departmentAddress": "3rd cross, First Street",
        "departmentCode": "IT-006"
    }
}

After calling above api you can check log that some of api has same trace id and span id where some has different
because trace id will be per request where span id per service so may be one request travel through multiple services

2021-07-07 11:49:08.521  INFO [DEPARTMENT-SERVICE,b30ba60b26b4c20d,b30ba60b26b4c20d,true] 12296 --- [nio-9001-exec-9] c.e.d.controller.DepartmentController    : inside findbydepartment inside department service
2021-07-07 11:49:08.521  INFO [DEPARTMENT-SERVICE,b30ba60b26b4c20d,b30ba60b26b4c20d,true] 12296 --- [nio-9001-exec-9] c.e.d.service.DepartmentService          : inside save department inside department service
2021-07-07 11:49:53.789  INFO [DEPARTMENT-SERVICE,9609647388d5d535,3a7518fe84129869,true] 12296 --- [nio-9001-exec-2] c.e.d.controller.DepartmentController    : inside findbydepartment inside department service
2021-07-07 11:49:53.790  INFO [DEPARTMENT-SERVICE,9609647388d5d535,3a7518fe84129869,true] 12296 --- [nio-9001-exec-2] c.e.d.service.DepartmentService          : inside save department inside department service


2021-07-07 11:49:48.518  INFO [USER-SERVICE,a413fe279307c3c7,a413fe279307c3c7,true] 9680 --- [nio-9002-exec-4] com.ecw.user.controller.UserController   : Inside saveUser of UserController
2021-07-07 11:49:48.518  INFO [USER-SERVICE,a413fe279307c3c7,a413fe279307c3c7,true] 9680 --- [nio-9002-exec-4] com.ecw.user.service.UserService         : saveUser inside UserService
2021-07-07 11:49:53.782  INFO [USER-SERVICE,9609647388d5d535,9609647388d5d535,true] 9680 --- [nio-9002-exec-5] com.ecw.user.controller.UserController   : Inside getUserWithDepartment of UserController
2021-07-07 11:49:53.782  INFO [USER-SERVICE,9609647388d5d535,9609647388d5d535,true] 9680 --- [nio-9002-exec-5] com.ecw.user.service.UserService         : getUserWithDepartment inside UserService

You can see this trace id(9609647388d5d535) traverse in both service this is the same request travese in both and then produce a result.
So in this way you can track your requst also

Now you can check your service through hystrix dashboard also using below url
  http://localhost:9295/hystrix
add url in text box for tracking 
  http://localhost:9191/actuator/hystrix.stream
  
 You can see how many services in process completed or timeout  

