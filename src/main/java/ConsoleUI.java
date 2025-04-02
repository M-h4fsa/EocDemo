import java.util.Scanner;
import java.util.List;

public class ConsoleUI {
    private Game game;
    private Scanner scanner;

    public ConsoleUI(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to Echoes of Command!");
        game.sortLeaders(); // Sort leaders alphabetically
        previewLevel();     // New: Search for a level
        selectLeader();
        playGame();
    }

    private void selectLeader() {
        System.out.println("\nSelect a leader:");
        List<Leader> leaders = game.getLeaders();
        for (int i = 0; i < leaders.size(); i++) {
            System.out.println((i + 1) + ". " + leaders.get(i).getName() +
                    " - " + leaders.get(i).getBackstory());
        }
        System.out.print("Enter number (1-" + leaders.size() + "): ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // Clear buffer
        game.selectLeader(choice);
        System.out.println("\nSelected: " + game.getCurrentLeader().getName());
    }

    private void playGame() {
        while (!game.isGameOver()) {
            Level level = game.getCurrentLevel();
            System.out.println("\nLevel " + level.getNumber() + ": " + level.getDescription());
            System.out.println("1. " + level.getChoices()[0].getText());
            System.out.println("2. " + level.getChoices()[1].getText());
            System.out.print("Choose (1-2): ");
            int choice = scanner.nextInt() - 1;
            scanner.nextLine(); // Clear buffer
            game.makeChoice(choice);
            System.out.println("\nHistorical Summary: " + level.getSummary());
            displayProgressBar();
        }
        System.out.println("\n" + game.getFinalSummary());
    }

    private void displayProgressBar() {
        int score = game.getScore();
        int max = game.getMaxScore();
        System.out.print("Historical Accuracy: [");
        for (int i = 0; i < max; i++) {
            System.out.print(i < score ? "*" : "-");
        }
        System.out.println("] " + score + "/" + max);
    }

    // New: Search for a level by keyword
    private void previewLevel() {
        System.out.print("\nEnter a keyword to preview a level (or press Enter to skip): ");
        String keyword = scanner.nextLine();
        if (!keyword.trim().isEmpty()) {
            Level found = game.searchLevel(keyword);
            if (found != null) {
                System.out.println("Found: Level " + found.getNumber() + ": " + found.getDescription());
                System.out.println("Choices: 1. " + found.getChoices()[0].getText() +
                        "  2. " + found.getChoices()[1].getText());
            } else {
                System.out.println("No level found with keyword '" + keyword + "'.");
            }
        }
    }
}
