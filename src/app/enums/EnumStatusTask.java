package app.enums;

public enum EnumStatusTask {
	
	TODO(1,"Fazer"),
	INPROGRESS(2,"Em Progresso"),
	DONE(3,"Feito"),
	EXPIRED(4,"Expirado");
	
	private Integer statusCode;
	private String statusName;
	
	private EnumStatusTask(Integer statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public String getStatusName() {
		return statusName;
	}	
}
