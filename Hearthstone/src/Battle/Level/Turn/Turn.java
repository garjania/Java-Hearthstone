package Battle.Level.Turn;

import Battle.Level.Actions.*;
import Data.Card.*;
import Player.Player;
import UI.Battle.BattleUI;

import java.util.Random;
import java.util.Scanner;

public class Turn {
    private Integer TurnNum;
    private Player Friend;
    private Player Enemy;
    private Player Current;
    private String events = "";

    public Turn(Player friend, Player enemy){
        TurnNum = 1;
        Friend = friend;
        Enemy = enemy;
        Current = enemy;
        if(Friend.getCode() > Enemy.getCode())
            Current = Friend;
        else
            Current = Enemy;


    }

    public Turn(){}

    public void setup(){
        events();
        System.out.println("Turn " + TurnNum.toString() + " started!");
        events += "Turn " + TurnNum.toString() + " started!\n";
        System.out.println(Current.getName() + "'s turn!");
        events += Current.getName() + "'s turn!\n";
        System.out.println("[" + Current.getMaxMP().toString() + " - " + TurnNum.toString() + "]");
        events += "[" + Current.getMaxMP().toString() + " - " + TurnNum.toString() + "]\n";
    }

    public void start(){

        setup();

        String Input = "";
        Scanner scn = new Scanner(System.in);

        while(!Input.equals("Done") && Current.equals(Friend)){
            Input = scn.nextLine();
            String[] parts = Input.split(" ");

            switch (parts[0]){
                case "Use":
                    try {
                        Action.useCard(Current.getField().getMonsterField().get(Integer.parseInt(parts[1]) - 1));
                    }catch (Exception e){
                        System.out.println("Slot is Empty");
                        e.printStackTrace();
                    }
                    break;
                case "Set":
                    Action.setHandIndex(Current.getHand().get(Integer.parseInt(parts[1]) - 1), Integer.parseInt(parts[3]) - 1);
                    break;
                case "Info":
                    parts[1] = Input.replace("Info ", "");
                    for (int i = 0; i < Current.getHand().size(); i++) {
                        if(Current.getHand().get(i).getName().equals(parts[1])){
                            System.out.println(Current.getHand().get(i));
                            break;
                        }
                    }
                default:
                    break;
            }

            switch (Input){
                case "View Hand":
                    Action.viewHand(Current);
                    break;
                case "View Graveyard":
                    Action.viewGraveYard(Current, Current.getEnemy());
                    break;
                case "View SpellField":
                    Action.viewSpellField(Current, Current.getEnemy());
                    break;
                case "View MonsterField":
                    Action.viewMonsterField(Current, Current.getEnemy());
                    break;
                case "View Items":
                    Action.viewItems(Current);
                    break;
                case "Help":
                    help();
                    break;
                case "Status":
                    System.out.println(Friend.getName() + " : " + Friend.getHP() + " - " + Enemy.getName() + " : " + Enemy.getHP());
                default:
                    break;
            }
        }

        enemyAttack();
    }

    public void enemyAttack(){
        if(Current.isEvil() && Current.equals(Enemy)){
            EnemyPlay play = new EnemyPlay(Enemy);
            try{
                play.startSmart();
            }catch (Exception e){
                play.startRandom();
                e.printStackTrace();
            }
        }
    }

    public Turn nextTurn(){
        Turn turn = new Turn();
        turn.TurnNum = TurnNum + 1;
        turn.Friend = Friend.CopyPlayer();
        turn.Enemy = Enemy.CopyPlayer();
        turn.Friend.setEnemy(turn.Enemy);
        turn.Enemy.setEnemy(turn.Friend);
        if(Current.equals(Friend)){
            turn.Current = turn.Enemy;
            turn.Enemy.setMaxMP(turn.Enemy.getMaxMP() + 1);
        }
        else{
            turn.Current = turn.Friend;
            turn.Friend.setMaxMP(turn.Friend.getMaxMP() + 1);
        }
        turn.Friend.setMP(turn.Friend.getMaxMP());
        turn.Enemy.setMP(turn.Enemy.getMaxMP());
        return turn;
    }

    private void events(){
        Friend.setMP(Friend.getMaxMP());
        Enemy.setMP(Enemy.getMaxMP());
        for (int i = 0; i < Current.getField().getMonsterField().size(); i++)
            if(Current.getField().getMonsterField().get(i) != null && !Current.getField().getMonsterField().get(i).isNimble() && Current.getField().getMonsterField().get(i).isSleep())
                Current.getField().getMonsterField().get(i).setSleep(false);

        if(TurnNum != 1)
            for (int i = 0; i < Current.getDeck().size(); i++) {
                if(Current.getDeck().get(i) != null){
                    Card theCard = Current.getDeck().get(i);
                    if(theCard.getName().equals("Elven Druid"))
                        continue;
                    Current.addToHand(theCard);
                    Current.getDeck().remove(i);
                    break;
                }
            }

    }

    private void help(){
        System.out.println("1. Use #SlotNum: To use a specific card which is on the Monster Field");
        System.out.println("2. Set HandIndex to #SlotNum: To set a card which is on the hand, in the field");
        System.out.println("3. View Hand: To view the cards in your hand");
        System.out.println("4. View Graveyard: To view the cards in your graveyard");
        System.out.println("5. View SpellField: To view the cards in both ’players spell fields");
        System.out.println("6. View MonsterField: To view the cards in both ’players monster fields");
        System.out.println("7. View Items: To view the items in your backpack");
        System.out.println("8. Info \"Card Name\": To view full information about a card");
        System.out.println("9. Done: To end your turn");
    }

    public Player getCurrent(){
        return Current;
    }

    public Player getEnemy() {
        return Enemy;
    }

    public Player getFriend() {
        return Friend;
    }

    public String getEvents() {
        return events;
    }
}
