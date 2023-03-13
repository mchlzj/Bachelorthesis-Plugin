package domain.results;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;


public class FindingResultCache {

	private static  List<Finding> findings = new ArrayList<Finding>();
	
	public static void addFinding(Finding finding) {
		findings.add(finding);
	}
	
	public static List<Finding> getFindings() {
		return findings;
	}
	
	
	public static Finding getFindingByNode(ASTNode node) {
		Finding toReturn = null;
		
		for(Finding finding : findings) {
			if(finding.getFindingNode().equals(node)) {
				toReturn = finding;
			}
		}
		return toReturn;
	}
	
	public static void resetFindings() {
		findings.clear();
	}
}
