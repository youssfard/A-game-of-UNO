package com.example.unogame.CardEffects;

import com.example.unogame.Utils.Card;
import com.example.unogame.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SkipEffect implements CardEffect{

    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        gc.playCard(card);
        gc.nextTurn();
        gc.nextTurn();
    }
}
