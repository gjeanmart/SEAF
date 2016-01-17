package com.seaf.core.service.model.utils;

import com.seaf.core.service.model.UserDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlSeeAlso(UserDto.class)
public class EnvelopeList {

	private List<?> data;
	private long	total;

	public EnvelopeList(List<?> data, long total) {
		this.data = data;
		this.total = total;
	}

	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return "EnvelopeList [data=" + data + ", total=" + total + "]";
	}
}
