package alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import anfdephd1.AnfDepHd1Suite;
import anfdephd2.AnfDepHd2Suite;
import anfdepid1.AnfDepId1Suite;
import anfdepmd1.AnfDepMd1Suite;
import anfdepnd1.AnfDepNd1Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
AnfDepHd1Suite.class,
AnfDepHd2Suite.class,
AnfDepId1Suite.class,
AnfDepMd1Suite.class,
AnfDepNd1Suite.class
})
public class AllTestsSuite {

}
