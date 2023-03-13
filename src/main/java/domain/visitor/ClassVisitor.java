package domain.visitor;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import domain.classifier.IASTNodeClassifier;
import domain.classifier.category.DependencyClassifier;
import domain.filter.IASTNodeFilterer;
import domain.filter.dependency.DependencyFilter;
import domain.results.Finding;
import domain.results.FindingResultCache;

public class ClassVisitor extends ASTVisitor{

	private MethodVisitor methodVisitor;


	private IASTNodeFilterer filterer;
	private IASTNodeClassifier classifier;
	
	public ClassVisitor() {
		super();
		this.methodVisitor = new MethodVisitor();
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		node.accept(methodVisitor);
		return super.visit(node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {

		filterer = new DependencyFilter(methodVisitor.getVariableDependencies(), methodVisitor.getMethodDependencies(), methodVisitor.getParameterDependencies());
		filterer.filter();
		
		methodVisitor.getVariableDependencies().forEach(dependency -> createFinding(dependency.getNode()));
		methodVisitor.getMethodDependencies().forEach(dependency -> createFinding(dependency.getNode()));
		methodVisitor.getParameterDependencies().forEach(paramList -> paramList.getParameters().forEach(param -> createFinding(param)));

		
		classifier = new DependencyClassifier(methodVisitor.getVariableDependencies(), methodVisitor.getParameterDependencies(), methodVisitor.getMethodDependencies());
		classifier.classify();	
		
		
		

		clearNodesAfterMethod();
	}


	private void clearNodesAfterMethod() {
		methodVisitor.getVariableDependencies().clear();
		methodVisitor.getMethodDependencies().clear();
		methodVisitor.getParameterDependencies().clear();
	}

	private void createFinding(ASTNode node) {
		Finding finding = new Finding();
		finding.setFindingNode(node);
		FindingResultCache.addFinding(finding);
		finding.setDisplayNode(node);
	}	

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private List<Expression> getFilteredLiterals(List<Expression> literals, List<ClassInstanceCreation> newInstances, List<ReturnStatement> returnStatements) {
//		List<Expression> filteredItems = new ArrayList<>();
//		
//		for(Expression literal: literals) {
//			filterLiteralIsInReturnStmt(filteredItems, literal);			
//			filterLiteralVariableIsInReturnStmt(returnStatements, filteredItems, literal);			
//			filterLiteralIsInArgument(newInstances, filteredItems, literal);
//		}
//		
//		literals.removeAll(filteredItems);
//		return literals;
//		
//	}
//
//	private void filterLiteralIsInArgument(List<ClassInstanceCreation> newInstances, List<Expression> filteredItems,
//			Expression literal) {
//		for(ClassInstanceCreation instance : newInstances) {
//			List<Expression> arguments = instance.arguments();
//			for(Expression argument : arguments) {
//				if(argument.subtreeMatch(new ASTMatcher(), literal)) {
//					filteredItems.add(literal);
//				}
//			}
//		}
//	}
//
//	private void filterLiteralVariableIsInReturnStmt(List<ReturnStatement> returnStatements,
//			List<Expression> filteredItems, Expression literal) {
//		if(literal.getParent() instanceof VariableDeclarationFragment) {
//			VariableDeclarationFragment literalFragment = (VariableDeclarationFragment) literal.getParent();
//			for(ReturnStatement returnStatement: returnStatements) {
//				if(returnStatement.getExpression().subtreeMatch(new ASTMatcher(), literalFragment.getName())) {
//					filteredItems.add(literal);
//				}
//			}
//		}
//	}
//
//	private void filterLiteralIsInReturnStmt(List<Expression> filteredItems, Expression literal) {
//		if(literal.getParent() instanceof ReturnStatement) {
//			filteredItems.add(literal);
//		}
//	}
//	
//	private List<ClassInstanceCreation> getFilteredNewInstances(List<ClassInstanceCreation> newInstances, List<ReturnStatement> returnStatements) {
//	
//		List<ClassInstanceCreation> filteredItems = new ArrayList<>();
//		
//		for(ClassInstanceCreation newInstance : newInstances) {	
//			filterNewInstanceIsInReturnStmt(filteredItems, newInstance);
//			filterNewInstanceVariableIsInReturnStmt(returnStatements, filteredItems, newInstance);
//			filterNewInstanceIsPrimitiveWrapper(filteredItems, newInstance);
//		}
//		newInstances.removeAll(filteredItems);
//		return newInstances;
//	}
//
//	private void filterNewInstanceIsPrimitiveWrapper(List<ClassInstanceCreation> filteredItems,
//			ClassInstanceCreation newInstance) {
//		String typeName = newInstance.getType().toString();
//		if(typeNameIsPrimitiveWrapper(typeName)) {
//			filteredItems.add(newInstance);
//		}
//	}
//
//	private void filterNewInstanceVariableIsInReturnStmt(List<ReturnStatement> returnStatements,
//			List<ClassInstanceCreation> filteredItems, ClassInstanceCreation newInstance) {
//		if(newInstance.getParent() instanceof VariableDeclarationFragment) {
//			VariableDeclarationFragment newInstanceFragment = (VariableDeclarationFragment) newInstance.getParent();
//			for(ReturnStatement returnStatement: returnStatements) {
//				if(returnStatement.getExpression().subtreeMatch(new ASTMatcher(), newInstanceFragment.getName())) {
//					filteredItems.add(newInstance);
//				}
//			}
//		}
//	}
//
//	private void filterNewInstanceIsInReturnStmt(List<ClassInstanceCreation> filteredItems,
//			ClassInstanceCreation newInstance) {
//		if(newInstance.getParent() instanceof ReturnStatement) {
//			filteredItems.add(newInstance);
//		}
//	}
//	
//	private boolean typeNameIsPrimitiveWrapper(String typeName) {
//		return "Boolean".equals(typeName) ||
//				"Character".equals(typeName) ||
//				"Short".equals(typeName) ||
//				"Integer".equals(typeName) ||
//				"Long".equals(typeName) ||
//				"Float".equals(typeName) ||
//				"Double".equals(typeName) ||
//				"Boolean".equals(typeName);
//	}

//	public List<ClassInstanceCreation> getFilteredInstancesInMethod() {
//		return filteredInstancesInMethod;
//	}
//
//	public List<Expression> getFilteredliteralsInMethod() {
//		return filteredliteralsInMethod;
//	}
//}
