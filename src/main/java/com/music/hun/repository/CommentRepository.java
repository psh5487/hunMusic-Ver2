package com.music.hun.repository;

import com.music.hun.model.userActivity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
