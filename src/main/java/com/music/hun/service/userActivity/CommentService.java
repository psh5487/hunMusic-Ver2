package com.music.hun.service.userActivity;

import com.music.hun.model.userActivity.Comment;
import com.music.hun.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment saveComment(Long userId, Long musicId, String content) {

        Comment comment = Comment.builder()
                .userId(userId)
                .musicId(musicId)
                .content(content)
                .registeredAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }
}
