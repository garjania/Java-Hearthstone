package Data.Card;
import Data.Spell.*;
import Player.Player;

public class SpellCard extends Card {

    private transient int MP = super.getMP();
    private transient Type Type = super.getType();
    private transient Spell Spell;

    public SpellCard(String name, int MP, Type type, Spell spell){
        super(name, MP, type, Kind.SPELL);
        Spell = spell;
    }

    @Override
    public void setPlayer(Player player){
        super.setPlayer(player);
    }

    @Override
    public Spell getSpell(){
        return Spell;
    }

    @Override
    public void doSpell(){
        Spell.doSpell(getPlayer());
    }

    @Override
    public void changePosition(Position position){
        setPosition(position);
        if(Type == Type.AURA && position != Position.FIELD){
            for (int i = 0; i < Spell.getEffect().size(); i++) {
                Spell.getEffect().get(i).getReverseEffect().start();
            }
        }

        if(Type == Type.AURA && position == Position.FIELD){
            Spell.doSpell(getPlayer());
        }

        if(Type == Type.CONTINUOUS && position == Position.FIELD){
            Spell.doSpell(getPlayer());
        }
        if(Type == Type.INSTANT && position == Position.FIELD){
            Spell.doSpell(getPlayer());
            getPlayer().getField().moveToGrave(this);
        }
    }

    public void setSpell(Spell spell) {
        Spell = spell;
    }

    @Override
    public String getName(){
        return super.getName();
    }

    @Override
    public Card copyCard(){
        SpellCard card = new SpellCard(getName(), MP, Type, Spell);
        card.setPosition(this.getPosition());
        card.setNumberInField(this.getNumberInField());
        card.setPlayer(getPlayer());
        card.setPath(getPath());
        card.setBanned(isBanned());
        return card;
    }

    @Override
    public Integer getCost(){
        return 700 * MP;
    }

    @Override
    public String toString(){
        return getName() + " Info:\nName: " + getName() + "\nMP cost: " + Integer.toString(MP) + "\nType: " + Type.toString() + "\nDetails: " + Spell.toString();
    }

    @Override
    public Double compareCard(Card card){
        if(card.getKind() != Kind.SPELL && !equals(card))
            return 0.0;
        SpellCard theCard = (SpellCard) card;
        return (MP - theCard.MP) * 1.0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj instanceof SpellCard)
            return super.getName().equals(((SpellCard) obj).getName());

        return false;
    }
}

