package ui.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import domain.AnalysisRunner;
import domain.results.Finding;
import domain.results.FindingResult;
import domain.results.FindingResultCache;
import domain.results.type.FindingCategory;
import ui.view.ResultDisplay;
import ui.view.ResultView;


public class RunAnalysisHandler extends AbstractHandler{

	private static final String LOGGERNAME = "RunnerHandlerLogger";
	private static final Logger LOGGER = Logger.getLogger(LOGGERNAME);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		

		Object selectedObject = null;
		
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			selectedObject = structuredSelection.getFirstElement();
		}

			if(selectedObject instanceof IJavaElement) {

				CompilationUnit cu = null;
				FindingResult result = FindingResult.get();
				ResultDisplay resultDisplay = new ResultDisplay();
				
				/**
				 * Packagefragment Root
				 */
				if(selectedObject instanceof IPackageFragmentRoot) {

					IPackageFragmentRoot pf = (IPackageFragmentRoot) selectedObject;

					
					        try {
								for (IJavaElement element : pf.getChildren()) {
								    if (element instanceof IPackageFragment) {
								        try {
											traversePackage((IPackageFragment) element,result, resultDisplay);
										} catch (JavaModelException e) {
											LOGGER.severe(e.getMessage());
										}
								    }
								}
							} catch (JavaModelException e) {
								LOGGER.severe(e.getMessage());
							}
					try {
						PackageTraverser.traverse(pf);
					} catch (JavaModelException e1) {
						LOGGER.severe(e1.getMessage());
					}
					
				}
				
				/**
				 * Packagefragment
				 */
				else if(selectedObject instanceof IPackageFragment) {
					IPackageFragment pf = (IPackageFragment) selectedObject;
					
						try {
							for(ICompilationUnit unit : pf.getCompilationUnits()) {
								ASTParser parser = createParser(unit);
							    cu = (CompilationUnit) parser.createAST(null);
								
								AnalysisRunner runner = new AnalysisRunner(cu);
								runner.runAnalysis();
								FindingResult localResult = FindingResult.getFromCache();
								resultDisplay.addResults(localResult, cu);
								FindingResultCache.resetFindings();	
							    
							}
						} catch (JavaModelException e) {
							LOGGER.severe(e.getMessage());
						}}
				
				/**
				 * File
				 */
				else {
					cu = (CompilationUnit) createParser(selectedObject).createAST(null);
					AnalysisRunner runner = new AnalysisRunner(cu);
					runner.runAnalysis();
					
					
					//TODO - Sicherheitshalber Dependency Category gesetzt!
					List<Finding> findinsWithoutCategory = new ArrayList<>();
					for(Finding finding: FindingResultCache.getFindings()) {
						if(finding.getCategory() == null) {
							findinsWithoutCategory.add(finding);
							finding.setCategory(FindingCategory.NO_DEPENDENCY);
							
						}
					}

					FindingResult localResult = FindingResult.getFromCache();
					result.addFromOtherResult(localResult);
					FindingResultCache.resetFindings();	
					resultDisplay.addResults(localResult, cu);
				}

				MetricsCalculator metricsCalculator = new MetricsCalculator(result);
				Metrics metrics = metricsCalculator.getMetrics();
				
				IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				try {
				
					IViewPart resultView = activePage.showView("TestabilityAnalyzer.resultView");
					((ResultView) resultView).updateView(metrics,resultDisplay);
				
				} catch (PartInitException e) {
					LOGGER.severe(e.getMessage());
				}
				
			}
		return null;
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
                LOGGER.info("Traversing class: " + type.getFullyQualifiedName());
            }
        }
    }
	}
}
