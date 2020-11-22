package app.data.abstracts;

import app.interfaces.Editable;
import app.utils.Util;

public abstract class Base implements Editable{
	
	private Long id;
	private String name;
	
	public Base(Integer id, String name) {
		super();
		setId();
		setName(name);
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	private void setId() {
		id = Util.generateId();
	}
	
	public void setName(String name) {
		this.name = Util.validateName(name);
	}
}
