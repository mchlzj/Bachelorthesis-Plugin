package domain.classifier.category;

import java.util.List;

import domain.classifier.IASTNodeClassifier;
import domain.dependencies.MethodDependency;
import domain.dependencies.VariableDependency;
import domain.results.FindingResultCache;
import domain.results.type.FindingCategory;

public class HardcodedClassifier implements IASTNodeClassifier{
	
	private final List<VariableDependency> variableDependencies;
	private final List<MethodDependency> methodDependencies;
	
	public HardcodedClassifier(List<VariableDependency> variableDependencies , List<MethodDependency> methodDependencies) {
		this.variableDependencies = variableDependencies;
		this.methodDependencies = methodDependencies;
		
	}
	
	@Override
	public void classify() {
		for(VariableDependency dependency : variableDependencies) {

			FindingResultCache.getFindingByNode(dependency.getNode()).setDisplayNode(dependency.getNode());
			FindingResultCache.getFindingByNode(dependency.getNode()).setCategory(FindingCategory.HARDCODED_DEPENDENCY);
		}
		for(MethodDependency dependency : methodDependencies) {
			FindingResultCache.getFindingByNode(dependency.getNode()).setDisplayNode(dependency.getNode());
			FindingResultCache.getFindingByNode(dependency.getNode()).setCategory(FindingCategory.HARDCODED_DEPENDENCY);
		}
	}

}
