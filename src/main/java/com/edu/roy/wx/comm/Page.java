package com.edu.roy.wx.comm;

import java.util.List;
import java.util.Map;

public class Page<T> {

	private int total;
	private int pages;
	private int start;
	private int current;
	private int size;
	private List<T> data;
	private Map<String, Object> param;
	private String order;
	private boolean asc;

	public int getCurrent() {
		if (current >= pages) {
			current = pages;
		} else if (current <= 0) {
			current = 1;
		}
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	private void refresh() {
		pages = ((size == 0 || total == 0) ? 1 : (total % size == 0 ? total / size : (total / size + 1)));
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		refresh();
	}

	public int getPages() {
		refresh();
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getStart() {
		start = (current - 1) * size;
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
		refresh();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
