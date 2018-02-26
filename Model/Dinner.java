package com.yumtiff.mohitkumar.tiffin.Model;

/**
 * Created by mohit.gupta on 1/28/2017.
 */

public class Dinner {
    String id;
    String Userid;
    String categoryid;
    String subcategoryid;
    String discountid;
    String dishname;
    String dishimage;
    String quantity;
    String rating;
    String dishdescription;
    String dishcode;
    String createdon;
    String modifiedby;
    String modifieon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getSubcategoryid() {
        return subcategoryid;
    }

    public void setSubcategoryid(String subcategoryid) {
        this.subcategoryid = subcategoryid;
    }

    public String getDiscountid() {
        return discountid;
    }

    public void setDiscountid(String discountid) {
        this.discountid = discountid;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getDishimage() {
        return dishimage;
    }

    public void setDishimage(String dishimage) {
        this.dishimage = dishimage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDishdescription() {
        return dishdescription;
    }

    public void setDishdescription(String dishdescription) {
        this.dishdescription = dishdescription;
    }

    public String getDishcode() {
        return dishcode;
    }

    public void setDishcode(String dishcode) {
        this.dishcode = dishcode;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public String getModifieon() {
        return modifieon;
    }

    public void setModifieon(String modifieon) {
        this.modifieon = modifieon;
    }

    String title,price,discription;
    int img;

    public Dinner() {
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
