package ui.logic;

public enum MetricsType {

	NOHCD("NOHCD", "Number of hardcoded Dependencies"),
	NOID("NOID", "Number of injectable Dependencies"),
	ROI("ROI", "Ratio of injectability"),
	NOMD("NOMD", "Number of mockable dependencies"),
	ROM("ROM", "Ratio of mockability"),
	ND("NOND", "Number of No Dependencies"),
	ROT("ROT", "Ratio of Testability");
	
	private String name;
	private String description;
	
	MetricsType(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
}
