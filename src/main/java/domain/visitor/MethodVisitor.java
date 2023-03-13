package domain.visitor;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import domain.dependencies.MethodDependency;
import domain.dependencies.ParameterListDependency;
import domain.dependencies.VariableDependency;

public class MethodVisitor extends ASTVisitor{


	private final List<MethodDeclaration> methodDeclaraion;
	private final List<ParameterListDependency> parameterDependencies;
	private final List<VariableDependency> variableDependencies;
	private final List<MethodDependency> methodDependencies;
	private final List<Expression> arguments;

	
	public MethodVisitor() {
		this.methodDeclaraion = new ArrayList<>();
		this.parameterDependencies = new ArrayList<>();
		this.variableDependencies = new ArrayList<>();
		this.methodDependencies = new ArrayList<>();
		this.arguments = new ArrayList<>();
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		variableDependencies.forEach(dependency -> arguments.forEach(argument -> ((VariableDependency) dependency).setIsUsedAsArgument(argument)));
		methodDependencies.forEach(
				methodDependency -> variableDependencies.forEach(
						variableDependency -> ((MethodDependency) methodDependency).setIsInAssignment((VariableDependency) variableDependency)));
	}

	@Override
	public boolean visit(Assignment node) {
		VariableDependency dependency = new VariableDependency(node);
		variableDependencies.add(dependency);
		return super.visit(node);
	}
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if(node.getInitializer() != null) {
			VariableDependency dependency = new VariableDependency(node);
			variableDependencies.add(dependency);			
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ReturnStatement node) {
		variableDependencies.forEach(dependency -> ((VariableDependency) dependency).setIsInReturnStmt(node));
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		node.arguments().forEach(argument -> {
			if(argument instanceof Expression) {
				arguments.add((Expression) argument);
			}
		});
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		MethodDependency methodDependency = new MethodDependency(node);
		this.methodDependencies.add(methodDependency);			
		return super.visit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		if(!Modifier.isAbstract(node.getModifiers())) {
			methodDeclaraion.add(node);
			if(node.parameters().size() != 0) {
				ParameterListDependency paramDependency = new ParameterListDependency(node);
				parameterDependencies.add(paramDependency);				
			}
		}
		return super.visit(node);
	}

	public List<MethodDeclaration> getMethodDeclaration() {
		return methodDeclaraion;
	}

	public List<VariableDependency> getVariableDependencies() {
		return variableDependencies;
	}
	
	public List<MethodDependency> getMethodDependencies() {
		return methodDependencies;
	}
	
	public List<ParameterListDependency> getParameterDependencies() {
		return parameterDependencies;
	}
}
