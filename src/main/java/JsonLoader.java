import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {
    private String filePath;

    public JsonLoader(String filePath) {
        this.filePath = filePath;
    }

    public List<Leader> loadLeaders() throws Exception {
        String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Leader> leaders = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject leaderJson = jsonArray.getJSONObject(i);
            String name = leaderJson.getString("name");
            String backstory = leaderJson.getString("backstory");
            JSONArray levelsJson = leaderJson.getJSONArray("levels");
            List<Level> levels = new ArrayList<>();

            for (int j = 0; j < levelsJson.length(); j++) {
                JSONObject levelJson = levelsJson.getJSONObject(j);
                int number = levelJson.getInt("number");
                String description = levelJson.getString("description");
                JSONArray choicesJson = levelJson.getJSONArray("choices");
                Choice[] choices = new Choice[2];
                choices[0] = new Choice(choicesJson.getJSONObject(0).getString("text"),
                        choicesJson.getJSONObject(0).getBoolean("isHistorical"));
                choices[1] = new Choice(choicesJson.getJSONObject(1).getString("text"),
                        choicesJson.getJSONObject(1).getBoolean("isHistorical"));
                String summary = levelJson.getString("summary");

                levels.add(new Level(number, description, choices, summary));
            }
            leaders.add(new Leader(name, backstory, levels));
        }
        return leaders;
    }
}
