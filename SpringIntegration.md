_<font color='red'>Genson website has moved to <a href='http://owlike.github.io/genson/'>http://owlike.github.io/genson/</a> and the code is hosted on GitHub at <a href='https://github.com/owlike/genson'>https://github.com/owlike/genson</a></font>_

# Integration #
To enable json support in Spring MVC with Genson, you need to register Gensons MessageConverter implementation, [GensonMessageConverter](http://code.google.com/p/genson/source/browse/src/main/java/com/owlike/genson/ext/spring/GensonMessageConverter.java).

```
<mvc:annotation-driven>
  <mvc:message-converters>
    <bean class="com.owlike.genson.ext.spring.GensonMessageConverter"/>
  </mvc:message-converters>
</mvc:annotation-driven>
```