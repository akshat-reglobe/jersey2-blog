# Java REST Service application

Project contains:

* [Jersey 2][2]
* [Jetty][3] (as [Maven plugin][10])
* [Spring][4] (IoC, Profiles)
* [MongoDB] as data source (Spring Data)
* Redis integration with [Jedis][8] ([Spring Data][5])
* [Swagger for Jersey][6] API documentation with [Swagger UI][7]
* Integration tests with [Jersey Test Framework][11] support (Jetty as HTTP container)

## Test (unit & integration)

    mvn clean test

## Usage

    mvn clean package -Dspring.profiles.active="dev" jetty:run

[2]: https://jersey.java.net
[3]: http://www.eclipse.org/jetty
[4]: http://spring.io/
[5]: http://projects.spring.io/spring-data-redis/
[6]: https://github.com/wordnik/swagger-core/wiki/Java-JAXRS-Quickstart
[7]: http://swagger.wordnik.com/
[8]: https://github.com/xetorthio/jedis
[9]: https://jax-rs-spec.java.net/
[10]: http://docs.codehaus.org/display/JETTY/Maven+Jetty+Plugin
[11]: https://jersey.java.net/documentation/latest/test-framework.html