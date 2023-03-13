package anfdephd1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import anfdephd1.anfdephd1a.AnfDepHd1ASuite;
import anfdephd1.anfdephd1b.AnfDepHd1BSuite;
import anfdephd1.anfdephd1c.AnfDepHd1CSuite;
import anfdephd1.anfdephd1d.AnfDepHd1DSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
DontIdentifyMultipleAssignmentsWithNoValueTest.class,
DontIdentifyNullValues.class,
IdentifyArraysTest.class,
IdentifyAssignmentsAndVariableDeclarationFragmentsTest.class,
IdentifyAssignmentsTest.class,
IdentifyListsTest.class,
IdentifyLiteralsTest.class,
IdentifyMultipleAssignmentsTest.class,
IdentifyObjectsTest.class,
IdentifyObjectsWithThisKeywordTest.class,
IdentifyPrimitivesTest.class,
IdentifyStaticMethodsTest.class,
IdentifyWrapperAndPrimitivesTest.class,
AnfDepHd1ASuite.class,
AnfDepHd1BSuite.class,
AnfDepHd1CSuite.class,
AnfDepHd1DSuite.class,
})
public class AnfDepHd1Suite {

}
