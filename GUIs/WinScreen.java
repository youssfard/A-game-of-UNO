package com.example.unogame.GUIs;

import com.example.unogame.MenuGUI;
import com.example.unogame.Utils.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class WinScreen {

    private String name;
    private Settings settings;

    public void show(Stage primaryStage){
        System.out.println(getSettings().getBots());
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane,1280,720);
        startGUI(pane,primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void styleButtons(ArrayList<Button> buttons){
        for(Button button : buttons){
            button.setPrefSize(300,70);
            button.setTextFill(Color.WHITE);
            button.setFont(Font.font("System", FontWeight.BOLD, 16));
            button.setBackground(new Background(new BackgroundFill(Color.rgb(128, 0, 32), new CornerRadii(20), Insets.EMPTY)));
        }
    }

    private void startGUI(StackPane pane,Stage primaryStage){
        Label label = new Label(name+" Won!");
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(75,0,130),null,null)));
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("System", FontWeight.BOLD, 20));

        ArrayList<Button> buttons = new ArrayList<>();
        Button playAgain = new Button("PLAY AGAIN");
        Button mainMenu = new Button("MAIN MENU");
        playAgain.setCursor(javafx.scene.Cursor.HAND);
        playAgain.setCursor(javafx.scene.Cursor.HAND);

        buttons.add(playAgain);
        buttons.add(mainMenu);
        styleButtons(buttons);

        VBox centerBox = new VBox(20);
        centerBox.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5),new CornerRadii(20),null)));
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPrefSize(400, 400);
        centerBox.setMaxSize(400, 400);

        centerBox.getChildren().add(label);
        centerBox.getChildren().add(mainMenu);
        centerBox.getChildren().add(playAgain);

        VBox.setMargin(label, new Insets(0, 0, 25, 0));
        VBox.setMargin(mainMenu, new Insets(0, 0, 5, 0));

        pane.getChildren().add(centerBox);

        playAgain.setOnAction(e -> {
            GUI gui = new GUI();
            gui.setSettings(this.getSettings());
            try {
                gui.start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        mainMenu.setOnAction(e -> {
            MenuGUI menuGUI = new MenuGUI();
            try {
                menuGUI.start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Settings getSettings() {
        return settings;
    }


}
