package com.music.hun.service.music;

import com.music.hun.model.music.Music;
import com.music.hun.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MusicChooseService {

    @Autowired
    private MusicRepository musicRepository;

    /* 한 주의 선곡표 만들기 */
    public List<Music> chooseThisWeekMusics() {

        List<Music> allMusics = musicRepository.findAll();
        int listLength = allMusics.size();

        // 음반 list 가 충분하지 않을 때
        if (listLength < 15) {
            return null;
        }

        // 랜덤하게 15개 뽑기
        int[] randomIdxArr = new int[15];
        Random r = new Random();

        for(int i = 0; i < 15; i++) {
            randomIdxArr[i] = r.nextInt(listLength);

            for(int j = 0; j < i; j++) {
                if(randomIdxArr[i] == randomIdxArr[j]) {
                    i--;
                }
            }
        }

        // 뽑힌 음악 넣기
        List<Music> thisWeekMusics = new ArrayList<>();

        for(int idx : randomIdxArr) {
            thisWeekMusics.add(allMusics.get(idx));
        }
        return thisWeekMusics;
    }
}
