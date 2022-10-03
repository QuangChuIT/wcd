package com.aptech.product;

import com.aptech.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity<Long> {
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "price", nullable = false)
    private int price = 0;
    @Column(name = "discount")
    private double discount = 0;
    @Column(name = "quantity")
    private int quantity = 0;
    @Column(name = "status")
    private boolean status;
    @Column(name = "slug")
    private String slug;
    @Column(name = "sumary")
    private String sumary = "";
    @Column(name = "photo")
    private String photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
