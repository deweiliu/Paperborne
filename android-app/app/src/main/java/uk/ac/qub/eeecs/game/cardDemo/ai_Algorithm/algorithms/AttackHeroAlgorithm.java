package uk.ac.qub.eeecs.game.cardDemo.ai_Algorithm.algorithms;

import java.util.ArrayList;
import java.util.Comparator;

import uk.ac.qub.eeecs.game.cardDemo.Cards.Card;
import uk.ac.qub.eeecs.game.cardDemo.Hero;
import uk.ac.qub.eeecs.game.cardDemo.ai_Algorithm.AIDecision;

/**
 * Created by 40216004 Dewei Liu on 08/03/2018.
 */

public class AttackHeroAlgorithm extends AlgorithmSuperclass {
    public AttackHeroAlgorithm(Hero humanPlayer, Hero AIPlayer) {
        super(humanPlayer, AIPlayer);
    }

    private Card attacker;

    @Override
    public final int actionNumber() {
        return AIDecision.ATTACK_HERO;
    }

    public Card getAttacker() {
        super.checkValid_ThrowException();
        return this.attacker;
    }

    /*******************************************************************************************/
    @Override
    protected final void algorithm() {
        ArrayList<Card> myBoardCards = super.getMyBoardCards();
        ArrayList<Card3> cards = new ArrayList<>();
        for (Card each : myBoardCards) {
            if (!each.isFinishedMove()) {

                //If the card is valid to attack hero, add it into the list for later decision
                Card3 card = new Card3(each);
                cards.add(card);
            }
        }

        //If no cards is valid to attack
        if (cards.isEmpty()) {
            super.isValid = false;
        } else {

            //The computer has the possibility of 20% to make a mistake accidentally
            if (random.nextInt(5) == 0) {
                super.isValid = false;
            } else {
                MyComparator comparator = new MyComparator();

                //Sort in descendant order by the attack value
                cards.sort(comparator);

                //So I decide to attack with the most powerful card
                attacker = cards.get(0).getCard();
                super.isValid = true;
            }
        }
    }

    private class MyComparator implements Comparator<Card3> {

        @Override
        public int compare(Card3 card3, Card3 t1) {
            if (card3.getAttackValue() < t1.getAttackValue()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private class Card3 {
        private int attackValue;
        private Card card;

        public Card3(Card card) {
            this.card = card;
            this.attackValue = card.getAttackValue();
        }

        public int getAttackValue() {
            return attackValue;
        }

        public Card getCard() {
            return card;
        }
    }
}
