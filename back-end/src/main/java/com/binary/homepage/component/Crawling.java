package com.binary.homepage.component;

import com.binary.homepage.domain.GrassInfo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Crawling {

    private static final String url = "https://www.acmicpc.net/user/";
    private static final String splitStr = "],\\[";

    public String[] getGrassData(String grassName) {
        Connection conn = Jsoup.connect(url + grassName);

        Document document = null;

        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (document == null) return null;

        String grassData = "";
        Elements selects = document.select("script");

        for (Element select : selects) {
            if (select.toString().contains("user_day_problems")) {
                grassData = select.html();
                break;
            }
        }

        return grassData.substring(74, grassData.length() - 4).split(splitStr);
    }

    public GrassInfo getTodayGrassData(String grassName) {
        String[] grassData = getGrassData(grassName);

        for (String grassStr : grassData) {
            String[] splitInfo = grassStr.split(",");

            int year = Integer.parseInt(splitInfo[0].substring(0, 4));
            int month = Integer.parseInt(splitInfo[0].substring(4, 6));
            int day = Integer.parseInt(splitInfo[0].substring(6, 8));

            LocalDate date = LocalDate.of(year, month, day);

            if (LocalDate.now().plusDays(-1).equals(date)) {
                int grassNum = Integer.parseInt(splitInfo[1]);
                return GrassInfo.createGrassInfo(date, grassNum);
            }
        }

        return null;
    }

    public List<GrassInfo> listGrassInfo(String grassName) {
        List<GrassInfo> grassInfos = new ArrayList<>();
        String[] grassData = getGrassData(grassName);

        for (String grassInfo : grassData) {
            String[] splitInfo = grassInfo.split(",");

            int year = Integer.parseInt(splitInfo[0].substring(0, 4));
            int month = Integer.parseInt(splitInfo[0].substring(4, 6));
            int day = Integer.parseInt(splitInfo[0].substring(6, 8));

            if (year != LocalDate.now().getYear()) continue;

            LocalDate date = LocalDate.of(year, month, day);
            int grassNum = Integer.parseInt(splitInfo[1]);

            grassInfos.add(GrassInfo.createGrassInfo(date, grassNum));
        }

        return grassInfos;
    }

}
