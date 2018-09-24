package Data;

import Data.Spell.*;
import Player.Player;

public class Item{

    private String Name;
    private transient int Cost;
    private transient Spell Spell;
    private String path;
    private boolean banned = false;

    public Item(String name, int cost, Spell spell) {
        this.Cost = cost;
        this.Name = name;
        path = "resources/Images/Data View/Items/" + name + ".png";
        this.Spell = spell;
    }
    public Item() {

    }

    public Item copyItem(){
        Item item = new Item();
        item.Name = new String(Name);
        item.Cost = Cost;
        item.Spell = Spell;
        item.path = path;
        item.banned = banned;
        return item;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public Integer getCost() {
        return Cost;
    }

    public Spell getSpell() {
        return Spell;
    }

    @Override
    public boolean equals(Object obj) {
        return this.Name.equals(((Item) obj).getName());
    }

    @Override
    public int hashCode(){
        return Name.hashCode();
    }

    @Override
    public String toString(){
        return Name + " :\nSpell Detail : "+ Spell.toString();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSpell(Spell spell) {
        Spell = spell;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isBanned() {
        return banned;
    }
}

