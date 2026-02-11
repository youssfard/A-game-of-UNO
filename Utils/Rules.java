package com.example.unogame.Utils;

import com.example.unogame.GameController;

import java.util.List;

public class Rules {
    public static boolean isPlayable(Card card, Card topCard, GameController gc) {

        if(card.getType().equals("Swap") || card.getType().equals("Steal") || card.getType().equals("Judgement")) {
            return true;
        }

        if (card.getType().equals("wild")) {
            return true;
        }

        if (card.getType().equals("wildDraw")) {
            if(gc.getSettings().getStacking4s() && gc.getDraw4stack()>0){
                return true;
            }else{
                if(noPlayableCards(gc.getCurrentPlayerObject(), topCard,gc)){
                    return true;
                }
            }

        }

        if (card.getColor().equals(topCard.getColor())) {
            return true;
        }

        if (card.getType().equals("normal") && topCard.getType().equals("normal")
                && card.getNumber().equals(topCard.getNumber())) {
            return true;
        }

        if (!card.getType().equals("normal") && card.getType().equals(topCard.getType())) {
            return true;
        }

        return false;


    }
    private static boolean noPlayableCards(Player player, Card topCard, GameController gc) {
        boolean noCards = true;
        List<Card> hand = player.getHand();
        for(Card c : hand) {
            if(!c.getType().equals("wildDraw") && isPlayable(c,topCard,gc)) {
                noCards = false;
            }
        }
        return noCards;
    }


}
