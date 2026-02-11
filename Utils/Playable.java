package com.example.unogame.Utils;

import com.example.unogame.GUIs.GUI;
import com.example.unogame.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface Playable {
    void playTurn(GameController gameController, Button pileButton, HBox topBox, HBox playerBox, VBox leftBox, VBox rightBox, BorderPane root, GUI gui);
}
