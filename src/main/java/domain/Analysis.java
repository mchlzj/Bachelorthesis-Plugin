package domain;


import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import domain.results.FindingResult;
import domain.results.FindingResultCache;

public class Analysis {

	private AnalysisRunner runner;
	private ASTParser parser;
	private CompilationUnit cu;
	private IJavaElement javaElement;
	
	public Analysis(IJavaElement javaElement) {
		this.javaElement = javaElement;
		
		
	}


	public void init() {
		this.parser = getParser();
		
		cu = getCompilationUnitFromParser();
		runner = new AnalysisRunner(cu);
		runner.runAnalysis();
	}
	
	
	private ASTParser getParser() {
		this.parser = ASTParser.newParser(AST.getJLSLatest());
		ICompilationUnit compilationUnit = (ICompilationUnit) this.javaElement;
		parser.setSource(compilationUnit);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		return parser;
	}
	
	private CompilationUnit getCompilationUnitFromParser( ) {
		this.cu = (CompilationUnit) this.parser.createAST(null);
		return cu;
	}
	
	public FindingResult getResult() {
		return FindingResult.getFromCache();
	}
	
	public void resetAnalysis() {
		FindingResultCache.resetFindings();
	}
	
	
	
}
