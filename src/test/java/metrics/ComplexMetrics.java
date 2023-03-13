package metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.After;
import org.junit.Test;

import domain.Analysis;
import domain.results.FindingResult;
import domain.results.FindingResultCache;
import ui.logic.Metrics;
import ui.logic.MetricsCalculator;
import util.WorkspaceHelper;

public class ComplexMetrics {
	
	IJavaElement javaElement;
	
	@Test
	public void testeInjectable033() throws JavaModelException {
		this.javaElement = this.javaElement = WorkspaceHelper.getJavaElement("metrics/complex/ROI033.java");
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		FindingResult result = FindingResult.getFromCache();
		MetricsCalculator calc = new MetricsCalculator(result);
		Metrics metrics = calc.getMetrics();
		// ACT
		
		
		// ASSERT
		assertEquals(2, metrics.getNOHCDVal(), 0);
		assertEquals(1, metrics.getNOIDVal(),0);
		assertEquals(0.33, metrics.getROIVal(),0.01);
		assertEquals(0, metrics.getNOMDVal(),0);
		assertEquals(0, metrics.getROMVal(),0);
		assertEquals(0, metrics.getNDVal(),0);
		assertEquals(0, metrics.getROTVal(),0);
	}
	
	@Test
	public void testeMockable033() throws JavaModelException {
		this.javaElement = this.javaElement = WorkspaceHelper.getJavaElement("metrics/complex/ROM033.java");
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		FindingResult result = FindingResult.getFromCache();
		MetricsCalculator calc = new MetricsCalculator(result);
		Metrics metrics = calc.getMetrics();
		// ACT
		
		
		// ASSERT
		assertEquals(1, metrics.getNOHCDVal(), 0);
		assertEquals(1, metrics.getNOIDVal(),0);
		assertEquals(0.66, metrics.getROIVal(),0.01);
		assertEquals(1, metrics.getNOMDVal(),0);
		assertEquals(0.33, metrics.getROMVal(),0.01);
		assertEquals(0, metrics.getNDVal(),0);
		assertEquals(0, metrics.getROTVal(),0);
	}
	
	@Test
	public void testeTestable025() throws JavaModelException {
		this.javaElement = this.javaElement = WorkspaceHelper.getJavaElement("metrics/complex/ROT025.java");
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		FindingResult result = FindingResult.getFromCache();
		MetricsCalculator calc = new MetricsCalculator(result);
		Metrics metrics = calc.getMetrics();
		// ACT
		
		
		// ASSERT
		assertEquals(2, metrics.getNOHCDVal(), 0);
		assertEquals(2, metrics.getNOIDVal(),0);
		assertEquals(0.75, metrics.getROIVal(),0);
		assertEquals(2, metrics.getNOMDVal(),0);
		assertEquals(0.5, metrics.getROMVal(),0.01);
		assertEquals(2, metrics.getNDVal(),0);
		assertEquals(0.25, metrics.getROTVal(),0.01);
	}

	@After
	public void teardown() {
		FindingResultCache.resetFindings();
	}
}
