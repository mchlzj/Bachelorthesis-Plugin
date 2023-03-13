package domain.dependencies;

import java.lang.reflect.Modifier;
import java.util.Optional;

import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;

public class MethodDependency implements IDependency{

	private MethodInvocation node;
	private boolean isInAssignment;
	private boolean isInnerUseMethod;
	private Expression variable;
	
	public MethodDependency(MethodInvocation node) {
		this.node = node;
		this.isInAssignment = false;
		this.isInnerUseMethod = createIsInnerUseMethod();
		this.variable = node.getExpression();
	}
	
	private boolean createIsInnerUseMethod() {
		return node.getParent() instanceof ExpressionStatement && node.getExpression() == null;
	}
	
	public void setIsInAssignment(VariableDependency dependency) {
		if(!dependency.isGlobalesField() 
		&&(dependency.getValue() instanceof MethodInvocation)) {
			this.isInAssignment = ((MethodInvocation) dependency.getValue())
										.subtreeMatch(new ASTMatcher(), this.node);
		}
	}
	
	public MethodInvocation getNode() {
		return this.node;
	}
	
	public boolean getIsInAssignment() {
		return this.isInAssignment;
	}
	
	public boolean getIsInnerUseMethod() {
		return this.isInnerUseMethod;
	}

	public Optional<Expression> getVariable() {
		return Optional.ofNullable(this.variable);
	}

	public boolean isStatic() {
		IMethodBinding methodBinding = this.node.resolveMethodBinding();
		return Modifier.isStatic(methodBinding.getModifiers());
	}
	
	public boolean isCalledFromOwnClass() {
		return this.node.getExpression() == null;
	}
	
	public boolean isCalledFromAnotherObject() {
		if(this.node.getExpression() == null) {
			return false;
		}
		if(this.node.getExpression().getNodeType() != ASTNode.TYPE_LITERAL) {
			return !(this.node.getExpression().getNodeType() == ASTNode.SIMPLE_NAME &&
					((SimpleName) this.node.getExpression()).resolveBinding() instanceof ITypeBinding);
		}
		return false;
	}
	
	public boolean isInReturnStmt() {
	    ASTNode currentNode = this.node;
	    while (currentNode != null && currentNode.getNodeType() != ASTNode.METHOD_DECLARATION) {
	        if (currentNode.getNodeType() == ASTNode.RETURN_STATEMENT) {
	            ReturnStatement returnStatement = (ReturnStatement) currentNode;
	            if (returnStatement.getExpression() != null && returnStatement.getExpression().equals(node)) {
	                return true;
	            }
	            break;
	        }
	        currentNode = currentNode.getParent();
	    }
	    return false;
	}
}
