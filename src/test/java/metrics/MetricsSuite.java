package metrics;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
ComplexMetrics.class,
RootPackageTest.class,
SimpleMetricsTest.class
})
public class MetricsSuite {

}
