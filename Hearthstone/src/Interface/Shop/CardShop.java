package Interface.Shop;

import Data.Card.Card;
import Data.Card.Kind;
import Player.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CardShop{

    private Shop shop;
    private ArrayList<Card> AllCards;

    public CardShop(Shop shop){
        this.shop = shop;
        AllCards = shop.getData().getCards();
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
                if(i > 0 && i < contain.length - 2)
                    if(i != contain.length - 3)
                        parts[1] += contain[i] + " ";
                    else
                        parts[1] += contain[i];
                if(i == contain.length - 1)
                    parts[2] = contain[i];
            }

            switch (parts[0]){
                case "Buy":
                    for (Card card: AllCards) {
                        if(card.getName().equals(parts[1])){
                            buy(card, Integer.parseInt(parts[2]));
                            break;
                        }
                    }
                    break;
                case "Sell":
                    for (Card card: AllCards) {
                        if(card.getName().equals(parts[1])){
                            sell(card, Integer.parseInt(parts[2]));
                            break;
                        }
                    }
                    break;
                case "Info":
                    parts[1] = Input.replace("Info ","");
                    for (Card card: AllCards) {
                        if(card.getName().equals(parts[1])){
                            System.out.println(card.toString());
                            break;
                        }
                    }
                    break;
                case "Edit":
                    shop.getAnInterface().getEditDeck().start();
                    System.out.println("Card Shop");
                    break;
                case "Help":
                    help();
                    break;
                case "Again":
                    again();
                    break;
                default:
                    break;
            }
        }
    }

    //Again
    public void again(){
        System.out.println(shop.getPlayer().getGils().toString() + " Gills");
        System.out.println("Shop List:");
        for (Integer i = 1; i <= AllCards.size(); i++) {
            System.out.println(i.toString() + ".  " + AllCards.get(i-1).getName() + " " + AllCards.get(i-1).getCost());
        }
        System.out.println("Card Inventory:");
        Integer i = 1;
        Set<Card> CardSet = shop.getPlayer().getInventoryMap().keySet();
        for (Card card: CardSet) {
            System.out.println(i.toString() + ".  " + card.getName() + " / " + shop.getPlayer().getInventoryMap().get(card));
            i++;
        }

    }
    //Help
    public void help(){
        System.out.println(shop.getPlayer().getGils().toString() + " Gills");
        System.out.println("1. Buy \"Card Name\" - #NumberToBuy: To buy a certain number of a card from shop");
        System.out.println("2. Sell \"Card Name\" - #NumberToSell: To sell a certain number of a card from inventory");
        System.out.println("3. Info \"Card Name\": To get more information about a card");
        System.out.println("4. Edit Deck: To edit Deck and remove and add cards to it");
        System.out.println("5. Exit: To return to shop menu");
    }
    //buy
    public void buy(Card card, Integer number){
        Player player = shop.getPlayer();

        if(player.getGils() < card.getCost() * number){
            System.out.println("Not enough Gil!");
            return;
        }

        if(card.getKind() == Kind.MONSTER)
            switch (card.getType()){
                case NORMAL:
                    if(number > 4){
                        System.out.println("Not allowed!");
                        return;
                    }
                    break;
                case SPELLCASTER:
                    if(number > 2){
                        System.out.println("Not allowed!");
                        return;
                    }
                    break;
                case GENERAL:
                    if(number > 2){
                        System.out.println("Not allowed!");
                        return;
                    }
                    break;
                case HERO:
                    if(number > 4){
                        System.out.println("Not allowed!");
                        return;
                    }
                    break;
                default:
                    break;
            }

        if(card.getKind() == Kind.SPELL){
            if(card.getName().equals("Blodd Feast") || card.getName().equals("Reaper's Scythe") || card.getName().equals("Meteor Shower")){
                System.out.println("Not allowed!");
                return;
            }

            if(card.getMP() < 6){
                if(number > 3){
                    System.out.println("Not allowed!");
                    return;
                }
            }else{
                if(number > 2){
                    System.out.println("Not allowed!");
                    return;
                }
            }
        }

        player.editGils(-1 * card.getCost() * number);

        for (int i = 0; i < number; i++) {
            Card theCard = card.copyCard();
            theCard.setPlayer(player);
            player.addToCardInventory(theCard);
        }

        System.out.println("Successfully bought " + number.toString() + " of " + card.getName() + "!");
    }
    //sell
    public void sell(Card card, Integer number){
        if(shop.getPlayer().getInventoryMap().containsKey(card))
            if(shop.getPlayer().getInventoryMap().get(card) < number){
                System.out.println("Not enough cards!");
                return;
            }else{
                Player player = shop.getPlayer();

                for (int i = 0; i < number; i++) {
                    player.removeFromCardInventory(card);
                }

                player.editGils((card.getCost() * number) / 2);
                System.out.println("Successfully sold " + number.toString() + " of " + card.getName() + "!");
            }
    }

}
