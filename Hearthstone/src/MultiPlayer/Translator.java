package MultiPlayer;

import Battle.Level.Actions.Action;
import Data.Card.Card;
import Data.Card.MonsterCard;
import Player.Player;
import UI.Battle.BattleUI;
import UI.Battle.BattleVisualEffects;
import UI.Battle.Events;
import UI.GeneralClasses.GameResources;

import java.util.ArrayList;

public class Translator {
    private  BattleUI battleUI;
    private ArrayList<String> events = new ArrayList<>();
    private String lastEvent = "";

    public void setEvent(String event) {
        if(!event.equals(lastEvent)) {
            events.add(event);
            lastEvent = event;
        }
    }

    public Translator(BattleUI battleUI) {
        this.battleUI = battleUI;
        battleUI.setTranslator(this);
    }

    public void listen(){
        synchronized (events){
            if(events.size() > 0) {
                String event = events.get(0);
                translate(event);
                events.remove(0);
            }
        }
    }

    public void translate(String in){
        String[] parts = in.split("_");
        if(in.isEmpty())
            return;
        if(in.contains("Hand")){
            setHand(parts[1], parts[2]);
        }else if(in.contains("AttackCard")){
            attackCard(parts[1], parts[2]);
        }else if(in.contains("AttackPlayer")){
            attackPlayer(parts[1]);
        }else if(in.contains("Cast")) {
            castSpell(parts[1]);
        }else if(in.contains("@")){
            done();
        }else if(in.contains("CHAT")){
            newMessage(parts[1]);
        }else{
            setStatus(in);
        }
    }

    private void setHand(String name, String num){
        int index = Integer.parseInt(num);
        Card card = GameResources.getData().getCardsMap().get(name);
        card.setPlayer(battleUI.getTurn().getEnemy());
        card.setNumberInField(index);
        Action.setHandIndex(card, index);
        battleUI.refresh();


    }

    private void attackCard(String num1, String num2){
        int attackerCardNumber = Integer.parseInt(num1);
        int defenderCardNumber = Integer.parseInt(num2);
        if(attackerCardNumber == -1 || defenderCardNumber == -1)
            return;
        boolean canAttack = true;
        for (int i = 0; i < battleUI.getTurn().getFriend().getField().getMonsterField().size(); i++) {
            if(battleUI.getTurn().getFriend().getField().getMonsterField().get(i) != null && battleUI.getTurn().getFriend().getField().getMonsterField().get(i).isDefender()) {
                Events.getInformationLog().setBattleEvent(battleUI.getTurn().getEnemy().getField().getMonsterField().get(attackerCardNumber).getName() + " Clashed With " + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).getName() + "\n");
                battleUI.getTurn().getEnemy().getField().getMonsterField().get(attackerCardNumber).attackCard(battleUI.getTurn().getFriend().getField().getMonsterField().get(i));
                canAttack = false;
                break;
            }
        }
        if(canAttack) {
            Events.getInformationLog().setBattleEvent(battleUI.getTurn().getEnemy().getField().getMonsterField().get(attackerCardNumber).getName() + " Clashed With " + battleUI.getTurn().getFriend().getField().getCardField().get(defenderCardNumber).getName());
            battleUI.getEnemy().getPlayer().getField().getMonsterField().get(attackerCardNumber).attackCard(battleUI.getMe().getPlayer().getField().getCardField().get(defenderCardNumber));
        }
        battleUI.refresh();

    }

    private void attackPlayer(String num){
        int attackerCardNumber = Integer.parseInt(num);
        if(attackerCardNumber == -1)
            return;
        boolean canAttack = true;
        for (int i = 0; i < battleUI.getTurn().getFriend().getField().getMonsterField().size(); i++) {
            if(battleUI.getTurn().getFriend().getField().getMonsterField().get(i) != null && battleUI.getTurn().getFriend().getField().getMonsterField().get(i).isDefender()) {
                battleUI.getTurn().getEnemy().getField().getMonsterField().get(attackerCardNumber).attackCard(battleUI.getTurn().getFriend().getField().getMonsterField().get(i));
                Events.getInformationLog().setBattleEvent(battleUI.getTurn().getEnemy().getField().getMonsterField().get(attackerCardNumber).getName() + " Clashed With " + battleUI.getTurn().getFriend().getField().getMonsterField().get(i).getName() + "\n");
                System.out.println("hello");
                canAttack = false;
                battleUI.refresh();
                break;
            }
        }
        if(canAttack)
            battleUI.getTurn().getEnemy().getField().getMonsterField().get(attackerCardNumber).attackPlayer(battleUI.getTurn().getFriend());
    }

    private void setStatus(String in){
        String[] parts = in.split("_");
        setPlayer(parts[0], battleUI.getTurn().getFriend());
        for (int i = 0; i < 5; i++) {
            setCard(parts[i+1], i, battleUI.getTurn().getFriend());
        }
        setPlayer(parts[6], battleUI.getTurn().getEnemy());
        for (int i = 0; i < 5; i++) {
            setCard(parts[i+7], i, battleUI.getTurn().getEnemy());
        }
        battleUI.refresh();
    }

    private void setPlayer(String in, Player player){
        in = in.replace("(", "");
        in = in.replace(")", "");
        String[] parts = in.split(",");
        player.setMP(Integer.parseInt(parts[0]));
        player.setHP(Integer.parseInt(parts[1]));

    }

    private void setCard(String in, int i, Player player){
        if(in.equals("null")) {
            player.getField().getMonsterField().set(i, null);
            return;
        }

        in = in.replace("(", "");
        in = in.replace(")", "");
        String[] parts = in.split(",");

        if(player.getField().getMonsterField().get(i) == null || !player.getField().getMonsterField().get(i).getName().equals(parts[0])){
            MonsterCard card = (MonsterCard) GameResources.getData().getCardsMap().get(parts[0]);
            card.setCurrentHP(Integer.parseInt(parts[1]));
            card.setCurrentAP(Integer.parseInt(parts[2]));
            if(parts[3].equals("true"))
                card.setSleep(true);
            else
                card.setSleep(false);
            if(parts[4].equals("true"))
                card.setUsedSpell(true);
            else
                card.setUsedSpell(false);
            if(parts[5].equals("true"))
                card.setAttacked(true);
            else
                card.setAttacked(false);
            card.setPlayer(player);
            Action.setHandIndex(card, i);
            return;
        }

        player.getField().getMonsterField().get(i).setCurrentHP(Integer.parseInt(parts[1]));
        player.getField().getMonsterField().get(i).setCurrentAP(Integer.parseInt(parts[2]));
        if(parts[3].equals("true"))
            player.getField().getMonsterField().get(i).setSleep(true);
        else
            player.getField().getMonsterField().get(i).setSleep(false);
        if(parts[4].equals("true"))
            player.getField().getMonsterField().get(i).setUsedSpell(true);
        else
            player.getField().getMonsterField().get(i).setUsedSpell(false);
        if(parts[5].equals("true"))
            player.getField().getMonsterField().get(i).setAttacked(true);
        else
            player.getField().getMonsterField().get(i).setAttacked(false);
    }

    private void castSpell(String num){
        int index = Integer.parseInt(num);
        battleUI.getTurn().getEnemy().getField().getMonsterField().get(index).doSpell();
    }

    public void done(){
        battleUI.nexTurn();
        battleUI.refresh();
    }

    public ArrayList<String> getEvent() {
        return events;
    }

    private void newMessage(String message){
        battleUI.getmyItemsLog().getChat().addMessage(message, false);
    }
}