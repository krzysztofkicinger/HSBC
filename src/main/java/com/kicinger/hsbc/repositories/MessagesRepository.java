package com.kicinger.hsbc.repositories;

import com.kicinger.hsbc.domain.Message;
import com.kicinger.hsbc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByAuthor(User author);

    @Query("SELECT m FROM Message m WHERE m.author IN :followees")
    List<Message> findTimelineMessages(@Param("followees") List<User> followees);

}
