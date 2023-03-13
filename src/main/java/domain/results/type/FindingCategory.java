package domain.results.type;

public enum FindingCategory {
	HARDCODED_DEPENDENCY("Hardcoded Dependency", FindingSeverity.HIGH),
	INJECTABLE_DEPENDENCY("Injectable Dependency", FindingSeverity.MEDIUM),
	MOCKABLE_DEPENDENCY("Mockable Dependency", FindingSeverity.LOW),
	NO_DEPENDENCY("No Dependency", FindingSeverity.NO);
	

	String name;
	FindingSeverity severity;

	FindingCategory(String name, FindingSeverity severity) {
		this.name = name;
		this.severity = severity;
	}

	public String getName() {
		return name;
	}
	
	public FindingSeverity getSeverity() {
		return severity;
	}
}
