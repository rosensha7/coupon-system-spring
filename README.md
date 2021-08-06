# Read Me First

## About This Project
This is the backend code of an online coupon store. It's implemented with Spring Boot framework and written in Java.
The store was created as part of the main project in the John Bryce Full-Stack Java Development course.

## Technologies for making this project
- Java
- Spring Boot
- Hibernate (with Spring JPA)
- MySQL
- MariaDB
- REST API

# Getting Started

To run this project, make sure you have XAMPP installed and running PhpMyAdmin on localhost (port 3306). 
The system supports MariaDB but can be configured to other databases through the application.properties file. 

Once you have the system up and running on localhost, you can interract with the REST API through Swagger2:
http://localhost:8080/swagger-ui.html#/admin-controller

### Note: most end points require an authorization token in order to be used. If you wish to use them, please download the front end repository of this project, implemented in React.
### if you wish to only use Swagger (without authorization tokens) you can try it with the guest controller, which requires no tokens.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)








