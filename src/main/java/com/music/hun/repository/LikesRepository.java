package com.music.hun.repository;

import com.music.hun.model.music.Likes;
import com.music.hun.model.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    // 해당 유저의 좋아요 한 음반들
    List<Likes> findByUserId(Long userId);

    // 해당 음악의 좋아요 수 카운트
    Long countByMusicId(Long musicId);
}
