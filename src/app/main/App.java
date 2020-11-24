package app.main;

import java.util.Map;

import app.data.List;
import app.data.User;
import app.exceptions.InvalidListException;
import app.exceptions.InvalidUserException;
import app.utils.Util;
import app.utils.View;

public class App {

	public static void main(String[] args) {
		
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
		String errorMessage;
		Map<String, Integer> infoTasksAndLists;
		List list = null;
		
		do {
			options = new String[7];
			options[0] = "Adicionar Tarefa";
			options[1] = "Tarefas";
			options[2] = "Tarefas Atribuídas";
			options[3] = "Criar Lista";
			options[4] = "Listas";
			options[5] = "Configurações";
			options[6] = "Deslogar";
			option = View.mainMenu(user.getName(), options);
			
			switch (option) {
				case 1:
					
					View.addTaskMenu(user);
					break;
					
				case 2:
					options = new String[3];
					options[0] = "Editar Tarefa";
					options[1] = "Remover Tarefa";
					options[2] = "Voltar";
					
					try {
						infoTasksAndLists = View.listsGeneralMenu(user.getTasks(), options);
						switch (infoTasksAndLists.get("operation")) {
							case 1:
								View.editTaskMenu(SystemToDo.findTask(infoTasksAndLists.get("id")));
								break;
							case 2:
								user.removeTask(infoTasksAndLists.get("id"));
								break;
						}
					} catch (NumberFormatException e) {
						View.showError("ID inválido");
					}
					
					break;
				case 3:
					try {
						View.showMessage(user.getAssignedTasks());
					} catch (NullPointerException e) {}
					break;
				case 4:
					View.createListMenu(user);
					break;
				case 5:
					options = new String[5];
					options[0] = "Adicionar Tarefa";
					options[1] = "Tarefas";
					options[2] = "Editar Lista";
					options[3] = "Remover Lista";
					options[4] = "Voltar";
					
					try {
						infoTasksAndLists = View.listsGeneralMenu(user.getLists(), options);
						switch (infoTasksAndLists.get("operation")) {
							case 1:
								options = new String[2];
								options[0] = "Adicionar Tarefa";
								options[1] = "Voltar";
								
								list = user.findList(infoTasksAndLists.get("id"));
								if(list != null) {
									infoTasksAndLists = View.listsGeneralMenu(user.getTasks(), options);
									if(infoTasksAndLists.get("operation") == 1) {
										try {
											list.addTask(SystemToDo.findTask(infoTasksAndLists.get("id")));
										} catch (InvalidListException e) {
											View.showError(e.getMessage());
										}
									}
								}
								break;
							case 2:
								options = new String[2];
								options[0] = "Remover Tarefa";
								options[1] = "Voltar";
								
								list = user.findList(infoTasksAndLists.get("id"));
								if(list != null) {
									infoTasksAndLists = View.listsGeneralMenu(list.getTasks(), options);
									if(infoTasksAndLists.get("operation") == 1) {
										try {
											list.removeTask(infoTasksAndLists.get("id"));
										} catch (InvalidListException e) {
											View.showError(e.getMessage());
										}
									}
								}
								break;
							case 3:
								View.createListMenu(user);
								break;
							case 4:
								user.removeList(infoTasksAndLists.get("id"));
								break;
						}
					} catch (NumberFormatException e) {
						View.showError("ID inválido");
					}
					
					break;
				case 6:
					do {
						errorMessage = "";
						try {
							View.editSettingsMenu(user, errorMessage);
						} catch (IllegalArgumentException e) {
							errorMessage = e.getMessage();
						}
					} while (!errorMessage.isEmpty());
					errorMessage = "";
					break;
			}
			
		} while (option != 0);
	}
}
