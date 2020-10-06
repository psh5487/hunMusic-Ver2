package com.music.hun.service.userActivity;

import com.music.hun.model.userActivity.Likes;
import com.music.hun.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    public Likes likeOrUnlike(Long userId, Long musicId) {

        Optional<Likes> likes = likesRepository.findByUserIdAndMusicId(userId, musicId);

        if(likes.isPresent()) { // Unlike 로 처리
            likesRepository.delete(likes.get());
            return null;
        }

        // Like 로 처리
        Likes newLikes = Likes.builder()
                .userId(userId)
                .musicId(musicId)
                .createdAt(LocalDateTime.now())
                .build();

        return likesRepository.save(newLikes);
    }
}
