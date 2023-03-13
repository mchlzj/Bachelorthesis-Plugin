package domain.results;

import java.util.ArrayList;
import java.util.List;

import domain.results.type.FindingCategory;

public class FindingResult {

	private List<Finding> findings = new ArrayList<Finding>();
	
	private FindingResult() {
		
	}
	
	public static FindingResult get() {
		return new FindingResult();
	}
public static FindingResult getFromCache() {
	FindingResult findingResult = new FindingResult();
	findingResult.findings = List.copyOf(FindingResultCache.getFindings());
	return findingResult;
}

	public List<Finding> getFindings() {
		return findings;
	}
	
	public List<Finding> getMockableFindings() {
		return getFindingsByCategory(FindingCategory.MOCKABLE_DEPENDENCY);
	}	
	
	public List<Finding> getInjectableFindings() {
		return getFindingsByCategory(FindingCategory.INJECTABLE_DEPENDENCY);
	}
	
	public List<Finding> getHardcodedFindings() {
		return getFindingsByCategory(FindingCategory.HARDCODED_DEPENDENCY);
	}
	
	public List<Finding> getNoDependencyFindings() {
		return getFindingsByCategory(FindingCategory.NO_DEPENDENCY);
	}
	
	private List<Finding> getFindingsByCategory(FindingCategory category) {
		List<Finding> filteredFindings = new ArrayList<>();
		for(Finding finding: findings) {
			if(category.equals(finding.getCategory())) {
				filteredFindings.add(finding);
			}
		}
		return filteredFindings;
	}
	public void addFromOtherResult(FindingResult result) {
		this.findings.addAll(result.findings);
	}
	
}
