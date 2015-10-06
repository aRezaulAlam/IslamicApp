package com.agroho.islamicapp;

/**
 * Created by Rezaul on 2015-10-05.
 */
public class CategoryPOJO {

    private String categoryId;
    private String getCategoryName;

    public CategoryPOJO(){

    }

    public CategoryPOJO(String categoryId, String getCategoryName) {
        this.categoryId = categoryId;
        this.getCategoryName = getCategoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getGetCategoryName() {
        return getCategoryName;
    }

    public void setGetCategoryName(String getCategoryName) {
        this.getCategoryName = getCategoryName;
    }

    @Override
    public String toString() {
        return "CategoryPOJO{" +
                "categoryId='" + categoryId + '\'' +
                ", getCategoryName='" + getCategoryName + '\'' +
                '}';
    }
}
