package anfdepnd1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import anfdepnd1a.AnfDepNd1ASuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
IdentifyAlsoOneMockableBecauseTypeParamTest.class,
IdentifyOnlyNoDependenciesTest.class,
AnfDepNd1ASuite.class
})
public class AnfDepNd1Suite {

}
