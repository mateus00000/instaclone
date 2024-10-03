package com.instagram.clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instagram.clone.model.Post;
import com.instagram.clone.model.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
        // Exemplo de busca de postagens por usuário
        List<Post> findAllByUserId(Long userId);

        @Query("SELECT p FROM Post p WHERE p.id IN :userIds order by p.createdAt desc")
        List<Post> findAllByUserIdIn(@Param("userIds") List<Long> userIds);

        @Query("SELECT p, COUNT(DISTINCT l.id) AS likeCount, COUNT(DISTINCT c.id) AS commentCount " +
                        "FROM Post p LEFT JOIN p.likes l LEFT JOIN p.comments c " +
                        "WHERE p.user.id IN :userIds " +
                        "GROUP BY p.id " +
                        "ORDER BY p.createdAt DESC")
        List<Object[]> findAllWithLikesAndCommentsByUserIdIn(@Param("userIds") List<Long> userIds);

        @Query("SELECT p, COUNT(l), COUNT(c) " +
                        "FROM Post p " +
                        "LEFT JOIN Like l ON l.post.id = p.id " +
                        "LEFT JOIN Comment c ON c.post.id = p.id " +
                        "WHERE p.user.id = :userId " +
                        "GROUP BY p.id " +
                        "ORDER BY p.createdAt DESC")
        List<Object[]> findAllByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);

        void deleteByUser(User user);

}