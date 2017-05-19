package com.kicinger.hsbc.services;

import com.kicinger.hsbc.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
public interface UsersService {

    User createUser(String nick);

    Optional<User> getUser(String nick);

    List<User> getUsers();


}
