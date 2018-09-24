package Interface;

import Data.Card.Card;
import Player.*;

import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class EditDeck{

    private Interface anInterface;
    private Player Player;
    private ArrayList<Card> PlayerCards;
    private ArrayList<Card> PlayerDeck;

    EditDeck(Interface anInterface){
        this.anInterface = anInterface;
        Player = anInterface.getPlayer();
        PlayerCards = Player.getCardInventory();
        PlayerDeck = Player.getDeck();
    }

    public EditDeck(Player player){
        Player = player;
        PlayerCards = player.getCardInventory();
        PlayerDeck = player.getDeck();
    }

    public String start(){
        again();
        String Input = "";
        Scanner scn = new Scanner(System.in);

        while(!Input.equals("Exit") && !Input.equals("Start")){
            Input = scn.nextLine();
            String[] contain = Input.split(" ");
            String[] parts = {"", "", ""};
            for (int i = 0; i < contain.length; i++) {
                if(i == 0)
                    parts[0] = contain[0];
                if(i > 0 && i < contain.length - 1)
                    if(i != contain.length - 2)
                        parts[1] += contain[i] + " ";
                    else
                        parts[1] += contain[i];
                if(i == contain.length - 1)
                    parts[2] = contain[i];
            }

            switch (parts[0]){
                case "Add":
                    for (Card card: PlayerCards) {
                        if(card.getName().equals(parts[1])){
                            add(card, Integer.parseInt(parts[2]));
                            break;
                        }
                    }
                    break;
                case "Remove":
                    for (Card card: PlayerCards) {
                        if(card.getName().equals(parts[1])){
                            remove(card, Integer.parseInt(parts[2]));
                            break;
                        }
                    }
                    break;
                case "Info":
                    for (Card card: PlayerCards) {
                        if(card.getName().equals(parts[1])){
                            System.out.println(card.toString());
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return Input;

    }

    public void again(){
        System.out.println("Deck:");
        for (Integer i = 1; i <= 30; i++) {
            if(PlayerDeck.get(i-1) != null)
                System.out.println("Slot " + i.toString() + ":" + PlayerDeck.get(i-1).getName());
            else
                System.out.println("Slot " + i.toString() + ": Empty");
        }
        System.out.println("Other Cards:");
        Integer i = 1;
        for (Card card: Player.getInventoryMap().keySet()) {
            if(Player.getDeckMap().get(card) != null)
                System.out.println(i.toString() + ". " + Player.getInventoryMap().get(card).toString() + " " + card.getName() + " / " + Player.getDeckMap().get(card).toString() + " on deck");
            else
                System.out.println(i.toString() + ". " + Player.getInventoryMap().get(card).toString() + " " + card.getName() + " / 0 on deck");
            i++;
        }
    }

    public void help(){
        System.out.println("1. Add \"Card Name\" #CardSlotNum: To add cards to your deck");
        System.out.println("2. Remove \"Card Name\" #CardSlotNum: To remove cards from your deck");
        System.out.println("3. Info \"Card Name\": To get more information about a specific card");
        System.out.println("4. Exit: To return to the previous section");
    }

    public boolean add(Card card, Integer index){
        if(Player.getInventoryMap().containsKey(card))
            if(!Player.getDeckMap().containsKey(card) || Player.getInventoryMap().get(card) > Player.getDeckMap().get(card)){
                Player.addToDeck(card, index);
                System.out.println(card.getName() + " was added to slot " + index.toString());
                return true;
            }
        return false;
    }

    public static boolean add(Card card, Integer index, Player player){
        if(player.getInventoryMap().containsKey(card))
            if(!player.getDeckMap().containsKey(card) || player.getInventoryMap().get(card) > player.getDeckMap().get(card)){
                player.addToDeck(card, index);
                return true;
            }
        return false;
    }

    public boolean remove(Card card, Integer index){
        int count = 0;
        for (int i = 0; i < Player.getDeck().size(); i++) {
            if(Player.getDeck().get(i) != null)
                count++;
        }
        if(count > 25) {
            Player.removeFromDeck(card, index);
            Integer in = index + 1;
            System.out.println(card.getName() + " was removed from slot " + in.toString());
            return true;
        }
        return false;
    }

    public static boolean remove(Card card, Integer index, Player player){
        int count = 0;
        for (int i = 0; i < player.getDeck().size(); i++) {
            if(player.getDeck().get(i) != null)
                count++;
        }
        if(count > 25){
            player.removeFromDeck(card, index);
            return true;
        }
        return false;
    }

}
