package com.music.hun.service;

import com.music.hun.model.music.Music;
import com.music.hun.model.music.MusicCrawlingRequest;
import com.music.hun.model.music.MusicRequest;
import com.music.hun.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicCrawlingService musicCrawlingService;

    /* 모든 음반 찾기 */
    public List<Music> findAllMusics() {
        return musicRepository.findAll();
    }

    /* barcode 에 해당 하는 음반 찾기 */
    public Music findMusicByBarcode(String barcode) {
        return musicRepository.findByBarcode(barcode);
    }

    /* 전체 음반 수 카운트 */
    public long countAllMusics() {
        return musicRepository.count();
    }

    /* 해당 페이지로부터 10개 음반 찾기 */
    public List<Music> findMusicsWithSize(Pageable page) {
        Page<Music> musicPage = musicRepository.findAll(page);
        List<Music> musics = musicPage.getContent();
        return musics;
    }

    /* Pagination 관련 정보 계산 */
    public Map<String, Integer> paginationInfo(Pageable page) {
        Map<String, Integer> pageInfoMap = new HashMap<>();

        // 전체 음반 수
        int count = (int)musicRepository.count();

        // 전체 페이지수
        int pageCount = count / 10;

        if(count % 10 > 0)
            pageCount++;

        // 현재 페이지
        int cur = page.getPageNumber() + 1;

        // pagination 변수
        int pageRange = 3;

        // 몇 번째 블락인지
        int block = cur/pageRange;

        if(cur % pageRange == 0)
            block -= 1;

        //블락의 begin 값 구하기
        int begin = block * pageRange + 1;

        //마지막 블락이 몇 번째 블락인지
        int lastBlock = pageCount/pageRange;

        if(pageCount % pageRange == 0)
            lastBlock -= 1;

        //마지막 블락의 begin 값
        int lastBegin = lastBlock * pageRange + 1;

        pageInfoMap.put("pageRange", pageRange);
        pageInfoMap.put("pageCount", pageCount);
        pageInfoMap.put("cur", cur);
        pageInfoMap.put("begin", begin);
        pageInfoMap.put("lastBegin", lastBegin);
        pageInfoMap.put("count", count);

        return pageInfoMap;
    }

    /* 음반 직접 추가 하기 */
    public int insertMusic(MusicRequest musicRequest, String requestType) {
        // 중복 체크
        if (musicRepository.findByBarcode(musicRequest.getBarcode()) != null) {
            return -1;
        }

        // 필수 값 체크
        if (musicRequest.getBarcode().equals("")
                || musicRequest.getTitle().equals("")
                || musicRequest.getCategory() == null) {
            return -2;
        }

        // 음반 정보 가공
        Music music = musicInfoProcessing(musicRequest, requestType);

        musicRepository.save(music);
        return 0;
    }

    /* 음반 간편 추가 하기 */
    public int insetMusicSimply(MusicCrawlingRequest musicCrawlingRequest) throws IOException {
        // 중복 체크
        if (musicRepository.findByBarcode(musicCrawlingRequest.getBarcode()) != null) {
            return -1;
        }

        Map<String, Object> crawlingList = musicCrawlingService.crawling(musicCrawlingRequest);
        MusicRequest crawlingResult = (MusicRequest)crawlingList.get("crawlingResult");
        String crawlingSite = (String)crawlingList.get("crawlingSite");
        String requestType = crawlingSite.equals("Amazon") ? "insertWithAmazon" : "insertWithHottracks";

        // 크롤링 실패 : 판매하지 않는 음반일 경우
        if (crawlingResult == null) {
            return -3;
        }

        // 크롤링 실패 : 필수 값 못 받아옴
        if (crawlingResult.getBarcode().equals("")
                || crawlingResult.getTitle().equals("")
                || crawlingResult.getCategory() == null) {
            return -2;
        }

        // 음반 정보 가공
        Music music = musicInfoProcessing(crawlingResult, requestType);

        musicRepository.save(music);
        return 0;
    }

    /* 음반 업데이트 */
    public int updateMusic(MusicRequest musicRequest) {
        // 필수 값 체크
        if (musicRequest.getBarcode().equals("")
                || musicRequest.getTitle().equals("")
                || musicRequest.getCategory() == null) {
            return -2;
        }

        // 업데이트 할 음반 찾기
        Music music = musicRepository.findByBarcode(musicRequest.getBarcode());

        // 음반 정보 가공
        Music newMusic = musicInfoProcessing(musicRequest, "update");

        music.setTitle(newMusic.getTitle())
             .setArtist(newMusic.getArtist())
             .setComposer(newMusic.getComposer())
             .setCategory(newMusic.getCategory())
             .setTrack(newMusic.getTrack())
             .setLabel(newMusic.getLabel())
             .setNumOfDisc(newMusic.getNumOfDisc())
             .setImportance(newMusic.getImportance());

        musicRepository.save(music);
        return 0;
    }

    /* 음반 삭제 */
    public int deleteMusic(String barcode) {
        Music music = musicRepository.findByBarcode(barcode);

        if (music == null) {
            return -3;
        }

        musicRepository.delete(music);
        return 0;
    }

    // musicRequest 으로부터 음반 정보 가공
    private Music musicInfoProcessing(MusicRequest musicRequest, String requestType) {
        // 디스크 수
        String numOfDisc = musicRequest.getNumOfDisc().equals("") ? "1" : musicRequest.getNumOfDisc();

        // artist 값 처리
        String artist = musicRequest.getArtist();

        if (!requestType.equals("insertWithHottracks") && !requestType.equals("insertWithAmazon") && !artist.equals("")) {
            // artist 형식 맞추기
            artist = artist.replace("-", " ");

            // artist 첫글자만 대문자 처리
            artist = nameProcessing(artist);
        }

        // composer 값 처리
        String composer = "";
        String composerFromSelect = musicRequest.getComposerFromSelect();
        String composerFromInput = musicRequest.getComposerFromInput();

        if (requestType.equals("insert") || requestType.equals("insertWithHottracks")) {         // insert 요청일 경우, composer 값 처리
            if (composerFromSelect == null || composerFromSelect.equals("기타")) { // composer 가 select 되지 않았거나, '기타'일 경우
                if (composerFromInput.equals("")) { // composer 가 직접 입력 되지도 않았을 경우
                    composer = "기타";
                } else {
                    composer = nameProcessing(composerFromInput);
                }
            } else {
                composer = composerFromSelect;
            }
        } else if (requestType.equals("update")) {  // update 요청일 경우, composer 값 처리
            if (composerFromInput.equals("")) {     // composer 가 입력되지 않았을 경우
                composer = "기타";
            } else {
                composer = nameProcessing(composerFromInput);
            }
        } else if (requestType.equals("insertWithAmazon")) { // 아마존 크롤링 과정 거친 뒤의 insert 요청일 경우, composer 값 처리
            if (composerFromInput.equals("")) {              // composer 가 입력되지 않았을 경우
                composer = "기타";
            } else {
                composer = composerFromInput;
            }
        }

        // 카테고리 값 처리
        String category = musicRequest.getCategory() == null ? "기타" : musicRequest.getCategory();

        // 선호도 값 처리
        int importance = musicRequest.getImportance() == null ? 2 : Integer.parseInt(musicRequest.getImportance());

        Music music = Music.builder()
                .barcode(musicRequest.getBarcode())
                .title(musicRequest.getTitle())
                .artist(artist)
                .composer(composer)
                .category(category)
                .track(musicRequest.getTrack())
                .label(musicRequest.getLabel())
                .numOfDisc(numOfDisc)
                .importance(importance)
                .registeredAt(LocalDateTime.now())
                .build();
        return music;
    }

    // name 관련 입력 값 처리
    private String nameProcessing(String name) {
        // 직접 입력된 값의 첫 글자만 대문자 처리 ex) Chopin, Bach
        String[] arr = name.trim().split(" ");
        StringBuffer sb = new StringBuffer();

        for (String word : arr) {
            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1).toLowerCase())
              .append(" ");
        }

        // 여러 명일 경우, ', '로 구분 되어 있는 것 나누기
        name = sb.toString().trim().replace(", ", "\n");
        return name;
    }

    /* 특정 keyword 를 포함하는 곡 찾기 */
    public List<Music> findAllMusicsWithSearch(String searchWord) {
//        return musicRepository.findAllIgnoreCaseContaining(searchWord);
        return null;
    }

    /* 특정 keyword 를 포함하는 전체 곡 수 */
    public int countMusicsWithSearch(String searchWord) {
        return musicRepository.countIncludingKeyword(searchWord);
    }

}
