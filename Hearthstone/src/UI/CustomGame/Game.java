package UI.CustomGame;

import Data.Data;
import Player.Player;
import UI.GeneralClasses.GameResources;

import java.util.ArrayList;

public class Game {
    private String name;
    private Data myData;
    private Data enemyData;
    private ArrayList<Player> enemies = new ArrayList<>();

    public Game(String name) {
        this.name = new String(name);
        this.myData = GameResources.getData().copyData();
        this.enemyData = GameResources.getEnemyData().copyData();
        for (int i = 0; i < GameResources.getEnemies().size(); i++) {
            enemies.add(GameResources.getEnemies().get(i).CopyPlayer());
        }
    }

    public Data getMyData() {
        return myData;
    }

    public Data getEnemyData() {
        return enemyData;
    }

    public ArrayList<Player> getEnemies() {
        return enemies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Game copyGame(String name){
        Game game = new Game(name);
        game.myData = myData.copyData();
        game.enemyData = enemyData.copyData();
        for (int i = 0; i < enemies.size(); i++) {
            game.enemies.add(enemies.get(i).CopyPlayer());
        }
        return game;
    }

}
