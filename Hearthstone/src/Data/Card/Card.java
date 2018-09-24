package Data.Card;

import Data.Spell.Spell;
import Data.Spell.Target;
import Player.Player;

public class Card extends Target {

    private String Name;
    private transient Position position;
    private transient int MP;
    private transient Type Type;
    private transient Kind Kind;
    private transient Player player;
    private transient Integer numberInField = -1;
    private String path;
    private boolean isBanned = false;

    public Card(String name, int MP, Type type, Kind kind){
        Name = name;
        path = "resources/Images/Data View/Cards/Card Images/" + Name + ".jpg";
        this.MP = MP;
        Type = type;
        Kind = kind;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }


    public void setPosition(Position position) {
        this.position = position;
    }

    public Card copyCard(){
        return null;
    }

    @Override
    public String getName(){ return Name; }

    public void setName(String name) {
        Name = name;
    }

    public Integer getCost() {
        return -1;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type type) {
        Type = type;
    }

    public Kind getKind() {
        return Kind;
    }

    public void setKind(Kind kind) {
        Kind = kind;
    }

    public Integer getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public void changePosition(Position position){
        this.position = position;
    }

    public Spell getSpell(){
        return null;
    }

    public void doSpell(){
    }

    @Override
    public boolean equals(Object obj) {
        return this.Name.equals(((Card) obj).getName());
    }

    @Override
    public int hashCode(){
        return Name.hashCode();
    }

    public Integer getNumberInField() {
        return numberInField;
    }

    public void setNumberInField(Integer numberInField) {
        this.numberInField = numberInField;
    }

    public Double compareCard(Card card){
        return 0.0;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isBanned() {
        return isBanned;
    }
}
