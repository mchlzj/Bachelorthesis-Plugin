package anfdephd1;

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

public class IdentifyPrimitivesTest {
	IJavaElement javaElement;
	
	@Before
	public void arrange() throws JavaModelException {
		this.javaElement = WorkspaceHelper.getJavaElement("anfdephd1/IdentifyPrimitives.java");
	}
	
	@Test
	public void doTest() throws JavaModelException {
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		// ACT
		
		
		// ASSERT
		assertEquals( 8, FindingResult.getFromCache().getFindings().size());
	}
	
	@After
	public void teardown() {
		FindingResultCache.resetFindings();
	}
}
