# JavaSE Playground

### Description

A test field/playground project for testing out various Java SE features and frameworks. The project is written in Java SE 8 and uses Maven as build
and dependency management tool. In this project there could be found examples from the following features and frameworks:

* SSLContext (TLSv1)
* Design Patterns (Enum Singleton)
* Creating graphs with JGraph framework
* Creating graphs with JUNG (Java Universal Network/Graph) framework
* Custom class loaders
* Dynamic proxies
* Traits in Java 8
* Java IO Sockets
* Java NIO Sockets
* Java NIO File manipulation

### Building and running the examples

As we use Maven as build and dependency management tool, we can build the project with the following command:

```
mvn clean install
```

This command will build the project and will run the unit tests. Besides, the unit tests there are also several classes with main methods. These
classes are Swing applications, which implement graph visualizations. The list includes the following classes:

* GraphFactoryTest
* GraphLayoutTest
* JGraphTest
* CustomVertexEdgeTransformerTest
* InteractiveGraphViewer
* PowerGridGraphViewer
