package com.example.unogame.Utils;


import com.example.unogame.CardEffects.*;
import com.example.unogame.CardEffects.JudgmentEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;

    public Deck(Settings settings) {
        deck = new ArrayList<>();
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        String[] actions = {"Reverse","Skip","Draw2"};

        for (String color : colors) {
            deck.add(new Card(0, color, "normal",null)); // One 0 per color

            for (int i = 1; i < 10; i++) {
                deck.add(new Card(i, color, "normal",null));
                deck.add(new Card(i, color, "normal",null));
            }

            for (String action : actions) {
                CardEffect effect = null;

                if (action.equals("Draw2")) {
                    effect = new Draw2Effect();
                } else if (action.equals("Skip")) {
                    effect = new SkipEffect();
                } else if (action.equals("Reverse")) {
                    effect = new ReverseEffect();
                }

                deck.add(new Card(-1, color, action, effect));
                deck.add(new Card(-1, color, action, effect));
            }
        }

        for (int i = 0; i < 4; i++) {
            if(settings.getSwapCards()){
                deck.add(new Card(-1,"none", "Swap", new SwapEffect()));
            }
            if(settings.getStealCards()){
                deck.add(new Card(-1,"none", "Steal", new StealEffect()));
            }
            if(settings.getJudgementCards()){
                deck.add(new Card(-1,"none", "Judgement", new JudgmentEffect()));
            }
            deck.add(new Card(-1, "none", "wild",new WildEffect()));
            deck.add(new Card(-1, "none", "wildDraw",new WildDrawEffect()));
        }

        Collections.shuffle(deck);
    }

    public Card removeCard() {
        return deck.remove(deck.size() - 1);
    }
    public void addCardInDeck(Card card) {
        deck.add(0,card);
    }

    public List<Card> getCards() {
        return deck;
    }

    public int size() {
        return deck.size();
    }
}
