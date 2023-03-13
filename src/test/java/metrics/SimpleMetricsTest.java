package metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Analysis;
import domain.results.FindingResult;
import domain.results.FindingResultCache;
import ui.logic.Metrics;
import ui.logic.MetricsCalculator;
import util.WorkspaceHelper;

public class SimpleMetricsTest {

	IJavaElement javaElement;
	

	@Test
	public void testeOnlyHardcoded() throws JavaModelException {
		this.javaElement = this.javaElement = WorkspaceHelper.getJavaElement("metrics/OnlyHardcoded.java");
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		FindingResult result = FindingResult.getFromCache();
		MetricsCalculator calc = new MetricsCalculator(result);
		Metrics metrics = calc.getMetrics();
		// ACT
		
		
		// ASSERT
		assertEquals(1, metrics.getNOHCDVal(), 0);
		assertEquals(0, metrics.getNOIDVal(),0);
		assertEquals(0, metrics.getROIVal(),0);
		assertEquals(0, metrics.getNOMDVal(),0);
		assertEquals(0, metrics.getROMVal(),0);
		assertEquals(0, metrics.getNDVal(),0);
		assertEquals(0, metrics.getROTVal(),0);
	}
	
	@Test
	public void testeOnlyInjectable() throws JavaModelException {
		this.javaElement = this.javaElement = WorkspaceHelper.getJavaElement("metrics/OnlyInjectable.java");
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		FindingResult result = FindingResult.getFromCache();
		MetricsCalculator calc = new MetricsCalculator(result);
		Metrics metrics = calc.getMetrics();
		// ACT
		
		
		// ASSERT
		assertEquals(0, metrics.getNOHCDVal(), 0);
		assertEquals(1, metrics.getNOIDVal(),0);
		assertEquals(1, metrics.getROIVal(),0);
		assertEquals(0, metrics.getNOMDVal(),0);
		assertEquals(0, metrics.getROMVal(),0);
		assertEquals(0, metrics.getNDVal(),0);
		assertEquals(0, metrics.getROTVal(),0);
	}
	
	@Test
	public void testeOnlyMockable() throws JavaModelException {
		this.javaElement = this.javaElement = WorkspaceHelper.getJavaElement("metrics/OnlyMockable.java");
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		FindingResult result = FindingResult.getFromCache();
		MetricsCalculator calc = new MetricsCalculator(result);
		Metrics metrics = calc.getMetrics();
		// ACT
		
		
		// ASSERT
		assertEquals(0, metrics.getNOHCDVal(), 0);
		assertEquals(0, metrics.getNOIDVal(),0);
		assertEquals(0, metrics.getROIVal(),0);
		assertEquals(1, metrics.getNOMDVal(),0);
		assertEquals(1, metrics.getROMVal(),0);
		assertEquals(0, metrics.getNDVal(),0);
		assertEquals(0, metrics.getROTVal(),0);
	}
	
	@Test
	public void testeNoDependencies() throws JavaModelException {
		this.javaElement = this.javaElement = WorkspaceHelper.getJavaElement("metrics/NoDependencies.java");
		

		Analysis preparator = new Analysis(this.javaElement);
		preparator.init();
		FindingResult result = FindingResult.getFromCache();
		MetricsCalculator calc = new MetricsCalculator(result);
		Metrics metrics = calc.getMetrics();
		// ACT
		
		
		// ASSERT
		assertEquals(0, metrics.getNOHCDVal(), 0);
		assertEquals(0, metrics.getNOIDVal(),0);
		assertEquals(0, metrics.getROIVal(),0);
		assertEquals(0, metrics.getNOMDVal(),0);
		assertEquals(0, metrics.getROMVal(),0);
		assertEquals(1, metrics.getNDVal(),0);
		assertEquals(1, metrics.getROTVal(),0);
	}

	@After
	public void teardown() {
		FindingResultCache.resetFindings();
	}
}
