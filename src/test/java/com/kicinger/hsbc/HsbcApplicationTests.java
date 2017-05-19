package com.kicinger.hsbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UsersApplicationEndpointsTest.class,
		UsersServiceIntegrationTest.class,
        MessageServiceIntegrationTest.class,
        FollowingServiceIntegrationTest.class
})
public class HsbcApplicationTests {

}
