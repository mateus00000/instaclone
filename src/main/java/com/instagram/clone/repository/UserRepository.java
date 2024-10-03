package com.instagram.clone.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.instagram.clone.model.User;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @SuppressWarnings("null")
    Optional<User> findById(Long id);

    List<User> findByUsernameStartingWithIgnoreCase(String username);

    @Query("SELECT COUNT(f) FROM User u JOIN u.followers f WHERE u.username = :username")
    Long countFollowersByUsername(String username);

    @Query("SELECT COUNT(f) FROM User u JOIN u.following f WHERE u.username = :username")
    Long countFollowingByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_followers (user_id, follower_id) VALUES (:userId, :followerId)", nativeQuery = true)
    void followUser(Long userId, Long followerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_followers WHERE user_id = :userId AND follower_id = :followerId", nativeQuery = true)
    void unfollowUser(Long userId, Long followerId);

    @Query("SELECT COUNT(f) > 0 FROM User u JOIN u.following f WHERE u.id = :userId AND f.id = :targetId")
    boolean isFollowing(Long userId, Long targetId);

    @Query(value = "SELECT user_id FROM user_followers WHERE follower_id = :userId", nativeQuery = true)
    List<Long> findUserIdsFollowedBy(Long userId);

    boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comments WHERE user_id = :userId", nativeQuery = true)
    void deleteCommentsByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM likes WHERE user_id = :userId", nativeQuery = true)
    void deleteLikesByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_id IN (SELECT id FROM posts WHERE user_id = :userId)", nativeQuery = true)
    void deleteLikesByUserPosts(Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM comments WHERE post_id IN (SELECT id FROM posts WHERE user_id = :userId)", nativeQuery = true)
    void deleteCommentsByUserPosts(Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM posts WHERE user_id = :userId", nativeQuery = true)
    void deletePostsByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_followers WHERE user_id = :userId OR follower_id = :userId", nativeQuery = true)
    void deleteAllFollowsByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users WHERE id = :userId", nativeQuery = true)
    void deleteUserById(Long userId);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.followers f WHERE f.username = :username")
    List<User> findFollowersByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.following f WHERE f.username = :username")
    List<User> findFollowingByUsername(String username);

    @Query("SELECT u FROM User u ORDER BY SIZE(u.followers) DESC")
    List<User> findTopUsers(Pageable pageable);
}
