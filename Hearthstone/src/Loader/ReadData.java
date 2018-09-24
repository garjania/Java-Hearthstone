package Loader;

import Data.*;
import Data.Card.MonsterCard;
import Data.Card.MonsterTribe;
import Data.Card.SpellCard;
import Data.Card.Type;
import Data.Spell.Spell;
import org.json.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadData {

    public static void readItem(Data data) {

        String result = "";
        try{
            Scanner scn = new Scanner(new FileReader("resources/Data Json/Items.json"));
            while (scn.hasNext())
                result += scn.nextLine();

        }catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND");
        }
        ArrayList<Item> items = data.getItems();
        JSONObject Items = null;
        try {
            Items = new JSONObject(result);
            for (Integer i = 0; i < 8; i++) {
                JSONObject temp = Items.getJSONObject(i.toString());
                JSONObject spell = temp.getJSONObject("spell");
                ArrayList<String> SpellDetails = new ArrayList<>();
                for (int j = 0; j < spell.getJSONArray("spell").length(); j++) {
                    SpellDetails.add(spell.getJSONArray("spell").getString(j));
                }
                items.add(new Item(temp.getString("name"), temp.getInt("cost"), new Spell( SpellDetails, spell.getString("detail"))));
                data.addSpell(SpellDetails ,spell.getString("detail"));

            }
        }catch (Exception e){
            System.out.println("ERROE ACCURED IN READING ITEM");
            e.printStackTrace();
        }
    }

    public static void readAmulet(Data data) {
        String result = "";
        try {
            Scanner scn = new Scanner(new FileReader("resources/Data Json/Amulets.json"));
            while (scn.hasNext())
                result += scn.nextLine();
        }catch (Exception e){
            System.out.println("FILE NOT FOUND");
        }
        ArrayList<Amulet> amulets = data.getAmulets();
        JSONObject Amulets = null;
        try {
            Amulets = new JSONObject(result);
            for (Integer i = 0; i < 7; i++) {
                JSONObject temp = Amulets.getJSONObject(i.toString());
                JSONObject spell = temp.getJSONObject("spell");
                ArrayList<String> SpellDetais = new ArrayList<>();
                for (int j = 0; j < spell.getJSONArray("spell").length(); j++) {
                    SpellDetais.add(spell.getJSONArray("spell").getString(j));
                }
                amulets.add(new Amulet(temp.getString("name"), temp.getInt("cost"), new Spell( SpellDetais, spell.getString("detail"))));
                data.addSpell(SpellDetais,spell.getString("detail"));
            }
        }catch (Exception e){
            System.out.println("ERROE ACCURED IN READING ITEM");
        }

    }

    public static void readMonsterCard(Data data, String side) {
        String result = "";
        int count = 0;
        if(side.equals("Friend"))
            count = 32;
        else
            count = 12;
        try {
            Scanner scn = new Scanner(new FileReader("resources/Data Json/" + side + " Monster Card.json"));
            while (scn.hasNext())
                result += scn.nextLine() + "\n";
        }catch (Exception e){
            System.out.println("FILE NOT FOUND");
        }
        ArrayList<MonsterCard> monsters = data.getMonsters();
        JSONObject Monsters = null;
        try {
            Monsters = new JSONObject(result);
            for (Integer i = 0; i < count; i++) {
                JSONObject temp = Monsters.getJSONObject(i.toString());

                MonsterTribe monsterTribe = temp.get("Tribe").equals("Elves") ? MonsterTribe.ELVEN : temp.get("Tribe").equals("DragonBreed") ? MonsterTribe.DRAGONBREED : temp.get("Tribe").equals("Atlantian") ? MonsterTribe.ATLANTIAN : MonsterTribe.DEMONIC;

                Type type;
                switch (temp.getString("Type")) {
                    default:
                        type = null;

                    case "Normal":
                        type = Type.NORMAL;
                        break;
                    case "Spell Caster":
                        type = Type.SPELLCASTER;
                        break;
                    case "General":
                        type = Type.GENERAL;
                        break;
                    case "Hero":
                        type = Type.HERO;
                        break;


                }
                boolean isNimble = temp.get("speciality").equals("Nimble");
                boolean isDefender = temp.get("speciality").equals("Defender");

                JSONObject battleCry = temp.getJSONObject("battlecry");
                ArrayList<String> battleCryDetails = new ArrayList<>();
                for (int j = 0; j < battleCry.getJSONArray("spell").length(); j++) {
                    if (battleCry.getJSONArray("spell").getString(0).equals("none")) {
                        battleCryDetails.add(null);
                        break;
                    }
                    battleCryDetails.add(battleCry.getJSONArray("spell").getString(j));
                }

                JSONObject spell = temp.getJSONObject("spell");
                ArrayList<String> spellDetails = new ArrayList<>();
                for (int j = 0; j < spell.getJSONArray("spell").length(); j++) {
                    if (spell.getJSONArray("spell").getString(0).equals("none")) {
                        spellDetails.add(null);
                        break;
                    }
                    spellDetails.add(spell.getJSONArray("spell").getString(j));
                }

                JSONObject will = temp.getJSONObject("will");
                ArrayList<String> willDetails = new ArrayList<>();
                for (int j = 0; j < will.getJSONArray("spell").length(); j++) {
                    if (will.getJSONArray("spell").getString(0).equals("none")) {
                        willDetails.add(null);
                        break;
                    }
                    willDetails.add(will.getJSONArray("spell").getString(j));
                }

                Spell theBattlecry = null, theSpell = null, theWill = null;
                if(!battleCry.get("detail").equals("none")) {
                    theBattlecry = new Spell(battleCryDetails, battleCry.getString("detail"));
                    data.addSpell(battleCryDetails ,battleCry.getString("detail"));
                }
                if(!spell.get("detail").equals("none")) {
                    theSpell = new Spell(spellDetails, spell.getString("detail"));
                    data.addSpell(spellDetails,spell.getString("detail"));
                }
                if(!will.get("detail").equals("none")) {
                    theWill = new Spell(willDetails, will.getString("detail"));
                    data.addSpell(willDetails,will.getString("detail"));
                }
                monsters.add(new MonsterCard(temp.getString("name"), temp.getInt("MP cost"), type, temp.getInt("defualt HP"), temp.getInt("defualt AP"), monsterTribe, isNimble, isDefender, theSpell,  theBattlecry,  theWill));
                data.getCards().add(monsters.get(monsters.size() - 1));

            }
        }catch (Exception e){
            System.out.println("ERROE ACCURED IN READING ITEM");
            e.printStackTrace();
        }
    }

    public static void readSpellCard(Data data) {

        String result = "";
        try{
            Scanner scn = new Scanner(new FileReader("resources/Data Json/Spell Card.json"));
            while (scn.hasNext())
                result += scn.nextLine();

        }catch (FileNotFoundException e){
            System.out.println("FILE NOT FOUND");
        }
        ArrayList<SpellCard> spellCards = data.getSpell();
        JSONObject spells = null;
        try {
            spells = new JSONObject(result);
            for (Integer i = 0; i < 15; i++) {
                JSONObject temp = spells.getJSONObject(i.toString());
                JSONObject spell = temp.getJSONObject("spell");
                ArrayList<String> SpellDetails = new ArrayList<>();
                for (int j = 0; j < spell.getJSONArray("spell").length(); j++) {
                    SpellDetails.add(spell.getJSONArray("spell").getString(j));
                }

                Type type;
                switch (temp.getString("type")) {
                    default:
                        type = null;
                        break;
                    case "Instant":
                        type = Type.INSTANT;
                        break;
                    case "Continuous":
                        type = Type.CONTINUOUS;
                        break;
                    case "Aura":
                        type = Type.AURA;
                        break;
                }
                spellCards.add(new SpellCard(temp.getString("name"), temp.getInt("MP cost"), type, new Spell(SpellDetails, spell.getString("detail"))));
                data.addSpell(SpellDetails,spell.getString("detail"));
                data.getCards().add(spellCards.get(spellCards.size() - 1));
            }
        }catch (Exception e){
            System.out.println("ERROE ACCURED IN READING ITEM");
            e.printStackTrace();
        }

    }
}
