import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Leader> leaders;
    private Leader currentLeader;
    private Level currentLevel;
    private int currentLevelIndex;
    private int score;
    private ConsoleUI ui;
    private List<String> playerChoices;

    public Game(List<Leader> leaders, ConsoleUI ui) {
        this.leaders = leaders;
        this.ui = ui;
        this.score = 0;
        this.currentLevelIndex = 0;
        this.playerChoices = new ArrayList<>();
    }

    public void start() {
        ui.displayWelcomeMessage();
        ui.searchLevels(leaders);
        boolean keepPlaying = true;

        while (keepPlaying) {
            currentLeader = ui.selectLeader(leaders);
            score = 0;
            currentLevelIndex = 0;
            playerChoices.clear();

            playRound();

            displayChoicesSummary();
            String choice = ui.displayEndOfRoundOptions(score, currentLeader.getLevels().size());
            switch (choice) {
                case "1":
                    continue;
                case "2":
                    keepPlaying = false;
                    break;
            }
        }

        ui.displayGoodbyeMessage();
    }

    private void playRound() {
        int totalLevels = currentLeader.getLevels().size();
        while (currentLevelIndex < totalLevels) {
            currentLevel = currentLeader.getLevels().get(currentLevelIndex);
            ui.displayLevel(currentLevel);
            int choiceIndex = ui.getPlayerChoice() - 1;
            makeChoice(choiceIndex);
            currentLevelIndex++;
            displayProgressBar(score, totalLevels);
        }
    }

    private void makeChoice(int choiceIndex) {
        Choice selectedChoice = currentLevel.getChoices().get(choiceIndex);
        boolean isCorrect = selectedChoice.isHistorical();
        String choiceText = "Level " + currentLevel.getNumber() + ": You chose '" + selectedChoice.getText() + "' (" + (isCorrect ? "Correct" : "Incorrect") + ")";
        playerChoices.add(choiceText);

        if (isCorrect) {
            score++;
            System.out.println(ConsoleUI.ANSI_GREEN + "Correct! This was the historical choice." + ConsoleUI.ANSI_RESET);
        } else {
            System.out.println(ConsoleUI.ANSI_RED + "Incorrect. This was not the historical choice." + ConsoleUI.ANSI_RESET);
        }
        System.out.println(currentLevel.getSummary());
    }

    private void displayProgressBar(int score, int total) {
        int barWidth = 20;
        int progress = (int) ((double) score / total * barWidth);
        int percentage = (int) ((double) score / total * 100);

        StringBuilder bar = new StringBuilder(ConsoleUI.ANSI_CYAN + "Progress: [");
        for (int i = 0; i < barWidth; i++) {
            if (i < progress) {
                bar.append("█");
            } else {
                bar.append(" ");
            }
        }
        bar.append("] ").append(percentage).append("%").append(ConsoleUI.ANSI_RESET);
        System.out.println(bar.toString());
        System.out.println();
    }

    private void displayChoicesSummary() {
        System.out.println(ConsoleUI.ANSI_PURPLE + "\n=========================================");
        System.out.println("           Your Choices Summary          ");
        System.out.println("=========================================" + ConsoleUI.ANSI_RESET);
        for (String choice : playerChoices) {
            System.out.println(choice);
        }
        System.out.println();
    }

    public int getScore() {
        return score;
    }
}