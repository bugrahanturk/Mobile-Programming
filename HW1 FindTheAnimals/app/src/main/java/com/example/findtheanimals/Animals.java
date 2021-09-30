package com.example.findtheanimals;

public class Animals {
    private String animal_name;
    private String animal_img;
    private int animal_sound;


    public Animals(String animal_name, String animal_img, int animal_sound) {
        this.animal_name = animal_name;
        this.animal_img = animal_img;
        this.animal_sound = animal_sound;
    }


    public String getAnimal_name() {
        return animal_name;
    }

    public void setAnimal_name(String animal_name) {
        this.animal_name = animal_name;
    }

    public String getAnimal_img() {

        return animal_img;
    }

    public void setAnimal_img(String animal_img) {
        this.animal_img = animal_img;
    }

    public int getAnimal_sound() {
        return animal_sound;
    }

    public void setAnimal_sound(int animal_sound) {
        this.animal_sound = animal_sound;
    }
}
