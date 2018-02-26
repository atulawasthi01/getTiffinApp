package com.yumtiff.mohitkumar.tiffin.Model;

/**
 * Created by mohit.gupta on 1/28/2017.
 */

public class CatThree {
    String title,price,discription;
    int img;

    public CatThree(String title, String price, String discription, int img) {
        this.title=title;
        this.price=price;
        this.discription=discription;
        this.img=img;

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
