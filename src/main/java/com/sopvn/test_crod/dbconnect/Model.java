/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sopvn.test_crod.dbconnect;

/**
 *
 * @author Admin
 */
public class Model {

    private String book_id;
    private String book_name;
    private String description;
    private int price;
    private String img;

    private String pub_id;
    private String cat_id;

    public Model() {
        this.book_id = "";
        this.book_name = book_name;
        this.description = description;
        this.price = price;
        this.img = img;
        this.pub_id = pub_id;
        this.cat_id = cat_id;
    }

    public Model(String book_id, String book_name, String description, int price, String img, String pub_id, String cat_id) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.description = description;
        this.price = price;
        this.img = img;
        this.pub_id = pub_id;
        this.cat_id = cat_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public String getPub_id() {
        return pub_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPub_id(String pub_id) {
        this.pub_id = pub_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

}
