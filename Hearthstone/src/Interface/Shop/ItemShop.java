package Interface.Shop;

import Data.Item;
import Interface.Interface;
import Interface.Shop.Shop;
import Player.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ItemShop{

    private Shop shop;
    private ArrayList<Item> ItemCards;

    ItemShop(Shop shop){
        this.shop = shop;
        ItemCards = shop.getData().getItems();
    }

    public void start(){
        again();
        String Input = "";
        Scanner scn = new Scanner(System.in);

        while(!Input.equals("Exit")){
            Input = scn.nextLine();
            String[] contain = Input.split(" ");
            String[] parts = {"", "", ""};
            for (int i = 0; i < contain.length; i++) {
                if(i == 0)
                    parts[0] = contain[0];
                if(i > 0 && i < contain.length - 2) {
                    if (i != contain.length - 3)
                        parts[1] += contain[i] + " ";

                    else
                        parts[1] += contain[i];
                }
                if(i == contain.length - 1)
                    parts[2] = contain[i];
            }

            switch (parts[0]){
                case "Buy":
                    for (Item item : ItemCards) {
                        if(item.getName().equals(parts[1])){
                            buy(item, Integer.parseInt(parts[2]));
                            break;
                        }
                    }
                    break;
                case "Sell":
                    for (Item item : ItemCards) {
                        if(item.getName().equals(parts[1])){
                            sell(item, Integer.parseInt(parts[3]));
                            break;
                        }
                    }
                    break;
                case "Info":
                    parts[1] = Input.replace("Info ", "");
                    for (Item item : ItemCards) {
                        if(item.getName().equals(parts[1])){
                            System.out.println(item.toString());
                            break;
                        }
                    }
                    break;
                case "Edit":
                    shop.getAnInterface().getEditDeck().start();
                    System.out.println("Item Shop");
                    break;
                default:
                    break;
            }
        }
    }

    public void again(){
        System.out.println(shop.getPlayer().getGils().toString() + " Gills");
        System.out.println("Item List:");
        for (Integer i = 1; i <= ItemCards.size(); i++) {
            System.out.println(i.toString() + ".  " + ItemCards.get(i-1).getName() + " " + ItemCards.get(i-1).getCost());
        }
        System.out.println("Item Inventory:");
        Integer i = 1;
        Set<Item> ItemSet = shop.getPlayer().getItemMap().keySet();
        for (Item item: ItemSet) {
            System.out.println(i.toString() + ".  " + item.getName() + " / " + shop.getPlayer().getItemMap().get(item));
        }
    }

    public void buy(Item item, Integer number){
        Player player = shop.getPlayer();

        if(player.getGils() < item.getCost() * number){
            System.out.println("Not enough Gil!");
            return;
        }

        player.editGils( -1 * number * item.getCost());

        for (int i = 0; i < number ; i++) {
            Item theItem = item.copyItem();
            player.addItem(theItem);
        }

        System.out.println("Successfully bought" + number.toString() + "of " + item.getName() + "!");


    }


    public void sell(Item item, Integer number){
        if(shop.getPlayer().getItemMap().containsKey(item))
            if(shop.getPlayer().getItemMap().get(item) < number){
                System.out.println("Not enough cards!");
                return;
            }else{
                Player player = shop.getPlayer();

                for (int i = 0; i < number; i++) {
                    player.removeItem(item);
                }

                player.editGils((item.getCost() * number) / 2);
                System.out.println("Successfully sold" + number.toString() + "of " + item.getName() + "!");
            }
    }



}
