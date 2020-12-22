package com.example.inshort.dtos;

import java.util.List;

public class NewsApiRespDto {
    String status;
    Integer totalResults;
    List<NewsDto> articles;

    public NewsApiRespDto() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setArticles(List<NewsDto> articles) {
        this.articles = articles;
    }

    public List<NewsDto> getArticles() {
        return articles;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
