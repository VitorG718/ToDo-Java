package app.data;

import java.time.LocalDate;
import java.util.ArrayList;

import app.data.abstracts.Base;
import app.enums.EnumStatusTask;
import app.exceptions.InvalidBaseException;
import app.exceptions.InvalidTaskException;
import app.utils.Util;
import app.utils.View;

public class Task extends Base {

	private EnumStatusTask status;
	private LocalDate dueDate;
	private String notes;
	private User assignedUser;
	private ArrayList<User> members;

	public Task(Integer id, String name, EnumStatusTask status, User assignedUser) {
		
		super(id, name);
		setStatus(status);
		setAssignedUser(assignedUser);
		
		members = new ArrayList<User>();
	}
	
	public Task(Integer id, String name, EnumStatusTask status, LocalDate dueDate, User assignedUser) {
			
		this(id, name, status, assignedUser);
		setDueDate(dueDate);
	}
	
	public Task(Integer id, String name, EnumStatusTask status, String notes, User assignedUser) {
		
		this(id, name, status, assignedUser);
		setNotes(notes);
	}
	
	public Task(Integer id, String name, EnumStatusTask status, LocalDate dueDate, String notes, User assignedUser) {
		
		this(id, name, status, assignedUser);
		setDueDate(dueDate);
		setNotes(notes);
	}

	public EnumStatusTask getStatus() {
		return status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public String getNotes() {
		if(notes == null)
			return "";
		return notes;
	}
	
	public User getAssignedUser() {
		return assignedUser;
	}
	
	@Override
	public void setName(String name) {
		try {
			super.setName(name);
		} catch (InvalidBaseException e) {
			throw new InvalidTaskException(e.getMessage());
		}
	}

	public void setStatus(EnumStatusTask status) {
		if(status != null)
			this.status = status;
		else
			throw new InvalidTaskException("Status inv�lido");
	}

	public void setDueDate(LocalDate dueDate) {
		LocalDate currentDate = LocalDate.now();
		
		if(dueDate != null && dueDate.isBefore(currentDate))
			this.dueDate = dueDate;
		else
			throw new InvalidTaskException("Data de conclus�o inv�lida");
	}

	public void setNotes(String notes) {
		try {
			this.notes = Util.validateString(notes);
		} catch (IllegalArgumentException e) {
			throw new InvalidTaskException("Coment�rio inv�lido");
		}
	}

	private void setAssignedUser(User assignedUser) {
		if(assignedUser != null)
			this.assignedUser = assignedUser;
		else
			throw new InvalidTaskException("Usu�rio inv�lido");
	}
	
	public void addMember(User member) {
		if(member != null)
			members.add(member);
		else
			throw new InvalidTaskException("Colaborador inv�lido");
	}
	
	public void removeMember(User member) {
		if(!members.remove(member))
			throw new InvalidTaskException("Colaborador inv�lido");
	}
	
	public Boolean findMember(String email) {
		for (User user : members) {
			if(user.getEmail().equals(email))
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	public void editName() {
		String name = View.getString("Digite o novo nome: ", "Editar nome");
		if(!name.isEmpty())
			setName(name);
		else
			throw new InvalidTaskException("N�o foi poss�vel editar o nome");
	}
	
	@Override
	public String toString() {
		return "ID: " + getId() + " Nome: " + getName() + " Status: " + getStatus().getStatusName() + "\n";
	}
}
