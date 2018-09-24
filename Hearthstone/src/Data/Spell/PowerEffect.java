package Data.Spell;

import Player.Player;

import java.util.ArrayList;

public class PowerEffect extends Effect{
    private String PowerType;
    private Integer Amount;
    private ArrayList<Target> Targets = new ArrayList<>();
    private PowerEffect ReverseEffect;


    public PowerEffect(String powerType, int amount, String details, Player friend, boolean isReverse){
        super(details, friend);
        if(!isReverse)
            ReverseEffect = new PowerEffect(powerType, -1 * amount, details, friend, true);
        PowerType = powerType;
        Amount = amount;
    }

    @Override
    public Effect getReverseEffect() {
        return ReverseEffect;
    }

    @Override
    public void start(){
        if(Targets.size() > 0 || TargetExtractor())
            doEffect();
    }

    @Override
    public void doEffect(){
        Targets = getTargets();
        if(PowerType.equals("HP")){
            for (int i = 0; i < Targets.size(); i++) {
                if(Targets.get(i) != null)
                    Targets.get(i).changeHP(Amount);
            }
        }

        if(PowerType.equals("AP")){
            for (int i = 0; i < Targets.size(); i++) {
                if(Targets.get(i) != null)
                    Targets.get(i).changeAP(Amount);
            }
        }

        if(PowerType.equals("MP")){
            for (int i = 0; i < Targets.size(); i++) {
                if(Targets.get(i) != null && Targets.get(i) instanceof  Player)
                    ((Player) Targets.get(i)).setMaxMP(((Player) Targets.get(i)).getMaxMP() + Amount);
            }
        }
    }

    public String getPowerType() {
        return PowerType;
    }

    public Integer getAmount() {
        return Amount;
    }
}
