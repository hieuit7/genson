_<font color='red'>Genson website has moved to <a href='http://owlike.github.io/genson/'>http://owlike.github.io/genson/</a> and the code is hosted on GitHub at <a href='https://github.com/owlike/genson'>https://github.com/owlike/genson</a></font>_

### Prerequisite ###
In order to use Genson few things are required as Genson is dependency free.
It only requires Java 6 or higher.

### Download ###
Gesnon is available in the maven central repository.
Add this dependency to your pom or download it manually from the central.
```
<dependency>
  <groupId>com.owlike</groupId>
  <artifactId>genson</artifactId>
  <version>0.99</version>
</dependency>
```

---

### Basic use ###
[Genson](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Genson.html) is the main class of the library. It provides all the required methods to do java to json serialization and json to java deserialization. If the default configuration fits your needs you can use its no arg constructor otherwise you can use [Genson.Builder](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Genson.Builder.html) inner class to create custom instances of Genson.

#### Primitives conversion ####
```
Genson genson = new Genson();
String json = genson.serialize(777.777); // the output will be 777.777
genson.serialize(true); // output is true (without quotes)

genson.deserialize("777", int.class); // deserializes it into 777
genson.deserialize("777.777", Object.class); // will return 777.777 (a double)
genson.deserialize("null", Object.class); // will return null;
```

You can also work with arrays:
```
json = genson.serialize(new Object[]{1, 2.2, null, false, "hey!"}); // the result is [1,2.2,null,false,"hey!"]

// and now let's do some magic an deserialize it back without knowing the types:
genson.deserialize(json, Object[].class); // and we got back our initial array!
```

Genson can serialize generic maps and deserialize back to it, it also supports serialization of objects and deserialization into Maps, but we will see it later.
```
Map<String, Object> map = new HashMap<String, Object>();
map.put("aString", "hey");
map.put("aInt", 1);
map.put("anArray", new int[]{1, 2});
Map<String, Object> anotherMap = new HashMap<String, Object>();
anotherMap.put("aBool", false);
map.put("theOtherMap", anotherMap);

json = genson.serialize(map); // we obtain {"aString":"hey","aInt":1,"anArray":[1,2],"theOtherMap":{"aBool":false}}
genson.deserialize(json, Map.class); // we got the initial map back
```

You can also work with multidimensional arrays without problems. Note that if you want to deserialize to a unknown type the result will be and array of objects, so you won't be able to cast it to an array of int.
```
// result is [[[1,2],[5],[4]],[[6]]]
json = genson.serialize(new int[][][]{{{1, 2},{5},{4}},{{6}}});
int[][][] threeDimensionArray = genson.deserialize(json, int[][][].class);

// works also but you will have to cast each value separately as it is an array of objects.
Object object = genson.deserialize(json, Object.class);
```

#### Object databinding ####
Genson provides full support for object databinding by following standard JavaBean conventions. The basic rules for databinding are:
  * All public or package visibility fields, getters and setters will be used, including the inherited ones.
  * If a getter/setter exists for a field then it will be used instead of the field.
  * Transient and synthetic fields are not serialized nor used during deserialization.
  * If a field does not exist in the json stream then its field/setter will not be used.
  * If a value is null then you can choose whether you want it to be serialized as null or just skipped, by default null values are serialized. You can change the way null values are handled by registering your own [Converter](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Converter.html)

Lets have a closer look to an example.
```
class Person {
 String fullName;
 // will be converted even if it is private
 @JsonProperty private Date birthDate;
 Adress adress;
 @JsonIgnore public int ignoredField;
 private int privateNotDetected;
 private Person() {}

 @Creator public static Person create() {
   return new Person();
 }

 public String getFullName(){
  // will be used instead of direct field access
 }
}

class Adress {
  final int building;
  final String street;
  // only a constructor with arguments genson will use it during deserialization
  public Adress(@JsonProperty("building") int building, @JsonProperty("street")  String street) {
  }
}

Person someone = new Person("eugen", new GregorianCalendar(1986, 1, 16).getTime(), new Adress(157, "paris"));
// we obtain the following json string
//{"adress":{"building":157,"street":"paris"},"birthDate":"16 févr. 1986","happy":true,"fullName":"eugen"}
String json = genson.serialize(someone); 

// now we deserialize it back
someone = genson.deserialize(json, Person.class);
```

Note the use of [@JsonIgnore](http://genson.googlecode.com/git/javadoc/com/owlike/genson/annotation/JsonIgnore.html) and [@JsonProperty](http://genson.googlecode.com/git/javadoc/com/owlike/genson/annotation/JsonProperty.html) annotations. The first one allows to mark a field/method as ignored for serialization/deserialization, the second one allows you to specify a name for your property and to include fields/methods. In Genson we choose to give priority to explicit information (ex: annotations) over implicit information (ex: naming convetions, visibility, etc).
Another important thing to notice is that Adress provides a single constructor that takes arguments. Genson will invoke it with the corresponding values from the json string. You may also use static factory methods as the "create" method of Person class, to be detected they need to be marked with [@Creator](http://genson.googlecode.com/git/javadoc/com/owlike/genson/annotation/Creator.html) annotation and be static.

#### Remarks ####
  * If there is a no arg constructor Genson will use it instead of others constructors, except if there is a constructor or method annotated with [@Creator](http://genson.googlecode.com/git/javadoc/com/owlike/genson/annotation/Creator.html), then this one will be used.
  * In some cases you may have a single creator or a constructor annotated with @Creator but without Genson using it, this means that your creator has arguments but there is no way to resolve their names. Annotate each argument with @JsonProperty or enable automatic argument name resolution with [Genson.Builder.setWithDebugInfoPropertyNameResolver(true)](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Genson.Builder.html#setWithDebugInfoPropertyNameResolver(boolean)).
  * You can have only a Constructor/Method annotated with [@Creator](http://genson.googlecode.com/git/javadoc/com/owlike/genson/annotation/Creator.html) per class.
  * Actually inner and anonymous classes serialization is supported but not deserialization, except if it is a static inner class.
  * By default Genson will serialize objects using their compile type and not runtime type (List**<**Number**>** content will be serialized as Numbers no matter their runtime type) if you want to use runtime type configure your genson instance with [setUseRuntimeTypeForSerialization(true)](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Genson.Builder.html#setUseRuntimeTypeForSerialization(boolean)).

#### Interface/Abstract classes support ####
Another nice feature of Genson is its ability to deserialize an object serialized with Genson back into its concrete type. This feature is disabled by default, to enable it use [Genson.Builder.setWithClassMetadata(true)](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Genson.Builder.html#setWithClassMetadata(boolean)). This will enable serialization of class names and use it during deserialization to detect the concrete type. You should use Genson alias mechanism to associate an alias with the concerned class. Instead of serializing the class name the alias will be used.
It is a good practice to do so as it gives you the ability to rename your class or package without any impact on the json (especially useful if you store json into a database) and it is also safer from a security point of view.

```
// suppose we have the following interface and that the Person class from previous example implements it.
interface Entity {}

Genson genson = new Genson.Builder().setWithClassMetadata(true).create();
// json value will be {"@class":"mypackage.Person", ... other properties ...}
String json = genson.serialize(new Person());
// the value of @class property will be used to detect that the concrete type is Person
Entity entity = genson.deserialize(json, Entity.class);

// a better way to achieve the same thing would be to use an alias
// no need to use setWithClassMetadata(true) as when you add an alias Genson 
// will automatically enable the class metadata mechanism
genson = new Genson.Builder().addAlias("person", Person.class).create();

// output is {"@class":"person", ... other properties ...}
genson.serialize(new Person());
```

Metadata will be discussed more in depth in the advanced user guide.

#### Remarks ####
  * Class metadata and the overall metadata mechanism is available only for object types and not for arrays or litteral values. Metadata must always be the first name/value pairs in json objects.
  * Genson does not serialize, nor use class metadata for standard types (lists, primitives, etc).
  * If you define a custom Converter and do not want Genson to use class metadata for types supported by your Converter use [@HandleClassMetadata](http://genson.googlecode.com/git/javadoc/com/owlike/genson/annotation/HandleClassMetadata.html) annotation.

---


### Bean View ###
The [BeanView](http://genson.googlecode.com/git/javadoc/com/owlike/genson/BeanView.html) mechanism is Gensons response to problems were you need different representations of same objects at runtime.
Lets consider for example that you are working on a movie content application like IMDb. Movie objects will be stored in a cache to reduce database calls but also memory use. We could imagine a simplified model structure :

```
class Movie {
 String title;
 List<Person> actors;
 Person producer;
 // url to images of the movie
 List<URL> images;
 // url to trailers of the movie
 List<URL> trailers;
}

class Person {
 // some other data, we don't care
}
```

Now imagine you have a couple of webservices working with Movie objects but each needs a different representation of them. For example one could be intended for mobile developpers and provide them the full Movie item and another may be intended to present a top 100 of the best movies. In this case you don't need all the information present in the Movie item and you probably want only a single image to give an idea to the user what this movie is.

This means that you need to filter some properties and restructure others (the image list).
  * Usualy you end up creating Data Transfert Objects for each use case and just copy the needed properties. It is ugly and your cache looses some of its interest.
  * Or the library you were using before discovering Genson ;) gives you the ability to create custom Serializers/Deserializers and you write one for each of your use case and create multiple instances of your library with different configurations. It is maybe better but not always possible and it is painful to do.

The solution? We use the view approach of MVC.
Lets first have a look at an example and then analyse how it works.
```
class TopMovieView implements BeanView<Movie> {
 public TopMovieView() {}
 
 public String getTitle(Movie movie) {
  return movie.getTitle();
 }

 public URL getDefaultImage(Movie movie) {
  List<URL> images = movie.getImages();
  if ( images == null || images.isEmpty() ) return aDefaultImageURL;
  else return images.get(0); // just return the first one
 }

 public URL getDefaultTrailer(Movie movie) {
  // same as getDefaultImage
 }
}

Genson genson = new Genson();
// output is the full object serialized {....}
genson.serialize(gameOfThrones);

// output is {"title":"the title", "defaultImage": "urlToImage", "defaultTrailer": "urlToTrailer"}
String json = genson.serialize(gameOfThrones, TopMovieView.class);
```

As we can see only the properties defined in the view have been serialized all the rest was filtered. When you implement a BeanView the contract is:
  * Implement BeanView interface for the type you want to handle.
  * Implementations must provide a no arg constructor.
  * Implementations must be thread safe/stateless.
  * BeanViews will use standard JavaBean convetions except that
    * Getter/is methods take a single argument corresponding to the current object being serialized (matching the parameterized type of BeanView).
    * Setter methods take two arguments, the property to deserialize as first arg and the current object into which we deserialize as second arg (matching the parameterized type of BeanView).

#### Remarks ####
  * You can use a static factory method annotated with @Creator in BeanView implementations to create instances of the object you are building.

---


### Generic Types Support ###
#### Deserialize to Generic Lists ####
You may ask yourself "okay this is cool but how can I work with generic types?"
Indeed it is a good question. Due to type erasure you can not do things like List**<**String**>**.class, the solution
is to use what is called TypeTokens, see [this blog](http://gafter.blogspot.fr/2006/12/super-type-tokens.html) for an explanation. Gensons implementation of TypeToken is [GenericType](http://genson.googlecode.com/git/javadoc/com/owlike/genson/GenericType.html)

```
json = genson.serialize(Arrays.asList(1, 2));
List<Integer> listOfInt = genson.deserialize(json, new GenericType<List<Integer>>(){});
```

Genson will respect the type information.
```
Type type = new GenericType<List<Number>>(){}.getType();
// this will work, Genson will convert 2.2 to a double, but if the parameterized type was String, 2.2 
// would not be converted to an int 

genson.deserialize("[1, \"2.2\", null]", type);

// but if you want to deserialize a content that can not be converted to the parameterized type it will fail!
// this will throw an exception of the form "org.genson.TransformationRuntimeException: 
// Could not convert input value GG of type class java.lang.String to a Number type."

genson.deserialize("[1, \"GG\"]", type); 
```

#### Generic Objects ####
Genson has full support of java generics. It supports generic types specialization in subclasses, wildcards, type variables and so long.
```
 static class P {
 }
 static class GenericList<E extends P> {
  List<E> list;
 }

 // list elements will be deserialized to type P
 GenericList genericContainer = genson.deserialize("{\"list\":[{}, {}]}", GenericList.class);
```

---

### Custom Converter ###
In some cases Genson may not serialize/deserialize a class the way you would like. First you should try to have a look at the different options available to you, such as Genson configuration via the [Genson.Builder](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Genson.Builder.html), annotations or [BeanView](#BeanView.md). The BeanView mechanism is quite close to custom Converter except that it is easier to write.
The main advatange of writing custom Converters are : a greater control over serialization/deserialization and performances close to handwritten parsers as you eliminate the overhead of java reflection.

Here is an example of a converter handling java URLs.
```
class URLConverter implements Converter<URL> {
		public final static URLConverter instance = new URLConverter();
  private URLConverter() {}
  public URL deserialize(ObjectReader reader, Context ctx) throws TransformationException, IOException {
   return new URL(reader.valueAsString());
  }
  public void serialize(URL url, ObjectWriter writer, Context ctx) throws TransformationException, IOException {
   writer.writeValue(url.toExternalForm());
  }
}

// register the converter
Genson genson = new Genson.Builder().withConverters(new URLConverter()).create();
```

You can also create custom [Serializer](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Serializer.html) and [Deserializer](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Deserializer.html) if you want to handle only serialization or deserialization.


#### Converter using a custom Factory ####
If you need to create a custom Converter for more complex objects, you can create a custom [Factory](http://genson.googlecode.com/git/javadoc/com/owlike/genson/Factory.html) that will create your Converter and resolve Converters for your object properties. This way you can handle only the class you want and use the existing mechanism to handle the rest.

Factories are used to create instances of Converters for specific types. One of its advatanges is performance improvement as we link the converters when they are built and store them into a cache.

```
class URLContainer {
 public final URL[] urls;
 public URLContainer(URL[] urls) {
  this.urls = urls;
 }
}

// register the Converter factory
Genson genson = new Genson.Builder().withConverterFactory(URLContainerConverter.factoryInstance).create();

class URLContainerConverter implements Converter<URLContainer> {
 public final static Factory<Converter<URLConverter>> factoryInstance = new Factory<Converter<URLContainer>>() {

  public Converter<URLConveter> create(Type type, final Genson genson) {
   Converter<URL[]> urlArrayConverter = genson.provideConverter(URL[].class);
   return new URLConverter(urlArrayConverter );
  }
 };

 private final Converter<URL[]> urlArrayConverter;
 private URLContainerConverter(Converter<URL[]> urlArrayConverter) {
  this.urlArrayConverter = urlArrayConverter;
 }

 public URLContainer deserialize(ObjectReader reader, Context ctx) throws TransformationException, IOException {
  URL[] urlArray = null;
  reader.beginObject();
  for (;reader.hasNext();) {
   // you can use it to check the type of the value (int, double, string, null, etc)
   ValueType typeValue = reader.next();
   if ( "urls".equals(reader.name()) ) {
    urlArray = urlArrayConverter.deserialize(reader, ctx);
   }
  }
  reader.endObject();
  return new URLContainer(urlArray);
 }

 public void serialize(URLContainer object, ObjectWriter writer, Context ctx) throws TransformationException, IOException {
  writer.beginObject().writeName("url");
  urlArrayConverter.serialize(object.urls, writer, ctx);
  writer.endObject();
 }
}
```