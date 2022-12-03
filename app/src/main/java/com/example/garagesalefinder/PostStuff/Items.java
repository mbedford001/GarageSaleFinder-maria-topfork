package com.example.garagesalefinder.PostStuff;

import java.io.Serializable;

/**
 * Class for items
 * @author Maria Bedford
 * @version 10/18/2022
 */
public class Items implements Serializable {

        private String post_title;
        private String item_title;
        private String sale_post_username;
        private String category;
        private String image;
        private String description;
        private String price;
        private String quantity;

        /**
         * Constructor for items
         * @param post_title
         * @param item_title
         * @param sale_post_username
         * @param category
         * @param image
         * @param description
         * @param price
         * @param quantity
         */
        public Items(String post_title, String item_title, String sale_post_username,
                     String category,String image, String description, String price, String quantity){
                this.post_title = post_title;
                this.item_title = item_title;
                this.sale_post_username = sale_post_username;
                this.category = category;
                this.image = image;
                this.description = description;
                this.price = price;
                this.quantity = quantity;
        }

        public String getPost_title() {
                return post_title;
        }

        public String getItem_title() {
                return item_title;
        }

        public String getSale_post_username() {
                return sale_post_username;
        }

        public String getCategory() {
                return category;
        }

        public String getImage() {
                return image;
        }

        public String getDescription() {
                return description;
        }

        public String getPrice() {
                return price;
        }

        public String getQuantity() {
                return quantity;
        }

        public void setPost_title(String post_title) {
                this.post_title = post_title;
        }

        public void setItem_title(String item_title) {
                this.item_title = item_title;
        }

        public void setSale_post_username(String sale_post_username) {
                this.sale_post_username = sale_post_username;
        }

        public void setCategory(String category) {
                this.category = category;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public void setPrice(String price) {
                this.price = price;
        }

        public void setQuantity(String quantity) {
                this.quantity = quantity;
        }
}
