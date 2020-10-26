package com.music.controller.userActivity;

import com.music.model.user.User;
import com.music.model.userActivity.Comment;
import com.music.service.userActivity.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    /* 해당 음반에 댓글 남기기 */
    @PostMapping("/comment/{musicId}")
    public ResponseEntity<?> writeComment(@AuthenticationPrincipal User user,
                                       @PathVariable Long musicId,
                                       @RequestBody Map<String, String> content) throws URISyntaxException {
        if (user == null) {
            return new ResponseEntity<>("Not Logged User", HttpStatus.UNAUTHORIZED);
        }

        Comment comment = commentService.saveComment(user.getId(), musicId, content.get("content"));
        String url = "/api/auth/comment/" + musicId;

        return ResponseEntity.created(new URI(url)).body(comment);
    }

    /* 해당 음반의 댓글 가져오기 */
    @GetMapping("/comment/{musicId}")
    public ResponseEntity<?> getComment(@AuthenticationPrincipal User user, @PathVariable Long musicId) {

        if (user == null) {
            return new ResponseEntity<>("Not Logged User", HttpStatus.UNAUTHORIZED);
        }

        List<Comment> commentList = commentService.getComment(musicId);
        return ResponseEntity.ok(commentList);
    }

}
