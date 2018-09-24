package Data;
import Data.Spell.*;

public class Amulet {

    private String Name;
    private transient int Cost;
    private transient Spell Spell;
    private String path;
    private boolean banned = false;

    public Amulet copyAmulet(){
        Amulet amulet = new Amulet();
        amulet.Name = new String(Name);
        amulet.Cost = Cost;
        amulet.Spell = Spell;
        amulet.path = path;
        amulet.banned = banned;
        return amulet;

    }

    public Amulet() {

    }

    public Amulet(String name, int cost, Spell spell) {
        this.Cost = cost;
        this.Name = name;
        path = "resources/Images/Data View/Amulets/" + name + ".png";
        this.Spell = spell;
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

    public void setName(String name) {
        Name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return Name.equals(((Amulet) obj).getName());
    }

    @Override
    public int hashCode(){
        return Name.hashCode();
    }

    @Override
    public String toString(){
        return Name + ":\n" + Spell.toString();
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public void setSpell(Spell spell) {
        Spell = spell;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isBanned() {
        return banned;
    }
}
