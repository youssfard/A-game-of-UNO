package com.example.unogame.CardEffects;

import com.example.unogame.Utils.Card;
import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import com.example.unogame.Utils.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StealEffect implements CardEffect{
    @Override
    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        GUI gui = gc.getGUI();
        Player current = gc.getCurrentPlayerObject();
        String result = gui.colorPick(gc.getCurrentPlayerObject());
        card.setColor(result);
        gc.playCard(card);
        gc.nextTurn();
        Player nextPlayer = gc.getCurrentPlayerObject();
        Card aux = null;
        for(Card cardLoop:nextPlayer.getHand()){
            if(cardLoop.getColor().equals(result)){
                aux = cardLoop;
                break;
            }
        }
        if(aux != null){
            current.getHand().add(aux);
            nextPlayer.getHand().remove(aux);
        }

        gui.autoRefreshHands(current,topBox,leftBox,rightBox,playerBox,gc);
        gui.autoRefreshHands(nextPlayer,topBox,leftBox,rightBox,playerBox,gc);

    }
}
