package ui.logic;

import domain.results.FindingResult;

public class MetricsCalculator {

	private Metrics metrics;
	
	public MetricsCalculator(FindingResult result) {
		calculate(result);
	}
	
	private void calculate(FindingResult result) {
		double NOHCD = result.getHardcodedFindings().size();
		double NOID = result.getInjectableFindings().size();
		double NOMD =  result.getMockableFindings().size();
		double ND = result.getNoDependencyFindings().size();
		
		this.metrics = new Metrics(NOHCD, NOID,  NOMD, ND);
	}
	
	public Metrics getMetrics() {
		return this.metrics;
	}
}

