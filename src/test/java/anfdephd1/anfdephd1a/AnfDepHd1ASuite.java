package anfdephd1.anfdephd1a;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import anfdephd1.anfdephd1b.DontIdentifyNewKeywordInReturnStmtTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DontIdentifyAssignmentsFromMemberVariableMethodCallsTest.class,
	DontIdentifyAssignmentsFromObjectMethodsTest.class,
	DontIdentifyLiteralsInOtherNewKeywordTest.class,
	DontIdentifyNewKeywordInReturnStmtTest.class,
	DontIdentifyRecursiveMethodUseTest.class,
	DontIdentifyValuesInOtherConstructorTest.class
})
public class AnfDepHd1ASuite {

}
