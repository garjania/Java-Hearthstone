package Data.Spell;

import Data.Card.*;
import MultiPlayer.Connector;
import MultiPlayer.DataTransfer;
import Player.Player;
import UI.Battle.BattleVisualEffects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Effect {

    private ArrayList<Target> Targets;
    private String Details;
    private Player Friend;

    public Effect(String details, Player friend){
        Details = details;
        Friend = friend;
        Targets = new ArrayList<>();
    }

    public void setTargets(ArrayList<Target> targets, int count){
        System.out.println("List of Targets:");
        for (Integer i = 1; i <= targets.size(); i++) {
            System.out.println(i.toString() + " " + targets.get(i-1).getName());
        }

        String Input = "";
        Scanner scn = new Scanner(System.in);
        int num = 0;

        while (!Input.equals("Exit") && num != count){
            Input = scn.nextLine();
            String[] parts = Input.split(" ");

            if(parts[0].equals("Target")){
                Targets.add(targets.get(Integer.parseInt(parts[1])-1));
                num++;
            }
        }
    }

    public void start(){
    }

    public Effect getReverseEffect(){
        return null;
    }

    public boolean TargetExtractor(){
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(Details.split(" ")));
        ArrayList<Target> foundTargets = new ArrayList<>();
        String Condition = "random";
        ArrayList<String> Type = new ArrayList<>();
        Integer Count = 1;
        String Side = "all";
        String Place = "field";

        if(parts.contains("All") || parts.contains("all"))
            Condition = "all";
        else if(parts.contains("Select") || parts.contains("select") || parts.contains("selected"))
            Condition = "select";

        if(parts.contains("friendly") || parts.contains("Friendly") || parts.contains("friend") || parts.contains("Friend"))
            Side = "friend";
        else if (parts.contains("enemy") || parts.contains("Enemy"))
            Side = "enemy";

        if(parts.contains("three") || parts.contains("Three"))
            Count = 3;
        else if(parts.contains("two") || parts.contains("Two"))
            Count = 2;

        if(parts.contains("monster")){
            if(parts.contains("non-Hero"))
                Type.add("non-Hero");
            else if(parts.contains("normal"))
                Type.add("normal");
            else if(parts.contains("non-Atlantian"))
                Type.add("non-Atlantian");
            else if(parts.contains("Elven"))
                Type.add("Elven");
            else
                Type.add("monster");
        }

        if(parts.contains("spell"))
            Type.add("spell");

        if(!parts.contains("spell") && !parts.contains("monster") && (parts.contains("card") || parts.contains("cards")))
            Type.add("card");

        if(parts.contains("Player") || parts.contains("player"))
            Type.add("player");

        for (String str: parts) {
            if(str.equals("Field") || str.equals("field") || str.equals("Hand") || str.equals("hand") || str.equals("Graveyard") || str.equals("graveyard") || str.equals("Deck") || str.equals("deck")){
                Place = str.toLowerCase();
                break;
            }
        }

        if(!Side.equals("enemy")){
            for (String str: Type) {
                if(str.equals("non-Hero")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getField().getMonsterField().size(); i++) {
                            if(Friend.getField().getMonsterField().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getField().getMonsterField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getHand().size(); i++) {
                            if(Friend.getHand().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getField().getGrave().size(); i++) {
                            if(Friend.getField().getGrave().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getDeck().size(); i++) {
                            if(Friend.getDeck().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getDeck().get(i));
                        }
                }

                if(str.equals("normal")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getField().getMonsterField().size(); i++) {
                            if(Friend.getField().getMonsterField().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getField().getMonsterField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getHand().size(); i++) {
                            if(Friend.getHand().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getField().getGrave().size(); i++) {
                            if(Friend.getField().getGrave().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getDeck().size(); i++) {
                            if(Friend.getDeck().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getDeck().get(i));
                        }
                }

                if(str.equals("non-Atlantian")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getField().getMonsterField().size(); i++) {
                            if(Friend.getField().getMonsterField().get(i).getTribe() != MonsterTribe.ATLANTIAN)
                                foundTargets.add(Friend.getField().getMonsterField().get(i));
                        }
                }

                if(str.equals("Elven")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getField().getMonsterField().size(); i++) {
                            if(Friend.getField().getMonsterField().get(i).getTribe() == MonsterTribe.ELVEN)
                                foundTargets.add(Friend.getField().getMonsterField().get(i));
                        }
                }

                if(str.equals("monster")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getField().getMonsterField().size(); i++) {
                            foundTargets.add(Friend.getField().getMonsterField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getHand().size(); i++) {
                            if(Friend.getHand().get(i).getKind() == Kind.MONSTER)
                                foundTargets.add(Friend.getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getField().getGrave().size(); i++) {
                            if(Friend.getField().getGrave().get(i).getKind() == Kind.MONSTER)
                                foundTargets.add(Friend.getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getDeck().size(); i++) {
                            if(Friend.getDeck().get(i).getKind() == Kind.MONSTER)
                                foundTargets.add(Friend.getDeck().get(i));
                        }
                }

                if(str.equals("spell")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getField().getSpellField().size(); i++) {
                            foundTargets.add(Friend.getField().getSpellField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getHand().size(); i++) {
                            if(Friend.getHand().get(i).getKind() == Kind.SPELL)
                                foundTargets.add(Friend.getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getField().getGrave().size(); i++) {
                            if(Friend.getField().getGrave().get(i).getKind() == Kind.SPELL)
                                foundTargets.add(Friend.getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getDeck().size(); i++) {
                            if(Friend.getDeck().get(i).getKind() == Kind.SPELL)
                                foundTargets.add(Friend.getDeck().get(i));
                        }
                }

                if(str.equals("card")){
                    if(Place.equals("field")){
                            for (int i = 0; i < Friend.getField().getSpellField().size(); i++) {
                                foundTargets.add(Friend.getField().getSpellField().get(i));
                            }

                            for (int i = 0; i < Friend.getField().getMonsterField().size(); i++) {
                                foundTargets.add(Friend.getField().getMonsterField().get(i));
                            }
                    }
                    if(Place.equals("hand")){
                        for (int i = 0; i < Friend.getHand().size(); i++) {
                            foundTargets.add(Friend.getHand().get(i));
                        }

                        for (int i = 0; i < Friend.getHand().size(); i++) {
                            foundTargets.add(Friend.getHand().get(i));
                        }
                    }
                    if(Place.equals("graveyard")){
                        for (int i = 0; i < Friend.getField().getGrave().size(); i++) {
                            foundTargets.add(Friend.getField().getGrave().get(i));
                        }

                        for (int i = 0; i < Friend.getField().getGrave().size(); i++) {
                            foundTargets.add(Friend.getField().getGrave().get(i));
                        }
                    }

                }

                if(str.equals("player"))
                    foundTargets.add(Friend);
            }
        }

        if(!Side.equals("friend")){
            for (String str: Type) {
                if(str.equals("non-Hero")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getEnemy().getField().getMonsterField().size(); i++) {
                            if(Friend.getEnemy().getField().getMonsterField().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getEnemy().getField().getMonsterField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getEnemy().getHand().size(); i++) {
                            if(Friend.getEnemy().getHand().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getEnemy().getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getEnemy().getField().getGrave().size(); i++) {
                            if(Friend.getEnemy().getField().getGrave().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getEnemy().getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getEnemy().getDeck().size(); i++) {
                            if(Friend.getEnemy().getDeck().get(i).getType() != Data.Card.Type.HERO)
                                foundTargets.add(Friend.getEnemy().getDeck().get(i));
                        }
                }

                if(str.equals("normal")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getEnemy().getField().getMonsterField().size(); i++) {
                            if(Friend.getEnemy().getField().getMonsterField().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getEnemy().getField().getMonsterField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getEnemy().getHand().size(); i++) {
                            if(Friend.getEnemy().getHand().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getEnemy().getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getEnemy().getField().getGrave().size(); i++) {
                            if(Friend.getEnemy().getField().getGrave().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getEnemy().getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getEnemy().getDeck().size(); i++) {
                            if(Friend.getEnemy().getDeck().get(i).getType() == Data.Card.Type.NORMAL)
                                foundTargets.add(Friend.getEnemy().getDeck().get(i));
                        }
                }

                if(str.equals("non-Atlantian")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getEnemy().getField().getMonsterField().size(); i++) {
                            if(Friend.getEnemy().getField().getMonsterField().get(i).getTribe() != MonsterTribe.ATLANTIAN)
                                foundTargets.add(Friend.getEnemy().getField().getMonsterField().get(i));
                        }
                }

                if(str.equals("Elven")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getEnemy().getField().getMonsterField().size(); i++) {
                            if(Friend.getEnemy().getField().getMonsterField().get(i).getTribe() == MonsterTribe.ELVEN)
                                foundTargets.add(Friend.getEnemy().getField().getMonsterField().get(i));
                        }
                }

                if(str.equals("monster")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getEnemy().getField().getMonsterField().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getField().getMonsterField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getEnemy().getHand().size(); i++) {
                            if(Friend.getEnemy().getHand().get(i).getKind() == Kind.MONSTER)
                                foundTargets.add(Friend.getEnemy().getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getEnemy().getField().getGrave().size(); i++) {
                            if(Friend.getEnemy().getField().getGrave().get(i).getKind() == Kind.MONSTER)
                                foundTargets.add(Friend.getEnemy().getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getEnemy().getDeck().size(); i++) {
                            if(Friend.getEnemy().getDeck().get(i).getKind() == Kind.MONSTER)
                                foundTargets.add(Friend.getEnemy().getDeck().get(i));
                        }
                }

                if(str.equals("spell")){
                    if(Place.equals("field"))
                        for (int i = 0; i < Friend.getEnemy().getField().getSpellField().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getField().getSpellField().get(i));
                        }
                    if(Place.equals("hand"))
                        for (int i = 0; i < Friend.getEnemy().getHand().size(); i++) {
                            if(Friend.getEnemy().getHand().get(i).getKind() == Kind.SPELL)
                                foundTargets.add(Friend.getEnemy().getHand().get(i));
                        }
                    if(Place.equals("graveyard"))
                        for (int i = 0; i < Friend.getEnemy().getField().getGrave().size(); i++) {
                            if(Friend.getEnemy().getField().getGrave().get(i).getKind() == Kind.SPELL)
                                foundTargets.add(Friend.getEnemy().getField().getGrave().get(i));
                        }
                    if(Place.equals("deck"))
                        for (int i = 0; i < Friend.getEnemy().getDeck().size(); i++) {
                            if(Friend.getEnemy().getDeck().get(i).getKind() == Kind.SPELL)
                                foundTargets.add(Friend.getEnemy().getDeck().get(i));
                        }
                }

                if(str.equals("card")){
                    if(Place.equals("field")){
                        for (int i = 0; i < Friend.getEnemy().getField().getSpellField().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getField().getSpellField().get(i));
                        }

                        for (int i = 0; i < Friend.getEnemy().getField().getMonsterField().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getField().getMonsterField().get(i));
                        }
                    }
                    if(Place.equals("hand")){
                        for (int i = 0; i < Friend.getEnemy().getHand().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getHand().get(i));
                        }

                        for (int i = 0; i < Friend.getEnemy().getHand().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getHand().get(i));
                        }
                    }
                    if(Place.equals("graveyard")){
                        for (int i = 0; i < Friend.getEnemy().getField().getGrave().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getField().getGrave().get(i));
                        }

                        for (int i = 0; i < Friend.getEnemy().getField().getGrave().size(); i++) {
                            foundTargets.add(Friend.getEnemy().getField().getGrave().get(i));
                        }
                    }

                }

                if(str.equals("player"))
                    foundTargets.add(Friend.getEnemy());
            }
        }
        if(Condition.equals("all")){
            Targets = foundTargets;
            return true;
        }else if(Condition.equals("random") || Friend.isEvil()){
            Random rand = new Random();
            for (int i = 0; i < Count; i++) {
                int myRand = rand.nextInt(foundTargets.size());
                Targets.add(foundTargets.get(Math.abs(myRand)));
            }
            return true;
        }else{
            BattleVisualEffects.selectTarget(foundTargets, Targets, this);
            return false;
        }
    }

    public ArrayList<Target> getTargets() {
        return Targets;
    }

    public void doEffect(){

    }
}

