package Data;

import Data.Card.Card;
import Data.Card.MonsterCard;
import Data.Card.SpellCard;
import Data.Card.*;
import Data.Spell.Spell;

import java.util.*;

public class Data {

    private ArrayList<Card> Cards = new ArrayList<>();
    private ArrayList<MonsterCard> Monsters = new ArrayList<>();
    private ArrayList<SpellCard>  Spells = new ArrayList<>();
    private ArrayList<Item> Items = new ArrayList<>();
    private ArrayList<Amulet> Amulets = new ArrayList<>();
    private ArrayList<Spell> spellDetails = new ArrayList<>();

    public ArrayList<Amulet> getAmulets() {
        return Amulets;
    }

    public ArrayList<Item> getItems() {
        return Items;
    }

    public ArrayList<MonsterCard> getMonsters() {
        return Monsters;
    }

    public ArrayList<Spell> getSpellDetails() {
        return spellDetails;
    }

    public Map<String, Card> getCardsMap(){
        Map<String, Card> returnMap = new HashMap<>();
        for (int i = 0; i < Cards.size(); i++) {
            returnMap.put(Cards.get(i).getName(), Cards.get(i).copyCard());
        }
        return returnMap;
    }

    public Data copyData(){
        Data data = new Data();
        for (Card a : this.Cards){
            data.Cards.add(a.copyCard());
        }
        for (MonsterCard a : this.Monsters){
            data.Monsters.add((MonsterCard) a.copyCard());
        }
        for (SpellCard a : this.Spells){
            data.Spells.add((SpellCard) a.copyCard());
        }
        for (Item a : this.Items){
            data.Items.add(a.copyItem());
        }
        for ( Amulet a : this.Amulets){
            data.Amulets.add(a.copyAmulet());
        }
        for (Spell a: this.spellDetails) {
            data.spellDetails.add(a.copySpell());
        }

        return data ;
    }

    public Map<String, MonsterCard> getMonsterCardsMap(){
        Map<String, MonsterCard> returnMap = new HashMap<>();
        for (int i = 0; i < Monsters.size(); i++) {
            returnMap.put(Monsters.get(i).getName(), (MonsterCard) Monsters.get(i).copyCard());
        }
        return returnMap;
    }

    public Map<String, SpellCard> getSpellCardsMap(){
        Map<String, SpellCard> returnMap = new HashMap<>();
        for (int i = 0; i < Spells.size(); i++) {
            returnMap.put(Spells.get(i).getName(), (SpellCard) Spells.get(i).copyCard());
        }
        return returnMap;
    }

    public Map<String, Item> getItemsMap(){
        Map<String, Item> returnMap = new HashMap<>();
        for (int i = 0; i < Items.size(); i++) {
            returnMap.put(Items.get(i).getName(), Items.get(i).copyItem());
        }
        return returnMap;
    }

    public Map<String, Amulet> getAmuletsMap(){
        Map<String, Amulet> returnMap = new HashMap<>();
        for (int i = 0; i < Amulets.size(); i++) {
            returnMap.put(Amulets.get(i).getName(), Amulets.get(i).copyAmulet());
        }
        return returnMap;
    }

    public ArrayList<SpellCard> getSpell() {
        return Spells;
    }

    public ArrayList<Card> getCards() { return Cards; }

    public void addMonsterCard(MonsterCard monstercard){
        MonsterCard theCard = (MonsterCard) monstercard.copyCard();
        Monsters.add(theCard);
        Cards.add(theCard);
    }

    public void addSpellCard(SpellCard spellcard){
        SpellCard theCard = (SpellCard) spellcard.copyCard();
        Spells.add(theCard);
        Cards.add(theCard);
    }

    public void addItem(Item item){
        Items.add(item.copyItem());
    }

    public void addAmulet(Amulet amulet){
        Amulets.add(amulet);
    }

    public void addSpell(ArrayList<String> details,String detail){
        spellDetails.add(new Spell(details, detail));
    }
}









