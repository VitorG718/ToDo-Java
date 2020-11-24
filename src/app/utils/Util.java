package app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import app.data.List;
import app.data.Task;
import app.data.User;
import app.enums.EnumStatusTask;
import app.main.SystemToDo;

public abstract class Util {
	
	public static Integer generateId() {
		
		ArrayList<Task> tasks = SystemToDo.getTasks();
		Random generator = new Random();
		Integer newId;
		Boolean validId;
		
		do {
			newId = generator.nextInt();
			validId = Boolean.TRUE;
			if(newId.compareTo(0) <= 0)
				validId = Boolean.FALSE;
			else {
				for	(Task task: tasks) {
					if(task.getId().equals(newId))
						validId = Boolean.FALSE;
				}
			}
		} while(validId.equals(Boolean.FALSE));
		
		return newId;
	}
	
	public static String validateString(String s) {
		if(s != null && !s.trim().isEmpty())
			return s;
		throw new IllegalArgumentException();
	}
	
	public static String validateEmail(String email) {
		if(email == null || email.trim().isEmpty() || !email.contains("@") || email.charAt(0) == '.' || email.charAt(email.length()-1) == '.')
			throw new IllegalArgumentException("Email inválido");
		
		String emailIdentifier = email.split("@")[0];
		String emailDomain = email.split("@")[1];
		
		int i = 0;
		char letter;
		boolean validEmailIdentifier = true;
		for (i = 0; i < emailIdentifier.length(); i++) {
			letter = emailIdentifier.charAt(i);
			
			if(!Character.isLetterOrDigit(letter) && !(letter == '.')) {
				validEmailIdentifier = false;
				break;
			}
		}
		
		int domainsQuantity = 0;
		boolean validEmailDomain = true;
		for (i = 0; i < emailDomain.length(); i++) {
			letter = emailDomain.charAt(i);
			
			if(letter == '.')
				domainsQuantity++;
			
			if(!Character.isLetterOrDigit(letter) && !(letter == '.')) {
				validEmailDomain = false;
				break;
			}
		}
		
		if(!validEmailIdentifier || !validEmailDomain || domainsQuantity == 0)
			throw new IllegalArgumentException("Email inválido");
		
		return email;
	}
	
	public static User createUser(Map<String, String> data) {
		return new User(
				data.get("name"), 
				data.get("email"), 
				data.get("password") );
	}
	
	public static Task createTask(Map<String, String> data, User assignedUser) {
		EnumStatusTask status = null;
		LocalDate dueDate = null;
		
		for (int i = 0; i < EnumStatusTask.values().length; i++) {
			if(EnumStatusTask.values()[i].getStatusName().equals(data.get("status")))
				status = EnumStatusTask.values()[i];
		}
		
		if(!data.get("dueDate").isEmpty() && !data.get("notes").isEmpty()) {
			dueDate = LocalDate.parse(data.get("dueDate"), DateTimeFormatter.ofPattern("dd/MM/uuuu"));
			return new Task(data.get("name"), status, dueDate, data.get("notes"), assignedUser);
			
		} else if(!data.get("dueDate").isEmpty()) {
			dueDate = LocalDate.parse(data.get("dueDate"), DateTimeFormatter.ofPattern("dd/MM/uuuu"));
			return new Task(data.get("name"), status, dueDate, assignedUser);
			
		} else if(!data.get("notes").isEmpty()) {
			dueDate = LocalDate.parse(data.get("dueDate"), DateTimeFormatter.ofPattern("dd/MM/uuuu"));
			return new Task(data.get("name"), status, data.get("notes"), assignedUser);
		} 
			
		return new Task(data.get("name"), status, assignedUser);
	}
	
	public static List createList(String name) {
		return new List(name);
	}
}
