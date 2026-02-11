package com.example.unogame.CardEffects;

import com.example.unogame.Utils.Card;
import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WildEffect implements CardEffect{
    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        GUI gui = gc.getGUI();
        String result = gui.colorPick(gc.getCurrentPlayerObject());
        card.setColor(result);
        gc.playCard(card);
        gc.nextTurn();
    }
}
