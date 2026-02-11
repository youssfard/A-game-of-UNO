package com.example.unogame.Utils;

import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Playable {
    private String name;
    private List<Card> hand;
    private boolean unoDeclared;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.unoDeclared = false;
    }

    public abstract void playTurn(GameController gameController, Button pileButton, HBox topBox,
                                  HBox playerBox, VBox leftBox, VBox rightBox, BorderPane root, GUI gui);


    public void drawCard(Card card) {
        hand.add(card);
    }

    public String getName() {
        return name;
    }

    public boolean getUnoDeclared() {
        return unoDeclared;
    }

    public void setUnoDeclared(boolean unoDeclared) {
        this.unoDeclared = unoDeclared;
    }

    public void setHand(List<Card> cards) {
        this.hand = cards;
    }
    public List<Card> getHand(){
        return hand;
    }


    public void drawCards(List<Card> cards) {
        for(Card card : cards) {
            hand.add(card);
        }
    }
    public void removeCard(Card card) {
        hand.remove(card);
    }

    public abstract boolean isBot();


}
