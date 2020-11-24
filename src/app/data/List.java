package app.data;

import java.util.ArrayList;

import app.data.abstracts.Base;
import app.exceptions.InvalidBaseException;
import app.exceptions.InvalidListException;
import app.utils.View;

public class List extends Base {
	
	private ArrayList<Task> tasks;
	
	public List(String name) {
		super(name);
		
		tasks = new ArrayList<Task>();
	}
	
	public String getTasks() {
		StringBuilder stringTasks = new StringBuilder();
		
		for (Task task : tasks) {
			if(task != null) {
				stringTasks
					.append(task.toString())
					.append("\n");
			}
				
		}
		
		if(stringTasks.length() != 0)
			return String.valueOf(stringTasks);
		return "Não há tarefas";
	}
	
	public void addTask(Task task) {
		if(task != null) {
			tasks.add(task);
		} else 
			throw new InvalidListException("Tarefa inválida");
	}
	
	public void removeTask(Integer id) {
		Task tempTask = null;
		
		for (Task task : tasks) {
			if(task.getId().equals(id))
				tempTask = task;
		}
		
		if(tempTask != null)
			tasks.remove(tempTask);
		else
			throw new InvalidListException("Tarefa não encontrada");
	}
	
	@Override
	public void setName(String name) {
		try {
			super.setName(name);
		} catch (InvalidBaseException e) {
			throw new InvalidListException(e.getMessage());
		}
	}

	@Override
	public void editName() {
		String name = View.getString("Digite o novo nome: ", "Editar nome");
		if(!name.isEmpty())
			setName(name);
		else
			throw new InvalidListException("Não foi possível editar o nome");
	}
	
	@Override
	public String toString() {
		return "ID: " + getId() + " Nome: " + getName();
	}
	
}
