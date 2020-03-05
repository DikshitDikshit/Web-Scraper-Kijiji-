/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraper.kijiji;

import java.util.Objects;

/**
 *
 * @author User
 */
public class KijijiItem {

    private String id, url, imageUrl, imageName, price, title, date, location, description;

    KijijiItem(){
        
    }
    
    void setId(String id) {
        this.id = id;
    }

    void setUrl(String url) {
        this.url = url;
    }
    void setImageName(String imageName){
        this.imageName=imageName;
    }
    void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    void setPrice(String price) {
        this.price = price;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setDate(String date) {
        this.date = date;
    }

    void setLocation(String location) {
        this.location = location;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getImageName(){
        return imageName;    
    }

    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(getId());
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BadKijijiItem other = (BadKijijiItem) obj;
        return Objects.equals(getId(), other.getId());
    }
    
    @Override
    public String toString() {
        return String.format("[id:%s, image_url:%s, image_name:%s, price:%s, title:%s, date:%s, location:%s, description:%s]",
                getId(), getImageUrl(), getImageName(), getPrice(), getTitle(), getDate(), getLocation(), getDescription());
    }
}
