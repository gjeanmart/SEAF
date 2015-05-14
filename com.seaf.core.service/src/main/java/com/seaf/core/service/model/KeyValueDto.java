package com.seaf.core.service.model;

public class KeyValueDto {
	
	private String key;
	private String value;
	
	public KeyValueDto(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public KeyValueDto() {
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "KeyValueDto [key=" + key + ", value=" + value + "]";
	}
}
