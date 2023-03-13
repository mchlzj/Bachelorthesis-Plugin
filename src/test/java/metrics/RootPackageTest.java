package metrics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.After;
import org.junit.Test;

import domain.Analysis;
import domain.AnalysisRunner;
import domain.results.Finding;
import domain.results.FindingResult;
import domain.results.FindingResultCache;
import domain.results.type.FindingCategory;
import ui.logic.Metrics;
import ui.logic.MetricsCalculator;
import ui.logic.PackageTraverser;
import ui.view.ResultDisplay;
import util.WorkspaceHelper;

public class RootPackageTest {
	
	IJavaElement javaElement;
	
	
	@Test
	public void testeNoDependencies() throws JavaModelException {
		
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject("MetricsTestProject");
		IJavaProject javaProject = JavaCore.create(project);
		
		for(IPackageFragmentRoot root : javaProject.getPackageFragmentRoots()) {
			if(root.getKind() == IPackageFragmentRoot.K_SOURCE)  {
				this.javaElement = root;
			}
		}

		CompilationUnit cu = null;
		FindingResult result = FindingResult.get();
		ResultDisplay resultDisplay = new ResultDisplay();

		if(this.javaElement instanceof IPackageFragmentRoot) {

			IPackageFragmentRoot pf = (IPackageFragmentRoot) this.javaElement;

			        try {
						for (IJavaElement element : pf.getChildren()) {
						    if (element instanceof IPackageFragment) {
						        try {
									traversePackage((IPackageFragment) element,result, resultDisplay);
								} catch (JavaModelException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						    }
						}
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			try {
				PackageTraverser.traverse(pf);
			} catch (JavaModelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

		MetricsCalculator metricsCalculator = new MetricsCalculator(result);
		Metrics metrics = metricsCalculator.getMetrics();

		
		
		// ASSERT
		assertEquals(1, metrics.getNOHCDVal(), 0);
		assertEquals(1, metrics.getNOIDVal(),0);
		assertEquals(0.75, metrics.getROIVal(),0);
		assertEquals(1, metrics.getNOMDVal(),0);
		assertEquals(0.5, metrics.getROMVal(),0);
		assertEquals(1, metrics.getNDVal(),0);
		assertEquals(0.25, metrics.getROTVal(),0);
	}

	@After
	public void teardown() {
		FindingResultCache.resetFindings();
	}
	
	private  void traversePackage(IPackageFragment packageFragment, FindingResult result,  ResultDisplay resultDisplay) throws JavaModelException {
	    for (IJavaElement element : packageFragment.getChildren()) {
	        if (element instanceof IPackageFragment) {
	            traversePackage((IPackageFragment) element, result, resultDisplay);
	        } else if (element instanceof ICompilationUnit) {
	            ICompilationUnit unit = (ICompilationUnit) element;
	            for (IType type : unit.getAllTypes()) {
	            	ASTParser parser = createParser(unit);
				    CompilationUnit cu = (CompilationUnit) parser.createAST(null);
					
					AnalysisRunner runner = new AnalysisRunner(cu);
					runner.runAnalysis();
					
					
					
					//TODO - Sicherheitshalber Dependency Category gesetzt!
					List<Finding> findinsWithoutCategory = new ArrayList<>();
					for(Finding finding: FindingResultCache.getFindings()) {
						if(finding.getCategory() == null) {
							findinsWithoutCategory.add(finding);
							finding.setCategory(FindingCategory.NO_DEPENDENCY);
							finding.setDisplayNode(finding.getFindingNode());
						}
					}
					
					
					
					
					FindingResult localResult = FindingResult.getFromCache();
					result.addFromOtherResult(localResult);
					resultDisplay.addResults(localResult, cu);
					FindingResultCache.resetFindings();	
	                System.out.println("Traversing class: " + type.getFullyQualifiedName());
	            }
	        }
	    }
	}

	
	private ASTParser createParser(Object selectedObject) {
		IJavaElement javaElement = (IJavaElement) selectedObject;
		
		ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
		ICompilationUnit compilationUnit = (ICompilationUnit) javaElement;
		parser.setSource(compilationUnit);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		return parser;
	}	
}
