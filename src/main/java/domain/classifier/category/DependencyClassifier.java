package domain.classifier.category;

import java.util.List;

import domain.classifier.IASTNodeClassifier;
import domain.dependencies.MethodDependency;
import domain.dependencies.ParameterListDependency;
import domain.dependencies.VariableDependency;

public class DependencyClassifier  implements IASTNodeClassifier{


	
	private IASTNodeClassifier classifier;
	
	private List<VariableDependency> variableDependencies;
	private List<ParameterListDependency> paramDependencies;
	private List<MethodDependency> methodDependencies;
	
	public DependencyClassifier(List<VariableDependency> variableDependencies, List<ParameterListDependency> paramDependencies, List<MethodDependency> methodDependencies) {
		this.paramDependencies = paramDependencies;
		this.variableDependencies = variableDependencies;
		this.methodDependencies = methodDependencies;
	}
	
	@Override
	public void classify() {
		this.classifier = new HardcodedClassifier(variableDependencies, methodDependencies);
		this.classifier.classify();
		this.classifier = new InjectableClassifier(
				variableDependencies,methodDependencies, 
				paramDependencies);
		this.classifier.classify();
		this.classifier = new MockableClassifier(variableDependencies, methodDependencies, paramDependencies);
		this.classifier.classify();
	}
}
