# Vinyl Store
It's a place where you can find records of your favourire bands and buy them!
Vinyl Store is my pet-project where the technologies I've learned are put to use.

#### Stack: Java, Spring Boot, Spring Security, PostgreSQL, JPA, Hibernate, Maven, Docker, Apache Kafka, Redis, Spring Cloud Gateway, Netflix(Eureka), OpenFeign.

![](https://github.com/xdpxrt/pet-vinyl-store/blob/main/ProjectArchitecture.PNG)

Done at the moment:
- registration (app available only for authenticated users)
- moderation of all content
- storing of covers of records at the AamazonS3
- search records by different params
- caching popular data
- creation orders and notification to email of changing their statuses 
- sending messages about congratulating users on their birthday
  
Plans:
- add compilations of popular performers
- add pre-order functionality
