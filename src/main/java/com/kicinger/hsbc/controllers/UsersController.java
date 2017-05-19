package com.kicinger.hsbc.controllers;

import com.kicinger.hsbc.domain.Message;
import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.services.FollowingService;
import com.kicinger.hsbc.services.MessagesService;
import com.kicinger.hsbc.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Api
@RestController
@RequestMapping(path = "users")
public class UsersController {

    private final UsersService usersService;
    private final MessagesService messagesService;
    private final FollowingService followingService;

    @Autowired
    public UsersController(UsersService usersService, MessagesService messagesService, FollowingService followingService) {
        this.usersService = usersService;
        this.messagesService = messagesService;
        this.followingService = followingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(usersService.getUsers());
    }

    @RequestMapping(path = "{nick}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String nick) {
        return usersService.getUser(nick)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @ApiOperation(
            value = "Receiving a set of wall's messages",
            notes = "Response contains messages posted by the user."
    )
    @RequestMapping(path = "{nick}/wall", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getUsersWall(@PathVariable String nick) {
        return ResponseEntity.ok(messagesService.getUsersPostedMessages(nick));
    }


    @ApiOperation(
            value = "Posting a message",
            notes = "The user may be created when posting a message if matching user cannot be found. The message cannot be longer than 140 characters."
    )
    @RequestMapping(path = "{nick}/wall", method = RequestMethod.POST)
    public ResponseEntity<Message> createMessage(@PathVariable String nick, @RequestBody String message) {
        final String trimmedMessage = message.trim();
        if(trimmedMessage.length() > 140) {
            throw new IllegalArgumentException("The message cannot be longer than 140 characters");
        }
        return ResponseEntity.ok(messagesService.createMessage(nick, trimmedMessage));
    }

    @ApiOperation(
            value = "Receiving a set of messages from timeline",
            notes = "Response contains messages that were posted by followees of particular user only. Messages created by the user are not included within the response."
    )
    @RequestMapping(path = "{nick}/timeline", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getUsersTimeline(@PathVariable String nick) {
        return ResponseEntity.ok(messagesService.getUsersTimeline(nick));
    }

    @RequestMapping(path = "{nick}/followers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersFollowers(@PathVariable String nick) {
        return ResponseEntity.ok(followingService.getFollowers(nick));
    }

    @RequestMapping(path = "{nick}/followees", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersFollowees(@PathVariable String nick) {
        return ResponseEntity.ok(followingService.getFollowees(nick));
    }

    @RequestMapping(path = "{followerNick}/followees/{followeeNick}", method = RequestMethod.POST)
    public void followUser(@PathVariable String followerNick, @PathVariable String followeeNick) {
        followingService.follow(followerNick, followeeNick);
    }

    @RequestMapping(path = "{followerNick}/followees/{followeeNick}", method = RequestMethod.DELETE)
    public void unfollowUser(@PathVariable String followerNick, @PathVariable String followeeNick) {
        followingService.unfollow(followerNick, followeeNick);
    }


}
