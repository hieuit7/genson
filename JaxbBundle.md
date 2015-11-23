_<font color='red'>Genson website has moved to <a href='http://owlike.github.io/genson/'>http://owlike.github.io/genson/</a> and the code is hosted on GitHub at <a href='https://github.com/owlike/genson'>https://github.com/owlike/genson</a></font>_

# JAXB support #

Since version 0.95 Genson provides support for JAXB annotations. All annotations are not supported as some do not make sense in the JSON world. Genson annotations can be used to override Jaxb annotations.

**In Jersey JAXB bundle is enabled by default**. If you are using Genson outside Jersey you have to register JAXB bundle:

```
Genson genson = new Genson.Builder().with(new JAXBBundle()).create();
```

#### Supported annotations ####

  * XmlAttribute can be used to include a property in serialization/deserialization (can be used on fields, getter and setter). If a name is defined it will be used instead of the one derived from the method/field.

  * XmlElement works as XmlAttribute, if type is defined it will be used instead of the actual one.

  * XmlJavaTypeAdapter can be used to define a custom XmlAdapter. This adapter must have a no arg constructor.

  * XmlEnumValue can be used on enum values to map them to different values (it can be mixed with default behaviour: you can use this annotation on some enum values and let the others be ser/deser using the default strategy).

  * XmlAccessorType can be used to define how to detect properties (fields, methods, public only etc).

  * XmlTransient to exclude a field or get/set from ser/deser process.

#### Supported types ####
Actual implementation has default converters for Duration and XMLGregorianCalendar.


#### What might come next ####

> Support for cyclic references using XmlId and XmlIdRef, XmlType.
> Maybe XmlRootElement, even if I doubt of its pertinence in JSON...

> If there are other jaxb features you would like to be supported just open an issue or drop an email on the google group.