## _<font color='red'>Genson website has moved to <a href='http://owlike.github.io/genson/'>http://owlike.github.io/genson/</a> and the code is hosted on GitHub at <a href='https://github.com/owlike/genson'>https://github.com/owlike/genson</a></font>_**##**


Genson is an open-source library doing conversion from Java to Json and Json to Java. Genson targets people who want an extensible but also configurable, fast, scalable and easy to use library.


---

### <font color='green'>Nice features</font> ###
  * Easy to use, fast, highly configurable, lightweight and all that into a single small jar!
  * Full databinding and streaming support for efficient read/write
  * Support for polymorphic types (able to deserialize to an unknown type)
  * Does not require a default no arg constructor and really passes the values not just null, encouraging immutability. It can even be used with factory methods instead of constructors!
  * Full support for generic types
  * Easy to filter/include properties without requiring the use of annotations or mixins
  * You can apply filtering or transformation logic at runtime on any bean using the BeaView mechanism
  * [Genson provides](JSR353.md) a complete implementation of JSR 353
  * Starting with [Genson 0.95 JAXB annotations](JaxbBundle.md) and types are supported!
  * Automatic support for JSON in JAX-RS implementations
  * Serialization and Deserialization of maps with complex keys

And much more to come...

---


### Download it! ###
**Maven users**
```
<dependency>
  <groupId>com.owlike</groupId>
  <artifactId>genson</artifactId>
  <version>0.99</version>
</dependency>
```

**Non Maven users**

All releases available [here](http://mvnrepository.com/artifact/com.owlike/genson) and in the [download section](http://code.google.com/p/genson/downloads/list).




#### Motivation ####
Most librairies have one of the following disadvantages : they miss of important features or have a lot of features but you can hardly add new features by yourself. Gensons initial motivation is to solve those problems by trying to come with useful features out of the box and stay as much as possible open to extension.

#### Goals ####
  * Be as much extensible as possible by allowing users to add new functionnalities in a clean and easy way. Genson applies the philosophy that _"We can not think of every use case, so give to users the ability to do it by them self in a easy way"_.
  * Provide an easy to use API.
  * Try to be as fast and scalable or even faster than the most performant librairies.
  * Full support of Java generics.
  * Provide the ability to work with classes of which you don't have the source code.
  * Provide an efficient streaming API.

#### Documentation ####
  * [Javadoc](http://genson.googlecode.com/git/javadoc/index.html), latest version javadoc.
  * [Wiki](GettingStarted.md) presenting everything you want to know about Genson.
  * [Benchmarks](Metrics2.md) comparing Genson to other well known librairies.
  * [ChangeLog](Changelog.md) describing main features of each release.