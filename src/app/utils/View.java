package app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.data.List;
import app.data.Task;
import app.data.User;
import app.enums.EnumStatusTask;
import app.exceptions.InvalidTaskException;
import app.main.SystemToDo;

public abstract class View {
	
	public static String getString(String message, String title) {
		return JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE).trim();
	}
	
	public static Map<String, String> loginMenu(String errorMessage) {
		
		Map<String, String> data = new HashMap<>();
		JTextField emailField = new JTextField();
		JTextField passwordField = new JTextField();
		Integer operation;
		
		if(!errorMessage.isEmpty())
			errorMessage += "\n\n";
		
		Object[] fields = {errorMessage, "Email", emailField, "Senha", passwordField, "Senha deve ter no mínimo 8 caracteres","\n"};
		
		String[] options = {"Logar", "Cadastrar", "Sair"};
		
		operation = 1 + JOptionPane.showOptionDialog(null, fields, "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		data.put("email", emailField.getText().trim());
		data.put("password", passwordField.getText().trim());
		data.put("operation", String.valueOf(operation));
		
		return data;
	}
	
	public static Map<String, String> registerMenu(String errorMessage) {
		
		Map<String, String> data = new HashMap<>();
		JTextField nameField = new JTextField();
		JTextField emailField = new JTextField();
		JTextField passwordField = new JTextField();
		JTextField reEnterPasswordField = new JTextField();
		Integer operation;
		
		if(!errorMessage.isEmpty())
			errorMessage += "\n\n";
		
		Object[] fields = 
			{
				errorMessage, 
				"Nome", nameField,
				"Email", emailField, 
				"Senha", passwordField, 
				"Senha deve ter no mínimo 8 caracteres",
				"Confirme a senha", reEnterPasswordField,
				"\n" 
			};
		
		String[] options = {"Cadastrar", "Voltar"};
		
		operation = 1 + JOptionPane.showOptionDialog(null, fields, "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if(operation.equals(1) && !passwordField.getText().trim().equals(reEnterPasswordField.getText().trim()))
			throw new IllegalArgumentException("As senhas devem ser a mesma");
			
		data.put("name", nameField.getText().trim());
		data.put("email", emailField.getText().trim());
		data.put("password", passwordField.getText().trim());
		data.put("operation", String.valueOf(operation));
		
		return data;
	}
	
	public static Integer mainMenu(String username, String[] options) {
		
		Integer operation;

		operation = 1 + JOptionPane.showOptionDialog(null, username, "Menu Principal", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if(operation.equals(options.length))
			return 0;
		return operation;
	}
	
	public static Map<String, Integer> listsGeneralMenu(String message, String[] options) {
		
		Map<String, Integer> data = new HashMap<>();
		JTextField idInputField = new JTextField();
		Integer operation;
		
		Object[] fields = {message, "\n", "ID", idInputField, "\n", "Digite o ID (se possível) e selecione a opção desejada","\n"};
		
		operation = 1 + JOptionPane.showOptionDialog(null, fields, "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if(operation.equals(options.length))
			operation = 0;
		
		if(operation != 0)
			data.put("id", Integer.parseInt(idInputField.getText().trim()));
		data.put("operation", Integer.valueOf(operation));
		
		return data;
	}
	
	public static void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.PLAIN_MESSAGE);
	}
	
	private static Map<String, String> getDataTask(String[] options, String title) {
		
		Map<String, String> data = new HashMap<String, String>();
		
		JTextField nameField = new JTextField();
		JTextField dueDateField = new JTextField();
		JTextField notesField = new JTextField();
		
		javax.swing.JComboBox<String> statusField = new JComboBox<String>();

		statusField.addItem(EnumStatusTask.TODO.getStatusName());
		statusField.addItem(EnumStatusTask.INPROGRESS.getStatusName());
		statusField.addItem(EnumStatusTask.DONE.getStatusName());

		Integer operation;
		
		Object[] fields = {"Nome", nameField, "Data de Conclusão", dueDateField, "Status da tarefa", statusField, "Comentários", notesField,"\n"};
		
		operation = 1 + JOptionPane.showOptionDialog(null, fields, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		data.put("name", nameField.getText().trim());
		data.put("dueDate", dueDateField.getText().trim());
		data.put("notes", notesField.getText().trim());
		data.put("status", statusField.getSelectedItem().toString());
		data.put("operation", String.valueOf(operation));
		
		return data;			

	}
	
	private static ArrayList<User> membersMenu(ArrayList<User> members) {
		
		String[] options = { "Adicionar Colaborador", "Remover Colaborador", "Voltar" };
	
		JTextField emailField = new JTextField();
		Integer operation;
		String message;
		Boolean status;
		
		Object[] fields = new Object[5];
		User member = null;
		
		fields[1] = "\n";
		fields[2] = "Email";
		fields[3] = emailField;
		fields[4] = "\n";

		do {
			message = "";
			
			if(members.isEmpty())
				message = "Não há colaboradores ainda";
			else {
				for (User memberTemp : members) {
					if(memberTemp != null)
						message += memberTemp.toString();
				}
			}
			fields[0] = message;
			operation = 1 + JOptionPane.showOptionDialog(null, fields, "Colaboradores", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			
			switch (operation) {
				case 1:
					member = SystemToDo.findUser(emailField.getText().trim());
					if(member != null) {
						status = true;
						for (User memberTemp : members) {
							if(memberTemp.getEmail().equals(member.getEmail()))
								status = false;
						}
						if(status)
							members.add(member);
						else
							showError("Colaborador já foi adicionado");
					} else
						showError("Colaborador não encontrado");
					break;
		
				case 2:
					member = SystemToDo.findUser(emailField.getText().trim());
					if(member != null)
						members.remove(member);
					else
						showError("Colaborador não encontrado");
					break;
			}
		} while (operation != 3);
		
		return members;
	}
	
	public static void editTaskMenu(Task task) {
		
		LocalDate dueDate;
		String[] options = {"Salvar Mudanças", "Colaboradores", "Voltar"};
		
		Map<String, String> data;
		
		do {
			data = getDataTask(options, "Editar Tarefa");
				
			switch (Integer.parseInt(data.get("operation"))) {
				case 1:
					if(!data.get("name").isEmpty())
						task.setName(data.get("name"));
					
					if(!data.get("notes").isEmpty())
						task.setNotes(data.get("notes"));
					
					if(!data.get("dueDate").isEmpty()) {
						dueDate = LocalDate.parse(data.get("dueDate"), DateTimeFormatter.ofPattern("dd/MM/uuuu"));
						task.setDueDate(dueDate);
					}
					for (int i = 0; i < EnumStatusTask.values().length; i++) {
						if(EnumStatusTask.values()[i].getStatusName().equals(data.get("status")))
							task.setStatus(EnumStatusTask.values()[i]);
					}
					break;
					
				case 2:
					try {
						task.setMembers(membersMenu(task.getMembers()));
					} catch (InvalidTaskException e) {
						showError(e.getMessage());
					}
					
			}
		} while (Integer.parseInt(data.get("operation")) == 2);
	}
	
	public static void editListMenu(List list) {
		
		String[] options = {"Salvar Mudanças", "Voltar"};
		
		JTextField nameField = new JTextField();
		Integer operation;
		
		Object[] fields = {"Nome", nameField};
		operation = 1 + JOptionPane.showOptionDialog(null, fields, "Editar lista", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if(operation == 1 && !nameField.getText().trim().isEmpty())
			list.setName(nameField.getText().trim());
	}
	
	public static void createListMenu(User user) {
		
		List list = null;
		String[] options = {"Criar Lista", "Voltar"};
		
		JTextField nameField = new JTextField();
		Integer operation;
		
		Object[] fields = {"Nome", nameField};
		operation = 1 + JOptionPane.showOptionDialog(null, fields, "Criar lista", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if(operation == 1 && !nameField.getText().trim().isEmpty()) {
			list = Util.createList(nameField.getText().trim());
			user.addList(list);
		}
	}

	public static void addTaskMenu(User user) {
		
		Task task;
		ArrayList<User> members = new ArrayList<User>();
		String[] options = {"Adicionar Tarefa", "Colaboradores", "Voltar"};
		
		Map<String, String> data;
		
		do {
			data = getDataTask(options, "Adicionar Tarefa");
			
			switch (Integer.parseInt(data.get("operation"))) {
				case 1:
					task = Util.createTask(data, user);
					task.setMembers(members);
					user.addTask(task);
					break;
	
				case 2:
					try {
						members = membersMenu(members);
					} catch (InvalidTaskException e) {
						showError(e.getMessage());
					}
					break;
			}
		} while (Integer.parseInt(data.get("operation")) == 2);
	}
	
	public static void editSettingsMenu(User user, String errorMessage) {
		
		JTextField nameField = new JTextField();
		JTextField passwordField = new JTextField();
		JTextField reEnterPasswordField = new JTextField();
		Integer operation;
		
		if(!errorMessage.isEmpty())
			errorMessage += "\n\n";
		
		Object[] fields = 
			{
				errorMessage, 
				"Nome", nameField,
				"Senha", passwordField, 
				"Senha deve ter no mínimo 8 caracteres",
				"Digite a senha novamente", reEnterPasswordField,
				"\n" 
			};
		
		String[] options = {"Salvar Mudanças", "Voltar"};
		
		operation = 1 + JOptionPane.showOptionDialog(null, fields, "Configurações", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if (operation.equals(1)) {
			
			if (!passwordField.getText().trim().equals(reEnterPasswordField.getText().trim()))
				throw new IllegalArgumentException("As senhas devem ser a mesma");
			else if (!passwordField.getText().trim().isEmpty())
				user.setPassword(passwordField.getText().trim());
			
			if(!nameField.getText().trim().isEmpty())
				user.setName(nameField.getText().trim());
		}
			
	}

}
