package com.kicinger.hsbc.repositories;

import com.kicinger.hsbc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findUserByNick(String nick);

}
