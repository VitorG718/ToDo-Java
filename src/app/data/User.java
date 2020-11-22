package app.data;

import java.util.ArrayList;

import app.exceptions.InvalidUserException;
import app.interfaces.Editable;
import app.utils.View;

public class User implements Editable {
	
	private String name;
	private String email;
	private String password;
	private ArrayList<Task> tasks;
	private ArrayList<List> lists;

	
	public User(String name, String email, String password) {
		super();
		setName(name);
		setEmail(email);
		setPassword(password);
	}

	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	// TODO: implements sets
	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public void editName() {
		String name = View.getString("Digite o novo nome: ", "Editar nome");
		if(!name.isEmpty())
			setName(name);
		else
			throw new InvalidUserException("Não foi possível editar o nome");
	}
}
