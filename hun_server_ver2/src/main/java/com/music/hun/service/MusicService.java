package com.music.hun.service;

import com.music.hun.model.music.Music;
import com.music.hun.model.music.MusicRequest;
import com.music.hun.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    /* 모든 곡 찾기 */
    public List<Music> findAllMusics() {
        return musicRepository.findAll();
    }

    /* 전체 곡 수 카운트 */
    public long countAllMusics() {
        return musicRepository.count();
    }

    /* 해당 페이지로부터 10개 곡 찾기 */
    public List<Music> findMusicsWithSize(Pageable page) {
        //    Pageable page = new PageRequest(1, 20, new Sort...);
        //    //전체 수
        //    int total = result.getTotalElements();
        Page<Music> musicPage = musicRepository.findAll(page);
        List<Music> musics = musicPage.getContent();
        return musics;
    }

    /* barcode 에 해당 하는 곡 찾기 */
    public Music findMusicByBarcode(String barcode) {
        return musicRepository.findByBarcode(barcode);
    }

    /* 곡 삽입하기 */
    public Music insertMusic(MusicRequest musicRequest){
        // Todo : composer 기타일 경우 처리
        Music music = Music.builder()
                .barcode(musicRequest.getBarcode())
                .title(musicRequest.getTitle())
                .artist(musicRequest.getArtist())
                .composer(musicRequest.getComposer())
                .category(musicRequest.getCategory())
                .track(musicRequest.getTrack())
                .label(musicRequest.getLabel())
                .numOfDisc(musicRequest.getNumOfDisc())
                .importance(musicRequest.getImportance())
                .registeredAt(LocalDateTime.now())
                .build();

        Music newMusic = musicRepository.save(music);
        return newMusic;
    }

    /* 곡 업데이트 */
    public Music updateMusic(MusicRequest musicRequest) {
        Music music = musicRepository.findByBarcode(musicRequest.getBarcode());
        music.setTitle(musicRequest.getTitle())
             .setArtist(musicRequest.getArtist())
             .setComposer(musicRequest.getComposer())
             .setCategory(musicRequest.getCategory())
             .setTrack(musicRequest.getTrack())
             .setLabel(musicRequest.getLabel())
             .setNumOfDisc(musicRequest.getNumOfDisc())
             .setImportance(musicRequest.getImportance());

        Music newMusic = musicRepository.save(music);
        return newMusic;
    }

    /* 곡 삭제 */
    public void delete(String barcode) {
        Music music = musicRepository.findByBarcode(barcode);
        musicRepository.delete(music);
    }

    /* 특정 keyword 를 포함하는 곡 찾기 */
    public List<Music> findAllMusicsWithSearch(String searchWord) {
        return musicRepository.findAllIgnoreCaseContaining(searchWord);
    }

}
