package com.music.hun.repository;

import com.music.hun.model.music.Music;
import com.music.hun.model.userActivity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
}
