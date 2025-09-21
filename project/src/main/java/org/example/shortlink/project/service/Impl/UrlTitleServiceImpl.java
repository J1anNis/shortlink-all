package org.example.shortlink.project.service.Impl;

import lombok.SneakyThrows;
import org.example.shortlink.project.service.UrlTitleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Url 标题接口层实现类
 */
@Service
public class UrlTitleServiceImpl implements UrlTitleService {

    /**
     * 根据 URL 获取标题
     * @param url 目标网站地址
     * @return 网站标题
     */
    @SneakyThrows
    @Override
    public String getTitleByUrl(String url) {
        URL targetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", url);
            return document.title();
        }
        return "Error while fetching title";
    }
}
