package Interface;

import Battle.Battle;
import Data.*;
import Interface.Shop.Shop;
import Player.*;
import java.util.*;


public class Interface{

    private Shop shop;
    private Inventory inventory;
    private Next next;
    private EditDeck editDeck;
    private EditAmulets editAmulets;
    private Data Data;
    private Player Player;
    private String Menu = "1. Enter Shop: To enter shop and buy or sell cards and items\n2. Edit Inventory: To edit your amulet or deck\n3. Next: To go to deck and amulet customization";

    public Interface(Player player, Data data){
        Data = data;
        Player = player;
        editDeck = new EditDeck(this);
        editAmulets = new EditAmulets(this);
        shop = new Shop(this);
        inventory = new Inventory(this);
        next = new Next(this);
        Battle.setShop(shop);
    }

    public void start(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter Your Name:");
        getPlayer().setName(scn.nextLine());
        System.out.println(Menu);
        String Input = "";

        while(!Input.equals("Exit")){
            Input = scn.nextLine();
            switch (Input){
                case "Enter Shop":
                    shop.start();
                    System.out.println("Menu");
                    break;
                case "Edit Inventory":
                    inventory.start();
                    System.out.println("Menu");
                    break;
                case "Next":
                    next.start();
                    System.out.println("Menu");
                    break;
                case "Again":
                    System.out.println(Menu);
                    break;
                default:
                    break;
            }
        }
    }

    public void setData(Data data) {
        Data = data;
    }

    public void setPlayer(Player player) {
        Player = player;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public Data getData() {
        return Data;
    }

    public Player getPlayer() {
        return Player;
    }

    public Shop getShop() {
        return shop;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Next getNext() {
        return next;
    }

    public void setEditDeck(EditDeck editDeck) {
        this.editDeck = editDeck;
    }

    public EditDeck getEditDeck() {
        return editDeck;
    }

    public EditAmulets getEditAmulets() {
        return editAmulets;
    }
}

