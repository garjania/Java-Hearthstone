package MultiPlayer;

import Player.Player;
import UI.GeneralClasses.GameResources;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class DataTransfer {
    private static InitiateOnline onlineGame;
    private static Translator translator;
    public static boolean online = false;

    public static void setOnlineGame(InitiateOnline onlineGame) {
        DataTransfer.onlineGame = onlineGame;
        online = true;
    }

    public static void setTranslator(Translator translator) {
        DataTransfer.translator = translator;
    }

    public static void sendPlayer(Player player){
        String data = "#" + new Gson().toJson(player);
        onlineGame.getConnector().sendData(data);
    }

    public static void getPlayer(String data){
        JSONObject playerJson = null;
        Player player = null;
        try {
            playerJson = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            player = new Player((String) playerJson.get("Name"), true);
            player.setGils((Integer) playerJson.get("Gils"));
            player.setMysticCount((Integer) playerJson.get("mysticCount"));

            JSONArray itemArray = playerJson.getJSONArray("Items");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject item = itemArray.getJSONObject(i);
                player.getItems().add(GameResources.getData().getItemsMap().get(item.get("Name")));
            }

            JSONArray amuletArray = playerJson.getJSONArray("AmuletInventory");
            for (int i = 0; i < amuletArray.length(); i++) {
                JSONObject amulet = amuletArray.getJSONObject(i);
                player.getAmuletInventory().add(GameResources.getData().getAmuletsMap().get(amulet.get("Name")));
            }

            JSONArray cardArray = playerJson.getJSONArray("CardInventory");
            for (int i = 0; i < cardArray.length(); i++) {
                JSONObject card = cardArray.getJSONObject(i);
                player.getCardInventory().add(GameResources.getData().getCardsMap().get(card.get("Name")));
            }

            JSONArray deckArray = playerJson.getJSONArray("Deck");
            for (int i = 0; i < 30; i++) {
                JSONObject card = null;
                try{
                    card = deckArray.getJSONObject(i);
                    for (int j = 0; j < player.getCardInventory().size(); j++) {
                        if(player.getCardInventory().get(j).getName().equals(card.get("Name")))
                            player.getDeck().set(i,player.getCardInventory().get(j));
                    }
                }catch (JSONException e){
                    player.getDeck().set(i, null);
                }
            }
            player.setCode(playerJson.getInt("code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        synchronized (onlineGame){
            onlineGame.setEnemy(player);
            onlineGame.notify();
        }
    }

    public static Connector getConnector() {
        return onlineGame.getConnector();
    }

    public static void sendToTranslate(String in){
        synchronized (translator.getEvent()){
            translator.setEvent(in);
        }
    }

    public static void sendStatus(){
        getConnector().sendData(onlineGame.getStatus());
    }

}