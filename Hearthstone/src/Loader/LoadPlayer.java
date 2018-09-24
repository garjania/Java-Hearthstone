package Loader;

import Player.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Data.*;

import java.io.*;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class LoadPlayer {
    public static void savePlayer(Player player){
        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter("Data Json/Player.json");
            gson.toJson(player, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayer(Player player, Data data){

        JSONObject playerJson = null;
        String result = "";

        try {
            Scanner scn = new Scanner(new FileReader("Data Json/Player.json"));
            while (scn.hasNext())
                result += scn.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            playerJson = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            player = new Player((String) playerJson.get("Name"), false);
            player.setGils((Integer) playerJson.get("Gils"));
            player.setMysticCount((Integer) playerJson.get("mysticCount"));

            JSONArray itemArray = playerJson.getJSONArray("Items");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject item = itemArray.getJSONObject(i);
                player.getItems().add(data.getItemsMap().get(item.get("Name")));
            }

            JSONArray amuletArray = playerJson.getJSONArray("Amulets");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject amulet = amuletArray.getJSONObject(i);
                player.getAmuletInventory().add(data.getAmuletsMap().get(amulet.get("Name")));
            }

            JSONArray cardArray = playerJson.getJSONArray("CardInventory");
            for (int i = 0; i < cardArray.length(); i++) {
                JSONObject card = cardArray.getJSONObject(i);
                player.getCardInventory().add(data.getCardsMap().get(card.get("Name")));
            }

            JSONArray deckArray = playerJson.getJSONArray("Deck");
            for (int i = 0; i < 30; i++) {
                JSONObject card = deckArray.getJSONObject(i);
                if(card == null)
                    player.getDeck().add(null);
                else{
                    for (int j = 0; j < player.getCardInventory().size(); j++) {
                        if(player.getCardInventory().get(i).getName().equals(card.get("Name")))
                            player.getDeck().add(player.getCardInventory().get(i));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendData(){
        Socket socket = null;

        try {
            socket = new Socket("198.143.180.155", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = "";
        try {
            Scanner scn = new Scanner(new FileReader("Data Json/Player.json"));
            while (scn.hasNext())
                result += scn.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Formatter formatter = new Formatter(outputStream);
        formatter.format(result);
        formatter.flush();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
