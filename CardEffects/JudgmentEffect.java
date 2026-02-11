package com.example.unogame.CardEffects;

import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import com.example.unogame.Utils.Card;
import com.example.unogame.Utils.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JudgmentEffect implements CardEffect {
    @Override
    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        GUI gui = gc.getGUI();
        String result = gui.colorPick(gc.getCurrentPlayerObject());
        card.setColor(result);
        gc.playCard(card);
        Player current = gc.getCurrentPlayerObject();
        gc.nextTurn();
        Player victim = gc.getCurrentPlayerObject();

        if(cardTypeExists(victim,"wildDraw")){
            System.out.println(victim.getHand());
            victim.getHand().remove(getCardByType(victim,"wildDraw"));
            victim.drawCards(gc.drawFromDeck(2));
            gc.nextTurn();
            gui.autoRefreshHands(victim,topBox,leftBox,rightBox,playerBox,gc);
        }else{
            current.drawCards(gc.drawFromDeck(2));
            gui.autoRefreshHands(current,topBox,leftBox,rightBox,playerBox,gc);

        }


    }

    private Card getCardByType(Player player, String type) {
        for (Card card : player.getHand()) {
            if (card.getType().equals(type)) {
                return card;
            }
        }
        return null;
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
