_<font color='red'>Genson website has moved to <a href='http://owlike.github.io/genson/'>http://owlike.github.io/genson/</a> and the code is hosted on GitHub at <a href='https://github.com/owlike/genson'>https://github.com/owlike/genson</a></font>_

# Integration #
To enable json support in JAX-RS implementations with Genson, just drop the jar into your classpath. The implementation will detect it and use Genson for json conversions.
For the curious ones you can have a look at the [code](http://code.google.com/p/genson/source/browse/src/main/java/com/owlike/genson/ext/jaxrs/GensonJsonConverter.java).

Actually it has been tested with Jersey and Resteasy. It works out of the box.

# Customization #
In many cases you might want to customize Genson instance.
To do that use Genson.Builder to create a custom instance and then inject it with ContextResolver.

```
@Provider
public class GensonCustomResolver implements ContextResolver<Genson> {
    // configure the Genson instance
    private final Genson genson = new Genson.Builder().create();

    @Override
    public Genson getContext(Class<?> type) {
        return genson;
    }
}
```


**Note** By default Genson JAX-RS integration enables JAXB annotations support.