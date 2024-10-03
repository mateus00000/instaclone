package com.instagram.clone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.clone.model.Like;
import com.instagram.clone.model.Post;
import com.instagram.clone.model.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);
    void deleteByPostId(Long postId);
    void deleteByUser(User user);
    void deleteByPost(Post post);
    List<Like> findByPostId(Long postId);
}