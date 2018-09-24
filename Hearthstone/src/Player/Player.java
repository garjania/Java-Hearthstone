package Player;

import Data.*;
import Data.Card.Card;
import Data.Card.Position;
import Data.Spell.Target;
import Battle.Level.*;

import java.util.*;

public class Player extends Target{

    private String Name;
    private Integer Gils;
    private transient Integer MP, MaxMP = 1;
    private transient Integer HP, MaxHP = 10000;
    private transient Player Enemy;
    private transient Field Field;
    private transient Level thisLevel;
    private transient boolean Evil;
    private transient ArrayList<Card> Hand = new ArrayList<>();
    private ArrayList<Card> CardInventory = new ArrayList<>();
    private ArrayList<Card> Deck = new ArrayList<>(30);
    private ArrayList<Item> Items = new ArrayList<>();
    private Integer mysticCount = 3;
    private transient MysticHourGlass[] mystics = new MysticHourGlass[3];
    private ArrayList<Amulet> AmuletInventory = new ArrayList<>();
    private Amulet Amulet = null;
    private Integer code = new Random().nextInt();

    public Player(String name, boolean evil){
        Name = new String(name);
        Evil = evil;
        Gils = 10000;
        HP = MaxHP;
        for (int i = 0; i < 30; i++) {
            Deck.add(null);
        }
        Field = new Field(this);
        mystics[0] = new MysticHourGlass(this);
        mystics[1] = new MysticHourGlass(this);
        mystics[2] = new MysticHourGlass(this);
    }

    public Level getThisLevel() {
        return thisLevel;
    }

    public void setThisLevel(Level thisLevel) {
        this.thisLevel = thisLevel;
    }

    public void addToAmuletInventory(Amulet amulet){
        if(!AmuletInventory.contains(amulet))
            AmuletInventory.add(amulet);
    }

    public void addToCardInventory(Card card){
        CardInventory.add(card.copyCard());
    }

    public void removeFromCardInventory(Card card){
        CardInventory.remove(card);
    }

    public void addToHand(Card card){
        Hand.add(card);
        card.changePosition(Position.HAND);
    }

    public void removeFromHand(Card card){
        Hand.remove(card);
    }

    public void addToDeck(Card card, Integer number){
        Deck.set(number, card);
        if(card != null)
            card.changePosition(Position.DECK);
    }

    public void removeFromDeck(Card card, Integer number){
        if(Deck.get(number).equals(card))
            Deck.set(number, null);
    }

    public void addItem(Item item){
        Items.add(item.copyItem());
    }

    public void removeItem ( Item item) { Items.remove(item); }

    public void equip(Amulet amulet){
        Amulet = amulet.copyAmulet();
    }

    public void setEnemy(Player enemy) {
        Enemy = enemy;
    }

    public void setMaxHP(Integer maxHP) {
        MaxHP = maxHP;
    }

    public void setHP(Integer HP) {
        this.HP = HP;
    }

    public void setMaxMP(Integer maxMP){
        MaxMP = maxMP;
    }

    public Integer getMaxMP() {
        return MaxMP;
    }

    public void setMP(Integer MP) {
        this.MP = MP;
    }

    public void setMysticCount(Integer mysticCount) {
        this.mysticCount = mysticCount;
    }

    public void setName(String name){
        Name = name;
    }

    public Player getEnemy() {
        return Enemy;
    }

    public boolean isEvil() {
        return Evil;
    }

    @Override
    public String getName() {
        return Name;
    }

    public Integer getMP() {
        return MP;
    }

    public Integer getHP() {
        return HP;
    }

    public boolean useMystic(){
        if(mysticCount == 0)
            return false;
        mysticCount--;
        return true;
    }

    public boolean useMystic(Level level){
        int i = 0;
        while(i < 3 && mystics[i] == null){
            i++;
        }
        if(i < 3){
            mystics[i].doSpell(level);
            mystics[i] = null;
            mysticCount--;
            System.out.println("Mystic Hour Glass Used!");
            return true;
        }
        return false;
    }

    @Override
    public boolean changeHP(Integer hp){
        if(hp + HP > MaxHP){
            HP += hp;
            MaxHP = HP;
        }

        else
            HP += hp;
        if(HP < 0){
            HP = 0;
            return false;
        }

        return true;
    }

    public Integer getMaxHP() {
        return MaxHP;
    }

    public ArrayList<Item> getItems() { return Items; }

    public void setItems(ArrayList<Item> items) {
        Items = items;
    }

    public Map<Item, Integer> getItemMap(){
        Map<Item, Integer> map = new HashMap<Item, Integer>();
        for (Item item: Items)
            if(map.containsKey(item))
                map.put(item, map.get(item) + 1);
            else
                map.put(item, 1);

        return map;

    }

    public ArrayList<Amulet> getAmuletInventory() {
        return AmuletInventory;
    }

    public Map<Amulet, Integer> getAmuletMap(){
        Map<Amulet, Integer> map = new HashMap<Amulet, Integer>();
        for (Amulet amulet: AmuletInventory)
            if(map.containsKey(amulet))
                map.put(amulet, map.get(amulet) + 1);
            else
                map.put(amulet, 1);

        return map;

    }

    public ArrayList<Card> getCardInventory() {
        return CardInventory;
    }

    public Map<Card, Integer> getInventoryMap(){
        Map<Card, Integer> map = new HashMap<>();
        for (Card card: CardInventory)
            if(map.containsKey(card))
                map.put(card, map.get(card) + 1);
            else
                map.put(card, 1);

        return map;

    }

    public ArrayList<Card> getDeck() {
        return Deck;
    }

    public Map<Card, Integer> getDeckMap(){
        Map<Card, Integer> map = new HashMap<Card, Integer>();
        for (Card card: Deck)
            if(card != null)
                if(map.containsKey(card))
                    map.put(card, map.get(card) + 1);
                else
                    map.put(card, 1);

        return map;

    }

    public ArrayList<Card> getHand() {
        return Hand;
    }

    public Map<Card, Integer> getHandMap(){
        Map<Card, Integer> map = new HashMap<Card, Integer>();
        for (Card card: Hand)
            if(map.containsKey(card))
                map.put(card, map.get(card) + 1);
            else
                map.put(card, 1);

        return map;

    }

    public Integer getGils() {
        return Gils;
    }

    public Field getField() {
        return Field;
    }

    public void editGils(int gil){
        Gils += gil;
    }

    public void setGils(int gil){
        Gils = gil;
    }

    public Player CopyPlayer(){
        Player player = new Player(Name, Evil);
        player.setEnemy(Enemy);
        player.MP = MP;
        player.HP = HP;
        player.MaxMP = MaxMP;
        player.MaxHP = MaxHP;
        for (Card card: CardInventory) {
            Card theCard = card.copyCard();
            theCard.setPlayer(player);
            player.CardInventory.add(theCard);
        }
        int i = 0;
        for (Card card: Deck) {
            Card theCard;
            if(card != null)
                theCard = card.copyCard();
            else
                theCard = null;
            if(theCard != null)
                theCard.setPlayer(player);
            player.addToDeck(theCard, i++);
        }
        for (Item item: Items) {
            player.addItem(item.copyItem());
        }
        for (Amulet amulet: AmuletInventory) {
            player.addToAmuletInventory(amulet.copyAmulet());
        }
        for (Card card: Hand) {
            Card theCard = card.copyCard();
            theCard.setPlayer(player);
            player.addToHand(theCard);
        }
        if(Amulet != null)
            player.Amulet = Amulet.copyAmulet();
        player.Field.setPlayer(player);
        player.Field = Field.copyField(player);
        player.code = code;
        return player;
    }

    public Amulet getAmulet() {
        return Amulet;
    }

    public Double comparePlayer(Player player){
        Double score = 0.0;
        for (int i = 0; i < Hand.size(); i++) {
            score += Hand.get(i).compareCard(player.Hand.get(i));
        }
        return (HP - player.HP) * 0.08 + score;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
