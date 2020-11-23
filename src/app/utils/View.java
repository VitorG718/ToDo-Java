package app.utils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
	
	public static Integer mainMenu(String username) {
		
		Integer operation;
		String[] options = 
			{
				"Adicionar Tarefa",
				"Tarefas",
				"Tarefas Atribuídas",
				"Criar Lista",
				"Listas",
				"Configurações",
				"Deslogar"
			};
		
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
		
		data.put("id", Integer.parseInt(idInputField.getText().trim()));
		data.put("operation", Integer.valueOf(operation));
		
		return data;
	}
}
