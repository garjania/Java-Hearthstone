package Interface.Shop;

import Data.Data;
import Interface.*;
import Player.*;

import java.util.Scanner;

public class Shop{

    private Interface anInterface;
    private String Menu;
    private String Help;
    private CardShop cardShop;
    private ItemShop itemShop;
    private AmuletShop amuletShop;

    public Shop(Interface anInterface){
        this.anInterface = anInterface;
        Menu = "Remaining Gil: " + Integer.toString(anInterface.getPlayer().getGils()) + " Gil\n1.Card Shop\n2.Item Shop\n3.Amulet Shop\n4.Exit";
        cardShop = new CardShop(this);
        itemShop = new ItemShop(this);
        amuletShop = new AmuletShop(this);
    }

    public void setCardShop(CardShop cardShop) {
        this.cardShop = cardShop;
    }

    public void setItemShop(ItemShop itemShop) {
        this.itemShop = itemShop;
    }

    public void setAmuletShop(AmuletShop amuletShop) {
        this.amuletShop = amuletShop;
    }

    public void start(){
        System.out.println(Menu);
        String Input = "";
        Scanner scn = new Scanner(System.in);

        while(!Input.equals("Exit")){
            Input = scn.nextLine();
            switch (Input){
                case "Card Shop":
                    cardShop.start();
                    System.out.println("Shop");
                    break;
                case "Item Shop":
                    itemShop.start();
                    System.out.println("Shop");
                    break;
                case "Amulet Shop":
                    amuletShop.start();
                    System.out.println("Shop");
                    break;
                case "Help":
                    help();
                    break;
                default:
                    break;
            }
        }
    }

    public Player getPlayer() {
        return anInterface.getPlayer();
    }

    public Data getData() {
        return anInterface.getData();
    }

    public Interface getAnInterface() {
        return anInterface;
    }

    public void setData(Data data) {
        anInterface.setData(data);
    }

    public void setPlayer(Player player) {
        anInterface.setPlayer(player);
    }

    public CardShop getCardShop() {
        return cardShop;
    }

    public ItemShop getItemShop() {
        return itemShop;
    }

    public AmuletShop getAmuletShop() {
        return amuletShop;
    }

    //Help
    public void help(){
        System.out.println(Menu);
    }

}
