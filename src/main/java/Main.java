import java.util.List;

public class Main {
    public static void main(String[] args) {
        JsonLoader jsonLoader = new JsonLoader();
        List<Leader> leaders = jsonLoader.loadLeaders("history.json");
        ConsoleUI ui = new ConsoleUI();
        Game game = new Game(leaders, ui);
        game.start();
    }
}