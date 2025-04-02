import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println(ANSI_CYAN + "=========================================");
        System.out.println("      Welcome to Echoes of Command!      ");
        System.out.println("=========================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Step into the shoes of WWII leaders and make historical decisions.");
        System.out.println("Choose wisely—your decisions will shape history.\n" + ANSI_RESET);
    }

    public void displayGoodbyeMessage() {
        System.out.println(ANSI_CYAN + "\n=========================================");
        System.out.println("   Thank you for playing Echoes of Command!   ");
        System.out.println("=========================================" + ANSI_RESET);
    }

    public void searchLevels(List<Leader> leaders) {
        System.out.println(ANSI_PURPLE + "Would you like to search for a level before starting?" + ANSI_RESET);
        System.out.println("Enter a keyword to search for a level (e.g., 'D-Day', '1940'), or press Enter to skip.");
        System.out.print(ANSI_GREEN + "Search keyword: " + ANSI_RESET);
        String searchTerm = scanner.nextLine().trim();

        if (searchTerm.isEmpty()) {
            System.out.println(ANSI_CYAN + "Skipping search...\n" + ANSI_RESET);
            return;
        }

        searchTerm = searchTerm.toLowerCase();
        List<String> results = new ArrayList<>();
        for (Leader leader : leaders) {
            for (Level level : leader.getLevels()) {
                if (level.getDescription().toLowerCase().contains(searchTerm)) {
                    StringBuilder result = new StringBuilder();
                    result.append(ANSI_BLUE + "Leader: " + leader.getName() + ANSI_RESET + "\n");
                    result.append(ANSI_YELLOW + "Level " + level.getNumber() + ": " + level.getDescription() + ANSI_RESET + "\n");
                    result.append("Choices:\n");
                    result.append("  1. " + level.getChoices().get(0).getText() + "\n");
                    result.append("  2. " + level.getChoices().get(1).getText() + "\n");
                    results.add(result.toString());
                }
            }
        }

        if (results.isEmpty()) {
            System.out.println(ANSI_RED + "\nNo levels found matching '" + searchTerm + "'.\n" + ANSI_RESET);
        } else {
            System.out.println(ANSI_CYAN + "\n=========================================");
            System.out.println("            Search Results               ");
            System.out.println("=========================================" + ANSI_RESET);
            for (String result : results) {
                System.out.println(result);
                System.out.println(ANSI_CYAN + "-----------------------------------------");
            }
            System.out.println(ANSI_RESET);
        }

        System.out.println(ANSI_GREEN + "Press Enter to continue to leader selection..." + ANSI_RESET);
        scanner.nextLine();
    }

    public Leader selectLeader(List<Leader> leaders) {
        List<Leader> sortedLeaders = new ArrayList<>(leaders);
        sortLeaders(sortedLeaders);

        System.out.println(ANSI_CYAN + "=========================================");
        System.out.println("            Select a Leader              ");
        System.out.println("=========================================" + ANSI_RESET);
        for (int i = 0; i < sortedLeaders.size(); i++) {
            System.out.println(ANSI_YELLOW + (i + 1) + ". " + sortedLeaders.get(i).getName() + " - " + sortedLeaders.get(i).getBackstory() + ANSI_RESET);
        }
        System.out.print(ANSI_GREEN + "Enter the number of your choice: " + ANSI_RESET);

        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (choice >= 0 && choice < sortedLeaders.size()) {
                    break;
                }
                System.out.print(ANSI_RED + "Invalid choice. Please enter a number between 1 and " + sortedLeaders.size() + ": " + ANSI_RESET);
            } catch (NumberFormatException e) {
                System.out.print(ANSI_RED + "Invalid input. Please enter a number: " + ANSI_RESET);
            }
        }

        Leader selectedLeader = sortedLeaders.get(choice);
        System.out.println(ANSI_CYAN + "\nYou have chosen " + selectedLeader.getName() + "!\n" + ANSI_RESET);
        return selectedLeader;
    }

    private void sortLeaders(List<Leader> leaders) {
        int n = leaders.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (leaders.get(j).getName().compareToIgnoreCase(leaders.get(j + 1).getName()) > 0) {
                    Leader temp = leaders.get(j);
                    leaders.set(j, leaders.get(j + 1));
                    leaders.set(j + 1, temp);
                }
            }
        }
    }

    public void displayLevel(Level level) {
        System.out.println(ANSI_CYAN + "=========================================");
        System.out.println("Level " + level.getNumber() + ":");
        System.out.println("=========================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + level.getDescription() + ANSI_RESET);
        System.out.println(ANSI_GREEN + "\nChoices:" + ANSI_RESET);
        System.out.println("1. " + level.getChoices().get(0).getText());
        System.out.println("2. " + level.getChoices().get(1).getText());
        System.out.print(ANSI_GREEN + "Enter your choice (1 or 2): " + ANSI_RESET);
    }

    public int getPlayerChoice() {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 1 || choice == 2) {
                    break;
                }
                System.out.print(ANSI_RED + "Invalid choice. Please enter 1 or 2: " + ANSI_RESET);
            } catch (NumberFormatException e) {
                System.out.print(ANSI_RED + "Invalid input. Please enter a number: " + ANSI_RESET);
            }
        }
        return choice;
    }

    public String displayEndOfRoundOptions(int score, int totalLevels) {
        System.out.println(ANSI_CYAN + "\n=========================================");
        System.out.println("             Round Complete!             ");
        System.out.println("=========================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Your score: " + score + " out of " + totalLevels + ANSI_RESET);
        System.out.println(ANSI_GREEN + "\nWhat would you like to do?" + ANSI_RESET);
        System.out.println("1. Play the game (choose a different leader)");
        System.out.println("2. Quit the game");
        System.out.print(ANSI_GREEN + "Enter your choice (1 or 2): " + ANSI_RESET);

        String choice;
        while (true) {
            choice = scanner.nextLine().trim();
            if (choice.equals("1") || choice.equals("2")) {
                break;
            }
            System.out.print(ANSI_RED + "Invalid choice. Please enter 1 or 2: " + ANSI_RESET);
        }
        return choice;
    }
}