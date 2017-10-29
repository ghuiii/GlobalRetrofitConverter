package me.ghui.retrofit.converter;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by ghui on 07/04/2017.
 */

public class GlobalConverterFactory extends Converter.Factory {

	private HashMap<Class<? extends Annotation>, Converter.Factory> mFactoryClassHashMap;

	public static GlobalConverterFactory create() {
		return new GlobalConverterFactory();
	}


	private GlobalConverterFactory() {
	}

	/**
	 * Add a converter factory associated with the clazz Annotation, the first added one will be as the default factory.
	 * @param factory factory of converter
	 * @param clazz the annotion class
	 * @return this
	 */
	public GlobalConverterFactory add(Converter.Factory factory, Class<? extends Annotation> clazz) {
		if (factory == null || clazz == null) {
			throw new IllegalArgumentException("Converter.Factory and Class cannot be null");
		}

		if (mFactoryClassHashMap == null) {
			mFactoryClassHashMap = new HashMap<>();
		}

		mFactoryClassHashMap.put(clazz, factory);
		return this;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		for (Annotation annotation : annotations) {
			Converter.Factory factory = mFactoryClassHashMap.get(annotation.annotationType());
			if (factory != null) {
				return factory.responseBodyConverter(type, annotations, retrofit);
			}
		}
		return null;
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		for (Annotation annotation : methodAnnotations) {
			Converter.Factory factory = mFactoryClassHashMap.get(annotation.annotationType());
			if (factory != null) {
				return factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
			}
		}
		return null;
	}

}
