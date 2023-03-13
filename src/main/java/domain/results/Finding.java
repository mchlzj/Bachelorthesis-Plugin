package domain.results;

import org.eclipse.jdt.core.dom.ASTNode;

import domain.dependencies.VariableDependency;
import domain.results.type.FindingCategory;


public class Finding {
	
	VariableDependency dependency;
	ASTNode findingNode;
	ASTNode displayNode;
	int line;
	String url;
	FindingCategory category;

	
	public Finding() {
		
	}
	
	public ASTNode getFindingNode() {
		return findingNode;
	}

	public void setFindingNode(ASTNode node) {
		this.findingNode = node;
	}
	
	public ASTNode getDisplayNode() {
		return displayNode;
	}

	public void setDisplayNode(ASTNode displayNode) {
		this.displayNode = displayNode;
	}

	public FindingCategory getCategory() {
		return category;
	}

	public void setCategory(FindingCategory category) {
		this.category = category;
	}
}
