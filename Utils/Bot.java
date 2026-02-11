package com.example.unogame.Utils;

import com.example.unogame.*;
import com.example.unogame.GUIs.GUI;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

public class Bot extends Player {
    public Bot(String name) {
        super(name);
    }

    public void playTurn(GameController gameController, Button pileButton, HBox topBox, HBox playerBox, VBox leftBox, VBox rightBox, BorderPane root, GUI gui){
        Player current = gameController.getCurrentPlayerObject();
        if(!gameController.isGameOver()){
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                List<Card> hand = current.getHand();
                boolean playable = false;
                if(cardTypeExists(current,"Draw2") && gameController.getDraw2Stack()>0){
                    for (Card card : hand) {
                        if (card.getType().equals("Draw2")) {
                            playCard(gameController,card,gui,topBox,playerBox,pileButton,leftBox,rightBox,root);
                            playable = true;
                            break;
                        }
                    }
                }else if(cardTypeExists(current,"wildDraw") && gameController.getDraw4stack()>0){
                    for (Card card : hand) {
                        if (card.getType().equals("wildDraw")) {
                            playCard(gameController,card,gui,topBox,playerBox,pileButton,leftBox,rightBox,root);
                            playable = true;
                            break;
                        }
                    }
                }else{
                    for (Card card : hand) {
                        if (Rules.isPlayable(card, gameController.getTopCard(), gameController)) {
                            playCard(gameController,card,gui,topBox,playerBox,pileButton,leftBox,rightBox,root);
                            playable = true;
                            break;
                        }
                    }
                }
                if (!playable) {
                    Card lastCard = gameController.drawFromDeck();
                    current.drawCard(lastCard);

                    if (Rules.isPlayable(lastCard, gameController.getTopCard(), gameController)) {
                        playCard(gameController,lastCard,gui,topBox,playerBox,pileButton,leftBox,rightBox,root);
                    } else {
                        System.out.println("BOT drew and skipped: " + lastCard.getNumber() + lastCard.getColor() + lastCard.getType());
                        gameController.nextTurn();
                    }
                }

                if (gameController.getCurrentPlayerObject().isBot()) {
                    playTurn(gameController, pileButton, topBox, playerBox, leftBox, rightBox, root,gui);
                }
            });
            pause.play();
        }
    }

    private void playCard(GameController gameController, Card card,GUI gui,HBox topBox,HBox playerBox,Button pileButton,VBox leftBox,VBox rightBox,BorderPane root) {
        gameController.applyActionCards(card, gui, topBox, playerBox, pileButton, leftBox, rightBox, root);
        String path = "/cards/" + card.getNumber() + card.getColor() + card.getType() + ".png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        pileButton.setGraphic(new ImageView(image));
        System.out.println("BOT played: " + card.getNumber() + card.getColor() + card.getType());
        gui.setColorLabel(card.getColor());
    }

    @Override
    public boolean isBot() {
        return true;
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
