package UI.Map.MapContents;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private Tiles[][] tiles;
    private ArrayList<Tiles[][]> layers;
    private Image map;
    private JSONObject mapJson;
    private Integer height, width;
    private Integer currentNumber = 0;
    private String name;

    public Map(String name) {

        this.name = name;

        String pngLocation = "resources/Images/Map/" + name + ".png";
        String jsonLocation = "resources/Images/Map/" + name + ".json";

        String result = "";
        try{
            Scanner scn = new Scanner(new FileReader(jsonLocation));
            while (scn.hasNext())
                result += scn.nextLine();
        }catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND");
        }

        map = new Image(new File(pngLocation).toURI().toString());
        try {
            mapJson = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        layers = readMap(mapJson);
    }

    private ArrayList<Tiles[][]> readMap(JSONObject mapData){
        ArrayList<Tiles[][]> arrays = new ArrayList<>();

        try {
            height = (int) mapData.get("height");
            width = (int) mapData.get("width");
            this.tiles = new Tiles[height][width];

            JSONArray layers = mapData.getJSONArray("layers");
            for (int i = 0; i < layers.length(); i++) {
                Tiles[][] tiles = new Tiles[height][width];

                JSONObject layer = layers.getJSONObject(i);
                JSONArray data = layer.getJSONArray("data");

                for (int j = 0; j < data.length(); j++) {
                    Tiles theTile = new Tiles((Integer) data.get(j));
                    tiles[j/width][j%width] = theTile;
                    if((Integer) data.get(j) != 0){
                        this.tiles[j/width][j%width] = theTile;
                    }
                }

                arrays.add(tiles);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrays;
    }

    public Image getMap() {
        return map;
    }

    public boolean canGo(Double x, Double y){
        return tiles[y.intValue() / 64][x.intValue() / 64].canGo();
    }

    public boolean canFight(Double x, Double y){
        return tiles[y.intValue() / 64][x.intValue() / 64].CanFight();
    }
}
