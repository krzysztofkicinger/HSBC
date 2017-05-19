package com.kicinger.hsbc;

import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.services.UsersService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by krzysztofkicinger on 18/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceIntegrationTest {

    @Autowired
    private UsersService usersService;

    @Test
    @Transactional
    public void testUsersCreation() {
        String nick = "Test User 1";
        User user = usersService.createUser(nick);
        assertThat(user)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("nick", nick);
    }

    @Test
    @Transactional
    public void testGetUsers() {
        String nick1 = "Test User 1";
        String nick2 = "Test User 2";
        String nick3 = "Test User 3";
        User user1 = usersService.createUser(nick1);
        User user2 = usersService.createUser(nick2);
        User user3 = usersService.createUser(nick3);
        List<User> users = usersService.getUsers();
        assertThat(users)
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(user1, user2, user3);

    }

    @Test(expected = Exception.class)
    @Transactional
    public void testUniqueNickConstraint() {
        final String nick = "Test User 1";
        usersService.createUser(nick);
        usersService.createUser(nick);
    }

    @Test
    @Transactional
    public void testGetUserByNick() {
        String nick = "Test User 1";
        usersService.createUser(nick);
        User user = usersService.getUser(nick).orElse(null);
        assertThat(user)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("nick", nick);
    }

    @Test
    public void testGetNonExistingUser() {
        User user = usersService.getUser("Non existing user's nick").orElse(null);
        assertThat(user)
                .isNull();
    }

}
