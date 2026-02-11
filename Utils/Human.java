package com.example.unogame.Utils;

import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Human extends Player {
    public Human(String name) {
        super(name);
    }

    public void playTurn(GameController gameController, Button pileButton, HBox topBox, HBox playerBox, VBox leftBox, VBox rightBox, BorderPane root, GUI gui){
        System.out.println("Human playing turn");
    }

    public void checkUnoStatus(Player player, GUI gui, HBox playerBox,GameController gc){
        if(player.getHand().size() == 1 && !player.getUnoDeclared()){
            System.out.println(player.getUnoDeclared());
            player.drawCards(gc.drawFromDeck(2));
            gui.refreshHand(gc,player,playerBox);
        }else if(player.getHand().size() >= 2){
            player.setUnoDeclared(false);
            System.out.println(player.getUnoDeclared());
        }
    }


    public boolean isBot(){
        return false;
    }
}
