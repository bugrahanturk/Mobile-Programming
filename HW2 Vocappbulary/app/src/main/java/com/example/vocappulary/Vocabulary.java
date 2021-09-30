package com.example.vocappulary;

import java.io.Serializable;

public class Vocabulary implements Serializable {

    private int voc_id;
    private String english;
    private String turkish;
    private String sentence;
    private String synonym;
    private String antonym;
    private Vocabulary_type voc_type_id;
    private byte[] image;

    public Vocabulary() {
    }

    public Vocabulary(int voc_id, String english, String turkish, String sentence, String synonym, String antonym, Vocabulary_type voc_type_id, byte[] image) {
        this.voc_id = voc_id;
        this.english = english;
        this.turkish = turkish;
        this.sentence = sentence;
        this.synonym = synonym;
        this.antonym = antonym;
        this.voc_type_id = voc_type_id;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getVoc_id() {
        return voc_id;
    }

    public void setVoc_id(int voc_id) {
        this.voc_id = voc_id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getTurkish() {
        return turkish;
    }

    public void setTurkish(String turkish) {
        this.turkish = turkish;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getAntonym() {
        return antonym;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    public Vocabulary_type getVoc_type_id() {
        return voc_type_id;
    }

    public void setVoc_type_id(Vocabulary_type voc_type_id) {
        this.voc_type_id = voc_type_id;
    }
}
