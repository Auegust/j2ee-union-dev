package com.iteye.tianshi.core.spring.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 *
 *
 * @author   jiangzx@yahoo.com
 * @Date	 2011-7-25 下午08:07:34
 */
public class MappingFastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private SerializeConfig serializeConfig = null; 

	/**
	 * Construct a new {@code BindingJacksonHttpMessageConverter}.
	 */
	public MappingFastJsonHttpMessageConverter() {
		super(new MediaType("application", "json", DEFAULT_CHARSET));
		
		serializeConfig = new SerializeConfig();
		serializeConfig.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		serializeConfig.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		serializeConfig.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		//JavaType javaType = getJavaType(clazz);
		//return this.objectMapper.canDeserialize(javaType) && canRead(mediaType);
		return true;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		//return this.objectMapper.canSerialize(clazz) && canWrite(mediaType);
		return true;
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		// should not be called, since we override canRead/Write instead
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		int i;   
		while ((i = inputMessage.getBody().read()) != -1) {   
		    baos.write(i);   
		}   
		return JSON.parseObject(baos.toString(), clazz);
	}

	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		String jsonString = JSON.toJSONString(o, serializeConfig);
		OutputStream out = outputMessage.getBody();
		out.write(jsonString.getBytes(DEFAULT_CHARSET));
	}

}