package com.example.inshort.dtos;

public class NewsDto {
    Integer id;
    String title;
    String msg;
    String image;
    String url;

    public NewsDto() {}
    public NewsDto(Integer id, String title, String msg, String image) {
        this.id = id;
        this.title = title;
        this.msg = msg;
        this.image = image;
    }

    public Integer getId() { return  this.id; }
    public String getTitle() { return  this.title; }
    public String getMsg() { return  this.msg; }
    public String getImage() { return  this.image; }
    public String getUrl() { return  this.url; }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setMsg(String msg) { this.msg = msg; }
    public void setImage(String image) { this.image = image; }
    public void setUrl(String url) { this.url = url; }

}
