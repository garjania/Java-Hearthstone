package Battle.Level.Turn;

import Battle.Level.Actions.Action;
import Data.Card.Card;
import Data.Card.Kind;
import Data.Card.MonsterCard;
import Data.Spell.Target;
import Player.Player;

import java.lang.ref.SoftReference;
import java.util.Random;

public class EnemyPlay {
    private Player Player;

    EnemyPlay(Player player){
        Player = player;
    }

    public void startRandom(){
        Random rand = new Random();
        if(rand.nextInt(2) == 1 && Player.getField().getMonsterField().size() > 0){
            Card card = null;
            int c = 0;
            while (card == null & c != 8) {
                card = Player.getField().getMonsterField().get(rand.nextInt(Player.getField().getMonsterField().size()));
                c++;
            }
            if(card == null)
                return;
            if(rand.nextInt(2) == 0 && card.getKind() == Kind.MONSTER){
                for (int i = 0; i < Player.getEnemy().getField().getMonsterField().size(); i++) {
                    if(Player.getEnemy().getField().getMonsterField().get(i) != null && Player.getEnemy().getField().getMonsterField().get(i).isDefender()){
                        ((MonsterCard) card).attackCard(Player.getEnemy().getField().getMonsterField().get(i));
                        return;
                    }
                }

                if(rand.nextInt(Player.getEnemy().getField().getCardField().size() + 1) == 0){
                    ((MonsterCard) card).attackPlayer(Player.getEnemy());
                }
                else{
                    MonsterCard theCard = Player.getEnemy().getField().getMonsterField().get(rand.nextInt(Player.getEnemy().getField().getMonsterField().size()));
                    int d = 0;
                    while (theCard == null && c != 5){
                        theCard = Player.getEnemy().getField().getMonsterField().get(rand.nextInt(Player.getEnemy().getField().getMonsterField().size()));
                        d++;
                    }
                    if(theCard != null )
                        ((MonsterCard) card).attackCard(theCard);
                }
            }else{
                if(card.getSpell() != null)
                    card.doSpell();
            }
        }
        else{
            int rnd = rand.nextInt(Player.getHand().size());
            if(rnd < 0)
                rnd = -1 * rnd;
            Action.setHandIndex(Player.getHand().get(rnd));
        }
    }

    public void startSmart(){
        Random rand = new Random();
        int rnd = rand.nextInt(Player.getHand().size());
        if(rnd < 0)
            rnd = -1 * rnd;
        Action.setHandIndex(Player.getHand().get(rnd));
        Double score = 0.0;
        MonsterCard myMonster = null;
        Target enemyTarget = null;
        for (int i = 0; i < Player.getField().getMonsterField().size(); i++) {
            if(Player.getField().getMonsterField().get(i) == null)
                continue;
            for (int j = 0; j < Player.getEnemy().getField().getCardField().size(); j++) {
                if(Player.getEnemy().getField().getCardField().get(i) == null)
                    continue;
                Player me = Player.CopyPlayer();
                Player temp = Player.getEnemy().CopyPlayer();
                me.getField().getMonsterField().get(i).attackCard(temp.getField().getCardField().get(j));
                Double thisScore = me.comparePlayer(Player) - temp.comparePlayer(Player.getEnemy());
                if(thisScore > score){
                    score = thisScore;
                    myMonster = Player.getField().getMonsterField().get(i);
                    enemyTarget = Player.getEnemy().getField().getCardField().get(j);
                }
            }
            Player me = Player.CopyPlayer();
            Player temp = Player.getEnemy().CopyPlayer();
            me.getField().getMonsterField().get(i).attackPlayer(temp);
            Double thisScore = me.comparePlayer(Player) - temp.comparePlayer(Player.getEnemy());
            if(thisScore > score){
                score = thisScore;
                myMonster = Player.getField().getMonsterField().get(i);
                enemyTarget = Player.getEnemy();
            }
        }
        if(enemyTarget == null || myMonster == null){
            startRandom();
            return;
        }
        for (int i = 0; i < Player.getEnemy().getField().getMonsterField().size(); i++) {
            if(Player.getEnemy().getField().getMonsterField().get(i) != null && Player.getEnemy().getField().getMonsterField().get(i).isDefender()){
                myMonster.attackCard(Player.getEnemy().getField().getMonsterField().get(i));
                return;
            }
        }
        if(enemyTarget instanceof Player)
            myMonster.attackPlayer((Player) enemyTarget);
        else
            myMonster.attackCard((Card) enemyTarget);
    }
}
