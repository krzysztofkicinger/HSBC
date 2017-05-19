package com.kicinger.hsbc.repositories;

import com.kicinger.hsbc.domain.Following;
import com.kicinger.hsbc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    List<Following> getFollowersByFollowee(User followee);

    List<Following> getFollowersByFollower(User follower);

    void deleteDistinctByFolloweeAndFollower(User followee, User follower);

}
