package app.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import app.data.abstracts.Base;
import app.enums.EnumStatusTask;
import app.exceptions.InvalidBaseException;
import app.exceptions.InvalidTaskException;
import app.utils.View;

public class Task extends Base {

	private EnumStatusTask status;
	private LocalDate dueDate;
	private String notes;
	private User assignedUser;
	private ArrayList<User> members;

	public Task(String name, EnumStatusTask status, User assignedUser) {
		
		super(name);
		setStatus(status);
		setAssignedUser(assignedUser);
		
		members = new ArrayList<User>();
	}
	
	public Task(String name, EnumStatusTask status, LocalDate dueDate, User assignedUser) {
			
		this(name, status, assignedUser);
		setDueDate(dueDate);
	}
	
	public Task(String name, EnumStatusTask status, String notes, User assignedUser) {
		
		this(name, status, assignedUser);
		setNotes(notes);
	}
	
	public Task(String name, EnumStatusTask status, LocalDate dueDate, String notes, User assignedUser) {
		
		this(name, status, assignedUser);
		setDueDate(dueDate);
		setNotes(notes);
	}

	public EnumStatusTask getStatus() {
		return status;
	}

	public String getDueDate() {
		return dueDate.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
	}
	
	public String getNotes() {
		if(notes == null)
			return "";
		return notes;
	}
	
	public User getAssignedUser() {
		return assignedUser;
	}
	
	public ArrayList<User> getMembers() {
		return members;
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
			throw new InvalidTaskException("Status inválido");
	}

	public void setDueDate(LocalDate dueDate) {
		LocalDate currentDate = LocalDate.now();

		if(dueDate != null && dueDate.isAfter(currentDate))
			this.dueDate = dueDate;
		else
			throw new InvalidTaskException("Data de conclusão inválida");
	}

	public void setNotes(String notes) {
		if(notes != null)
			this.notes = notes;
		else
			throw new InvalidTaskException("Comentário inválido");
	}

	private void setAssignedUser(User assignedUser) {
		if(assignedUser != null)
			this.assignedUser = assignedUser;
		else
			throw new InvalidTaskException("Usuário inválido");
	}
	
	public void setMembers(ArrayList<User> members) {
		if(members != null)
			this.members = members;
		else
			throw new InvalidTaskException("Colaboradores inválidos");
	}
	
	public void addMember(User member) {
		if(member != null)
			members.add(member);
		else
			throw new InvalidTaskException("Colaborador inválido");
	}
	
	public void removeMember(User member) {
		if(!members.remove(member))
			throw new InvalidTaskException("Colaborador inválido");
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
			throw new InvalidTaskException("Não foi possível editar o nome");
	}
	
	@Override
	public String toString() {
		String infos = "ID: " + getId() 
			+ "\nNome: " + getName() 
			+ "\nStatus: " + getStatus().getStatusName() + "\n";
		
		if(dueDate != null)
			infos += "Data de conclusão: " + getDueDate() + "\n";
		
		if(notes != null && !notes.isEmpty())
			infos += "Comentários: " + getNotes() + "\n";
		
		return infos;
	}
}
