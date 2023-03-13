package domain.dependencies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

public class ParameterListDependency implements IDependency{

	private MethodDeclaration node;
	
	List<SingleVariableDeclaration> parameters;
	
	public ParameterListDependency(MethodDeclaration node) {
		this.node = node;
		parameters = new ArrayList<>();
		node.parameters().forEach(param -> {
			if(param instanceof SingleVariableDeclaration) {
				parameters.add((SingleVariableDeclaration) param);
			}
		});
	}
	
	@Override
	public ASTNode getNode() {
		return node;
	}

	public List<SingleVariableDeclaration> getParameters() {
		return parameters;
	}
}
