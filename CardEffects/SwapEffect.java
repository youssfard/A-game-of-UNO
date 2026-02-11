package com.example.unogame.CardEffects;

import com.example.unogame.Utils.Card;
import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import com.example.unogame.Utils.Player;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SwapEffect implements CardEffect{
    @Override
    public void applyEffect(GameController gc, Card card, Button pileButton, HBox playerBox, HBox topBox, VBox leftBox, VBox rightBox, BorderPane root) {
        GUI gui = gc.getGUI();
        String result = gui.colorPick(gc.getCurrentPlayerObject());
        card.setColor(result);
        gc.playCard(card);

        Player current = gc.getCurrentPlayerObject();
        List<Card> currentHand = current.getHand();

        gc.nextTurn();

        Player nextPlayer = gc.getCurrentPlayerObject();
        List<Card> nextHand = nextPlayer.getHand();
        current.setHand(new ArrayList<>(nextHand));
        nextPlayer.setHand(new ArrayList<>(currentHand));

        gui.autoRefreshHands(current,topBox,leftBox,rightBox,playerBox,gc);
        gui.autoRefreshHands(nextPlayer,topBox,leftBox,rightBox,playerBox,gc);


    }
}
