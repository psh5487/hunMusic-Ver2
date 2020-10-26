package com.music.service.userActivity;

import com.music.model.userActivity.Comment;
import com.music.repository.CommentRepository;
import com.music.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Comment saveComment(Long userId, Long musicId, String content) {

        Comment comment = Comment.builder()
                .userId(userId)
                .musicId(musicId)
                .content(content)
                .registeredAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }


    public List<Comment> getComment(Long musicId) {
        // 사용자 정보까지 담는 결과를 만들기 위해서, N+1 문제 고민해봐야함
        return commentRepository.findAllByMusicId(musicId);
    }

}
