package Battle;

import Battle.Level.*;
import Interface.Interface;
import Interface.Shop.Shop;
import Player.Player;

public class Battle{

    private Level level;
    private Player player;
    private static Shop shop;

    public static void setShop(Shop shop){
        Battle.shop = shop;
    }

    public Battle(Player player){
        this.player = player;
    }

    public void start(){
        player.setThisLevel(level);
        level.start();
        shop.start();

    }

    public void setPlayerLevel(){
        player.setThisLevel(level);
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setNextLevel(Level level){
        Level theLevel = this.level;
        while (theLevel.getNextLevel() != null){
            theLevel = theLevel.getNextLevel();
        }
        theLevel.setNextLevel(level);
    }

    public Level getLevel(){
        return level;
    }

    public void goNextLevel(){
        if(level.getNextLevel() != null)
            level = level.getNextLevel();
    }
}
