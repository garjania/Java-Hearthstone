package Interface.Shop;


import Data.Amulet;

import java.util.ArrayList;
import java.util.Scanner;

public class AmuletShop{

    private Shop shop;
    private ArrayList<Amulet> Amulets;

    public AmuletShop(Shop shop){
        this.shop = shop;
        Amulets = shop.getData().getAmulets();
    }

    public void start(){
        again();
        String Input = "";
        Scanner scn = new Scanner(System.in);

        while(!Input.equals("Exit")){
            Input = scn.nextLine();
            String[] parts = Input.split(" ");

            switch (parts[0]){
                case "Buy":
                    parts[1] = Input.replace("Buy ", "");
                    for (Amulet amulet: Amulets) {
                        if(amulet.getName().equals(parts[1])){
                            buy(amulet);
                            break;
                        }
                    }
                    break;
                case "Sell":
                    for (Amulet amulet: shop.getPlayer().getAmuletInventory()) {
                        if(amulet.getName().equals(parts[1])){
                            sell(amulet);
                            break;
                        }
                    }
                    break;
                case "Info": parts[1] = Input.replace("Info ","");
                    for (Amulet amulet: Amulets) {
                        if(amulet.getName().equals(parts[1])){
                            System.out.println(amulet.toString());
                            break;
                        }
                    }
                    break;
                case "Help":
                    help();
                case "Edit":
                    shop.getAnInterface().getEditAmulets().start();
                    System.out.println("Amulet Shop");
                    break;
                case "Again":
                    again();
                    break;
                default:
                    break;
            }
        }

        //print shop list and amulet inventory + equip from data(call again)
        //while(){}
    }
    public void again(){
        System.out.println(shop.getPlayer().getGils());

        System.out.println("\tShop List:");
        for (int i = 0; i < Amulets.size(); i++) {
            System.out.print(i + 1);
            System.out.println(".\t"+ Amulets.get(i).getName() + " " + Amulets.get(i).getCost());
        }

        if(shop.getPlayer().getAmulet() != null)
            System.out.println("Equipped Amulet: " + shop.getPlayer().getAmulet().getName());
        else
            System.out.println("Equipped Amulet: Empty");
        System.out.println("Amulet Inventory:");

        for (int i = 0; i < shop.getPlayer().getAmuletInventory().size(); i++) {
            System.out.print(i + 1);
            System.out.println(".\t"+ shop.getPlayer().getAmuletInventory().get(i).getName());
        }
    }

    public void help() {
        System.out.println("#GilDetails\n" +
                "1.\tBuy \"Amulet Name\" - #NumberToBuy: To buy a number of an amulet from the shop\n" +
                "2.\tSell \"Amulet Name\" - #NumberToSell: To sell a number of an amulet from amulet inventory\n" +
                "3.\tInfo \"Amulet Namet\": To get full info on an amulet\n" +
                "4.\tEdit Amulets: To equip or remove your hero's amulet\n" +
                "5.\tExit: To exit to the shop menu");
    }

    public void buy(Amulet amulet) {
        if (shop.getPlayer().getAmulet() != null && amulet.equals(shop.getPlayer().getAmulet())) {
            System.out.println("Item Not In Store");
            return;
        }
        for (Amulet a: shop.getPlayer().getAmuletInventory()) {
            if (amulet.equals(a)) {
                System.out.println("Item Not In Store");
                return;
            }
        }
        if (shop.getPlayer().getGils() < amulet.getCost()) {
            System.out.println("Not enough Gil!");
        } else {
            Amulet temp = amulet.copyAmulet();
            shop.getPlayer().editGils(-1 * temp.getCost());
            shop.getPlayer().addToAmuletInventory(temp);
            System.out.println("Successfuly bought " + temp.getName());
        }
    }

    public void sell(Amulet amulet) {
        if (amulet.equals(shop.getPlayer().getAmulet())) {
            System.out.println("Cannot Sell Item");
            return;
        }
        shop.getPlayer().getAmuletInventory().remove(amulet);
        shop.getPlayer().editGils(amulet.getCost());
    }

}
