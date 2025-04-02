import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Game game;
    private Scanner scanner;

    // ANSI color codes for console output
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public ConsoleUI(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        printHeader("Welcome to Echoes of Command!");
        game.sortLeaders();
        previewLevel();
        selectLeader();
        playGame();
    }

    private void selectLeader() {
        printSectionHeader("Select a Leader");
        List<Leader> leaders = game.getLeaders();
        for (int i = 0; i < leaders.size(); i++) {
            System.out.println(CYAN + (i + 1) + ". " + leaders.get(i).getName() +
                    RESET + " - " + leaders.get(i).getBackstory());
        }
        System.out.print(YELLOW + "Enter number (1-" + leaders.size() + "): " + RESET);
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // Clear buffer
        game.selectLeader(choice);
        System.out.println(GREEN + "\nSelected: " + game.getCurrentLeader().getName() + RESET);
    }

    private void playGame() {
        while (!game.isGameOver()) {
            Level level = game.getCurrentLevel();
            printSectionHeader("Level " + level.getNumber() + " - " + game.getCurrentLeader().getName());
            System.out.println(CYAN + "Situation:" + RESET);
            System.out.println(wrapText(level.getDescription(), 80));
            System.out.println(YELLOW + "Choices:" + RESET);
            System.out.println("  1. " + level.getChoices()[0].getText());
            System.out.println("  2. " + level.getChoices()[1].getText());
            System.out.print(YELLOW + "Choose (1-2): " + RESET);
            int choice = scanner.nextInt() - 1;
            scanner.nextLine(); // Clear buffer
            game.makeChoice(choice);
            System.out.println(GREEN + "\nHistorical Summary:" + RESET);
            System.out.println(wrapText(level.getSummary(), 80));
            displayProgressBar();
        }
        printSectionHeader("Game Over");
        System.out.println(wrapText(game.getFinalSummary(), 80));
    }

    private void displayProgressBar() {
        int score = game.getScore();
        int max = game.getMaxScore();
        System.out.print(YELLOW + "Historical Accuracy: [" + RESET);
        for (int i = 0; i < max; i++) {
            System.out.print(i < score ? GREEN + "*" + RESET : RED + "-" + RESET);
        }
        System.out.println(YELLOW + "] " + score + "/" + max + RESET);
    }

    private void previewLevel() {
        printSectionHeader("Level Preview");
        System.out.print(YELLOW + "Enter a keyword to preview a level (or press Enter to skip): " + RESET);
        String keyword = scanner.nextLine();
        if (!keyword.trim().isEmpty()) {
            Level found = game.searchLevel(keyword);
            if (found != null) {
                System.out.println(GREEN + "Found: Level " + found.getNumber() + ":" + RESET);
                System.out.println(wrapText(found.getDescription(), 80));
                System.out.println(YELLOW + "Choices:" + RESET);
                System.out.println("  1. " + found.getChoices()[0].getText());
                System.out.println("  2. " + found.getChoices()[1].getText());
            } else {
                System.out.println(RED + "No level found with keyword '" + keyword + "'." + RESET);
            }
        }
    }

    // Helper: Print a section header with borders
    private void printHeader(String title) {
        int width = 50;
        String border = "=".repeat(width);
        System.out.println(CYAN + BOLD + border + RESET);
        System.out.println(CYAN + BOLD + centerText(title, width) + RESET);
        System.out.println(CYAN + BOLD + border + RESET);
    }

    private void printSectionHeader(String title) {
        int width = 50;
        String border = "-".repeat(width);
        System.out.println("\n" + YELLOW + border + RESET);
        System.out.println(YELLOW + centerText(title, width) + RESET);
        System.out.println(YELLOW + border + RESET);
    }

    // Helper: Center text within a given width
    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        if (padding <= 0) return text;
        return " ".repeat(padding) + text + " ".repeat(padding + (width - text.length()) % 2);
    }

    // Helper: Wrap text to a specified width
    private String wrapText(String text, int width) {
        StringBuilder wrapped = new StringBuilder();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() + 1 > width) {
                wrapped.append(line.toString().trim()).append("\n");
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }
        if (line.length() > 0) {
            wrapped.append(line.toString().trim());
        }
        return wrapped.toString();
    }
}