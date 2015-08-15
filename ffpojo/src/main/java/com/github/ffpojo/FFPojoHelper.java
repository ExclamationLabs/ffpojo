package com.github.ffpojo;

import com.github.ffpojo.container.HybridMetadataContainer;
import com.github.ffpojo.container.MetadataContainer;
import com.github.ffpojo.exception.FFPojoRuntimeException;
import com.github.ffpojo.exception.MetadataContainerException;
import com.github.ffpojo.metadata.RecordDescriptor;
import com.github.ffpojo.parser.RecordParser;
import com.github.ffpojo.parser.RecordParserFactory;


public class FFPojoHelper {

	private static FFPojoHelper singletonInstance;
	
	private MetadataContainer metadataContainer;
	
	private FFPojoHelper() throws FFPojoRuntimeException {
		try {
			this.metadataContainer = new HybridMetadataContainer();
		} catch (MetadataContainerException e) {
			throw new FFPojoRuntimeException(e);
		}
	}
	
	public static FFPojoHelper getInstance() throws FFPojoRuntimeException {
		if (singletonInstance == null) {
			singletonInstance = new FFPojoHelper();
		}
		return singletonInstance;
	}
	
	public <T> T createFromText(Class<T> recordClazz, String text) throws FFPojoRuntimeException {
		try{
			RecordDescriptor recordDescriptor = metadataContainer.getRecordDescriptor(recordClazz);
			RecordParser recordParser = RecordParserFactory.createRecordParser(recordDescriptor);
			return recordParser.parseFromText(recordClazz, text);
		}catch(Exception e){
			throw new FFPojoRuntimeException(e);
		}
		
	}
	
	public <T> String parseToText(T record) throws FFPojoRuntimeException {
		try{			
			RecordDescriptor recordDescriptor = metadataContainer.getRecordDescriptor(record.getClass());
			RecordParser recordParser = RecordParserFactory.createRecordParser(recordDescriptor);
			return recordParser.parseToText(record);
		}catch(Exception e){
			throw new FFPojoRuntimeException(e);
		}
	}
	
	public RecordParser getRecordParser(Class<?> recordClazz) throws FFPojoRuntimeException {
		try{
			RecordDescriptor recordDescriptor = metadataContainer.getRecordDescriptor(recordClazz);
			return RecordParserFactory.createRecordParser(recordDescriptor);			
		}catch(Exception e){
			throw new FFPojoRuntimeException(e);
		}
	}
	
}
