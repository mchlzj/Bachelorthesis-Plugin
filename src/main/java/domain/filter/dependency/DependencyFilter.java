package domain.filter.dependency;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import domain.dependencies.MethodDependency;
import domain.dependencies.ParameterListDependency;
import domain.dependencies.VariableDependency;
import domain.filter.IASTNodeFilterer;

public class DependencyFilter implements IASTNodeFilterer{

	private List<ASTNode> nodesForFiltering;	
	private List<VariableDependency> variableDependencies;
	private List<MethodDependency> methodDependencies;
	private List<ParameterListDependency> parameterDependencies;

	
	public DependencyFilter(List<VariableDependency> variableDependencies, List<MethodDependency> methodDependencies, List<ParameterListDependency> parameterDependencies) {
		this.variableDependencies = variableDependencies;
		this.methodDependencies = methodDependencies;
		this.parameterDependencies = parameterDependencies;
	}

	@Override
	public void filter() {

		filterVariableDependencies();
		filterMethodDependencies();
		filterParameterDependencies();
		
	}

	private void filterVariableDependencies() {
		List<VariableDependency> toRemove = new ArrayList<>();

		variableDependencies.forEach(dependency -> {
			if(dependency.isInReturnStmt()) {
				toRemove.add(dependency);
			}
			if(dependency.isUsedAsArgument()) {
				toRemove.add(dependency);
			}
			if(dependency.isUseOfInnerMethod()) {
				toRemove.add(dependency);
			}
			if(dependency.isAssignmentFromParentClass()) {
				toRemove.add(dependency);
			}
			if(dependency.getUsesMemberVariable()) {
				toRemove.add(dependency);
			}
			if(dependency.isDeclaredInLoop()) {
				toRemove.add(dependency);
			}
			if(dependency.isNullValue()) {
				toRemove.add(dependency);
			}
			variableDependencies.forEach(secondDependency -> {
				if(dependency.sameObjectIsAssigned(secondDependency)) {
					toRemove.add(dependency);
				}
			});
		});
		variableDependencies.removeAll(toRemove);
		
	}
	
	private void filterMethodDependencies() {
		List<MethodDependency> toRemove = new ArrayList<>();
		methodDependencies.forEach(methodDependency -> {
			if(methodDependency.getIsInAssignment()) {
				toRemove.add(methodDependency);
			}
			if(methodDependency.getIsInnerUseMethod()) {
				toRemove.add(methodDependency);
			}
			if(methodDependency.isCalledFromOwnClass()) {
				toRemove.add(methodDependency);
			}
			if(methodDependency.isCalledFromAnotherObject()) {
				toRemove.add(methodDependency);
			}
			if(methodDependency.isInReturnStmt()) {
				toRemove.add(methodDependency);
			}
		});	
		methodDependencies.removeAll(toRemove);
	}
	
	private void filterParameterDependencies() {
		List<MethodDependency> toRemoveFromMethod = new ArrayList<>();
		List<VariableDependency> toRemoveFromVariable = new ArrayList<>();
		
		for(ParameterListDependency paramList : parameterDependencies) {
			for(SingleVariableDeclaration param : paramList.getParameters()) {
				
				variableDependencies.forEach(variableDependency -> {
					if(variableDependency.getValue().subtreeMatch(new ASTMatcher(), param.getName())) {
						toRemoveFromVariable.add(variableDependency);
					}
				});
			}
		}
		methodDependencies.removeAll(toRemoveFromMethod);
		variableDependencies.removeAll(toRemoveFromVariable);
	}
	
	


}
