package anfdephd1.anfdephd1d;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
DontIdentifyInnerMethodUseTest.class,
DontIdentifyParentMethodUseTest.class
})
public class AnfDepHd1DSuite {

}
