public class Choice {
    private String text;
    private boolean isHistorical;

    public Choice(String text, boolean isHistorical) {
        this.text = text;
        this.isHistorical = isHistorical;
    }

    public String getText() {
        return text;
    }

    public boolean isHistorical() {
        return isHistorical;
    }

    public int getScoreValue() {
        return isHistorical ? 1 : 0;
    }
}
