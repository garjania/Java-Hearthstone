package Battle.Level;

import Battle.Level.Turn.Turn;
import Player.Player;
import UI.Battle.BattleUI;
import UI.GeneralClasses.GeneralUI;

import java.util.ArrayList;
import java.util.Collections;

public class Level {

    private String Name;
    private Player Friend;
    private Player Enemy;
    private ArrayList<Turn> Turns = new ArrayList<>();
    private Turn CurrentTurn;
    private Level NextLevel = null;
    private Integer GainGil;
    private String events = "";

    public Level(Player friend, Player enemy, Integer gainGil, String name){
        Name = name;
        Friend = friend;
        Enemy = enemy;
        GainGil = gainGil;
    }

    public void start(){
        setup();

        while (isFinished() != 0){
            CurrentTurn.start();
            CurrentTurn = CurrentTurn.nextTurn();
            Turns.add(CurrentTurn);
        }

        Friend.setItems(CurrentTurn.getFriend().getItems());

    }

    public void setup(){
        System.out.println("Level: " + Name);
        events += "Level: " + Name + "\n";

        Friend.setEnemy(Enemy);
        Enemy.setEnemy(Friend);

        Player copiedFriend = Friend.CopyPlayer();
        Player copiedEnemy = Enemy.CopyPlayer();

        CurrentTurn = new Turn(copiedFriend, copiedEnemy);

        Collections.shuffle(copiedFriend.getDeck());
        Collections.shuffle(copiedEnemy.getDeck());

        int i = 0, j = 0;
        while (j < 5){
            if(copiedFriend.getDeck().get(i) != null){
                copiedFriend.addToHand(copiedFriend.getDeck().get(i));
                j++;
            }
            i++;
        }
        j = 0; i = 0;
        while (j < 5){
            if(copiedEnemy.getDeck().get(i) != null){
                copiedEnemy.addToHand(copiedEnemy.getDeck().get(i));
                j++;
            }
            i++;
        }
        if(copiedFriend.getAmulet() != null){
            copiedFriend.getAmulet().getSpell().doSpell(copiedFriend);
        }
        if(copiedEnemy.getAmulet() != null)
            copiedEnemy.getAmulet().getSpell().doSpell(copiedEnemy);

        System.out.println("Battle against " + copiedEnemy.getName() + " started!");
        events += "Battle against " + copiedEnemy.getName() + " started!\n";
        System.out.println(CurrentTurn.getCurrent().getName() + " starts the battle");
        events += CurrentTurn.getCurrent().getName() + " starts the battle\n";
        System.out.println("player drew " + copiedFriend.getHand().get(0).getName() + ", " + copiedFriend.getHand().get(1).getName() + ", " + copiedFriend.getHand().get(2).getName() + ", " + copiedFriend.getHand().get(3).getName() + ", " + copiedFriend.getHand().get(4).getName() + ", ");
        events += "player drew " + copiedFriend.getHand().get(0).getName() + ", " + copiedFriend.getHand().get(1).getName() + ", " + copiedFriend.getHand().get(2).getName() + ", " + copiedFriend.getHand().get(3).getName() + ", " + copiedFriend.getHand().get(4).getName() + "\n";

        Turns.add(CurrentTurn);
    }

    public Level getNextLevel() {
        return NextLevel;
    }

    public ArrayList<Turn> getTurns() {
        return Turns;
    }

    public void setEnemy(Player enemy) {
        Enemy = enemy;
        Friend.setEnemy(Enemy);
    }

    public void setCurrentTurn(Turn currentTurn) {
        CurrentTurn = currentTurn;
    }

    public Turn getCurrentTurn() {
        return CurrentTurn;
    }

    public void setNextLevel(Level nextLevel) {
        NextLevel = nextLevel;
    }

    public int isFinished(){

        if(CurrentTurn.getEnemy().getHP() == 0){
            Friend.editGils(GainGil);
            return 1;
        }

        if(CurrentTurn.getFriend().getHP() == 0){
            return -1;
        }

        if(CurrentTurn.getFriend().getField().getGrave().size() == 30 && CurrentTurn.getEnemy().getField().getGrave().size() == 30){
            if(CurrentTurn.getFriend().getHP() != 0 && CurrentTurn.getFriend().getHP() == 0){
                Friend.editGils(GainGil);
                System.out.println("Winner Winner Chicken Dinner!");
                return 1;
            }
            else if(CurrentTurn.getFriend().getHP() == 0 && CurrentTurn.getFriend().getHP() != 0){
                System.out.println("You are out of Mystic Hourglass Game Over!");
                return -1;
            }
            else{
                System.out.println("You are out of Mystic Hourglass Game Over!");
                return -1;
            }
        }

        return 0;
    }

    public void useMystic(){
        Friend.useMystic(this);
    }

    public Player getFriend() {
        return Friend;
    }

    public Player getEnemy() {
        return Enemy;
    }

    public String getEvents(){
        return events;
    }

    public Integer getGainGil() {
        return GainGil;
    }
}
