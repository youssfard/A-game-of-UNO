package com.example.unogame;

import com.example.unogame.CardEffects.CardEffect;
import com.example.unogame.GUIs.GUI;
import com.example.unogame.GUIs.WinScreen;
import com.example.unogame.Utils.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class GameController {

    private List<Card> pile;
    private List<Player> players;

    private Deck deck;

    private int currentPlayer;
    private int direction;
    private GUI gui;
    private Settings settings;
    private boolean gameOver;
    private int draw2Stack;
    private int draw4stack;

    public GameController() {
        pile = new ArrayList<>();
        players = new ArrayList<>();
        direction = 1;
        gameOver = false;
        draw2Stack = 0;
        draw4stack = 0;
    }


    public Card getTopCard() {
        return pile.get(pile.size() - 1);
    }

    public List<Card> getPile() {
        return pile;
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer + direction + players.size()) % players.size();
    }
    public Player getCurrentPlayerObject() {
        return players.get(currentPlayer);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getDraw4stack(){
        return draw4stack;
    }

    public void setDraw4stack(int draw4stack){
        this.draw4stack = draw4stack;
    }


    public int getDraw2Stack() {
        return draw2Stack;
    }

    public void setDraw2Stack(int draw2Stack) {
        this.draw2Stack = draw2Stack;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
        this.deck = new Deck(settings);
    }

    public Settings getSettings() {
        return settings;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GUI getGUI(){
        return gui;
    }

    public void setGUI(GUI gui){
        this.gui = gui;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void addPlayers(){
        Player player = new Human("Youssef");
        players.add(player);

        for (int i = 1; i <= settings.getBots(); i++) {
            players.add(new Bot("Bot" + i));
        }
    }

    public void startGame() {
        currentPlayer = 0;
        System.out.println("Game started");
        Card drawnCard = drawFromDeck();
        while(drawnCard.getType().equals("wild") || drawnCard.getType().equals("wildDraw") || drawnCard.getType().equals("Swap")) {
            deck.addCardInDeck(drawnCard);
            drawnCard = drawFromDeck();
        }
        pile.add(drawnCard);

        for(Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCard(drawFromDeck());
            }
        }
        applyFirstCardAction(drawnCard);
    }

    public Card drawFromDeck() {
        Card card = deck.removeCard();
        return card;
    }

    public List<Card> drawFromDeck(int count){
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            Card card = deck.removeCard();
            cards.add(card);
        }
        return cards;
    }

    public void playCard(Card card) {
        Player player = players.get(currentPlayer);
        player.removeCard(card);
        pile.add(card);
    }

    public void applyFirstCardAction(Card card) {
        if(card.getType().equals("Skip")) {
            this.nextTurn();
        }else if(card.getType().equals("Reverse")) {
            if(this.getPlayers().size() ==2){
                this.nextTurn();
            }else{
                direction *= -1;
            }
        }else if(card.getType().equals("Draw2")) {
            Player victim = getCurrentPlayerObject();
            victim.drawCards(this.drawFromDeck(2));

            this.nextTurn();
        }
    }


    public void applyActionCards(Card card, GUI gui, HBox topBox, HBox playerBox, Button pileButton, VBox leftBox, VBox rightBox, BorderPane root) {
        Player current = this.getCurrentPlayerObject();
        CardEffect effect = card.getEffect();
        if(effect!=null){
            effect.applyEffect(this,card,pileButton,playerBox,topBox,leftBox,rightBox,root);
        }else{
            this.playCard(card);
            this.nextTurn();
        }
        gui.autoRefreshHands(current,topBox,leftBox,rightBox,playerBox,this);
        checkDeck(deck);
        if(current instanceof Human){
            ((Human)current).checkUnoStatus(current,gui,playerBox,this);
        }
        checkWinner(current);
    }

    private void checkDeck(Deck deck) {
        if (deck.getCards().isEmpty()) {
            List<Card> pile = this.getPile();
            Card topCard = pile.get(pile.size() - 1);

            List<Card> refillCards = new ArrayList<>();
            for (int i = 0; i < pile.size() - 1; i++) {
                refillCards.add(pile.get(i));
            }
            pile.clear();
            pile.add(topCard);

            Collections.shuffle(refillCards);
            for (Card card : refillCards) {
                deck.addCardInDeck(card);
            }
        }
    }

    private void checkWinner(Player player){
        if(player.getHand().isEmpty()) {
            Stage stage = gui.getStage();
            WinScreen winScreen = new WinScreen();
            winScreen.setName(player.getName());
            winScreen.setSettings(this.getSettings());
            winScreen.show(stage);
            this.setGameOver(true);
        }
    }

}