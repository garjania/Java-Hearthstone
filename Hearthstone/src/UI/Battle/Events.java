package UI.Battle;

import Battle.Level.Actions.Action;
import Data.Card.Card;
import Data.Card.Kind;
import Data.Card.MonsterCard;
import Data.Card.Position;
import MultiPlayer.DataTransfer;
import Player.Player;
import UI.Battle.BattleField.Cells.CardCell;
import UI.Battle.BattleField.InformationLog;
import UI.Battle.BattleField.ItemsLog;
import UI.Battle.BattleField.PlayerUI;
import UI.DataView.Card.CardView;

import java.util.Random;

public class Events {
    private static InformationLog informationLog;
    private static BattleUI battle;

    public static void setInformationLog(InformationLog informationLog) {
        Events.informationLog = informationLog;
    }

    public static void setBattle(BattleUI battle) {
        Events.battle = battle;
    }

    public static InformationLog getInformationLog() {
        return informationLog;
    }

    public static void setHandIndex(CardCell cell, Integer num, Kind kind, boolean isEnemy){
        if(cell.getCardView().getCard() == null){
            Card card = ItemsLog.getCardCells().getCells().get(num).getCardView().getCard();
            if(card.getKind() == kind){
                if(Action.setHandIndex(card, cell.getNum())){
                    informationLog.setBattleEvent(card.getName() + " was set in MonsterFiled slot " + cell.getNum() + ". " + card.getMP() + " MP was used\n");
                    CardView theCard = new CardView(card);
                    cell.setCard(theCard);
                    battle.refreshLog();
                }
            }

            if(battle.isMultiplayer() && !isEnemy)
                DataTransfer.getConnector().sendData("Hand_" + card.getName() + "_" + cell.getNum() + "_" + new Random().nextInt() );
        }

    }

    public static void SpellCast(Card card, boolean isEnemy){
        if(card.getPosition() == Position.FIELD && !card.getPlayer().isEvil()){
            card.doSpell();
            informationLog.setBattleEvent(card.getName() + " has Cast a spell");
            if(battle.isMultiplayer() && !isEnemy)
                DataTransfer.getConnector().sendData("Cast_" +  card.getNumberInField() + "_" + new Random().nextInt());
        }
        battle.refreshField();
    }

    public static void attackCard(CardCell cell, PlayerUI player, Integer num, boolean isEnemy){
        Card card = cell.getCardView().getCard();
        boolean canAttack = true;
        MonsterCard selected = player.getPlayer().getEnemy().getField().getMonsterField().get(num);
        for (int i = 0; i < card.getPlayer().getField().getMonsterField().size(); i++) {
            if(card.getPlayer().getField().getMonsterField().get(i) != null && card.getPlayer().getField().getMonsterField().get(i).isDefender()) {
                selected.attackCard(card.getPlayer().getField().getMonsterField().get(i));
                informationLog.setBattleEvent(selected.getName() + " Clashed With " + card.getPlayer().getField().getMonsterField().get(i).getName() + "\n");
                if(battle.isMultiplayer() && !isEnemy)
                    DataTransfer.getConnector().sendData("AttackCard_" + selected.getNumberInField() + "_" + i + "_" + new Random().nextInt());
                canAttack = false;
                break;
            }
        }
        if(canAttack){
            if(battle.isMultiplayer() && !isEnemy)
                DataTransfer.getConnector().sendData("AttackCard_" + selected.getNumberInField() + "_" + card.getNumberInField() + "_" + new Random().nextInt());
            if(card.getPlayer().isEvil()) {
                selected.attackCard(card);
                informationLog.setBattleEvent(selected.getName() + " Clashed With " + card.getName() + "\n");
            }
        }

        battle.refreshField();
    }

    public static void attackPlayer(Player player, Integer num, boolean isEnemy){
        MonsterCard selected = player.getEnemy().getField().getMonsterField().get(num);
        boolean canAttack = true;
        for (int i = 0; i < player.getField().getMonsterField().size(); i++) {
            if(player.getField().getMonsterField().get(i) != null && player.getField().getMonsterField().get(i).isDefender()) {
                selected.attackCard(player.getField().getMonsterField().get(i));
                informationLog.setBattleEvent(selected.getName() + " Clashed With " + player.getField().getMonsterField().get(i).getName() + "\n");
                if(battle.isMultiplayer() && !isEnemy)
                    DataTransfer.getConnector().sendData("AttackCard_" + selected.getNumberInField() + "_" + i + "_" + new Random().nextInt());
                canAttack = false;
                break;
            }
        }
        if(canAttack && player.isEvil()) {
            selected.attackPlayer(player);
            informationLog.setBattleEvent(selected.getName() + " Attacked Player\n");
            if(battle.isMultiplayer() && !isEnemy)
                DataTransfer.getConnector().sendData("AttackPlayer_" + selected.getNumberInField() + "_" + new Random().nextInt());
        }
    }
}
