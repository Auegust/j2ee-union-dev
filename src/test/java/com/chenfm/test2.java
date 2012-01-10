package com.chenfm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class test2 {
	public static void main(String[] args) throws Exception {
		new test2().ss();
	}
	
	public void ss() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sequence.properties");
		OutputStream outputStream;
		try {
			Properties properties = new Properties();
			properties.load(inputStream);
	   		String code = properties.getProperty("sequence");
	   		System.out.println(code);;
	   		String path = this.getClass().getClassLoader().getResource("sequence.properties").getPath().substring(1).replace("%20", " ");
	   		outputStream = new FileOutputStream(new File(path));
	   		String codes = "sequence="+String.valueOf(Integer.valueOf(code)+1);
			outputStream.write(codes.getBytes());
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
