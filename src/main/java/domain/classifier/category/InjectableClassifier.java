package domain.classifier.category;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import domain.classifier.IASTNodeClassifier;
import domain.dependencies.MethodDependency;
import domain.dependencies.ParameterListDependency;
import domain.dependencies.VariableDependency;
import domain.results.FindingResultCache;
import domain.results.type.FindingCategory;

public class InjectableClassifier implements IASTNodeClassifier{
		
	private final List<VariableDependency> variableDependencies;
	private final List<ParameterListDependency> paramDependencies;
	private final List<MethodDependency> methodDependencies;
	
	public InjectableClassifier(
			List<VariableDependency> variableDependencies, List<MethodDependency> methodDependencies, 
			List<ParameterListDependency> paramDependencies) {
		this.paramDependencies = paramDependencies;
		this.variableDependencies = variableDependencies;
		this.methodDependencies = methodDependencies;

	}
	
	@Override
	public void classify() {
		paramDependencies.forEach(
				paramDependency -> paramDependency.getParameters().forEach(
						param -> 
						{
							variableDependencies.forEach(
									variableDependency -> classifyInjectabilityOnAssignments(param, variableDependency));
							methodDependencies.forEach(
									methodDependency -> classifyInjectabilityOnMethods(param, methodDependency));
						}));
		
		paramDependencies.forEach(
				paramList -> paramList.getParameters().forEach(
						param -> FindingResultCache.getFindingByNode(param).setCategory(FindingCategory.INJECTABLE_DEPENDENCY)));

	}
	
	private void classifyInjectabilityOnAssignments(SingleVariableDeclaration param, VariableDependency dependency) {
		Name paramName = param.getName();	
			if(paramName.subtreeMatch(new ASTMatcher(), dependency.getValue())) {
				FindingResultCache.getFindingByNode(dependency.getNode()).setCategory(FindingCategory.INJECTABLE_DEPENDENCY);
				FindingResultCache.getFindingByNode(dependency.getNode()).setDisplayNode(param);

			}
		}
	private void classifyInjectabilityOnMethods(SingleVariableDeclaration param, MethodDependency dependency) {
		dependency.getVariable().ifPresent(variable -> {
			Name paramName = param.getName();
			if(paramName.subtreeMatch(new ASTMatcher(), variable)) {
				FindingResultCache.getFindingByNode(dependency.getNode()).setCategory(FindingCategory.INJECTABLE_DEPENDENCY);
				FindingResultCache.getFindingByNode(dependency.getNode()).setDisplayNode(param);

			}
		});
	}
}
