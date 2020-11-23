package app.data.abstracts;

import app.exceptions.InvalidBaseException;
import app.interfaces.Editable;
import app.utils.Util;

public abstract class Base implements Editable{
	
	private Integer id;
	private String name;
	
	public Base(Integer id, String name) {
		super();
		setId();
		setName(name);
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	private void setId() {
		id = Util.generateId();
	}
	
	public void setName(String name) {
		try {
			this.name = Util.validateString(name);
		} catch (IllegalArgumentException e) {
			throw new InvalidBaseException(e.getMessage());
		}
	}
}
