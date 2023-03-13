package domain.dependencies;

import java.lang.reflect.Modifier;
import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

public class VariableDependency implements IDependency{

	
	private ASTNode node;
	private Expression name;
	private Expression value;
	private boolean isInReturnStmt;
	private boolean isUsedAsArgument;

	
	public VariableDependency(ASTNode node) {
		this.node = node;
		this.name = setName();
		this.value = setValue();
		this.isInReturnStmt = false;
		this.isUsedAsArgument = false;
	}

	
	public Expression getName() {
		return this.name;
	}

	private Expression setName() {
		if(this.node instanceof VariableDeclarationFragment) {
			return ((VariableDeclarationFragment) this.node).getName();
		}
			return ((Assignment) this.node).getLeftHandSide();
	}
	
	public Expression getValue() {
		return this.value;
	}

	private Expression setValue() {
		if(this.node instanceof VariableDeclarationFragment) {
			return ((VariableDeclarationFragment) node).getInitializer();
		} 
		return ((Assignment) node).getRightHandSide();
	}
	
	public void setIsInReturnStmt(ReturnStatement returnStmt) {
		if(returnStmt.getExpression() != null) {
			this.isInReturnStmt = returnStmt.getExpression().subtreeMatch(new ASTMatcher(), this.name);
		}
	}
	
	public boolean isInReturnStmt() {
		return isInReturnStmt;
	}
	
	public void setIsUsedAsArgument(Expression argument) {
		boolean bool  = argument.subtreeMatch(new ASTMatcher(), this.name);
		if(bool) {
			this.isUsedAsArgument = true;
		};
	}
	
	public boolean isUsedAsArgument() {
		return this.isUsedAsArgument;
	}
	
	public boolean isGlobalesField() {
		if(!(this.name instanceof Name)) {
			return false;
		}
		return ((Name) this.name).isQualifiedName();	
	}
	
	public ASTNode getNode() {
		return this.node;
	}
	
	public boolean isUseOfInnerMethod() {
		if(this.value instanceof MethodInvocation) {
			if( ((MethodInvocation)this.value).getExpression() == null) {
				return true;
			}			
		}
		return false;
	}
	
	public boolean sameObjectIsAssigned(VariableDependency dependency) {	
		if(this.value instanceof MethodInvocation) {
			MethodInvocation methodInvocation = (MethodInvocation) this.value;
			Expression methodExp = methodInvocation.getExpression();
			if(methodExp != null) {
				return methodExp.subtreeMatch(new ASTMatcher(), dependency.name);
			}
		}
		return this.value.subtreeMatch(new ASTMatcher(), dependency.name);
	}
	
	public boolean isFromStaticMethod() {
		if(this.value instanceof MethodInvocation) {
			MethodInvocation methodInvocation = (MethodInvocation) this.value;
			IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
			return Modifier.isStatic(methodBinding.getModifiers());
		}
		return false;
	}

	
	public boolean isAssignmentFromParentClass() {;
		return (this.value instanceof SuperMethodInvocation);
	}
	
	public boolean getUsesMemberVariable() {
		
		if(this.value instanceof MethodInvocation) {
			MethodInvocation invocation = (MethodInvocation) this.value;
			if(invocation.getName() == null) {
				return false;
			}
			IBinding binding = invocation.getName().resolveBinding();
			if(binding instanceof IMethodBinding) {
				IMethodBinding methodBinding = (IMethodBinding) binding;
				if(!Modifier.isStatic(methodBinding.getModifiers())) {
					return invocation.getExpression() instanceof SimpleName || invocation.getExpression() instanceof FieldAccess;
				}
			}

		}
		return false;

	}
	
	public boolean isDeclaredInLoop() {
		if(this.node.getParent() == null) {
			return false;
		}
		ASTNode parentNode = this.node.getParent();
		if(parentNode.getParent() != null) {
			return parentNode.getParent() instanceof ForStatement || parentNode.getParent() instanceof WhileStatement;
		}
		return false;
	}
	
	public boolean isNullValue() {
		return this.value instanceof NullLiteral;
	}
}
