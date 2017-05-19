package com.kicinger.hsbc.services;

import com.kicinger.hsbc.domain.Message;
import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Service
public class MessagesServiceImpl implements MessagesService {

    private final UsersService usersService;
    private final FollowingService followingService;
    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesServiceImpl(UsersService usersService, FollowingService followingService, MessagesRepository messagesRepository) {
        this.usersService = usersService;
        this.followingService = followingService;
        this.messagesRepository = messagesRepository;
    }

    @Override
    @Transactional
    public Message createMessage(String nick, String content) {
        User author = createUserIfNecessaryAndGet(nick);
        return messagesRepository.save(Message.create(content, author));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getUsersPostedMessages(String nick) {
        Optional<User> user = usersService.getUser(nick);
        return user.map(messagesRepository::findAllByAuthor).orElseGet(Collections::emptyList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getUsersTimeline(String nick) {
        List<User> followees = followingService.getFollowees(nick);
        Predicate<List<User>> emptyListPredicate = List::isEmpty;
        return Optional.of(followees)
                .filter(emptyListPredicate.negate())
                .map(messagesRepository::findTimelineMessages)
                .orElseGet(Collections::emptyList);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private User createUserIfNecessaryAndGet(String nick) {
        Optional<User> user = usersService.getUser(nick);
        return user.orElseGet(() -> usersService.createUser(nick));
    }

}
