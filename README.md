# Modular Architecture - Hexagonal Demo Project

This project is a sample production-ready implementation for deomstrating the power of Hexagonal Architecture (aka Ports And Adapters Pattern) written in Java.

![Build Ticket API](https://github.com/AlicanAkkus/Modular-Architecture-Hexagonal-Demo-Project/workflows/Build%20Ticket%20API%20Image/badge.svg)
![Build Payment API](https://github.com/AlicanAkkus/Modular-Architecture-Hexagonal-Demo-Project/workflows/Build%20Payment%20API%20Image/badge.svg)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=hex&metric=ncloc)](https://sonarcloud.io/dashboard?id=hex)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=hex&metric=coverage)](https://sonarcloud.io/dashboard?id=hex)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=hex&metric=sqale_index)](https://sonarcloud.io/dashboard?id=hex)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=hex&metric=code_smells)](https://sonarcloud.io/dashboard?id=hex)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=hex&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=hex)

---

## Table of Contents
* [Motivation](#Motivation)
* [What is the Hexagonal Architecture](#What-is-the-Hexagonal-Architecture)
* [Technologies Used](#Technologies-Used)
* [What You Will Learn](#What-You-Will-Learn)  
* [Useful Links From Authors](#Useful-Links-From-Authors)
* [Useful Links about Hexagonal Architecture](#Useful-Links-about-Hexagonal-Architecture)  
* [History](#History)
* [Contributing](#Contributing)
* [Code of Conduct](#Code-of-Conduct)
* [Authors](#Authors)
* [License](#License)

## Wiki
* [How to build and run tests](../../wiki/How-to-build-and-run-tests)
* [How to run acceptance tests at local](../../wiki/How-to-run-acceptance-tests-at-local)

## Reports
* [Sonar Report](https://sonarcloud.io/dashboard?id=hex)

---

### Hexagonal Architecture Flow Diagram (click to enlarge)
[![Hexagonal Architecture Flow Diagram](docs/images/hexagonal-flow-diagram_sm.jpg)](docs/images/hexagonal-flow-diagram.jpg)


### Motivation

We want to write clean, maintainable, well-defined boundary context, well-tested domain code and isolate business logic from outside concern.

### What is the Hexagonal Architecture

The hexagonal architecture was invented by Alistair Cockburn in an attempt to avoid known structural pitfalls in object-oriented software design, such as undesired dependencies between layers and contamination of user interface code with business logic, and published in 2005.

> A timeless goal of software engineering has been to separate code that changes frequently from code that is stable.
> 
> ~ James Coplien / Lean Architecture

We recommend Hexagonal Architecture for those who want to write clean, maintainable, well-defined boundary context, well-tested domain and decoupling business logic from technical code.

### Technologies Used

You can use any programming language for implementing Hexagonal Architecture. Here is the list of technologies we used for the demo application;

* Spring Boot 2
* Java 11
* Gradle 7
* Mono Repo  
* Docker
* Mysql  
* Redis  
* Kafka
* Groovy 3
* Cucumber 
* GitHub Actions 

It's so easy to reach `100% code coverage` if you design by tests wisely via TDD. There are the types of tests written:

* Unit tests
* Dockerized integration tests
* Consumer driven contract tests
* External verifier contract tests
* Acceptance tests

### What You Will Learn

* Implementing two microservices with spring boot
* Designing the apis with hexagonal architecture
* Packaging by functions and screaming architecture
* How Ports and adapters separate business from integration points  
* Writing behaviour focused unit tests at domain module
* Writing integration tests isolated from business at infra module
* Using gradle to configure microservices and modules
* Using mono-repo to handle all apis in single git repository
* Implementing consumer driven contract tests
* Creating different testing styles for validating data adapters, event consumers/publishers, controllers 

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

This repository is a side project developed while preparing the conference talk "Growing Hexagonal Microservices With TDD" at Java Day Istanbul 2020. As the authors of this repository, we've been using hexagonal architecture in all microservices (i.e. more than 50+ microservices) running in production since 2017. We believe modularity is the key for building maintainable and high quality software. That's why we shared our demo project with the community to attract their interest on the topic.

### Contributing
Please feel free to open tickets to suggest new features and improvement points. Please [feel free to contribute](CONTRIBUTING.md). 

### Code of Conduct
Please take a lot at [code of conduct](CODE_OF_CONDUCT.md) before opening issues or creating pull requests.

### Authors
* **Lemi Orhan Ergin**, Software Crafter at [Craftgate](https://craftgate.io) -> [Github](https://github.com/lemiorhan), [Twitter](https://twitter.com/lemiorhan), [Linkedin](https://www.linkedin.com/in/lemiorhan/)
* **Alican Akkuş**, Software Crafter at [Craftgate](https://craftgate.io) -> [Github](https://github.com/AlicanAkkus), [Twitter](https://twitter.com/alican_akkus), [Linkedin](https://www.linkedin.com/in/aakkus/)

### License

Distributed under the MIT License. See [LICENSE](LICENSE.txt) for more information.
