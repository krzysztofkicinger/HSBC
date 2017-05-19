package com.kicinger.hsbc.services;

import com.kicinger.hsbc.domain.Following;
import com.kicinger.hsbc.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
public interface FollowingService {

    Optional<Following> follow(String followeeNick, String followerNick);

    void unfollow(String follooweeNick, String followerNick);

    List<User> getFollowers(String nick);

    List<User> getFollowees(String nick);

}
