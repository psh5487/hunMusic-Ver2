package com.music.hun.controller.hunActivity;

import com.music.hun.model.user.User;
import com.music.hun.model.userActivity.Comment;
import com.music.hun.model.userActivity.CommentDto;
import com.music.hun.service.userActivity.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /* 인증된 사용자의 해당 음반에 댓글 남기기 */
    @PostMapping("/comment/{musicId}")
    public ResponseEntity writeComment(@AuthenticationPrincipal User user,
                                       @PathVariable Long musicId,
                                       @RequestBody Map<String, String> content) {
        if (user == null) {
            return new ResponseEntity<>("로그인된 사용자가 아닙니다.", HttpStatus.UNAUTHORIZED);
        }

        Comment comment = commentService.saveComment(user.getId(), musicId, content.get("content"));

        CommentDto commentDto = CommentDto.builder()
                .username(user.getName())
                .profileImgUrl(user.getProfileImgUrl())
                .content(comment.getContent())
                .registeredAt(comment.getRegisteredAt())
                .build();

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
}
