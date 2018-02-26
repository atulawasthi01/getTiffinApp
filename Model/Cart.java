package com.yumtiff.mohitkumar.tiffin.Model;

import com.yumtiff.mohitkumar.tiffin.Activity.Maincart;
import com.yumtiff.mohitkumar.tiffin.data.FeedItem;

import java.util.List;

/**
 * Created by mohit.gupta on 2/5/2017.
 */

public class Cart {

    String dishname,dishimage,dishdescription,price;

    public Cart(Maincart maincart, List<FeedItem> feedItems) {
    }

    public String getDishimage() {
        return dishimage;
    }

    public void setDishimage(String dishimage) {
        this.dishimage = dishimage;
    }

    public String getDishdescription() {
        return dishdescription;
    }

    public void setDishdescription(String dishdescription) {
        this.dishdescription = dishdescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }
}
   /* String cartFood,cartfoodDiscription,cartfoodPrice,cartfoodQuantity;
    int foodImg;
    String id, Userid, categoryid, subcategoryid, discountid, dishname, dishimage, quantity, price, rating, dishdescription, dishcode,
            subcategoryname, createdon, modifiedon, modifiedBy, createdby,region;

    public Cart() {

    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(String modifiedon) {
        this.modifiedon = modifiedon;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Cart(String cartFood, String cartfoodDiscription, String cartfoodPrice, String cartfoodQuantity, int foodImg) {
        this.cartFood=cartFood;
        this.cartfoodDiscription=cartfoodDiscription;
        this.cartfoodPrice=cartfoodPrice;
        this.cartfoodQuantity=cartfoodQuantity;
        this.foodImg=foodImg;

    }


    public String getCartFood() {
        return cartFood;
    }

    public void setCartFood(String cartFood) {
        this.cartFood = cartFood;
    }

    public String getCartfoodDiscription() {
        return cartfoodDiscription;
    }

    public void setCartfoodDiscription(String cartfoodDiscription) {
        this.cartfoodDiscription = cartfoodDiscription;
    }

    public String getCartfoodPrice() {
        return cartfoodPrice;
    }

    public void setCartfoodPrice(String cartfoodPrice) {
        this.cartfoodPrice = cartfoodPrice;
    }

    public String getCartfoodQuantity() {
        return cartfoodQuantity;
    }

    public void setCartfoodQuantity(String cartfoodQuantity) {
        this.cartfoodQuantity = cartfoodQuantity;
    }

    public int getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(int foodImg) {
        this.foodImg = foodImg;
    }


}
*/