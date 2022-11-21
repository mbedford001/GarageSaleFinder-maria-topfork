package com.example.garagesalefinder.PostStuff;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

/**
     * Abstract class for creating a post
     * @author .......
     * @version 9/29/2022
     */
    public class Post implements Serializable {

        private String owner;
        private String location;
        private String title;
        //add list of items
        private String description;
        //fix date
        private Date date;
        private String time;
        private String image;
        private String priceRange;



        /**
         * Constructor to create a Post
         * @param owner the username of the user who created the post
         * @param location the location of the sale
         * @param title the title of the sale
         * @param description the description of the post
         * @param time the time the sale is taking place
         * @param priceRange the pricerange is the range of different prices you have from lowest to highest (ex. $.50-$5)
         * @param image a string of the image link
         */
        public Post(String owner, String location, String title, String description,
                    String time, String priceRange, String image){
            this.owner = owner;
            this.location = location;
            this.title = title;
            this.description = description;
            this.date = date;
            this.time = time;
            this.priceRange = priceRange;
        }


        /**
         * method to set the owner of the sale
         * @param owner the username of the owner of the post
         */
        public void setOwner(String owner){
            this.owner = owner;
        }

        /**
         * method to get the owner of the sale
         * @return returns the username of the owner of the post
         */
        public String getOwner(){
            return this.owner;
        }

        /**
         * method to set the location of the sale
         * @param location the location of the sale
         */
        public void setLocation(String location){
            this.location=location;
        }

        /**
         * method to get the location of a sale
         * @return the location of the sale
         */
        public String getLocation(){
            return this.location;
        }

        /**
         * method to set the title of the post
         * @param title the title of the post
         */
        public void setTitle(String title){
            this.title = title;
        }

        /**
         * method to get the title of the post
         * @return the title of the post
         */
        public String getTitle(){
            return this.title;
        }

        /**
         * method to set the description of the post
         * @param description the description of the post
         */
        public void setDescription(String description){
            this.description = description;
        }

        /**
         * method to get the description of the post
         * @return the description of the post
         */
        public String getDescription(){
            return this.description;
        }

        /**
         * method to get the date of the post
         * @param date the date of the sale
         */
        public void setDate(Date date){
            this.date = date;
        }

        /**
         * method to get the date of the sale
         * @return the date of the sale
         */
        public Date getDate(){
            return this.date;
        }

        /**
         * method to set the time of the sale
         * @param time the time the sale will take place
         */
        public void setTime(String time){
            this.time = time;
        }

        /**
         * method to get the time of the sale
         * @return the time of the sale
         */
        public String getTime(){
            return this.time;
        }

        /**
         * method to set the price range of the sale
         * @param priceRange the price range of the sale (1-5) 1 begin the cheapest, 5 being the most expensive
         */
        public void setPriceRange(String priceRange){
            this.priceRange = priceRange;
        }

        /**
         * method to get the price range of the sale
         * @return the price range of the sale (1-5) 1 begin the cheapest, 5 being the most expensive
         */
        public String getPriceRange(){
            return this.priceRange;
        }

    /**
     * method to set image link
     * @param image the image link
     */
    public void setImage(String image){
            this.image = image;
        }

    /**
     * method to get the image link
     * @return
     */
    public String getImage(){
        return this.image;
        }

}


