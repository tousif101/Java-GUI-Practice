package model;

public class Card {
    /**
     * The number on the card.
     */
    protected final int number;
    /**
     * The flag indicating whether or not the card is face-up.
     */
    protected boolean isFaceUp;
    /**
     * canFlip flag indicates whether the card can be flipped face-up.
     */
    protected boolean canFlip;

    public Card(int number, boolean canFlip) {
        this.number = number;
        this.isFaceUp = false;
        this.canFlip = canFlip;
    }

    /**
     * @return A boolean indicating whether or not card is face-up.
     */

    public boolean isFaceUp() {
        return this.isFaceUp;
    }

    /**
     * @return An integer that is the number on the face of the card.
     */

    public int getNumber() {
        return this.number;
    }

    /**
     * Toggle the flag indicating whether or not the card is face-up.
     * It only toggles the state of the card if this card allows flipping.
     */

    public void toggleFace() {
        if (this.canFlip) {
            this.isFaceUp = !this.isFaceUp;
        }
    }

    /**
     * This method sets canFlip and toggles face up/down if canFlip is true.
     * If canFlip is false, then no card flipping is performed until
     * canFlip is a true value.
     * It only toggles the state of the card if this card allows flipping.
     *
     * @param canFlip a true value allows the card to be flipped face up.
     */

    public void toggleFace(boolean canFlip) {
        this.canFlip = canFlip;
        toggleFace();
    }

    /**
     * @return number indicator or blank-out string when face is down.
     */

    public String toString() {
        if (this.isFaceUp()) {
            return "-" + this.number + "-";
        } else {
            return "***";
        }
    }
}
