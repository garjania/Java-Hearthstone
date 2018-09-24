package Battle.Level.Actions;
import Data.Card.*;
import Data.Item;
import Player.*;
import UI.Battle.BattleVisualEffects;

import java.util.ArrayList;
import java.util.Scanner;

public class Action {

    public static void useCard(Card card){
        UseCard.start(card);
    }

    public static boolean setHandIndex(Card card, Integer num){
        if(card.getPlayer().getMP() >= card.getMP()){
            BattleVisualEffects.soundEffect("set.mp3");
            card.getPlayer().removeFromHand(card);
            card.getPlayer().getField().addToField(card, num);
            card.getPlayer().setMP(card.getPlayer().getMP() - card.getMP());
            Integer temp = num + 1;
            String str = card.getName() + " was set in ";
            if(card.getKind() == Kind.MONSTER)
                str = str.concat("MonsterFiled");
            else
                str = str.concat("SpellField");
            str = str.concat(" slot " + temp.toString() + ". " + card.getMP().toString() + " MP was used");
            System.out.println(str);
            return true;
        }else
            System.out.println("Not enough MP");
        return false;
    }

    public static void setHandIndex(Card card){
        if(card.getPlayer().getMP() >= card.getMP()){
            BattleVisualEffects.soundEffect("set.mp3");
            card.getPlayer().removeFromHand(card);
            card.getPlayer().getField().addToField(card);
            card.getPlayer().setMP(card.getPlayer().getMP() - card.getMP());
        }
    }

    public static void viewHand(Player player){
        System.out.println("Your Hand : ");
        int i = 1;
        for (Card card : player.getHand()){
            System.out.println(i + " .  " + card.getName());
            i++ ;

        }

    }

    public static void viewGraveYard(Player player, Player enemy){
        ArrayList<Card> PlayerGrave = player.getField().getGrave();
        ArrayList<Card> EnemyGrave = enemy.getField().getGrave();
        int i = 1 ;
        System.out.println("Your Graveyard :");
        for ( Card card : PlayerGrave){
            System.out.println(i + " .  " + card.getName());
            i++ ;
        }
        System.out.println("Enemy Graveyard :");
        i = 1 ;
        for ( Card card : EnemyGrave){
            System.out.println(i + " .  " + card.getName());
            i++ ;
        }

    }

    public static void viewSpellField(Player player, Player enemy){
        ArrayList<SpellCard> PlayerSpell = player.getField().getSpellField();
        ArrayList<SpellCard> EnemySpell = enemy.getField().getSpellField();
        int i = 1 ;
        System.out.println("Your SpellField :");
        for ( Card card : PlayerSpell){
            if(card != null)
                System.out.println("Slot" + i + " :  " + card.getName());
            else
                System.out.println("Slot" + i + " :  Empty");
            i++ ;
        }
        System.out.println("Enemy SpellField :");
        i = 1 ;
        for ( Card card : EnemySpell){
            if(card != null)
                System.out.println("Slot" + i + " :  " + card.getName());
            else
                System.out.println("Slot" + i + " :  Empty");
            i++ ;
        }

    }

    public static void viewMonsterField(Player player, Player enemy){
        Integer i = 1 ;
        System.out.println("Your MonsterField :");
        for (MonsterCard monsterCard : player.getField().getMonsterField()){
            if(monsterCard != null){
                String info = "Slot"+ i.toString() + " :  " + monsterCard.getName() + "   HP : " + monsterCard.getCurrentHP() + "  AP : " + monsterCard.getCurrentAP();
                if(monsterCard.isDefender())
                    info = info.concat(" Defensive");
                if(monsterCard.isNimble())
                    info = info.concat(" Nimble");
                if(monsterCard.isUsedSpell())
                    info = info.concat(" Used Spell");
                else
                    info = info.concat(" Has Spell");
                System.out.println(info);
                i++ ;
            }else{
                System.out.println("Empty");
            }

        }
        System.out.println("Enemy MonsterField :");
        i = 1 ;
        for (MonsterCard card : enemy.getField().getMonsterField()){
            if(card != null){
                String info = "Slot"+ i.toString() + " :  " + card.getName() + "   HP : " + card.getCurrentHP() + "  AP : " + card.getCurrentAP();
                if(card.isDefender())
                    info = info.concat(" Defensive");
                if(card.isNimble())
                    info = info.concat(" Nimble");
                if(card.isUsedSpell())
                    info = info.concat(" Used Spell");
                else
                    info = info.concat(" Has Spell");
                System.out.println(info);
                i++ ;
            }else{
                System.out.println("Empty");
            }
        }

    }

    public static void viewItems(Player player){
        Integer i = 1;
        for (Item item: player.getItems()) {
            System.out.println(i.toString() + ". " + item.getName());
            i++;
        }
        Scanner scn = new Scanner(System.in);
        String input = "";
        while (true){
            input = scn.nextLine();
            if(input.equals("Exit"))
                break;

            if(input.split(" ")[0].equals("Info")){
                input = input.replace("Info ", "");
                for (int j = 0; j < player.getItems().size(); j++) {
                    if(player.getItems().get(j).getName().equals(input)){
                        System.out.println(player.getItems().get(j).toString());
                    }
                }
            }

            if(input.split(" ")[0].equals("Use")){
                player.getItems().get(Integer.parseInt(input.split(" ")[1]) - 1).getSpell().doSpell(player);
            }
        }
    }
}

class UseCard{

    public static void start(Card card){
        System.out.println("Using " + card.getName() + ":");
        if (card.getKind() == Kind.MONSTER){
            System.out.println("HP: " + ((MonsterCard) card).getCurrentHP().toString() + " AP: " + ((MonsterCard) card).getCurrentAP().toString());
            System.out.println("Is Sleeping: " + ((MonsterCard) card).isSleep());
            if(card.getSpell() != null){
                System.out.println("Can Cast: " + !((MonsterCard) card).isUsedSpell());
            }
        }

        String Input = "";
        Scanner scn = new Scanner(System.in);

        while (!Input.equals("Exit")){
            Input = scn.nextLine();
            String[] parts = Input.split(" ");

            switch (parts[0]){
                case "Help":
                    help(card);
                    break;
                case "Info":
                    System.out.println(card.toString());
                    break;
                case "Cast":
                    if(card instanceof MonsterCard)
                        if(!((MonsterCard) card).isSleep() && ((MonsterCard) card).getSpell() != null){
                            System.out.println(card.getName() + " had cast a spell:");
                            System.out.println(card.getSpell().toString());
                            card.doSpell();
                        }
                    break;
                case "Attack":
                    if(card instanceof MonsterCard)
                        if(!((MonsterCard) card).isSleep()){
                            if(!((MonsterCard) card).hasAttacked()){
                                attack(parts[1], card);
                                ((MonsterCard) card).setAttacked(true);
                            }
                            else
                                System.out.println("The Monster cannot attack anymore");
                        }
                        else
                            System.out.println("LET THE MONSTER SLEEP!!");
                    break;
            }
        }
    }

    private static void help(Card card){
        System.out.println("1. Attack #EnemyMonsterSlot / Player: To attack the card on that slot of enemy MonsterField");
        if(card.getKind() == Kind.MONSTER)
            System.out.println("2. Info: To get full information on card\n" + "3. Exit: To go back to Play UI.Menu.Menu");
        else
            System.out.println("2. Cast Spell: To cast the ’cards spell\n3. Info: To get full information on card\n4. Exit: To go back to Play UI.Menu.Menu");
    }

    private static void attack(String target, Card card){
        if(card.getKind() != Kind.MONSTER)
            return;
        for (int i = 0; i < card.getPlayer().getEnemy().getField().getMonsterField().size(); i++) {
            if(card.getPlayer().getEnemy().getField().getMonsterField().get(i).isDefender()){
                ((MonsterCard) card).attackCard(card.getPlayer().getEnemy().getEnemy().getField().getMonsterField().get(i));
                return;
            }
        }

        if(target.equals("Player")) {
            ((MonsterCard) card).attackPlayer(card.getPlayer().getEnemy());
        }else{
            ((MonsterCard) card).attackCard(card.getPlayer().getEnemy().getField().getMonsterField().get(Integer.parseInt(target)-1));
        }

    }

}
