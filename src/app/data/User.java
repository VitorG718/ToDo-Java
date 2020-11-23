package app.data;

import java.util.ArrayList;

import app.exceptions.InvalidUserException;
import app.interfaces.Editable;
import app.main.SystemToDo;
import app.utils.Util;
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
		
		tasks = new ArrayList<Task>();
		lists = new ArrayList<List>();
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
	
	public void setName(String name) {
		try {
			this.name = Util.validateString(name);
		} catch (IllegalArgumentException e) {
			if(e.getMessage() != null)
				throw new InvalidUserException(e.getMessage());
			else
				throw new InvalidUserException("Nome inv�lido");
		}
	}

	public void setEmail(String email) {
		try {
			this.email = Util.validateEmail(email);
		} catch (IllegalArgumentException e) {
			if(e.getMessage() != null)
				throw new InvalidUserException(e.getMessage());
			else
				throw new InvalidUserException("Email inv�lido");
		}
	}

	public void setPassword(String password) {
		if(password != null && !password.trim().isEmpty() && password.length() >= 8)
			this.password = password;
		else
			throw new InvalidUserException("Senha inv�lida");
	}
	
	public void addTask(Task task) {
		if(task != null) {
			tasks.add(task);
			SystemToDo.addTask(task);
		} else 
			throw new InvalidUserException("Tarefa inv�lida");
	}
	
	public void removeTask(Long id) {
		Task tempTask = SystemToDo.findTask(tasks, id);
		
		if(tempTask != null) {
			tasks.remove(tempTask);
			SystemToDo.removeTask(tempTask);
		} else
			throw new InvalidUserException("Tarefa n�o encontrada");
	}
	
	public String getTasks() {
		StringBuilder stringTasks = new StringBuilder();
		
		for (Task task : tasks) {
			if(task != null)
				stringTasks.append(task.toString());
		}
		
		if(stringTasks.length() != 0)
			return String.valueOf(stringTasks);
		return "N�o h� tarefas";
	}
	
	public String getAssignedTasks() {
		String assignedTasks = SystemToDo.getAssingnedTasks(this);
		if(assignedTasks != null)
			return String.valueOf(assignedTasks);
		return "N�o h� tarefas atribu�das";
	}
	
	public void addList(List list) {
		if(list != null)
			lists.add(list);
		else
			throw new InvalidUserException("Lista inv�lida");
	}
	
	public void removeList(Long id) {
		List tempList = findList(id);
		if(tempList == null || !lists.remove(tempList))
			throw new InvalidUserException("Lista n�o encontrada");
	}
	
	public String getLists() {
		StringBuilder stringLists = new StringBuilder();
		
		for (List list : lists) {
			stringLists
				.append(list.toString());
		}
		
		if(stringLists.length() != 0)
			return String.valueOf(stringLists);
		return "N�o h� listas";
	}
	
	public List findList(Long id) {
		for (List list : lists) {
			if(list.getId().equals(id))
				return list;
		}
		
		return null;
	}
	
	@Override
	public void editName() {
		String name = View.getString("Digite o novo nome: ", "Editar nome");
		if(!name.isEmpty())
			setName(name);
		else
			throw new InvalidUserException("N�o foi poss�vel editar o nome");
	}
}
