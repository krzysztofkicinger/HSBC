package com.kicinger.hsbc;

import com.kicinger.hsbc.controllers.UsersController;
import com.kicinger.hsbc.domain.Message;
import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.services.FollowingService;
import com.kicinger.hsbc.services.MessagesService;
import com.kicinger.hsbc.services.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by krzysztofkicinger on 18/05/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersApplicationEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private MessagesService messagesService;

    @MockBean
    private FollowingService followingService;

    @Before
    public void setUp() throws Exception {
        User testUser1 = User.create("testUser");
        User testUser2 = User.create("testUser2");
        List<User> users = Stream.of(testUser1, testUser2).collect(Collectors.toList());
        Message testMessage1 = Message.create("Test Message 1", testUser1);
        Message testMessage2 = Message.create("Test Message 2", testUser2);
        List<Message> messages = Stream.of(testMessage1, testMessage2).collect(Collectors.toList());
        Mockito.when(usersService.getUsers()).thenReturn(Stream.of(testUser1, testUser2).collect(Collectors.toList()));
        Mockito.when(usersService.getUser(Matchers.anyString())).thenReturn(Optional.of(testUser1));
        Mockito.when(messagesService.getUsersTimeline(Matchers.anyString())).thenReturn(messages);
        Mockito.when(messagesService.getUsersPostedMessages(Matchers.anyString())).thenReturn(messages);
        Mockito.when(followingService.getFollowees(Matchers.anyString())).thenReturn(users);
        Mockito.when(followingService.getFollowers(Matchers.anyString())).thenReturn(users);
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetUserByNick() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/Nick"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetUsersWall() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{nick}/wall", "Nick"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetUsersTimeline() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{nick}/timeline", "Nick"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetUsersFollowers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{nick}/followers", "Nick"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetUsersFollowees() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/Nick/followees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testFollowEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/{nick}/followees/{followeeNick}", "Nick 1", "Nick 2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUnfollowEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{nick}/followees/{followeeNick}", "Nick 1", "Nick 2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
