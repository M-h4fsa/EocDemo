public class Level {
    private int number;
    private String description;
    private Choice[] choices;
    private String summary;

    public Level(int number, String description, Choice[] choices, String summary) {
        this.number = number;
        this.description = description;
        this.choices = choices;
        this.summary = summary;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Choice[] getChoices() {
        return choices;
    }

    public String getSummary() {
        return summary;
    }
}