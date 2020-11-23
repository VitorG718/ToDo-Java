package app.main;

import java.util.Map;

import app.data.User;
import app.exceptions.InvalidUserException;
import app.utils.Util;
import app.utils.View;

public class App {

	public static void main(String[] args) {
		
		SystemToDo.addUser(new User("Vitor", "vitor9040@gmail.com", "teemo4355"));
		
		Map<String, String> info;
		String erroMessage = "";
		User user = null;
		Boolean status;
		
		do {
			info = View.loginMenu(erroMessage);
			
			switch (Integer.parseInt(info.get("operation"))) {
				case 1:
					try {
						user = SystemToDo
								.login(info.get("email"), info.get("password"));
						mainMenu(user);
					} catch (IllegalArgumentException e) {
						erroMessage = e.getMessage();
					}
					break;
					
				case 2:
					erroMessage = "";
					do {
						status = false;
						try {
							info = View.registerMenu(erroMessage);
							
							if(info.get("operation").equals("1")) {
								user = Util.createUser(info);
								SystemToDo.addUser(user);
								mainMenu(user);
							}
						} catch (IllegalArgumentException | InvalidUserException e) {
							erroMessage = e.getMessage();
							status = true;
						}
					} while (status);
					erroMessage = "";
					break;
			}
		} while (Integer.parseInt(info.get("operation")) != 3);
	}
	
	public static void mainMenu(User user) {
		
		Integer option;
		String[] options;
		Map<String, Integer> infoTasksAndLists;
		
		do {
			option = View.mainMenu(user.getName());
			
			switch (option) {
				case 1:
					
					break;
				case 2:
					options = new String[3];
					options[0] = "Editar Tarefa";
					options[1] = "Remover Tarefa";
					options[2] = "Voltar";
					
					infoTasksAndLists = View.listsGeneralMenu(user.getTasks(), options);
					switch (infoTasksAndLists.get("operation")) {
						case 1:
							
							break;
						case 2:
							
							break;
					}
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
	
					break;
				case 6:
					
					break;
			}
			
		} while (option != 0);
	}
}
