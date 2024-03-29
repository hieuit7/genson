package com.owlike.genson.convert;

import java.lang.reflect.Type;

import com.owlike.genson.Converter;
import com.owlike.genson.Factory;
import com.owlike.genson.Genson;

/**
 * A chained factory of Converters that gives the ability to implementations to decorate the
 * converter created by the next factory.
 * 
 * One of Genson big strengths is its extensive use of Decorator and Chain of Responsibility design
 * patterns. Chain of responsibility is applied in chaining Factories that can build Decorated
 * Converters. ChainedFactory is the base class for such factories. But as ChainedFactory next
 * element is an instance of Factory, you are free to apply the chain principle for your custom
 * factories differently if you want.
 * 
 * The global idea behind this design is to provide great extensibility to the library by allowing
 * to add new functionalities to existing converters in a non intrusive way (extension and source
 * modification). This is achieved by applying the decorator pattern. Here is an example of a
 * decorated converter that adds null handling support.
 * 
 * <pre>
 * public class NullConverter extends Wrapper&lt;Converter&lt;Object&gt;&gt; implements
 * 		Converter&lt;Object&gt; {
 * 	private final Converter&lt;Object&gt; converter;
 * 
 * 	public NullConverter(Converter&lt;Object&gt; converter) {
 * 		super(converter);
 * 		this.converter = converter;
 * 	}
 * 
 * 	public void serialize(Object obj, ObjectWriter writer, Context ctx) {
 * 		if (obj == null)
 * 			writer.writeNull();
 * 		else
 * 			converter.serialize(obj, writer, ctx);
 * 	}
 * 
 * 	public Object deserialize(ObjectReader reader, Context ctx) {
 * 		if (TypeValue.NULL == reader.getTypeValue()) {
 * 			return null;
 * 		} else
 * 			return converter.deserialize(reader, ctx);
 * 
 * 	}
 * }
 * 
 * // now we need a factory to create the nullconverter for type T and wire it with the existing
 * // factories so we can get an instance of the converter for that type.
 * public class NullConverterFactory extends ChainedFactory {
 * 	public NullConverterFactory(Factory&lt;Converter&lt;?&gt;&gt; next) {
 * 		super(next);
 * 	}
 * 
 * 	public Converter&lt;?&gt; create(Type type, Genson genson, Converter&lt;?&gt; nextConverter) {
 * 		return new NullConverter(nextConverter);
 * 	}
 * }
 * </pre>
 * 
 * As you can see it is pretty simple but also powerful. Note that our NullConverter extends
 * Wrapper class. When you encapsulate converters you should extend Wrapper
 * class this way Genson can access the class information of wrapped converters. Imagine for example
 * that you put some annotation on converter A and wrap it in converter B, now if you wrap B in C
 * you wont be able to get class information of A (ex: its annotations). Wrapper class
 * allows to merge class information of current implementation and the wrapped one.
 * 
 * 
 * @see com.owlike.genson.convert.NullConverter NullConverter
 * @see com.owlike.genson.convert.BasicConvertersFactory BasicConvertersFactory
 * @see com.owlike.genson.Wrapper Wrapper
 * 
 * @author eugen
 * 
 */
public abstract class ChainedFactory implements Factory<Converter<?>> {
	private Factory<? extends Converter<?>> next;

	protected ChainedFactory() {
	}

	protected ChainedFactory(Factory<Converter<?>> next) {
		this.next = next;
	}

	public Converter<?> create(Type type, Genson genson) {
		Converter<?> nextConverter = null;
		if (next != null) {
			nextConverter = next.create(type, genson);
		}
		Converter<?> converter = create(type, genson, nextConverter);
		return converter == null ? nextConverter : converter;
	}

	/**
	 * This method will be called by {@link #create(Type, Genson)} with nextConverter being the
	 * converter created for current type by the next factory. This means that ChainedFactory will
	 * first create a converter with the next factory and then use it's own create method.
	 * 
	 * @param type for which this factory must provide a converter
	 * @param genson instance that you can use when you need a converter for some other type (for
	 *        example a converter of List&lt;Integer&gt; will need a converter for Integer type).
	 * @param nextConverter created by the next factory, may be null.
	 * @return null or a converter for this type
	 */
	protected abstract Converter<?> create(Type type, Genson genson, Converter<?> nextConverter);

	/**
	 * Chains this factory with next and returns next (the tail) so you can do things like
	 * chain1.withNext(new chain2).withNext(new chain3); the resulting chain is
	 * chain1=>chain2=>chain3. Don't forget to keep a reference to the head (chain1).
	 * 
	 * @param next factory
	 * @return the next factory passed as argument
	 */
	public final <T extends Factory<? extends Converter<?>>> T withNext(T next) {
		if (this.next != null)
			throw new IllegalStateException("next factory has already been set for " + getClass()
					+ " you can not override it!");
		this.next = next;
		return next;
	}

	/**
	 * @return a reference to the next factory, may be null.
	 */
	public final Factory<? extends Converter<?>> next() {
		return next;
	}
}
