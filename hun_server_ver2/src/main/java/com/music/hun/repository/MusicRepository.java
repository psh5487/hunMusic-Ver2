package com.music.hun.repository;

import com.music.hun.model.music.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, String> {
    /* Common */
    // 모든 곡 찾기
    List<Music> findAll();

    // 해당 페이지로부터 10개 곡 찾기
    Page<Music> findAll(Pageable pageable);

    // barcode 에 해당 하는 곡 찾기
    Music findByBarcode(String barcode);

    // 전체 곡 수 카운트
    long count();

    /* 검색 */
    // 특정 keyword 를 포함하는 곡 찾기
    List<Music> findAllIgnoreCaseContaining(String searchWord);

    // 특정 keyword 를 포함하는 전체 곡 수

    /* 선곡표 만들기 */
    // 뽑힌 id 에 해당하는 곡 정보 가져오기

}
