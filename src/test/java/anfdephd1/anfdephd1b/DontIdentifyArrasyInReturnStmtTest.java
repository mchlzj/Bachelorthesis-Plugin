package anfdephd1.anfdephd1b;

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

public class DontIdentifyArrasyInReturnStmtTest {
	IJavaElement javaElement;
	
	@Before
	public void arrange() throws JavaModelException {
		this.javaElement = WorkspaceHelper.getJavaElement("anfdephd1/anfdephd1b/DontIdentifyArrasyInReturnStmt.java");
	}
	
	@Test
	public void doTest() throws JavaModelException {
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		// ACT
		
		
		// ASSERT
		assertEquals( 2, FindingResult.getFromCache().getFindings().size());
	}
	
	@After
	public void teardown() {
		FindingResultCache.resetFindings();
	}
}
