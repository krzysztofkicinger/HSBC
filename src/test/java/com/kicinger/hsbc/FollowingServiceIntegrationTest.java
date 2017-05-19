package com.kicinger.hsbc;

import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.services.FollowingService;
import com.kicinger.hsbc.services.MessagesService;
import com.kicinger.hsbc.services.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by krzysztofkicinger on 18/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowingServiceIntegrationTest {

    @Autowired
    private UsersService usersService;

    @Autowired
    private FollowingService followingService;


    private final String firstUserNick = "First User Nick";
    private final String secondUserNick = "Second User Nick";

    @Test
    @Transactional
    public void testFollow() {
        testFollowFunctionality();
    }

    @Test
    @Transactional
    public void testUnfollow() {
        testFollowFunctionality();
        followingService.unfollow(firstUserNick, secondUserNick);
        assertThat(followingService.getFollowers(firstUserNick)).isEmpty();
        assertThat(followingService.getFollowees(secondUserNick)).isEmpty();
    }

    private void testFollowFunctionality() {
        User firstUser = usersService.createUser(firstUserNick);
        User secondUser = usersService.createUser(secondUserNick);
        followingService.follow(firstUserNick, secondUserNick);

        List<User> followers = followingService.getFollowers(firstUserNick);
        assertThat(followers)
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(secondUser);

        List<User> followees = followingService.getFollowees(secondUserNick);
        assertThat(followees)
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(firstUser);
    }


}
