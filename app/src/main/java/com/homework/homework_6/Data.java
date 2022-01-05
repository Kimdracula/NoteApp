package com.homework.homework_6;

public class Data {
    public Data(String header, String description) {
        this.header = header;
        this.description = description;
    }

    private String header;
   private String description;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
