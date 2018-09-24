package Interface;

import Data.*;
import Data.Card.Card;

import java.util.*;

public class Inventory{

    private Interface anInterface;
    private EditDeck editDeck;
    private EditAmulets editAmulets;

    Inventory(Interface anInterface){
        this.anInterface = anInterface;
        editDeck = this.anInterface.getEditDeck();
        editAmulets = this.anInterface.getEditAmulets();
    }

    public void start(){
        String Input = "";
        Scanner scn = new Scanner(System.in);

        while (!Input.equals("Exit")){
            Input = scn.nextLine();
            switch (Input){
                case "Help":
                    help();
                    break;
                case "Card Inventory":
                    CardInventory();
                    break;
                case "Item Inventory":
                    ItemInventory();
                    break;
                case "Amulet Inventory":
                    AmuletInventory();
                    break;
                case "Edit Deck":
                    editDeck.start();
                    break;
                case "Edit Amulet":
                    editAmulets.start();
                    break;
                default:
                    break;
            }
        }

    }

    public void help(){
        System.out.println("1. Card Inventory: To view your cards");
        System.out.println("2. Item Inventory: To view your items");
        System.out.println("3. Amulet Inventory: To view your amulets");
        System.out.println("4. Edit Deck: To edit your card deck");
        System.out.println("5. Edit Amulets: To equip or remove your amulets");
        System.out.println("6. Exit: To exit to previous menu");
    }

    public void CardInventory(){
        System.out.println("Card Inventory:");
        Integer i = 1;
        for (Card card: this.anInterface.getPlayer().getInventoryMap().keySet()) {
            if(this.anInterface.getPlayer().getDeckMap().get(card) != null)
                System.out.println(i.toString() + ". " + this.anInterface.getPlayer().getInventoryMap().get(card).toString() + " " + card.getName() + " / " + this.anInterface.getPlayer().getDeckMap().get(card).toString() + " on deck");
            else
                System.out.println(i.toString() + ". " + this.anInterface.getPlayer().getInventoryMap().get(card).toString() + " " + card.getName() + " / 0 on deck");
            i++;
        }
    }

    public void ItemInventory(){
        System.out.println("Item Inventory:");
        Integer i = 1;
        for (Item item: this.anInterface.getPlayer().getItemMap().keySet()) {
            System.out.println(i.toString() + ". " + this.anInterface.getPlayer().getItemMap().get(item).toString() + " " + item.getName());
            i++;
        }
    }

    public void AmuletInventory(){
        System.out.println("Amulet Inventory");
        Integer i = 1;
        for (Amulet amulet: this.anInterface.getPlayer().getAmuletMap().keySet()) {
            System.out.println(i.toString() + " " + this.anInterface.getPlayer().getAmuletMap().get(amulet).toString() + " " + amulet.getName());
            i++;
        }
    }



}
