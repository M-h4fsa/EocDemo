public class Main {
    public static void main(String[] args) {
        try {
            JsonLoader loader = new JsonLoader("history.json");
            Game game = new Game(loader.loadLeaders());
            ConsoleUI ui = new ConsoleUI(game);
            ui.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}