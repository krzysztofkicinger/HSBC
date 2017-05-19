package com.kicinger.hsbc.services;

import com.kicinger.hsbc.domain.Following;
import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.repositories.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Service
public class FollowingServiceImpl implements FollowingService {

    private final UsersService usersService;
    private final FollowingRepository followingRepository;

    @Autowired
    public FollowingServiceImpl(UsersService usersService, FollowingRepository followingRepository) {
        this.usersService = usersService;
        this.followingRepository = followingRepository;
    }

    @Override
    @Transactional
    public Optional<Following> follow(String followeeNick, String followerNick) {
        Optional<User> followee = usersService.getUser(followeeNick);
        Optional<User> follower = usersService.getUser(followerNick);
        if(follower.isPresent() && followee.isPresent()) {
            return Optional.of(followingRepository.save(Following.create(followee.get(), follower.get())));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void unfollow(String followeeNick, String followerNick) {
        Optional<User> followee = usersService.getUser(followeeNick);
        Optional<User> follower = usersService.getUser(followerNick);
        if(follower.isPresent() && followee.isPresent()) {
            followingRepository.deleteDistinctByFolloweeAndFollower(followee.get(), follower.get());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getFollowers(String nick) {
        return usersService.getUser(nick)
                .map(this::getUserFollowers)
                .orElseGet(Collections::emptyList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getFollowees(String nick) {
        return usersService.getUser(nick)
                .map(this::getUserFollowees)
                .orElseGet(Collections::emptyList);
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    private List<User> getUserFollowers(User followee) {
        return getFollowersOrFollowees(followingRepository.getFollowersByFollowee(followee), Following::getFollower);
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    private List<User> getUserFollowees(User follower) {
        return getFollowersOrFollowees(followingRepository.getFollowersByFollower(follower), Following::getFollowee);
    }

    private List<User> getFollowersOrFollowees(List<Following> followers, Function<Following, User> mapperFunction) {
        return followers
                .stream()
                .distinct()
                .map(mapperFunction)
                .collect(Collectors.toList());
    }

}
