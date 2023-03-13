package domain.results.type;

public enum FindingSeverity {
	HIGH(3),
	MEDIUM(2),
	LOW(1),
	NO(0);
	
	private final int severityLevel;
	
	private FindingSeverity(int severityLevel) {
		this.severityLevel = severityLevel;
	}
	public int getSeverityLevel() {
		return severityLevel;
	}
}
