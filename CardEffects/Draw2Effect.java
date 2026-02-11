package com.example.unogame.CardEffects;

import com.example.unogame.Utils.Card;
import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import com.example.unogame.Utils.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Draw2Effect implements CardEffect{

    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        GUI gui = gc.getGUI();
        gc.playCard(card);
        gc.setDraw2Stack(gc.getDraw2Stack()+1);
        gc.nextTurn();
        Player victim = gc.getCurrentPlayerObject();

        if(!gc.getSettings().getStacking2s()){
            System.out.println("not enqbled");
            victim.drawCards(gc.drawFromDeck(2));
            gui.autoRefreshHands(victim,topBox,leftBox,rightBox,playerBox,gc);
            gc.nextTurn();

        }else{
            if(cardTypeExists(victim,"Draw2")){
                System.out.println("Waiting for plus 2");
            }
            else{
                victim.drawCards(gc.drawFromDeck(2* gc.getDraw2Stack()));
                gc.setDraw2Stack(0);
                gui.autoRefreshHands(victim,topBox,leftBox,rightBox,playerBox,gc);
                gc.nextTurn();
            }
        }




    }

    private boolean cardTypeExists(Player player, String type) {
        boolean found = false;
        for(Card cardSearch:player.getHand()){
            if(cardSearch.getType().equals(type)){
                found = true;
                break;
            }
        }
        return found;
    }
}
