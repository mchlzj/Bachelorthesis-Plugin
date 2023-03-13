package domain.classifier.category;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import domain.classifier.IASTNodeClassifier;
import domain.dependencies.MethodDependency;
import domain.dependencies.ParameterListDependency;
import domain.dependencies.VariableDependency;
import domain.results.FindingResultCache;
import domain.results.type.FindingCategory;

public class MockableClassifier implements IASTNodeClassifier{

private List<VariableDependency> variableDependencies;
private List<MethodDependency> methodDependencies;
private List<ParameterListDependency> paramListDependencies;

	public MockableClassifier(List<VariableDependency> variableDependencies, List<MethodDependency> methodDependencies, List<ParameterListDependency> paramListDependencies) {
		this.variableDependencies = variableDependencies;
		this.methodDependencies = methodDependencies;
		this.paramListDependencies = paramListDependencies;
	}
	
	@Override
	public void classify() {
		for(VariableDependency dependency : variableDependencies) {
			if(FindingResultCache.getFindingByNode(dependency.getNode()).getDisplayNode() instanceof SingleVariableDeclaration) {
				classifyMockabilityByType(dependency.getNode(),((SingleVariableDeclaration)FindingResultCache.getFindingByNode(dependency.getNode()).getDisplayNode()).getType());	
			}
		}
		for(MethodDependency dependency : methodDependencies) {
			if(FindingResultCache.getFindingByNode(dependency.getNode()).getDisplayNode() instanceof SingleVariableDeclaration) {
				classifyMockabilityByType(dependency.getNode(),((SingleVariableDeclaration)FindingResultCache.getFindingByNode(dependency.getNode()).getDisplayNode()).getType());	
			}
		}
		for(ParameterListDependency paramListDependency: paramListDependencies) {
			for(SingleVariableDeclaration param: paramListDependency.getParameters()) {
				classifyMockabilityByType(param, param.getType());
			}
		}
		
	}
	
	private void classifyMockabilityByType(ASTNode node, Type type) {
		if(type.isPrimitiveType()) {
			FindingResultCache.getFindingByNode(node).setCategory(FindingCategory.NO_DEPENDENCY);
		}
		if(type.isArrayType()) {
			ArrayType arrType = (ArrayType) type;
			classifyMockabilityByType(node, arrType.getElementType());
		}
		if(type.isParameterizedType()) {
			ParameterizedType parametType = (ParameterizedType) type;
			List<Type> argumentTypes = parametType.typeArguments();
			if(argumentTypes.size()>0) {
				for(Type argType : argumentTypes) {
					classifyMockabilityByType(node, argType);		
				}
			}
		}
		if(type.isSimpleType()) {
			ITypeBinding paramTypeBinding = type.resolveBinding();
			if(paramTypeBinding != null) {
				String typeName = paramTypeBinding.getQualifiedName();
				if(isWrapperClass(typeName)) {
					FindingResultCache.getFindingByNode(node).setCategory(FindingCategory.NO_DEPENDENCY);
				} else {
					if(paramTypeBinding != null) {
						classifyImplementsInterface(node, paramTypeBinding);
						classifyIsInterface(node, paramTypeBinding);			
					}
					
				}
			}
		}
	}
	
	private boolean isWrapperClass(String typeName) {
		return "java.lang.String".equals(typeName)
				|| "java.lang.Boolean".equals(typeName)
				|| "java.lang.Integer".equals(typeName)
				|| "java.lang.Byte".equals(typeName)
				|| "java.lang.Character".equals(typeName)
				|| "java.lang.Short".equals(typeName)
				|| "java.lang.Long".equals(typeName)
				|| "java.lang.Float".equals(typeName)
				|| "java.lang.Double".equals(typeName)
				|| "java.lang.Void".equals(typeName);
	}


	private void classifyIsInterface(ASTNode node, ITypeBinding paramType) {
		if(paramType.isInterface()) {
			FindingResultCache.getFindingByNode(node).setCategory(FindingCategory.NO_DEPENDENCY);
		}
	}

	private void classifyImplementsInterface(ASTNode node, ITypeBinding paramType) {
		if(paramType.isClass() && (paramType.getInterfaces().length != 0)) {
			FindingResultCache.getFindingByNode(node).setCategory(FindingCategory.MOCKABLE_DEPENDENCY);
		}
	}

}
