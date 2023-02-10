package com.binary.homepage.component;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

}
