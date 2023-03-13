package anfdepnd1;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Analysis;
import domain.results.FindingResult;
import domain.results.FindingResultCache;
import util.WorkspaceHelper;

public class IdentifyOnlyNoDependenciesTest {
	IJavaElement javaElement;
	
	@Before
	public void arrange() throws JavaModelException {
		this.javaElement = WorkspaceHelper.getJavaElement("anfdepnd1/IdentifyOnlyNoDependencies.java");
	}
	
	@Test
	public void doTest() throws JavaModelException {
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		// ACT
		
		
		// ASSERT
		assertEquals( 4, FindingResult.getFromCache().getNoDependencyFindings().size());
	}
	
	@After
	public void teardown() {
		FindingResultCache.resetFindings();
	}
}
