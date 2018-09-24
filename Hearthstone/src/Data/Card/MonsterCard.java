package Data.Card;

import Data.Spell.*;
import Player.Player;
import UI.Battle.BattleVisualEffects;

public class MonsterCard extends Card {

    private transient Integer CurrentHP, ConstantHP;
    private transient Integer CurrentAP, ConstantAP;
    private transient Integer MP = super.getMP();
    private transient Type Type = super.getType();
    private transient MonsterTribe Tribe;
    private transient boolean Defender, Nimble;
    private transient boolean isSleep, usedSpell, hasAttacked = false;
    private transient Spell Spell, BattleCry, Will;
    private transient String Description;
    private transient Integer attackedTimes = 0;

    public MonsterCard(String name, int MP, Type type, int HP, int AP, MonsterTribe tribe, boolean nimble, boolean defender, Spell spell, Spell battleCry, Spell will){
        super(name, MP, type, Kind.MONSTER);
        ConstantHP = HP;
        CurrentHP = HP;
        ConstantAP = AP;
        CurrentAP = AP;
        Nimble = nimble;
        if(Nimble)
            isSleep = false;
        else
            isSleep = true;
        Defender = defender;
        Spell = spell;
        BattleCry = battleCry;
        Will = will;
        if(Spell != null)
            usedSpell = false;
        else
            usedSpell = true;
        Tribe = tribe;
    }

    public void attackCard(Card card){
        if(attackedTimes > 0)
            return;
        else
            attackedTimes++;
        if(card == null)
            return;
        if(isSleep)
            return;
        if(card.getKind() == Kind.SPELL)
            card.getPlayer().getField().moveToGrave(card);
        else{
            this.changeHP( -1 * ((MonsterCard) card).getCurrentAP());
            card.changeHP( -1 * this.getCurrentAP());
        }
        BattleVisualEffects.soundEffect("fight.mp3");

    }

    public void attackPlayer(Player player){
        if(attackedTimes > 0)
            return;
        else
            attackedTimes++;
        player.changeHP(-1 * CurrentAP);
        BattleVisualEffects.soundEffect("fight.mp3");
    }

    public void changeAP(int power){
        CurrentAP += power;
    }

    public boolean isNimble() {
        return Nimble;
    }

    public boolean isDefender() {
        return Defender;
    }

    public boolean isSleep() {
        return isSleep;
    }

    public boolean isUsedSpell() {
        return usedSpell;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public void setUsedSpell(boolean in){
        usedSpell = in;
    }

    public void setCurrentAP(Integer currentAP) {
        CurrentAP = currentAP;
    }

    public void setCurrentHP(Integer currentHP) {
        CurrentHP = currentHP;
    }

    public void setNimble(boolean nimble) {
        Nimble = nimble;
    }

    public void setDefender(boolean defender) {
        Defender = defender;
    }

    public void setSpell(Spell spell){
        this.Spell = spell;
    }

    public void setBattleCry(Spell battleCry) {
        BattleCry = battleCry;
    }

    public void setWill(Data.Spell.Spell will) {
        Will = will;
    }

    public Spell getBattleCry() {
        return BattleCry;
    }

    public Spell getWill() {
        return Will;
    }

    @Override
    public Spell getSpell() { return Spell; }

    @Override
    public void doSpell(){
        if(Spell != null && !usedSpell){
            usedSpell = true;
            Spell.doSpell(getPlayer());
        }
    }

    @Override
    public void changePosition(Position position){
        setPosition(position);
        if(BattleCry != null && position == Position.FIELD)
            BattleCry.doSpell(getPlayer());
        if(Will != null && position == Position.GRAVEYARD)
            Will.doSpell(getPlayer());
    }

    @Override
    public boolean changeHP(Integer hp){
        CurrentHP += hp;
        if(CurrentHP <= 0){
            CurrentHP = 0;
            getPlayer().getField().moveToGrave(this);
            return false;
        }
        return true;
    }

    @Override
    public Integer getCost(){
        switch (Type){
            case HERO:
                return 1000*MP;
            case NORMAL:
                return 300*MP;
            case GENERAL:
                return 700*MP;
            case SPELLCASTER:
                return 500*MP;
            default:
                return -1;
        }
    }

    @Override
    public String getName(){
        return super.getName();
    }

    public Integer getCurrentHP() {
        return CurrentHP;
    }

    public Integer getCurrentAP() {
        return CurrentAP;
    }

    public MonsterTribe getTribe() {
        return Tribe;
    }

    public void setSleep(boolean sleep) {
        isSleep = sleep;
    }

    @Override
    public void setPlayer(Player player){
        super.setPlayer(player);
    }

    @Override
    public Card copyCard(){
        MonsterCard card = new MonsterCard(getName(), MP, Type, ConstantHP, ConstantAP, Tribe, Nimble, Defender, Spell, BattleCry, Will);
        card.setNumberInField(this.getNumberInField());
        card.isSleep = this.isSleep;
        card.setPosition(this.getPosition());
        card.CurrentHP = CurrentHP;
        card.CurrentAP = CurrentAP;
        card.setPlayer(getPlayer());
        card.setPath(getPath());
        card.setBanned(isBanned());
        return card;
    }

    @Override
    public String toString(){
        String returnStr = getName() + " Info:\nName: " + getName() + "\nHP: " + Integer.toString(ConstantHP) + "\nAP: " + Integer.toString(ConstantAP) + "\nMP cost: " + Integer.toString(MP) + "\nCard Type: " + Type.toString() + "\nCard Tribe: " + Tribe.toString() + "\nIs Defensive: " + Defender + "\nIs Nimble: " + Nimble + "\n";
        if(Spell != null )
            returnStr += Spell.toString() + "\n";
        if(BattleCry != null)
            returnStr += BattleCry.toString() + "\n";
        if(Will != null)
            returnStr += Will.toString() + "\n";

        return returnStr;
    }

    @Override
    public Double compareCard(Card card){
        if(card.getKind() != Kind.MONSTER && !equals(this))
            return 0.0;
        MonsterCard theCard = (MonsterCard) card;
        return  (CurrentHP- theCard.CurrentHP) * MP * 0.01;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MonsterCard)
            return super.getName().equals(((MonsterCard) obj).getName());
        return false;
    }

}
