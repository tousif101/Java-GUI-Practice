/*
 * ConcentrationModel.java
 *
 * This application uses ava.util.Observer notifications.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

import javafx.scene.image.Image;

/**
 * Definition for the model of a concentration card game.
 *
 * @author Arthur Nunes-Harwitt
 * @author ben k steele
 */

public class ConcentrationModel extends Observable { // java.util swingway

    /**
     * The total number of cards including duplicates
     */
    public static final int NUM_CARDS = 16;

    /**
     * The number of pairs.
     */
    public static final int NUM_PAIRS = NUM_CARDS / 2;

    /**
     * The undo stack for the game tracks pairing selections in progress.
     * The undo stack support the undo operation to undo 1 or 2 cards
     * if there are any card flips to undo.
     */
    private ArrayList<Card> undoStack;

    /**
     * There are NUM_PAIRS cards each of which contains an image
     * loaded from a file.
     */
    private ArrayList<Card> cards;

    /**
     * Store the number of moves made in the game.
     * A move is a card selection.
     */
    private int moveCount;

    /**
     * Construct a ConcentrationModel; there is only one configuration.
     */
    public ConcentrationModel() {

        this.cards = new ArrayList<Card>();

        for (int n = 0; n < NUM_PAIRS; ++n) {
            Card card1 = new Card(n, true);
            Card card2 = new Card(n, true);
            this.cards.add(card1);
            this.cards.add(card2);
        }
        this.reset();
    }

    /**
     * Push a card onto the undo stack.
     *
     * @param card The card to push.
     */
    private void push(Card card) {
        undoStack.add(card);
    }

    /**
     * Pop a card from the undo stack.
     *
     * @param toggle Flag indicates whether or not the state
     *               of the card will enable toggling or not.
     *               If the toggle is true, the card will be flipped and
     *               further flipping of the card will be permitted.
     *               If the toggle is false, the card will not be flipped and
     *               further flipping of the card will be prohibited until a reset.
     */
    private void pop(boolean toggle) {
        int s = undoStack.size();
        if (s > 0) {
            Card card = undoStack.get(s - 1);
            undoStack.remove(s - 1);
            if (toggle) {
                // re-enable flipping this card.
                card.toggleFace(toggle);
            }
        }
    }

    /**
     * Pop a card from the undo stack. (There are no parameters.)
     */
    private void pop() {
        pop(false);
    }

    /**
     * Undo selecting a card.
     */
    public void undo() {
        pop(true);
        // java.util swingway notification
        setChanged();
        notifyObservers();
    }

    /**
     * Turn over a card.
     *
     * @param n An integer referring to the nth card.
     */
    private void add(int n) {
        Card card = cards.get(n);
        if (!card.isFaceUp()) {
            card.toggleFace();
            push(card);
            ++this.moveCount;
        }
    }

    /**
     * Check to see if the two cards on the top of the undo stack have
     * the same value, and pop them off the undo stack if they match.
     */
    private void checkMatch() {
        if (undoStack.size() == 2 &&
                undoStack.get(0).getNumber() == undoStack.get(1).getNumber()) {
            pop();
            pop();
        }
    }

    /**
     * Select a card to turn face up from cards.
     * If there are already two cards selected, turn those back over.
     *
     * @param n An integer referring to the nth card.
     */
    public void selectCard(int n) {

        if (0 <= n && n < NUM_CARDS) {
            switch (undoStack.size()) {
                case 2:
                    undo();
                    undo();
                case 0:
                    add(n);
                    break;
                case 1:
                    add(n);
                    checkMatch();
                    break;
                default:
                    throw new RuntimeException(
                            "Internal Error: undoStack too big.");
            }
            // java.util swingway notification
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Get the cards.
     *
     * @return An ArrayList containing the cards on the board.
     */
    public ArrayList<Card> getCards() {
        ArrayList<Card> faces = new ArrayList<Card>();

        for (Card card : cards) {
            faces.add(card);
        }
        return faces;
    }

    /**
     * Get the cards showing them all.
     *
     * @return An ArrayList containing the cards on the board.
     */
    public ArrayList<Card> cheat() {
        ArrayList<Card> faces = new ArrayList<Card>();

        for (Card card : cards) {
            faces.add(card);
        }
        return faces;
    }

    /**
     * Get the number of moves, i.e., the count of card selections.
     *
     * @return An integer that represents the number of moves.
     */
    public int getMoveCount() {
        return this.moveCount;
    }

    /**
     * Reset the board.  All the cards are turned face-down and are
     * shuffled.  The undo stack and the number of moves are cleared.
     */
    public void reset() {

        for (Card card : cards) {
            if (card.isFaceUp()) {
                card.toggleFace(true);
            }
        }
        Collections.shuffle(cards);

        this.undoStack = new ArrayList<Card>();

        this.moveCount = 0;

        // java.util swingway notification
        setChanged();
        notifyObservers();
    }

    /**
     * Return the number of cards currently selected.
     *
     * @return An integer that represents the number of cards
     * selected.
     */
    public int howManyCardsUp() {
        return undoStack.size();
    }
}

