package anfdephd2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
IdentifyGlobalCallsFromClazzTest.class,
IdentifyGlobalValueCallsOrAssignmentsTest.class
})
public class AnfDepHd2Suite {

}
