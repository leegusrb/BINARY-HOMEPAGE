package com.binary.homepage.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrawlingTest {


    private static final String url = "https://www.acmicpc.net/user/hsw1920";
    private static final String splitStr = "],\\[";

    @Test
    public void process() {
        Connection conn = Jsoup.connect(url);

        Document document = null;

        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String data = getData(document);

        System.out.println(data);
        System.out.println(data.length());

        String[] listStr = data.substring(74, data.length() - 4).split(splitStr);
        System.out.println(Arrays.toString(listStr));
    }

    private String getData(Document document) {
        String data = "";
        Elements selects = document.select("script");

        for (Element select : selects) {
            if (select.toString().contains("user_day_problems")) {
                data = select.html();
                break;
            }
        }

        return data;
    }
}