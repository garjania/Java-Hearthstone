package Data.Spell;

import Player.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Spell{
    private String Detail;
    private ArrayList<String> PartialDetails;
    private Player Friend;
    private ArrayList<Effect> Effect = new ArrayList<>();

    public Spell(ArrayList<String> details, String detail){
        Detail = detail;
        PartialDetails = details;
    }

    public void doSpell(Player friend){
        Friend = friend;
        Effect = composeEffects();

        for (int i = 0; i < Effect.size(); i++) {
            try {
                Effect.get(i).start();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(Detail);
            }

        }
    }

    public ArrayList<Effect> composeEffects() {
        ArrayList<Effect> result = new ArrayList<>();
        for (String str: PartialDetails) {
            result.add(EffectExtractor(str));
        }
        return result;
    }

    public ArrayList<Data.Spell.Effect> getEffect() {
        return Effect;
    }

    public Effect EffectExtractor(String Detail){
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(Detail.split(" ")));

        if(parts.contains("Deal") || parts.contains("deal") || parts.contains("Decrease") || parts.contains("decrease") || parts.contains("Reduce") || parts.contains("reduce")){
            Integer Amount = 0;
            for (String str: parts) {
                try{
                    Integer.parseInt(str);
                }catch (Exception e){
                    continue;
                }
                Amount = Integer.parseInt(str);
            }
            if(parts.contains("HP") || parts.contains("Deal") || parts.contains("deal")){
                 return new PowerEffect("HP",-1 * Amount, Detail, Friend, false);
            }
            if(parts.contains("AP")){
                return new PowerEffect("AP",-1 * Amount, Detail, Friend, false);
            }
        }

        if(parts.contains("Increase") || parts.contains("increase") || parts.contains("Heal") || parts.contains("heal")){
            Integer Amount = 0;
            for (String str: parts) {
                try{
                    Integer.parseInt(str);
                }catch (Exception e){
                    continue;
                }
                Amount = Integer.parseInt(str);
            }
            if(parts.contains("HP")){
                return new PowerEffect("HP", Amount, Detail, Friend,false);
            }
            if(parts.contains("AP")){
                return new PowerEffect("AP", Amount, Detail, Friend, false);
            }

            if(parts.contains("MP")){
                return new PowerEffect("MP", Amount, Detail, Friend, false);
            }
        }

        if(parts.contains("Move") || parts.contains("move") || parts.contains("Send") || parts.contains("send") || parts.contains("Draw") || parts.contains("draw") || parts.contains("Return") || parts.contains("return")){
            ArrayList<String> places = new ArrayList<>();
            for (String str: parts) {
                if(str.equals("field") || str.equals("Field") || str.equals("hand") || str.equals("Hand") || str.equals("graveyard") || str.equals("deck"))
                    places.add(str);
            }
            if(places.size() == 1 && places.get(0).equals("deck"))
                places.add("");
            try {
                return new ChangeEffect(Detail, places.get(0), places.get(1), Friend);
            }catch (Exception e){
                for (String p: places) {
                    System.out.println(p);
                }
                System.out.println(Detail);
                System.exit(1);
            }

        }

        return null;
    }

    public Spell copySpell(){
        return new Spell(getPartialDetails(), getDetail());
    }

    @Override
    public String toString(){
        return Detail;
    }

    public ArrayList<String> getPartialDetails() {
        return PartialDetails;
    }

    public String getDetail() {
        return Detail;
    }
}

