package domain.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class JavaElementVisitor extends ASTVisitor{

	
	private ClassVisitor hardDependencyInClassVisitor;
	
	public JavaElementVisitor() {
		super();
		hardDependencyInClassVisitor = new ClassVisitor();
	}
	@Override
	public boolean visit(TypeDeclaration node) {
		if(!node.isInterface()) {
			node.accept(hardDependencyInClassVisitor);			
		}
		return true;
	}
}
