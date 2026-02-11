package com.example.unogame.Utils;

public class Settings {
    private int bots = 3;
    private boolean stacking2s = false;
    private boolean stacking4s = false;
    private boolean swapCards = false;
    private boolean stealCards = false;
    private boolean judgementCards = false;


    public void setJudgementCards(boolean judgementCards) {
        this.judgementCards = judgementCards;
    }
    public boolean getJudgementCards() {
        return judgementCards;
    }

    public boolean getSwapCards() {
        return this.swapCards;
    }

    public void setSwapCards(boolean swapCards) {
        this.swapCards = swapCards;
    }

    public boolean getStealCards() {
        return this.stealCards;
    }

    public void setStealCards(boolean stealCards) {
        this.stealCards = stealCards;
    }

    public boolean getStacking4s() {
        return this.stacking4s;
    }

    public void setBots(int number){
        this.bots = number;
    }

    public int getBots(){
        return this.bots;
    }

    public void setStacking2s(boolean state){
        this.stacking2s = state;
    }

    public boolean getStacking2s(){
        return this.stacking2s;
    }

    public void setStacking4s(boolean state){
        this.stacking4s = state;
    }
}


