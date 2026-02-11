package com.example.unogame.CardEffects;

import com.example.unogame.Utils.Card;
import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReverseEffect implements CardEffect{
    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        GUI gui = gc.getGUI();
        gc.playCard(card);
        if(gc.getPlayers().size()==2){
            gc.nextTurn();
            gc.nextTurn();
        }else{
            gc.setDirection(gc.getDirection() * -1);
            gc.nextTurn();
        }
        if(gc.getDirection()==1){
            gui.setDirectionLabel("C/Clockwise");
        }else{
            gui.setDirectionLabel("Clockwise");
        }
    }
}
