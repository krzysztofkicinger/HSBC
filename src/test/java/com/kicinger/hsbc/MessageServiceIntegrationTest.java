package com.kicinger.hsbc;

import com.kicinger.hsbc.domain.Message;
import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.services.FollowingService;
import com.kicinger.hsbc.services.MessagesService;
import com.kicinger.hsbc.services.UsersService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by krzysztofkicinger on 18/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceIntegrationTest {

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private FollowingService followingService;

    @Test
    @Transactional
    public void testMessageCreation() {
        final String usersNick = "Test User";
        final String messageContent = "This is test message";
        Message message = messagesService.createMessage(usersNick, messageContent);
        User createdUser = usersService.getUser(usersNick).orElse(null);
        assertThat(createdUser)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("nick", usersNick);
        assertThat(message)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("content", messageContent)
                .hasFieldOrPropertyWithValue("author", createdUser);
    }

    @Test
    @Transactional
    public void testUserPostedMessages() {
        final String usersNick = "Test User";
        final String firstMessageContent = "This is first test message";
        final String secondMessageContent = "This is second test message";
        Message firstMessage = messagesService.createMessage(usersNick, firstMessageContent);
        Message secondMessage = messagesService.createMessage(usersNick, secondMessageContent);
        User createdUser = usersService.getUser(usersNick).orElse(null);

        List<Message> messages = messagesService.getUsersPostedMessages(usersNick);
        assertThat(messages)
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(firstMessage, secondMessage);

        List<User> users = usersService.getUsers();
        assertThat(users)
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(createdUser);
    }

    @Test
    @Transactional
    public void testUserTimeline() {
        final String firstUserNick = "First User Nick";
        final String secondUserNick = "Second User Nick";
        final String firstMessageContent = "This is first test message";
        final String secondMessageContent = "This is second test message";
        Message firstMessage = messagesService.createMessage(firstUserNick, firstMessageContent);
        Message secondMessage = messagesService.createMessage(secondUserNick, secondMessageContent);
        User firstUser = usersService.getUser(firstUserNick).orElse(null);
        User secondUser = usersService.getUser(secondUserNick).orElse(null);

        assertThat(firstUser).isNotNull();
        assertThat(secondUser).isNotNull();

        followingService.follow(firstUserNick, secondUserNick);

        List<Message> messages = messagesService.getUsersTimeline(secondUserNick);
        assertThat(messages)
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(firstMessage)
                .doesNotContain(secondMessage);
    }


}
