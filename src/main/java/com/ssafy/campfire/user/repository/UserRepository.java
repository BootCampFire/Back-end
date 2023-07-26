package com.ssafy.campfire.user.repository;


import com.ssafy.campfire.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String Email);
    Optional<User> findByNickname(String nickname);
    //Optional<User> findByLoginId(String loginId);
    Optional<User> findByRefreshToken(String refreshToken);

    User findDeatailByEmail(String email);
}
