package com.example.unogame.GUIs;

import com.example.unogame.GameController;
import com.example.unogame.MenuGUI;
import com.example.unogame.Utils.Card;
import com.example.unogame.Utils.Player;
import com.example.unogame.Utils.Rules;
import com.example.unogame.Utils.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

import static javafx.application.Application.launch;

public class GUI {
    private boolean drew = false;
    private Label directionLabel;
    private Label colorLabel;
    private Card selectedCard = null;
    private Button selectedButton = null;
    private Settings settings;
    private Stage primaryStage;

    private static final Color PRIMARY_COLOR = Color.rgb(75, 0, 130);
    private static final Color SECONDARY_COLOR = Color.rgb(128, 0, 32);
    private static final Color TRANSPARENT_DARK = Color.rgb(0, 0, 0, 0.5);
    private static final CornerRadii DEFAULT_RADIUS = CornerRadii.EMPTY;
    private static final CornerRadii ROUNDED_RADIUS = new CornerRadii(20);
    private static final Insets NO_PADDING = Insets.EMPTY;
    private static final String defaultPath = "/cards/defaultsmall.png";
    private static final String defaultPathInverted = "/cards/defaultsmall2.png";


    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("UNO!");
        stage.setScene(scene);
        primaryStage = stage;

        GameController gameController = new GameController();
        gameController.setGUI(this);
        gameController.setSettings(settings);
        gameController.addPlayers();
        gameController.startGame();
        stage.show();
        startGUI(gameController, root);
    }

    public static void main(String[] args) {
        launch();
    }

    public void setColorLabel(String color) {
        this.colorLabel.setText(color);
    }

    public void setDirectionLabel(String direction) {
        this.directionLabel.setText(direction);
    }

    public Stage getStage() {
        return this.primaryStage;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    private void startGUI(GameController gameController, BorderPane root) {
        HBox centerBox = new HBox();


        centerBox.setAlignment(Pos.CENTER);
        StackPane centerPane = new StackPane(centerBox);

        Player player = gameController.getCurrentPlayerObject();
        Button pileButton = drawTopCard(gameController, centerBox);
        pileButton.setBackground(new Background(new BackgroundFill(TRANSPARENT_DARK, new CornerRadii(20, 0, 0, 20, false), NO_PADDING)));
        pileButton.setPadding(new Insets(30));

        HBox playerBox = new HBox();
        playerBox.setAlignment(Pos.CENTER);

        VBox cardWrapper = new VBox(playerBox);
        cardWrapper.setAlignment(Pos.CENTER);
        cardWrapper.setPadding(new Insets(10));
        cardWrapper.setBackground(roundedBackground(SECONDARY_COLOR));

        HBox bottomContainer = new HBox(cardWrapper);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setBackground(solidBackground(PRIMARY_COLOR));

        HBox topBox = new HBox();
        topBox.setAlignment(Pos.TOP_CENTER);
        VBox leftPlayerBox = new VBox();
        leftPlayerBox.setAlignment(Pos.CENTER_RIGHT);
        VBox rightPlayerBox = new VBox();
        rightPlayerBox.setAlignment(Pos.CENTER_LEFT);

        applyBackgrounds(List.of(centerPane, topBox, rightPlayerBox, leftPlayerBox), solidBackground(PRIMARY_COLOR));

        createUnoButton(centerPane, gameController);
        createNextButton(centerPane, gameController, pileButton, topBox, playerBox, leftPlayerBox, rightPlayerBox, root);

        ArrayList<Label> infoLabels = createInfoPanel(centerPane, gameController);
        directionLabel = infoLabels.get(0);
        colorLabel = infoLabels.get(1);

        showHands(gameController, topBox, leftPlayerBox, rightPlayerBox);
        addBoxes(root, centerPane, topBox, leftPlayerBox, rightPlayerBox, bottomContainer);
        createDrawButton(gameController,playerBox,centerBox);

        if (player.isBot()) {
            refreshHand(gameController, gameController.getPlayers().get(gameController.getCurrentPlayer() - 1), playerBox);
            player.playTurn(gameController, pileButton, topBox, playerBox, leftPlayerBox, rightPlayerBox, root, this);
        } else {
            refreshHand(gameController, player, playerBox);
        }
    }


    private Background solidBackground(Color color) {
        return new Background(new BackgroundFill(color, DEFAULT_RADIUS, NO_PADDING));
    }

    private Background roundedBackground(Color color) {
        return new Background(new BackgroundFill(color, ROUNDED_RADIUS, NO_PADDING));
    }

    private void applyBackgrounds(List<Region> regions, Background background) {
        for (Region region : regions) {
            region.setBackground(background);
        }
    }

    private void styleDialogButton(Button button) {
        button.setBackground(new Background(new BackgroundFill(TRANSPARENT_DARK, new CornerRadii(10), NO_PADDING)));
        setFontWhite(button, 14);
    }

    private void setFontWhite(Labeled label, int size) {
        Font labelFont = Font.font("System", FontWeight.BOLD, size);
        label.setFont(labelFont);
        label.setTextFill(Color.WHITE);
    }

    public String colorPick(Player player) {
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        if (player.isBot()) {
            Random rand = new Random();
            return colors[rand.nextInt(colors.length)];
        } else {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Red", colors);
            dialog.setTitle("Wild card color selection");
            dialog.setHeaderText("Select a color:");
            dialog.setGraphic(null);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setBackground(solidBackground(PRIMARY_COLOR));

            Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
            Region header = (Region) dialogPane.lookup(".header-panel");
            Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");

            applyBackgrounds(List.of(okButton, cancelButton, header), solidBackground(PRIMARY_COLOR));
            styleDialogButton(okButton);
            styleDialogButton(cancelButton);
            setFontWhite(headerLabel, 14);

            return dialog.showAndWait().orElse("No color selected");
        }
    }

    private void createUnoButton(StackPane centerPane, GameController gc) {
        Button unoButton = new Button("UNO!");
        unoButton.setCursor(javafx.scene.Cursor.HAND);
        unoButton.setMinSize(50, 50);
        unoButton.setBackground(new Background(new BackgroundFill(TRANSPARENT_DARK, ROUNDED_RADIUS, NO_PADDING)));
        setFontWhite(unoButton, 12);
        centerPane.getChildren().add(unoButton);
        StackPane.setAlignment(unoButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(unoButton, new Insets(10, 0, 10, 600));

        Player player = gc.getPlayers().get(0);
        unoButton.setOnAction(e -> {
            if (player.getHand().size() == 2) {
                player.setUnoDeclared(true);
                System.out.println("UNO declared!");
            } else {
                System.out.println("Can't declare UNO now.");
            }
        });
    }

    private void createNextButton(StackPane centerPane, GameController gc, Button pileButton,
                                  HBox topBox, HBox playerBox, VBox leftBox, VBox rightBox, BorderPane root) {
        Button nextButton = new Button("PLAY!");
        nextButton.setMinSize(50, 50);
        nextButton.setBackground(new Background(new BackgroundFill(TRANSPARENT_DARK, ROUNDED_RADIUS, NO_PADDING)));
        nextButton.setCursor(javafx.scene.Cursor.HAND);
        setFontWhite(nextButton, 12);
        centerPane.getChildren().add(nextButton);
        StackPane.setAlignment(nextButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(nextButton, new Insets(10, 0, 10, 720));

        nextButton.setOnAction(e -> {
            if (!hasPlayableCards(gc)) {
                gc.nextTurn();
                gc.getCurrentPlayerObject().playTurn(gc, pileButton, topBox, playerBox, leftBox, rightBox, root, this);
            } else {
                String imagePath = "/cards/" + selectedCard.getNumber() + selectedCard.getColor() + selectedCard.getType() + ".png";
                gc.applyActionCards(selectedCard, this, topBox, playerBox, pileButton, leftBox, rightBox, root);
                playerBox.getChildren().remove(selectedButton);
                Image newTopCardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
                pileButton.setGraphic(new ImageView(newTopCardImage));
                gc.getCurrentPlayerObject().playTurn(gc, pileButton, topBox, playerBox, leftBox, rightBox, root, this);
                colorLabel.setText(selectedCard.getColor());
            }
            drew = false;
        });
    }

    private boolean hasPlayableCards(GameController gc) {
        for (Card card : gc.getCurrentPlayerObject().getHand()) {
            if (Rules.isPlayable(card, gc.getTopCard(), gc)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Label> createInfoPanel(StackPane centerPane, GameController gc) {
        ArrayList<Label> infoLabels = new ArrayList<>();

        Label backLabel = new Label();
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/back.png")));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(40);
        logoImageView.setPreserveRatio(true);
        backLabel.setGraphic(logoImageView);
        backLabel.setCursor(javafx.scene.Cursor.HAND);

        backLabel.setOnMouseClicked(event -> {
            try {
                new MenuGUI().start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox infoPanel = new VBox(5);
        infoPanel.setBackground(new Background(new BackgroundFill(TRANSPARENT_DARK, ROUNDED_RADIUS, NO_PADDING)));
        infoPanel.setPrefWidth(200);
        infoPanel.setMaxHeight(50);
        infoPanel.setAlignment(Pos.CENTER);

        HBox directionBox = new HBox(5);
        HBox colorBox = new HBox(5);
        directionBox.setAlignment(Pos.CENTER);
        colorBox.setAlignment(Pos.CENTER);

        Label dirLabel = new Label("DIRECTION : ");
        Label dirLabelV = new Label(gc.getDirection() == 1 ? "C/Clockwise" : "Clockwise");

        Label colorLabel = new Label("COLOR : ");
        Label colorLabelV = new Label(gc.getPile().get(0).getColor());

        setFontWhite(dirLabel, 12);
        setFontWhite(colorLabel, 12);
        setFontWhite(colorLabelV, 12);
        setFontWhite(dirLabelV, 12);

        directionBox.getChildren().addAll(dirLabel, dirLabelV);
        colorBox.getChildren().addAll(colorLabel, colorLabelV);
        infoPanel.getChildren().addAll(directionBox, colorBox);

        infoPanel.setMouseTransparent(true);
        HBox infoWrapper = new HBox(10);
        infoWrapper.setAlignment(Pos.TOP_CENTER);
        infoWrapper.setPadding(new Insets(0, 500, 0, 0));
        infoWrapper.getChildren().addAll(backLabel, infoPanel);
        infoWrapper.setPickOnBounds(false);

        centerPane.getChildren().add(infoWrapper);
        StackPane.setAlignment(infoWrapper, Pos.TOP_CENTER);
        StackPane.setMargin(infoWrapper, new Insets(30, 0, 0, 0));

        infoLabels.add(dirLabelV);
        infoLabels.add(colorLabelV);
        return infoLabels;
    }

    private void createDrawButton(GameController gameController, HBox playerBox, HBox centerBox) {
        Button drawButton = new Button();
        drawButton.setCursor(javafx.scene.Cursor.HAND);
        drawButton.setMinSize(80, 120);
        Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cards/default.png")));
        ImageView defaultImageView = new ImageView(defaultImage);
        drawButton.setGraphic(defaultImageView);
        centerBox.getChildren().add(drawButton);
        drawButton.setBackground(Background.EMPTY);
        drawButton.setBorder(Border.EMPTY);
        drawButton.setPadding(new Insets(30));
        drawButton.setBackground(new Background(new BackgroundFill(TRANSPARENT_DARK, new CornerRadii(0, 20, 20, 0, false), Insets.EMPTY)));

        drawButton.setOnAction(actionEvent -> {
            if (!gameController.getCurrentPlayerObject().isBot() && !drew) {
                Card drawnCard = gameController.drawFromDeck();
                gameController.getCurrentPlayerObject().drawCard(drawnCard);
                refreshHand(gameController, gameController.getCurrentPlayerObject(), playerBox);
                drew = true;
            }
        });
    }

    public void autoRefreshHands(Player current, HBox topBox, VBox leftBox, VBox rightBox, HBox playerBox, GameController gc) {
        int index = gc.getPlayers().indexOf(current);
        int size = gc.getPlayers().size();

        if (current.isBot()) {
            if (size == 2 && index == 1) {
                refreshBotUI(topBox, current,defaultPath,40,60);
            } else if (size == 3) {
                if (index == 1) refreshBotUI(rightBox, current,defaultPathInverted,60,40);
                else if (index == 2) refreshBotUI(topBox, current,defaultPath,40,60);
            } else if (size == 4) {
                if (index == 1) refreshBotUI(rightBox, current,defaultPathInverted,60,40);
                else if (index == 2) refreshBotUI(topBox, current,defaultPath,40,60);
                else if (index == 3) refreshBotUI(leftBox, current,defaultPathInverted,60,40);
            }
        } else {
            refreshHand(gc, current, playerBox);
        }
    }

    public void refreshHand(GameController gameController, Player player, HBox playerBox) {
        playerBox.getChildren().clear();
        for (Card card : player.getHand()) {
            Button button = new Button();
            button.setCursor(javafx.scene.Cursor.HAND);
            button.setMinSize(80, 120);
            String imagePath = "/cards/" + card.getNumber() + card.getColor() + card.getType() + ".png";
            Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView cardImageView = new ImageView(cardImage);
            StackPane imagePane = new StackPane(cardImageView);
            button.setGraphic(imagePane);
            button.setBackground(Background.EMPTY);
            button.setBorder(Border.EMPTY);
            button.setPadding(new Insets(10));
            playerBox.getChildren().add(button);
            button.setOnAction(actionEvent -> {
                if (Rules.isPlayable(card, gameController.getTopCard(), gameController) && !gameController.getCurrentPlayerObject().isBot()) {
                    selectedCard = card;
                    selectedButton = button;
                } else {
                    System.out.println("Can't play that card.");
                }
            });
        }
    }

    public void refreshBotUI(Pane container, Player bot, String cardImagePath, double width, double height) {
        container.getChildren().clear();

        for (int i = 0; i < bot.getHand().size(); i++) {
            Button button = new Button();
            Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cardImagePath)));
            ImageView cardImageView = new ImageView(cardImage);
            button.setGraphic(cardImageView);
            button.setPrefSize(width, height);
            button.setBorder(Border.EMPTY);
            button.setPadding(new Insets(5));
            button.setBackground(new Background(new BackgroundFill(SECONDARY_COLOR, new CornerRadii(10), Insets.EMPTY)));
            container.getChildren().add(button);
        }
    }

    private Button drawTopCard(GameController gameController, HBox centerBox) {
        Card topCard = gameController.getPile().get(gameController.getPile().size() - 1);
        Button button = new Button();
        button.setMinSize(80, 120);
        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cards/" + topCard.getNumber() + topCard.getColor() + topCard.getType() + ".png")));
        ImageView cardImageView = new ImageView(cardImage);
        button.setGraphic(cardImageView);
        button.setBackground(Background.EMPTY);
        button.setBorder(Border.EMPTY);
        centerBox.getChildren().add(button);
        return button;
    }

    private void addBoxes(BorderPane root, StackPane centerBox, HBox topBox, VBox leftBox, VBox rightBox, Node bottomNode) {
        root.setCenter(centerBox);
        root.setTop(topBox);
        root.setLeft(leftBox);
        root.setRight(rightBox);
        root.setBottom(bottomNode);
    }

    private void showHands(GameController gc, HBox topBox, VBox leftBox, VBox rightBox) {
        int playerCount = gc.getPlayers().size();
        if (playerCount == 2) {
            refreshBotUI(topBox, gc.getPlayers().get(1),defaultPath,40,60);
        } else if (playerCount == 3) {
            refreshBotUI(rightBox, gc.getPlayers().get(1),defaultPathInverted,60,40);
            refreshBotUI(topBox, gc.getPlayers().get(2),defaultPath,40,60);
        } else {
            refreshBotUI(rightBox, gc.getPlayers().get(1),defaultPathInverted,60,40);
            refreshBotUI(topBox, gc.getPlayers().get(2),defaultPath,40,60);
            refreshBotUI(leftBox, gc.getPlayers().get(3),defaultPathInverted,60,40);
        }
    }
}