package Loader;

import Battle.Battle;
import Battle.Level.Level;
import Data.Card.Card;
import Data.Data;
import Interface.Interface;
import Player.Player;
import UI.CustomGame.Game;
import UI.GeneralClasses.GameResources;

public class LoadGame {
    public static Interface launch(){
        Player me = new Player("player1", false);

        Data myData = new Data();
        Data enemyData = new Data();

        ReadData.readMonsterCard(myData, "Friend");
        ReadData.readSpellCard(myData);
        ReadData.readItem(myData);
        ReadData.readAmulet(myData);

        Battle battle = new Battle(me);

        int j = 0;
        for (int i = 0; i < 5 ; i++) {
            Card card = myData.getMonsters().get(i).copyCard();
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 8; i < 13 ; i++) {
            Card card = myData.getMonsters().get(i).copyCard();
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 16; i < 21 ; i++) {
            Card card = myData.getMonsters().get(i).copyCard();
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 3 ; i++) {
            Card card = myData.getCardsMap().get("First Aid Kit");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 3 ; i++) {
            Card card = myData.getCardsMap().get("Throwing Knives");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 1 ; i++) {
            Card card = myData.getCardsMap().get("Poisonous Cauldron");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 1 ; i++) {
            Card card = myData.getCardsMap().get("Healing Ward");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 1 ; i++) {
            Card card = myData.getCardsMap().get("War Drum");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }

        ReadData.readMonsterCard(enemyData, "Enemy");
        ReadData.readSpellCard(enemyData);

        Player enemy1 = new Player("Goblin", true);

        for (int i = 0; i < 10; i++) {
            Card card = enemyData.getCardsMap().get("Goblin Smuggler");
            card.setPlayer(enemy1);
            enemy1.addToDeck(card, i);
        }
        for (int i = 0; i < 5; i++) {
            Card card = enemyData.getCardsMap().get("Goblin Shaman");
            card.setPlayer(enemy1);
            enemy1.addToDeck(card, i + 10);
        }
        for (int i = 0; i < 5; i++) {
            Card card = enemyData.getCardsMap().get("Throwing Knives");
            card.setPlayer(enemy1);
            enemy1.addToDeck(card, i + 15);
        }
        GameResources.getEnemies().add(enemy1);
        Level level1 = new Level(me, enemy1, 10000, "Mountainside Village");
        battle.setLevel(level1);

        Player enemy2 = new Player("Ogre", true);

        for (int i = 0; i < 6; i++) {
            Card card = enemyData.getCardsMap().get("Ogre Warrior");
            card.setPlayer(enemy2);
            enemy2.addToDeck(card, i);
        }
        for (int i = 0; i < 4; i++) {
            Card card = enemyData.getCardsMap().get("Ogre Frontliner");
            card.setPlayer(enemy2);
            enemy2.addToDeck(card, i + 6);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("Ogre Warchief");
            card.setPlayer(enemy2);
            enemy2.addToDeck(card, i + 10);
        }
        for (int i = 0; i < 3; i++) {
            Card card = enemyData.getCardsMap().get("Throwing Knives");
            card.setPlayer(enemy2);
            enemy2.addToDeck(card, i + 11);
        }
        for (int i = 0; i < 3; i++) {
            Card card = enemyData.getCardsMap().get("First Aid Kit");
            card.setPlayer(enemy2);
            enemy2.addToDeck(card, i + 14);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("Poisonous Cauldron");
            card.setPlayer(enemy2);
            enemy2.addToDeck(card, i + 17);
        }
        GameResources.getEnemies().add(enemy2);
        Level level2 = new Level(me, enemy2, 20000, "Town in the Plains");
        battle.setNextLevel(level2);

        Player enemy3 = new Player("Vampire Lord", true);

        for (int i = 0; i < 4; i++) {
            Card card = enemyData.getCardsMap().get("Undead");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i);
        }
        for (int i = 0; i < 3; i++) {
            Card card = enemyData.getCardsMap().get("Giant Bat");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 4);
        }
        for (int i = 0; i < 3; i++) {
            Card card = enemyData.getCardsMap().get("Stout Undead");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 7);
        }
        for (int i = 0; i < 2; i++) {
            Card card = enemyData.getCardsMap().get("Undead Mage");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 10);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("Vampire Acolyte");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 12);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("Vampire Prince");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 13);
        }
        for (int i = 0; i < 3; i++) {
            Card card = enemyData.getCardsMap().get("Blood Feast");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 14);
        }
        for (int i = 0; i < 2; i++) {
            Card card = enemyData.getCardsMap().get("First Aid Kit");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 17);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("War Drum");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 19);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("Poisonous Cauldron");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 20);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("Healing Ward");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 21);
        }
        for (int i = 0; i < 1; i++) {
            Card card = enemyData.getCardsMap().get("Greater Purge");
            card.setPlayer(enemy3);
            enemy3.addToDeck(card, i + 22);
        }
        GameResources.getEnemies().add(enemy3);
        Level level3 = new Level(me, enemy3, 30000, "Dark City");
        battle.setNextLevel(level3);

        Player enemy4 = new Player("Lucifer", true);

        for (int i = 0; i < 4; i++) {
            Card card = myData.getCardsMap().get("Imp");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i);
        }
        for (int i = 0; i < 3; i++) {
            Card card = myData.getCardsMap().get("Shade");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 4);
        }
        for (int i = 0; i < 3; i++) {
            Card card = myData.getCardsMap().get("Living Armor");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 7);
        }
        for (int i = 0; i < 2; i++) {
            Card card = myData.getCardsMap().get("Hellhound");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 10);
        }
        for (int i = 0; i < 2; i++) {
            Card card = myData.getCardsMap().get("Evil Eye");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 12);
        }
        for (int i = 0; i < 2; i++) {
            Card card = myData.getCardsMap().get("Necromancer");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 14);
        }
        for (int i = 0; i < 1; i++) {
            Card card = myData.getCardsMap().get("Dark Knight");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 16);
        }
        for (int i = 0; i < 1; i++) {
            Card card = myData.getCardsMap().get("Cerberus, Gatekeeper of Hell");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 17);
        }
        for (int i = 0; i < 3; i++) {
            Card card = myData.getCardsMap().get("Reaper's Scythe");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 18);
        }
        for (int i = 0; i < 3; i++) {
            Card card = myData.getCardsMap().get("First Aid Kit");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 21);
        }
        for (int i = 0; i < 1; i++) {
            Card card = myData.getCardsMap().get("Strategic Retreat");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 24);
        }
        for (int i = 0; i < 2; i++) {
            Card card = myData.getCardsMap().get("Healing Ward");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 25);
        }
        for (int i = 0; i < 1; i++) {
            Card card = myData.getCardsMap().get("War Drum");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 27);
        }
        for (int i = 0; i < 1; i++) {
            Card card = myData.getCardsMap().get("Meteor Shower");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 28);
        }
        for (int i = 0; i < 1; i++) {
            Card card = myData.getCardsMap().get("Magic Seal");
            card.setPlayer(enemy4);
            enemy4.addToDeck(card, i + 29);
        }
        GameResources.getEnemies().add(enemy4);
        Level level4 = new Level(me, enemy4, 40000, "Tower of Inferno");
        battle.setNextLevel(level4);

        Interface myInterface = new Interface(me, myData);
        myInterface.getNext().setBattle(battle);
        battle.setPlayerLevel();
        GameResources.setData(myData);
        GameResources.setEnemyData(enemyData);
        GameResources.setPlayer(me);
        GameResources.setGameInterface(myInterface);
        GameResources.setBattle(battle);
        return myInterface;
    }

    public static Interface launch(Game game){
        Player me = new Player("player1", false);

        Data myData = game.getMyData();
        Data enemyData = game.getEnemyData();

        Battle battle = new Battle(me);

        int j = 0;
        for (int i = 0; i < 5 ; i++) {
            Card card = myData.getMonsters().get(i).copyCard();
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 8; i < 13 ; i++) {
            Card card = myData.getMonsters().get(i).copyCard();
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 16; i < 21 ; i++) {
            Card card = myData.getMonsters().get(i).copyCard();
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 3 ; i++) {
            Card card = myData.getCardsMap().get("First Aid Kit");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 3 ; i++) {
            Card card = myData.getCardsMap().get("Throwing Knives");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 1 ; i++) {
            Card card = myData.getCardsMap().get("Poisonous Cauldron");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 1 ; i++) {
            Card card = myData.getCardsMap().get("Healing Ward");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }
        for (int i = 0; i < 1 ; i++) {
            Card card = myData.getCardsMap().get("War Drum");
            card.setPlayer(me);
            me.addToCardInventory(card);
            me.addToDeck(card, j++);
        }

        Player enemy1 = game.getEnemies().get(0);
        GameResources.getEnemies().add(enemy1);
        Level level1 = new Level(me, enemy1, 10000, "Mountainside Village");
        battle.setLevel(level1);

        Player enemy2 = game.getEnemies().get(1);
        GameResources.getEnemies().add(enemy2);
        Level level2 = new Level(me, enemy2, 20000, "Town in the Plains");
        battle.setNextLevel(level2);

        Player enemy3 = game.getEnemies().get(2);
        GameResources.getEnemies().add(enemy3);
        Level level3 = new Level(me, enemy3, 30000, "Dark City");
        battle.setNextLevel(level3);

        Player enemy4 = game.getEnemies().get(3);
        GameResources.getEnemies().add(enemy4);
        Level level4 = new Level(me, enemy4, 40000, "Tower of Inferno");
        battle.setNextLevel(level4);

        Interface myInterface = new Interface(me, myData);
        myInterface.getNext().setBattle(battle);
        battle.setPlayerLevel();

        GameResources.setData(myData);
        GameResources.setEnemyData(enemyData);
        GameResources.setPlayer(me);
        GameResources.setGameInterface(myInterface);
        GameResources.setBattle(battle);

        return myInterface;
    }
}
