package Player;

import Data.Card.*;
import Data.Spell.Spell;
import UI.Battle.BattleUI;
import UI.Battle.BattleVisualEffects;

import javax.naming.Name;
import java.io.File;
import java.util.ArrayList;

public class Field {
    private Player Player;
    private ArrayList<Card> Field = new ArrayList<>(8);
    private ArrayList<MonsterCard> MonsterField = new ArrayList<>(5);
    private ArrayList<SpellCard> SpellField = new ArrayList<>(3);
    private ArrayList<Card> Grave = new ArrayList<>();

    public Field(Player player){
        Player = player;
        for (int i = 0; i < 5; i++) {
            MonsterField.add(null);
        }
        for (int i = 0; i < 3; i++) {
            SpellField.add(null);
        }
        for (int i = 0; i < 8; i++) {
            Field.add(null);
        }
    }

    public Field(){
    }

    public void setPlayer(Player player) {
        Player = player;
    }

    public ArrayList<Card> getGrave() {
        return Grave;
    }

    public ArrayList<MonsterCard> getMonsterField() {
        return MonsterField;
    }

    public ArrayList<SpellCard> getSpellField() {
        return SpellField;
    }

    public ArrayList<Card> getCardField() {
        return Field;
    }

    public void addToField(Card card, Integer num){
        if(card.getKind() == Kind.MONSTER) {
            Field.set(num, card);
            card.setNumberInField(num);
        } else {
            Field.set(num + 5, card);
            card.setNumberInField(num + 5);
        }
        if(card.getKind() == Kind.MONSTER)
            MonsterField.set(num ,(MonsterCard) card);
        else
            SpellField.set(num, (SpellCard) card);
        card.changePosition(Position.FIELD);
    }

    public void addToField(Card card){
        card.setPosition(Position.FIELD);
        if(card.getKind() == Kind.MONSTER){
            for (int i = 0; i < 5; i++) {
                if(Field.get(i) == null){
                    Field.set(i, card);
                    card.setNumberInField(i);
                    break;
                }
            }
        }else{
            for (int i = 5; i < 8; i++) {
                if(Field.get(i) == null){
                    Field.set(i, card);
                    card.setNumberInField(i);
                    break;
                }
            }
        }
        if(card.getKind() == Kind.MONSTER){
            for (int i = 0; i < 5; i++) {
                if(MonsterField.get(i) == null){
                    MonsterField.set(i, (MonsterCard) card);
                    break;
                }
            }
        }else{
            for (int i = 0; i < 3; i++) {
                if(SpellField.get(i) == null){
                    SpellField.set(i, (SpellCard)card);
                    break;
                }
            }
        }
        card.changePosition(Position.FIELD);
    }

    public void moveToGrave(Card card){
        if(Field.indexOf(card) == -1)
            return;
        Field.set(card.getNumberInField(), null);
        if(card.getKind() == Kind.MONSTER)
            MonsterField.set(card.getNumberInField(), null);
        if(card.getKind() == Kind.SPELL)
            SpellField.set(card.getNumberInField() - 5, null);
        Grave.add(card);
        card.setNumberInField(-1);
        card.changePosition(Position.GRAVEYARD);
        BattleVisualEffects.soundEffect("die.mp3");

    }



    public Field copyField(Player player){
        Field retrunValue = new Field();
        for (Card card: Field) {
            Card theCard;
            if(card != null){
                theCard = card.copyCard();
                theCard.setPlayer(player);
                retrunValue.Field.add(theCard);
            }else{
                retrunValue.Field.add(null);
            }
        }
        for (MonsterCard card: MonsterField) {
            MonsterCard theCard;
            if(card != null){
                theCard = (MonsterCard) card.copyCard();
                theCard.setPlayer(player);
                retrunValue.MonsterField.add(theCard);
            }else{
                retrunValue.MonsterField.add(null);
            }
        }
        for (SpellCard card: SpellField) {
            SpellCard theCard;
            if(card != null){
                theCard = (SpellCard) card.copyCard();
                theCard.setPlayer(player);
                retrunValue.SpellField.add(theCard);
            }else{
                retrunValue.SpellField.add(null);
            }
        }
        for (Card card: Grave) {
            Card theCard = card.copyCard();
            theCard.setPlayer(player);
            retrunValue.Grave.add(theCard);
        }

        return retrunValue;
    }
}
