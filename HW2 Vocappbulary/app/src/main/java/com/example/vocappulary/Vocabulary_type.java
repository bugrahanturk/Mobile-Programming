package com.example.vocappulary;

import java.io.Serializable;

public class Vocabulary_type implements Serializable {
    private int voc_type_id;
    private String description;

    public Vocabulary_type(int voc_type_id, String description) {
        this.voc_type_id = voc_type_id;
        this.description = description;
    }

    public Vocabulary_type() {
    }

    public int getVoc_type_id() {
        return voc_type_id;
    }

    public void setVoc_type_id(int voc_type_id) {
        this.voc_type_id = voc_type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
