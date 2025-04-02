import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Leader> leaders;
    private Leader currentLeader;
    private int currentLevelIndex;
    private int score;
    private List<Choice> playerChoices;

    public Game(List<Leader> leaders) {
        this.leaders = leaders;
        this.currentLevelIndex = 0;
        this.score = 0;
        this.playerChoices = new ArrayList<>();
    }

    public void selectLeader(int leaderIndex) {
        if (leaderIndex >= 0 && leaderIndex < leaders.size()) {
            this.currentLeader = leaders.get(leaderIndex);
        }
    }

    public Leader getCurrentLeader() {
        return currentLeader;
    }

    public Level getCurrentLevel() {
        if (currentLeader != null && currentLevelIndex < currentLeader.getLevels().size()) {
            return currentLeader.getLevels().get(currentLevelIndex);
        }
        return null;
    }

    public void makeChoice(int choiceIndex) {
        Level level = getCurrentLevel();
        if (level != null && choiceIndex >= 0 && choiceIndex < level.getChoices().length) {
            Choice choice = level.getChoices()[choiceIndex];
            score += choice.getScoreValue();
            playerChoices.add(choice);
            currentLevelIndex++;
        }
    }

    public int getScore() {
        return score;
    }

    public int getMaxScore() {
        return currentLeader != null ? currentLeader.getLevels().size() : 0;
    }

    public boolean isGameOver() {
        return currentLeader != null && currentLevelIndex >= currentLeader.getLevels().size();
    }

    public String getFinalSummary() {
        StringBuilder summary = new StringBuilder("Game Over!\n");
        summary.append("Leader: ").append(currentLeader.getName()).append("\n");
        summary.append("Score: ").append(score).append("/").append(getMaxScore()).append("\n");
        summary.append("Choices Made:\n");
        for (int i = 0; i < playerChoices.size(); i++) {
            summary.append("Level ").append(i + 1).append(": ")
                    .append(playerChoices.get(i).getText()).append("\n");
        }
        return summary.toString();
    }

    public List<Leader> getLeaders() {
        return leaders;
    }

    // New: Bubble Sort to sort leaders by name
    public void sortLeaders() {
        for (int i = 0; i < leaders.size() - 1; i++) {
            for (int j = 0; j < leaders.size() - i - 1; j++) {
                if (leaders.get(j).getName().compareTo(leaders.get(j + 1).getName()) > 0) {
                    Leader temp = leaders.get(j);
                    leaders.set(j, leaders.get(j + 1));
                    leaders.set(j + 1, temp);
                }
            }
        }
    }

    // New: Linear Search to find a level by keyword in description
    public Level searchLevel(String keyword) {
        for (Leader leader : leaders) {
            for (Level level : leader.getLevels()) {
                if (level.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    return level;
                }
            }
        }
        return null; // Return null if no match found
    }
}