# Service Template

Standard SpringBoot Project Template

# Applied technology

* [Spring Boot](https://spring.io/projects/spring-boot) – as the basic framework
* [PostgreSQL](https://www.postgresql.org/) – as a basic relational database
* [Redis](https://redis.io/) – how to cache and queue messages via pub/sub
* [testcontainers](https://testcontainers.com/) – for isolated testing with a database
* [Liquibase](https://www.liquibase.org/) – to conduct database schema migration
* [Gradle](https://gradle.org/) – as an application build system
* [Lombok](https://projectlombok.org/) – for convenient work with POJO classes
* [MapStruct](https://mapstruct.org/) – for convenient mapping between POJO classes


# Task

notification_service should send a notification to a user who has been offered a new skill by another user

# Code

* Usual three - layer
  architecture – Controller, [Service](https://github.com/schonpink/post_services/blob/master/src/main/java/post/service/CommentEventService.java), [Repository](src/main/java/analytics/repository)
* The Repository layer is implemented on both JdbcTemplate and JPA (Hibernate)
* Implemented simple Messaging via [Redis pub/sub](https://redis.io/docs/manual/pubsub/)
    * [Configuration](src/main/java/notification/config/RedisConfig.java) –
      setting up [RedisTemplate](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html) –
      a class for convenient work with Redis and Spring
    * I implemented a SkillOfferedEventPublisher — the sender of the SkillOfferedEvent event in user_service
    * The SkillOfferedEventListener listens to the SkillOfferedEvent events and sends a notification to the user who received the skill offer. Also for this, I created the appropriate MessageBuilder implementation to build a correct subscription message via messages.yaml . Notifications are sent in the way that the recipient of the skill has chosen as preferred. My code implements one of the ways to deliver notifications to users - by email


# Tests

* SpringBootTest
* MockMvc
* Testcontainers
* AssertJ
* JUnit5
* Parameterized tests