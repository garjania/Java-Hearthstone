package UI.GeneralClasses;

import Battle.Battle;
import Data.Data;
import Interface.Interface;
import Player.Player;
import UI.BackPack.BackPack;
import UI.Battle.BattleUI;
import UI.Shop.ShopUI;

import java.util.ArrayList;

public class GameResources {
    private static Player player;
    private static Data data;
    private static Data enemyData;
    private static ArrayList<Player> enemies = new ArrayList<>();
    private static Interface gameInterface;
    private static Battle battle;

    public static void setBattle(Battle battle) {
        GameResources.battle = battle;
    }

    public static void setPlayer(Player player) {
        GameResources.player = player;
    }

    public static void setData(Data data) {
        GameResources.data = data;
    }

    public static void setEnemies(ArrayList<Player> enemies) {
        GameResources.enemies = enemies;
    }

    public static void setEnemyData(Data enemyData) {
        GameResources.enemyData = enemyData;
    }

    public static void setGameInterface(Interface gameInterface) {
        GameResources.gameInterface = gameInterface;
    }

    public static ArrayList<Player> getEnemies() {
        return enemies;
    }

    public static Data getEnemyData() {
        return enemyData;
    }

    public static Player getPlayer() {
        return player;
    }

    public static Data getData() {
        return data;
    }

    public static Battle getBattle() {
        return battle;
    }

    public static Interface getGameInterface() {
        return gameInterface;
    }
}
