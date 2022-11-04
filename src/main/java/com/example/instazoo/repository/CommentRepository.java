package com.example.instazoo.repository;

import com.example.instazoo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Long postId);

    Optional<Comment> findAllByUserId(Long userId);
}
