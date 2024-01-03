package com.example.expeditionsupportapp;
public class ImageClass {

    String imageUri;
    String imageName;

    public ImageClass() {
    }

    public ImageClass(String imageName, String image) {
        this.imageUri = image;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}


