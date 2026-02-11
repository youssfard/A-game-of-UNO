package com.example.unogame.Utils;

import com.example.unogame.CardEffects.CardEffect;

public class Card {
    private Integer number;
    private String color;
    private String type;
    private CardEffect effect;

    public Card(Integer number, String color, String type,CardEffect effect) {
        this.number = number;
        this.color = color;
        this.type = type;
        this.effect = effect;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNumber() {
        return number;
    }



    public String toString(){
        return "N: " + number + " C: " + color + " T: " + type;
    }

    public String getType() {
        return type;
    }

    public CardEffect getEffect() {
        return effect;
    }


}
