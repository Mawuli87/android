package com.messieyawo.bookapp.book;

public class ListItem {

    private String id;//user
    private String idImage;//user
    private String title;//book
    private String type;//
    private String school;


    private String price;
    private String description;


    private String phone;
    private String location;
    private String image;


    public ListItem(String id,String idImage ,String title, String type, String school, String price, String description, String phone, String location, String image) {
        this.id =id;
        this.idImage = idImage;
        this.title = title;
        this.type = type;
        this.id = id;

        this.school = school;
        this.price = price;
        this.description =description;
        this.phone = phone;
        this.location = location;
        this.image =image;

    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
