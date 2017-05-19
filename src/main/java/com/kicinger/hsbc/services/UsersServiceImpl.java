package com.kicinger.hsbc.services;

import com.kicinger.hsbc.domain.User;
import com.kicinger.hsbc.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public User createUser(String nick) {
        return usersRepository.save(User.create(nick));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(String nick) {
        return Optional.ofNullable(usersRepository.findUserByNick(nick));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return usersRepository.findAll();
    }


}
