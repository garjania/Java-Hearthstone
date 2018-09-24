package MultiPlayer;

import Battle.Level.Level;
import Player.Player;
import UI.Battle.BattleUI;
import UI.GeneralClasses.GameResources;

import java.util.Random;


public class InitiateOnline extends Thread{
    private Connector connector;
    private Player enemy;
    private Translator translator;
    private BattleUI battleUI;

    public InitiateOnline(int port) {
        DataTransfer.setOnlineGame(this);
        connector = new Connector(port);
        DataTransfer.sendPlayer(GameResources.getPlayer());
    }

    public InitiateOnline(String ip, int port) {
        DataTransfer.setOnlineGame(this);
        connector = new Connector(ip, port);
        DataTransfer.sendPlayer(GameResources.getPlayer());
    }

    private Level createLevel(){
        synchronized (this){
            try {
                if(enemy == null)
                    this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Level(GameResources.getPlayer(), enemy, 0, "MultiPlayer");
        }
    }

    public BattleUI createBattle(){
        BattleUI battle = new BattleUI(createLevel());
        battle.setConnector(connector);
        translator = new Translator(battle);
        DataTransfer.setTranslator(translator);
        battleUI = battle;
        return battle;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public String getStatus(){
        String output = "" ;
        output += "(" + battleUI.getTurn().getEnemy().getMP() + "," + battleUI.getTurn().getEnemy().getHP() + ")";
        for (int i = 0; i < battleUI.getTurn().getEnemy().getField().getMonsterField().size(); i++) {
            output += "_";
            if(battleUI.getTurn().getEnemy().getField().getMonsterField().get(i) == null)
                output += "null";
            else
                output += "(" + battleUI.getTurn().getEnemy().getField().getMonsterField().get(i).getName() + "," + battleUI.getTurn().getEnemy().getField().getMonsterField().get(i).getCurrentHP() + "," + battleUI.getTurn().getEnemy().getField().getMonsterField().get(i).getCurrentAP() + "," + battleUI.getTurn().getEnemy().getField().getMonsterField().get(i).isSleep() + "," + battleUI.getTurn().getEnemy().getField().getMonsterField().get(i).isUsedSpell() + "," + battleUI.getTurn().getEnemy().getField().getMonsterField().get(i).hasAttacked() + ")";
        }

        output += "_(" + battleUI.getTurn().getFriend().getMP() + "," + battleUI.getTurn().getFriend().getHP() + ")";
        for (int i = 0; i < battleUI.getMe().getPlayer().getField().getMonsterField().size(); i++) {
            output += "_";
            if(battleUI.getTurn().getFriend().getField().getMonsterField().get(i) == null)
                output += "null";
            else
                output += "(" + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).getName() + "," + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).getCurrentHP() + "," + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).getCurrentAP() + "," + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).isSleep() + "," + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).isUsedSpell() + "," + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).hasAttacked() + ")";
        }
        output += "_" + new Random().nextInt();
        return output;
    }
}
