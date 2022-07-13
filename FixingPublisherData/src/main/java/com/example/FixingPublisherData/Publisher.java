package com.example.FixingPublisherData;

public class Publisher {
    String code;
    String name;
    String file;
    String active;
    String[] categories;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = String.valueOf(active);
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
