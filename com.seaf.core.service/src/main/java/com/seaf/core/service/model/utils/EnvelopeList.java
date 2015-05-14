package com.seaf.core.service.model.utils;

import java.util.List;

public class EnvelopeList {
	
	private List<?> data;
	private long	total;
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
