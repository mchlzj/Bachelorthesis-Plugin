package domain;

import org.eclipse.jdt.core.dom.CompilationUnit;

import domain.visitor.JavaElementVisitor;

public class AnalysisRunner {
	
	private CompilationUnit cu;
	private JavaElementVisitor classVisitor;
	
	public AnalysisRunner(CompilationUnit cu) {
		this.cu = cu;
		this.classVisitor = new JavaElementVisitor();
	}
	
	
	public void runAnalysis() {
		cu.accept(this.classVisitor);
	}	
}
