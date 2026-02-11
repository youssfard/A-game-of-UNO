package com.example.unogame.CardEffects;

import com.example.unogame.Utils.Card;
import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import com.example.unogame.Utils.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WildDrawEffect implements CardEffect{
    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        GUI gui = gc.getGUI();
        String result = gui.colorPick(gc.getCurrentPlayerObject());
        card.setColor(result);
        gc.playCard(card);
        gc.setDraw4stack(gc.getDraw4stack() + 1);
        gc.nextTurn();
        Player victim = gc.getCurrentPlayerObject();
        if (!gc.getSettings().getStacking4s()) {
            System.out.println("not enqbled");
            victim.drawCards(gc.drawFromDeck(4));
            gui.autoRefreshHands(victim,topBox,leftBox,rightBox,playerBox,gc);
            gc.nextTurn();
        } else {
            if (cardTypeExists(victim, card)) {
                System.out.println("Waiting for plus 4");
            } else {
                System.out.println("No plus 4 found");
                victim.drawCards(gc.drawFromDeck(4 * gc.getDraw4stack()));
                gc.setDraw4stack(0);
                gui.autoRefreshHands(victim,topBox,leftBox,rightBox,playerBox,gc);
                gc.nextTurn();
            }
        }
    }

    private boolean cardTypeExists(Player player, Card card) {
        boolean found = false;
        for(Card cardSearch:player.getHand()){
            if(cardSearch.getType().equals(card.getType())){
                found = true;
                break;
            }
        }
        return found;
    }
}
