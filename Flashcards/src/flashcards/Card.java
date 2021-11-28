package flashcards;

public class Card {
    private String term;
    private String definition;
    private int mistakesMade = 0;

    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public Card(String term, String definition, int mistakesMade) {
        this.term = term;
        this.definition = definition;
        this.mistakesMade = mistakesMade;
    }

    public void mistakeWasMade() {
        mistakesMade++;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public int getMistakesMade() {
        return mistakesMade;
    }

    public void setMistakesMade(int mistakesMade) {
        this.mistakesMade = mistakesMade;
    }

}
