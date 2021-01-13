# Modular Architecture - Hexagonal Demo Project

This project is a sample implementation for Hexagonal Architecture (aka Ports And Adapters Pattern) written in Java.

![Build Ticket API Image](https://github.com/lemiorhan/hexagonal/workflows/Build%20Ticket%20API%20Image/badge.svg)
![Build Payment API Image](https://github.com/lemiorhan/hexagonal/workflows/Build%20Payment%20API%20Image/badge.svg)

## Table of Contents
* [Motivation](https://github.com/lemiorhan/hexagonal/README.MD)
* [What is the Hexagonal Architecture](https://github.com/lemiorhan/hexagonal/README.MD)
* [Technologies Used](https://github.com/lemiorhan/hexagonal/README.MD)
* [Usage](https://github.com/lemiorhan/hexagonal/README.MD)
* [Useful Links From Authors](https://github.com/lemiorhan/hexagonal/README.MD)
* [Useful Links about Hexagonal Architecture](https://github.com/lemiorhan/hexagonal/README.MD)  
* [History](https://github.com/lemiorhan/hexagonal/README.MD)
* [Roadmap](https://github.com/lemiorhan/hexagonal/README.MD)
* [Contributing](https://github.com/lemiorhan/hexagonal/README.MD)
* [Authors](https://github.com/lemiorhan/hexagonal/README.MD)
* [License](https://github.com/lemiorhan/hexagonal/README.MD)

### Motivation

We want to write clean, maintainable, well-defined boundary context, well-tested domain code and isolate business logic from outside concern.

### What is the Hexagonal Architecture

The hexagonal architecture was invented by Alistair Cockburn in an attempt to avoid known structural pitfalls in object-oriented software design, such as undesired dependencies between layers and contamination of user interface code with business logic, and published in 2005.

> A timeless goal of so"ware engineering has been to separate code that changes frequently from code that is stable.
> 
> ~ James Coplien / Lean Architecture

We recommend Hexagonal Architecture for those who want to write clean, maintainable, well-defined boundary context, well-tested domain and decoupling business logic from technical code.

### Technologies Used

You can use any programming language for implementing Hexagonal Architecture. Here is the list of technologies we used for the demo application;
* Spring Boot 2
* Java 11
* Gradle 6
* Mono Repo  
* Docker
* Mysql  
* Redis  
* Dockerized Integration Tests
* Consumer Driven Contract Testing

### Usage

After cloning the project on your local, you can use the following gradle commands to build and run tests.
```
# This command builds all modules, boot ups db and redis, runs contract tests and other tests, closes down db and redis 
./gradlew clean build

# If your local infra is already up, then this command only builds all modules and runs contract tests and other tests
./gradlew clean build -PskipInfraSetup
```

You can boot up all apis and infra with the following commands.

```
# This command boot ups db, redis and all apis
./run.sh up-all

# This command stops db, redis and kills all apis
./run.sh down-all
```

### Useful Links From Authors

* Modular Architecture for Pragmatic Developers - [Slides](https://speakerdeck.com/lemiorhan/modular-architecture-for-pragmatic-developers) - [Youtube](https://www.youtube.com/watch?v=aWOHq6AHNjU&t=10841s)
* Growing Hexagonal Microservices With TDD - [Slides](https://speakerdeck.com/lemiorhan/growing-hexagonal-microservices-by-tdd), [Youtube](https://www.youtube.com/watch?v=ZMB-Xlh5Rj0)
* It's Not Solid Anymore: Rethinking Software Design and Modularity - [Slides](https://speakerdeck.com/lemiorhan/it-is-not-solid-anymore), [Youtube](https://www.youtube.com/watch?v=pdtpWYNBzqM)

### Useful Links about Hexagonal Architecture

* ITT 2018 - Jakub Nabrdalik - Hexagonal Architecture in practice - [Youtube](https://www.youtube.com/watch?v=sOaS83Ir8Ck&t=12s)
* Ian Cooper on Hexagonal Architectures at Agile Yorkshire - [Youtube](https://www.youtube.com/watch?v=FJUevNLEtuU)
* Ports and Adapters Pattern, by Juan Manuel Garrido de Paz - [Blog](https://jmgarridopaz.github.io/content/hexagonalarchitecture.html)
* DDD, Hexagonal, Onion, Clean, CQRS, … How I put it all together, by Herberto Graca - [Blog](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)
* Hexagonal Architecture: three principles and an implementation example, by Erwan Alliaume, Sébastien Roccaserra - [Blog](https://blog.octo.com/en/hexagonal-architecture-three-principles-and-an-implementation-example/)

### History

This repository is a side project developed while preparing the conference talk "Growing Hexagonal Microservices With TDD" at Java Day Istanbul 2020.

### Roadmap

We will add additional automated tests to show how hexagonal architecture has impact on implementing tests. We will also learn from you. 
Please feel free to open tickets to suggest new features and improvement points.

### Contributing
Please feel free to contribute by adding new templates to this project:

* Fork it (https://github.com/lemiorhan/hexagonal)
* Create your feature branch (git checkout -b feature/fooBar)
* Commit your changes (git commit -am 'Add some fooBar')
* Push to the branch (git push origin feature/fooBar)
* Create a new Pull Request

### Authors
* [Lemi Orhan Ergin](https://github.com/lemiorhan) - [@lemiorhan](https://twitter.com/lemiorhan)
* [Alican Akkuş](https://github.com/AlicanAkkus) - [@alican_akkus](https://twitter.com/alican_akkus)

### License

Distributed under the MIT License. See [LICENSE](LICENSE.txt) for more information.