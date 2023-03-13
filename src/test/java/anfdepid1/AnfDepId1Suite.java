package anfdepid1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import anfdepid1a.AnfDepId1ASuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
Identify0Injectable1HardcodedDependenciesTest.class,
IdentifyAssignmentFromInjectableDependencyMethodCallTest.class,
IdentifyAssignmentFromInjectableDependencyMethodCallTest.class,
IdentifyOnly2InjectableDependenciesTest.class,
AnfDepId1ASuite.class
})
public class AnfDepId1Suite {

}
