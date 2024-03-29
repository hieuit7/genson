package com.owlike.genson.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated Serializer/Deserializer/Converter with @HandleClassMetadata indicate that they will
 * handle @class metadata during serialization and deserialization. By default it is handled by the
 * library in {@link com.owlike.genson.convert.ClassMetadataConverter ClassMetadataConverter}. Default
 * converters from {@link com.owlike.genson.convert.DefaultConverters DefaultConverters} annotated with @HandleClassMetadata
 * do not serialize type information nor do they use it during deserialization. For security reasons
 * class metadata is disabled by default. To enable it
 * {@link com.owlike.genson.Genson.Builder#setWithClassMetadata(boolean)
 * Genson.Builder.setWithClassMetadata(true)}
 * 
 * @see com.owlike.genson.convert.ClassMetadataConverter ClassMetadataConverter
 * @see com.owlike.genson.Genson.Builder#setWithClassMetadata(boolean)
 *      Genson.Builder.setWithClassMetadata(true)
 * 
 * @author eugen
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HandleClassMetadata {

}
