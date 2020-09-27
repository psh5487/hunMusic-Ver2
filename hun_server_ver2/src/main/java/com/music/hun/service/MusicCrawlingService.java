package com.music.hun.service;

import com.music.hun.model.music.Music;
import com.music.hun.model.music.MusicCrawlingRequest;
import com.music.hun.repository.MusicRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MusicCrawlingService {
    @Autowired
    private MusicRepository musicRepository;

    private int crawlingHottracks(MusicCrawlingRequest musicCrawlingRequest) throws IOException {
        String barcode = musicCrawlingRequest.getBarcode();

        // 핫트랙스 페이지 URL
        String url = "https://www.hottracks.co.kr/ht/record/detail/" + barcode;

        // 링크 대상 페이지에 접근
        Document doc = Jsoup.connect(url).userAgent("Jsoup Scraper").get();

        // 크롤링 전
        String title = "";
        String artist = "";
        String track = "";
        String label = "";
        String numOfDisc = "1";

        // 음반 title 구하기
        title = doc.select(".tit.mgt30").text();

        // 핫트랙스에 해당 음반일 없을 경우, 아마존 크롤링
        if(title.equals("")) {
            crawlingAmazon(musicCrawlingRequest);
        }
        else {
            /* artist, composer, track, label, numOfDisc 값 추출하여 저장 */
            // artist, label
            Elements NodeList = doc.select(".cover li");

            for (Element value : NodeList) {
                String innerText = value.text();

                if (innerText.contains("연주자 :")) {
                    artist = innerText.substring(6).replace("-", " ");

                    //artist 첫글자만 대문자 처리
                    String[] arr = artist.trim().split(" ");
                    StringBuffer sb = new StringBuffer();

                    for (String s : arr) {
                        sb.append(Character.toUpperCase(s.charAt(0)))
                                .append(s.substring(1).toLowerCase())
                                .append(" ");
                    }

                    artist = sb.toString().trim().replace(", ", "\n");
                    artist += "\n";
                } else if (innerText.contains("지휘자 :")) {
                    String conductor = innerText.substring(6).replace("-", " ");

                    //conductor 첫글자만 대문자 처리
                    String[] arr = conductor.trim().split(" ");
                    StringBuffer sb = new StringBuffer();

                    for (int j = 0; j < arr.length; j++) {
                        sb.append(Character.toUpperCase(arr[j].charAt(0)))
                                .append(arr[j].substring(1).toLowerCase())
                                .append(" ");
                    }

                    conductor = sb.toString().trim().replace(", ", "\n");

                    //artist에 붙여주기
                    artist += conductor;
                    artist += "\n";
                } else if (innerText.contains("오케스트라 :")) {
                    String orchestra = innerText.substring(8).replace("-", " ");

                    //orchestra 첫글자만 대문자 처리
                    String[] arr = orchestra.trim().split(" ");
                    StringBuffer sb = new StringBuffer();

                    for (int j = 0; j < arr.length; j++) {
                        sb.append(Character.toUpperCase(arr[j].charAt(0)))
                                .append(arr[j].substring(1).toLowerCase())
                                .append(" ");
                    }

                    orchestra = sb.toString().trim().replace(", ", "\n");

                    //artist에 붙여주기
                    artist += orchestra;
                    artist += "\n";
                } else if (innerText.contains("레이블 :")) {
                    label = innerText.substring(6);
                }
            }

            //artist 문자열에서 괄호 및 괄호 내용 제거하기
            Pattern PATTERN_BRACKET = Pattern.compile("\\([^\\(\\)]+\\)");
            Matcher matcher = PATTERN_BRACKET.matcher(artist);

            String removeTextArea;

            while(matcher.find()) {
                int startIndex = matcher.start();
                int endIndex = matcher.end();

                removeTextArea = artist.substring(startIndex, endIndex);
                artist = artist.replace(removeTextArea, "");
                matcher = PATTERN_BRACKET.matcher(artist);
            }

            //track
            Elements tracks = doc.select(".t_left");

            for(Element element: tracks) {  //Elements 길이만큼 반복된다.
                track += element.text();
                track += "\n";
            }

            //numOfDisc
            Elements NodeList2 = doc.select(".album_option li");

            String raw_numOfDisc = NodeList2.get(7).text();
            numOfDisc = raw_numOfDisc.substring(8, 9); //디스크 수 : 4 DISC | 에서 4 추출

            // save to DB
            Music music = Music.builder()
                    .barcode(barcode)
                    .title(title)
                    .artist(artist)
                    .composer(musicCrawlingRequest.getComposer())
                    .category(musicCrawlingRequest.getCategory())
                    .track(track)
                    .label(label)
                    .numOfDisc(numOfDisc)
                    .importance(musicCrawlingRequest.getImportance())
                    .registeredAt(LocalDateTime.now())
                    .build();
            save(music);
            return 0;
        }
        return 0;
    }

    private void crawlingAmazon(MusicCrawlingRequest musicCrawlingRequest) throws IOException {
        // 크롤링 전
        String title = "";
        String artist = "";
        String track = "";
        String label = "";
        String numOfDisc = "1";

        String barcode = musicCrawlingRequest.getBarcode();
        String composer = musicCrawlingRequest.getComposer();

        //아마존 페이지 url 가져오기
        String amazonUrl = "https://www.amazon.com/s?k=" + barcode;

        // 링크 대상 페이지에 접근 - GET 요청을 보내 Document 객체를 변수 doc에 저장하기
        Document amazonDoc = Jsoup.connect(amazonUrl).userAgent("Jsoup Scraper").get();

        // CSS 선택자를 사용해 링크 추출
        Elements titleLine = amazonDoc.select(".a-link-normal.a-text-normal");

        // 링크 URL 추출하기 (절대 경로)
        String nextUrl = "";
        nextUrl = titleLine.attr("abs:href");

        if(nextUrl.equals("")) { // 판매하지 않는 음반일 경우
            recordNotOnSaleMessage();
        }
        else {
            // 링크 대상 페이지에 접근하기
            Document nextDoc = Jsoup.connect(nextUrl).userAgent("Jsoup Scraper").get();

            /* title, artist, composer, track, label, numOfDisc 값 추출하여 저장하기 */
            // title
            title = nextDoc.select("#productTitle").text();

            // artist, 전처리
            Elements artists = nextDoc.select(".author > .a-link-normal");

            for(Element element: artists) {  //Elements 길이만큼 반복된다.
                String artistRaw = element.text();

                if(artistRaw.contains(",")) //artist 명에서 ',' 발견 ex) Gould, Glenn
                {
                    String[] arr = artistRaw.split(",");
                    artistRaw = arr[1].trim() + " " + arr[0].trim();
                }

                artistRaw = artistRaw.replace("-", " ");

                String artistLow = artistRaw.toLowerCase();

                final String[] composerList = {
                        "bach", "bartok", "beethoven", "bernstein", "bizet", "brahms", "britten", "chopin", "debussy",
                        "dvorak", "elgar", "faure", "franck", "gershwin", "grieg", "haydn", "handel", "johann strauss",
                        "liszt", "mahler", "mendelssohn", "messiaen", "mozart", "mussorgsky", "paganini", "prokofiev",
                        "puccini", "ravel", "rachmanino", "richard strauss", "rimsky", "rodrigo", "rosssini", "rubinstein",
                        "saens", "saëns", "salieri", "schoenberg", "schubert", "schumann", "shostakovich", "sibelius",
                        "stravinsky", "tchaikovsky", "telemann", "verdi", "vivaldi", "wagner", "weber"
                };

                if(Arrays.stream(composerList).anyMatch(artistLow::equals)) // artist 가 작곡가일 경우
                {
                    String composerLow = composer.toLowerCase();
                    String artistLastname = artistRaw.substring(artistRaw.lastIndexOf(" ")+1); //마지막 문자열만 가져오기(이름의 성만 가져오기)

                    if(!composerLow.contains(artistLastname.toLowerCase()))
                    {
                        composer += "\n";
                        composer += artistLastname.substring(0, 1).toUpperCase() + artistLastname.substring(1).toLowerCase(); //첫 글자만 대문자화
                    }
                }
                else // aritst 가 작곡가가 아닐 경우
                {
                    // artist 첫글자만 대문자 처리
                    String[] arr = artistRaw.trim().split(" ");
                    StringBuffer sb = new StringBuffer();

                    for (int j = 0; j < arr.length; j++) {
                        sb.append(Character.toUpperCase(arr[j].charAt(0)))
                                .append(arr[j].substring(1).toLowerCase())
                                .append(" ");
                    }

                    artist = sb.toString().trim();
                    artist += "\n";
                }
            }

            // track
            Elements tracks = nextDoc.select(".a-section > .TitleLink");

            for(Element element: tracks) {  //Elements 길이만큼 반복된다.
                track += element.text();
                track += "\n";
            }

            // label, numOfDisc
            Elements NodeList = nextDoc.select(".content li");

            for (Element element : NodeList) {
                String innerText = element.text();

                if (innerText.contains("Number of Discs:")) {
                    numOfDisc = innerText.substring(17);
                    System.out.println(numOfDisc);
                }

                if (innerText.contains("Label:")) {
                    label = innerText.substring(7);
                    System.out.println(label);
                }
            }

            Music music = Music.builder()
                    .barcode(barcode)
                    .title(title)
                    .artist(artist)
                    .composer(composer)
                    .category(musicCrawlingRequest.getCategory())
                    .track(track)
                    .label(label)
                    .numOfDisc(numOfDisc)
                    .importance(musicCrawlingRequest.getImportance())
                    .registeredAt(LocalDateTime.now())
                    .build();

            save(music);
        }
    }

    private int save(Music music) {
        Music newMusic = musicRepository.save(music);
        // Todo: DB Save 결과
        return 0;
    }

    private void recordNotOnSaleMessage() {
//        PrintWriter out = response.getWriter();
//        out.println("<script>alert('현재 판매되지 않는 음반으로, 곡을 직접 추가해야 합니다.'); location.href='"+context+"/MusicList';</script>");
//        out.flush();
    }

}
