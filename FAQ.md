_<font color='red'>Genson website has moved to <a href='http://owlike.github.io/genson/'>http://owlike.github.io/genson/</a> and the code is hosted on GitHub at <a href='https://github.com/owlike/genson'>https://github.com/owlike/genson</a></font>_

# FAQ #
  * **Where can I get Genson?**
> From maven central repository, see [Download](GettingStarted#Download.md) section of the user guide.

  * **How to exclude a property from serialization/deserialization?**
> Annotate your field and getter/setter with @JsonIgnore.


  * **How to use a field or getter/setter even if he is filtered by VisibilityFilter or if its name does not respect JavaBean conventions?**
> Annotate it with [@JsonProperty](http://genson.googlecode.com/git/javadoc/com/owlike/genson/annotation/JsonProperty.html). You can even indicate if you want it to be included only in serialization or deserialization.

  * **How to skip null values from output?**
```
new Genson.Builder().setSkipNull(true).create();
```

  * **How to create a custom converter/serializer/deserializer?**
> See the custom converter section of the user guide [here](GettingStarted#Custom_Converter.md).

  * **How to store state/pass data from one converter to another?**
    * Use [Context](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Context.html) class (prefered solution), it is intended to be shared across all converters for a single execution and already provides some usefull methods to store/retrieve data.
    * Use [ThreadLocalHolder](http://genson.googlecode.com/git/javadoc/com/owlike/genson/ThreadLocalHolder.html) which internally uses a ThreadLocal map to store data.

  * **How to change the default visibility of serialized/deserialized fields, methods and constructors?**
> Genson uses [VisibilityFilter](http://genson.googlecode.com/git/javadoc/com/owlike/genson/reflect/VisibilityFilter.html) for filtering fields, methods and constructors by their [Modifiers](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Modifier.html).
> For example if you want to accept all fields :
```
new Genson.Builder().setFieldVisibility(VisibilityFilter.ALL).create();

// you can also define a custom visibility filter : 
new Genson.Builder().setFieldVisibility(new VisibilityFilter(Modifier.TRANSIENT, ...other excluded modifiers...)).create();
```

  * **How to use only fields or methods?**
> For example if you want to use only fields :
```
new Genson.Builder().setUseGettersAndSetters(false).setFieldVisibility(VisibilityFilter.DEFAULT).create();
```
> You need to change the visibility filter for fields as by default it will only use fields with public/package visibility.

  * **How to deserialize into a interface/abstract class?**
> See the [class metadata mechanism](GettingStarted#Interface/Abstract_classes_support.md) discussed in the User Guide.

  * **How to deserialize into a class without a no argument constructor?**
> The main problem in using constructors with arguments is to resolve parameter names, as they are not available through introspection. Genson provides a couple of solutions:
    * If your constructor parameter names match the properties names then you can enable it with : [this feature](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Genson.Builder.html#setWithDebugInfoPropertyNameResolver(boolean)).
    * Annotate each parameter with @JsonProperty(theName) and specify the name of the matched property.
    * Use a custom Converter/Deserializer or a [Bean View](GettingStarted#Bean_View.md).