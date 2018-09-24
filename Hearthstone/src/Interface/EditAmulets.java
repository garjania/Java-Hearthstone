package Interface;

import Data.Amulet;

import java.util.ArrayList;
import java.util.Scanner;

public class EditAmulets{

    private Interface anInterface;
    private ArrayList<Amulet> PlayerInventory;
    private Amulet Equipped;

    EditAmulets(Interface anInterface){
        this.anInterface = anInterface;
        PlayerInventory = anInterface.getPlayer().getAmuletInventory();
        Equipped = anInterface.getPlayer().getAmulet();
    }

    public void again() {
        System.out.println("Amulets");
        for (int i = 0; i < PlayerInventory.size(); i++) {
            System.out.print(i + 1);
            System.out.println(".\t"+ PlayerInventory.get(i).getName());
        }
        if (Equipped != null)
            System.out.println("Player is equipped with " + Equipped.getName());
    }

    public void help() {
        System.out.println("1. Equip \"Amulet Name\": To equip the player with an amulet\n" +
                "2. Remove Amulet: To remove the amulet equipped with the player (if the player is\n" +
                "equipped with one)\n" +
                "3. Info \"Amulet Name\": To get more information about a specific amulet\n" +
                "4. Exit: To return to the previous section");
    }
    public void start() {
        again();
        String Input = "";
        Scanner scn = new Scanner(System.in);

        while(!Input.equals("Exit")){
            Input = scn.nextLine();
            if(Input.equals("Exit"))
                break;
            String[] parts = Input.split(" ");
            parts[1] = Input.replace(parts[0].concat(" "), "");

            switch (parts[0]) {
                case "Equip":
                    for (Amulet amulet: PlayerInventory) {
                        if(amulet.getName().equals(parts[1])){
                            anInterface.getPlayer().equip(amulet);
                            break;
                        }
                    }
                    System.out.println(parts[1] + " has equipped on the player");
                    break;

                case "Remove":
                    System.out.println(parts[1] + " has removed!");
                    anInterface.getPlayer().equip(null);
                    break;

                case "Info":
                    for (Amulet amulet: anInterface.getData().getAmulets()) {
                        if(amulet.getName().equals(parts[1])){
                            System.out.println(amulet.toString());
                            break;
                        }
                    }
                    break;

                default:
                    break;

            }

        }
    }
}