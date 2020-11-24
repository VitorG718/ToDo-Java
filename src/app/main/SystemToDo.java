package app.main;

import java.util.ArrayList;

import app.data.Task;
import app.data.User;

public abstract class SystemToDo {
	
	private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<Task> tasks = new ArrayList<Task>(); 
	
	public static ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public static void addTask(Task task) {
		if(task != null)
			tasks.add(task);
		else
			throw new IllegalArgumentException("Tarefa inválida");
	}
	
	public static void removeTask(Task task) {
		if(!tasks.remove(task))
			throw new IllegalArgumentException("Tarefa inválida");
	}
	
	public static Task findTask(Integer id) {		
		for (Task task : tasks) {
			if(task.getId().equals(id))
				return task;
		}
		return null;
	}
	
	public static Task findTask(ArrayList<Task> tasks, Integer id) {
		for (Task task : tasks) {
			if(task.getId().equals(id))
				return task;
		}
		return null;
	}
	
	public static String getAssingnedTasks(User user) {
		StringBuilder stringTasks = new StringBuilder();
		
		for (Task task : tasks) {
			if(task.findMember(user.getEmail())) {
				stringTasks
					.append(task.toString());
			}
		}
		
		if(stringTasks.length() == 0)
			return null;
		
		return String.valueOf(stringTasks);
	}
	
	public static void addUser(User user) {
		if(user != null)
			users.add(user);
		else
			throw new IllegalArgumentException("Usuário inválido");
	}
	
	public static User findUser(String email) {
		for (User user : users) {
			if(user.getEmail().equals(email))
				return user;
		}
		return null;
	}
	
	public static User login(String email, String password) {
		for (User user : users) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password))
				return user;
		}
		
		throw new IllegalArgumentException("Senha ou email inválido");
	}
 }
