package com.shakdel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ClientImplManager {
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ClientImpl {
		Class<?> value();
	}
	
	private static final Map<Class<?>, Map<String, Method>> IMPLS = new HashMap<>();
	
	public static void registerImpls(Class<?> impls) {
		ClientImpl desc = impls.getAnnotation(ClientImpl.class);
		if (desc == null)
			throw new IllegalArgumentException("Missing ClientImpl annotation! Did you forget `@ClientImpl(ClassName.class)`?");
		if (desc.value() == null)
			throw new IllegalArgumentException("ClientImpl is missing a class! Did you forget `ClassName.class`?");
		
		Map<String, Method> methods = new HashMap<>();
		for (Method method : impls.getDeclaredMethods()) {
			if (!(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())))
				throw new IllegalArgumentException("Method '" + method.getName() + "' is not accessible! Did you forget `public static`?");
			methods.put(method.getName(), method);
		}
		
		IMPLS.put(desc.value(), methods);
	}
	
	public static <T> T run(Object caller, String method, Class<T> returnType) {
		Class<?> clazz = caller.getClass();
		while (clazz != null) {
			Map<String, Method> methods = IMPLS.get(clazz);
			if (methods != null) {
				Method impl = methods.get(method);
				if (impl != null) {
					try {
						Object returnValue = impl.invoke(null, caller);
						if (returnType == null)
							return null;
						return returnType.cast(returnValue);
					} catch (Exception e) {
						throw new RuntimeException("Error while invoking impl for '" + method + "'", e);
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		throw new IllegalArgumentException("Missing impl for '" + method + "'! Did you forget `ClientImplManager.registerImpls(ClassNameImpl.class)`?");
	}
	public static void run(Object caller, String method) {
		run(caller, method, null);
	}
	
}