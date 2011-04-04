package com.redshape.bindings;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.redshape.bindings.accessors.AccessException;
import com.redshape.bindings.accessors.IPropertyReader;
import com.redshape.bindings.accessors.IPropertyWriter;
import com.redshape.bindings.annotations.Bindable;
import com.redshape.bindings.annotations.BindableReader;
import com.redshape.bindings.annotations.BindableWriter;
import com.redshape.bindings.annotations.ElementType;
import com.redshape.bindings.annotations.MapKey;
import com.redshape.bindings.annotations.MapValue;
import com.redshape.bindings.types.BindableType;
import com.redshape.bindings.types.IBindable;

import com.redshape.utils.StringUtils;

public class BeanInfo implements IBeanInfo {
	private static final Logger log = Logger.getLogger(BeanInfo.class);

	public static String WRITER_PART = "set";
	public static String READER_PART = "get";

	private Class<?> beanClazz;
	private List<AccessibleObject> members;
	private static Map<Class<?>, BindableType> TYPE_MAPPINGS = new HashMap<Class<?>, BindableType>();

	public static BindableType getTypeMapping( Class<?> type ) {
		return TYPE_MAPPINGS.get( type );
	}
	
	public static void addTypeMapping( Class<?> type, BindableType bindableType ) {
		TYPE_MAPPINGS.put(type, bindableType);
	}
	
	static {
		addTypeMapping( Enum.class, BindableType.ENUM );
		addTypeMapping( String.class, BindableType.STRING );
		addTypeMapping( Integer.class, BindableType.NUMERIC );
		addTypeMapping( Float.class, BindableType.NUMERIC );
		addTypeMapping( Double.class, BindableType.NUMERIC );
		addTypeMapping( Collection.class, BindableType.LIST );
		addTypeMapping( Map.class, BindableType.MAP );
		addTypeMapping( Boolean.class, BindableType.BOOLEAN );
		addTypeMapping( Date.class, BindableType.DATE );
	}
	
	public BeanInfo(Class<?> beanClazz) {
		if (beanClazz == null) {
			throw new IllegalArgumentException("null");
		}

		this.beanClazz = beanClazz;
	}

	@Override
	public Class<?> getType() {
		return this.beanClazz;
	}
	
	@Override
	public List<IBindable> getBindables() throws BindingException {
		List<IBindable> result = new ArrayList<IBindable>();

		for (AccessibleObject member : this.getMembers()) {
			Bindable bindable = member.getAnnotation(Bindable.class);
			if (bindable == null) {
				continue;
			}

			result.add(this.processMember(bindable, (Member) member));
		}

		return result;
	}

	protected IBindable processMember(Bindable bindable, Member member)
			throws BindingException {
		Class<?> type = this.getType(bindable, (AccessibleObject) member);

		BindableObject result = new BindableObject(this.findReader(type,
				bindable, (AccessibleObject) member), this.findWriter(type,
				bindable, (AccessibleObject) member));
		result.setId(this.getBindableId(bindable, member));
		result.setName(bindable.name());
		result.setType(type);
		result.setMetaType(this.getMetaType(type, bindable,
				(AccessibleObject) member));

		this.postProcessObject(result, (AccessibleObject) member);

		return result;
	}

	protected void postProcessObject(BindableObject object,
			AccessibleObject member) throws BindingException {
		switch (object.getMetaType()) {
		case LIST:
			ElementType type = member.getAnnotation(ElementType.class);
			if (type != null) {
				object.setElementType(type.value());
				object.setCollectionType( type.type() );
			}
			break;
		case MAP:
			MapKey key = member.getAnnotation(MapKey.class);
			if (key != null) {
				object.setKeyType(key.value());
				object.setKeyName(key.name());
			}

			MapValue value = member.getAnnotation(MapValue.class);
			if (value != null) {
				object.setValueType(value.value());
				object.setValueName(value.name());
			}
			break;
		}
	}

	protected String prepareFieldName(Member member) throws BindingException {
		String result;
		if (member instanceof Field) {
			result = member.getName();
		} else if (member instanceof Method) {
			final Method accessorMember = (Method) member;
			if (isWriterMember(accessorMember)) {
				result = member.getName().substring(WRITER_PART.length());
			} else if (isReaderMember(accessorMember)) {
				result = member.getName().substring(READER_PART.length());
			} else {
				throw new BindingException("Unconventional accessor name");
			}
		} else {
			throw new BindingException("Invalid binding annotation place");
		}

		return StringUtils.lcfirst(result);
	}

	protected String getBindableId(Bindable annotation, Member member)
			throws BindingException {
		String fieldName = annotation.id();
		if (fieldName == null || fieldName.isEmpty()) {
			fieldName = this.prepareFieldName(member);
			if (fieldName == null) {
				throw new BindingException("Cannot detect field name");
			}
		}

		return fieldName;
	}

	protected Class<?> getType( Bindable bindable, AccessibleObject member) throws BindingException {
		if ( bindable.targetType().length != 0 ) {
			return bindable.targetType()[0];
		}
		
		if (member instanceof Field) {
			return ((Field) member).getType();
		} else if (member instanceof Method) {
			if (isWriterMember((Method) member)) {
				return ((Method) member).getParameterTypes()[0];
			} else if (isReaderMember((Method) member)) {
				return ((Method) member).getReturnType();
			} else {
				throw new BindingException("Unconventional accessor name for : " + ( (Member) member).getName() );
			}
		} else {
			throw new BindingException("Invalid binding annotation place");
		}
	}

	protected BindableType getMetaType(Class<?> memberClazz, Bindable bindable,
			AccessibleObject member) {
		if (!bindable.type().equals(BindableType.NONE)) {
			return bindable.type();
		}
		
		BindableType type = getTypeMapping( memberClazz );
		if ( type != null ) {
			return type;
		} else if ( memberClazz.isArray() ) {
			return BindableType.LIST;
		} else {
			return BindableType.COMPOSITE;
		}
	}

	// FIXME: Refactor to remove duplicate code
	protected IPropertyReader findReader(Class<?> type, Bindable bindable,
			AccessibleObject member) throws BindingException {
		if (member.getAnnotation(BindableReader.class) != null) {
			return Accessors.getReaders().createReader((Member) member);
		}

		String fieldName = this.prepareFieldName((Member) member);

		IPropertyReader reader = null;
		for (AccessibleObject object : this.getMembers()) {
			BindableReader annotation = object
					.getAnnotation(BindableReader.class);
			if (annotation == null) {
				continue;
			}

			if (fieldName.equals(annotation.name())) {
				reader = Accessors.getReaders().createReader((Member) member);
				break;
			}
		}

		if (reader == null) {
			try {
				Field field = this.beanClazz.getField(fieldName);
				if (Modifier.isPublic(field.getModifiers())) {
					return Accessors.getReaders().createReader(field);
				}
			} catch (Throwable e) {
				log.info("Field is protected...");
			}

			try {
				reader = Accessors.getReaders().createReader(
						this.beanClazz.getMethod(StringUtils.toCamelCase(
								READER_PART + "_" + fieldName, false)));
			} catch (Throwable e) {
				log.info("Reader not found...");
			}
		}

		return reader;
	}

	// FIXME: Refactor to remove duplicate code
	protected IPropertyWriter findWriter(Class<?> type, Bindable bindable,
			AccessibleObject member) throws BindingException {
		if (member.getAnnotation(BindableWriter.class) != null) {
			return Accessors.getWriters().createWriter((Member) member);
		}

		String fieldName = this.prepareFieldName((Member) member);

		IPropertyWriter writer = null;
		for (AccessibleObject object : this.getMembers()) {
			BindableWriter annotation = object
					.getAnnotation(BindableWriter.class);
			if (annotation == null) {
				continue;
			}

			if (fieldName.equals(annotation.name())) {
				writer = Accessors.getWriters().createWriter((Member) object);
				break;
			}
		}

		if (writer == null) {
			try {
				Field field = this.beanClazz.getField(fieldName);
				if (Modifier.isPublic(field.getModifiers())) {
					return Accessors.getWriters().createWriter(field);
				}
			} catch (Throwable e) {
				log.info("Field is protected...");
			}

			try {
				writer = Accessors.getWriters().createWriter(
						this.beanClazz.getMethod(
								StringUtils.toCamelCase(WRITER_PART + "_"
										+ fieldName, false), type));
			} catch (Throwable e) {
				log.info("Writer not found...");
			}
		}

		return writer;
	}

	@Override
	public boolean isConstructable() {
		if (Modifier.isAbstract(this.beanClazz.getModifiers())) {
			return false;
		}

		for (Constructor<?> constructor : this.beanClazz.getConstructors()) {
			if (Modifier.isPublic(constructor.getModifiers())) {
				return true;
			}
		}

		for (Method method : this.beanClazz.getMethods()) {
			if (Modifier.isPublic(method.getModifiers())
					&& method.getReturnType().equals(this.beanClazz)
					&& Modifier.isStatic(method.getModifiers())) {
				return true;
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> BeanConstructor<T> getConstructor() throws NoSuchMethodException {
		return this.getConstructor( new Class[] {} );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> BeanConstructor<T> getConstructor(Class<T>[] args) {
		if (!this.isConstructable()) {
			return null;
		}

		for (Constructor<T> constructor : (Constructor<T>[]) this.beanClazz
				.getConstructors()) {
			if (!Modifier.isPublic(constructor.getModifiers())) {
				continue;
			}

			if ( args.length == 0 || constructor.getParameterTypes().equals(args)) {
				return new BeanConstructor<T>(constructor);
			}
		}

		for (Method method : this.beanClazz.getMethods()) {
			if (!method.getReturnType().equals(this.beanClazz)
					|| !Modifier.isPublic(method.getModifiers())
					&& !Modifier.isStatic(method.getModifiers())) {
				continue;
			}

			if (method.getParameterTypes().equals(args)) {
				return new BeanConstructor<T>(method);
			}
		}

		throw new NoSuchMethodError("No suitable constructor was founded");
	}

	protected List<AccessibleObject> getMembers() {
		if (this.members != null) {
			return this.members;
		}

		this.members = new ArrayList<AccessibleObject>();
		this.members.addAll(Arrays.asList(this.beanClazz.getMethods()));
		this.members.addAll(Arrays.asList(this.beanClazz.getFields()));
		this.members.addAll(Arrays.asList(this.beanClazz.getConstructors()));

		return this.members;
	}

	public static boolean isWriterMember(Method method) {
		return method.getName().startsWith("set") || method.getAnnotation( BindableWriter.class ) != null;
	}

	public static boolean isReaderMember(Method method) {
		return method.getName().startsWith("get") || method.getAnnotation( BindableReader.class ) != null;
	}

	public static class Accessors {
		private static Writers writers = new Writers();
		private static Readers readers = new Readers();

		private Accessors() {
		}

		public static void setWriters(Writers writers) {
			Accessors.writers = writers;
		}

		public static Writers getWriters() {
			return Accessors.writers;
		}

		public static void setReaders(Readers readers) {
			Accessors.readers = readers;
		}

		public static Readers getReaders() {
			return Accessors.readers;
		}

		public static class Writers extends Accessors {
			private Writers() {
			}

			public IPropertyWriter createWriter(Member member)
					throws AccessException {
				if (member instanceof Method) {
					return new MethodWriter((Method) member);
				} else if (member instanceof Field) {
					return new FieldWriter((Field) member);
				} else {
					throw new AccessException(
							"Wrong member given (only method or field allowed)");
				}
			}

			public static class FieldWriter extends Writers implements
					IPropertyWriter {
				private Field field;

				public FieldWriter(Field field) {
					this.field = field;
				}

				@Override
				public boolean isConsistent(Class<?> type) {
					return this.field.getType().isAssignableFrom(type);
				}

				@Override
				public void write(Object context, Object value)
						throws AccessException {
					if (!this.isConsistent(value.getClass())) {
						throw new AccessException("Inconsistent value given");
					}

					try {
						this.field.set(context, value);
					} catch (Throwable e) {
						throw new AccessException(e.getMessage(), e);
					}
				}

			}

			public static class MethodWriter extends Writers implements
					IPropertyWriter {
				private Method method;

				public MethodWriter(Method method) {
					this.method = method;
				}

				@Override
				public boolean isConsistent(Class<?> type) {
					return this.method.getParameterTypes().length != 0
							&& this.method.getParameterTypes()[0].equals(type);
				}

				@Override
				public void write(Object context, Object value)
						throws AccessException {
					if (!this.isConsistent(value.getClass())) {
						throw new AccessException("Inconsistent value given");
					}

					try {
						this.method.invoke(context, value);
					} catch (Throwable e) {
						throw new AccessException(e.getMessage(), e);
					}
				}
			}
		}

		public static class Readers extends Accessors {
			private Readers() {
				/** **/
			}

			public IPropertyReader createReader(Member member)
					throws AccessException {
				if (member instanceof Method) {
					return new MethodReader((Method) member);
				} else if (member instanceof Field) {
					return new FieldReader((Field) member);
				} else {
					throw new AccessException(
							"Wrong member given (only method or field allowed)");
				}
			}

			public static class FieldReader extends Readers implements
					IPropertyReader {
				private Field field;

				public FieldReader(Field field) {
					this.field = field;
				}

				@Override
				public boolean isConsistent(Class<?> type) {
					return type.isAssignableFrom(this.field.getType());
				}

				@SuppressWarnings("unchecked")
				@Override
				public <V> V read(Object context) throws AccessException {
					try {
						return (V) this.field.get(context);
					} catch (Throwable e) {
						throw new AccessException(e.getMessage(), e);
					}
				}

			}

			public static class MethodReader extends Readers implements
					IPropertyReader {
				private Method method;

				public MethodReader(Method method) {
					this.method = method;
				}

				@Override
				public boolean isConsistent(Class<?> type) {
					return type.isAssignableFrom(this.method.getReturnType());
				}

				@SuppressWarnings("unchecked")
				@Override
				public <V> V read(Object context) throws AccessException {
					try {
						return (V) this.method.invoke(context, new Object[] {});
					} catch (Throwable e) {
						throw new AccessException(e.getMessage(), e);
					}
				}

			}
		}

	}

}