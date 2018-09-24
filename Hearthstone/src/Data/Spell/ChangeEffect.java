package Data.Spell;

import Data.Card.Card;
import Data.Card.Position;
import Player.Player;

import java.util.ArrayList;

public class ChangeEffect extends Effect{
    private ArrayList<Target> Targets;
    private String Place1;
    private String Place2;
    private Player Friend;


    public ChangeEffect(String details, String place1, String place2, Player friend){
        super(details, friend);
        Friend = friend;
        Place1 = place1;
        Place2 = place2;
    }

    @Override
    public void start(){
        if(Targets.size() > 0 || TargetExtractor())
            doEffect();
    }

    @Override
    public void doEffect(){
        Targets = getTargets();
        if(Place1.equals("Field") || Place1.equals("field")){
            if(Place2.equals("Hand") || Place2.equals("hand")){
                for (int i = 0; i < Targets.size(); i++) {
                    if(Targets.get(i) instanceof Card){
                        if(((Card) Targets.get(i)).getPosition() == Position.FIELD){
                            ((Card) Targets.get(i)).changePosition(Position.HAND);
                            ((Card) Targets.get(i)).getPlayer().getField().getCardField().remove((Targets.get(i)));
                            ((Card) Targets.get(i)).getPlayer().addToHand(((Card) Targets.get(i)));
                        }
                    }
                }
            }

            if(Place2.equals("Graveyard") || Place2.equals("graveyard")){
                for (int i = 0; i < Targets.size(); i++) {
                    if(Targets.get(i) instanceof Card){
                        if(((Card) Targets.get(i)).getPosition() == Position.FIELD){
                            ((Card) Targets.get(i)).getPlayer().getField().moveToGrave(((Card) Targets.get(i)));
                        }
                    }
                }
            }
        }

        if(Place1.equals("Hand") || Place1.equals("hand")){
            if(Place2.equals("Field") || Place2.equals("field")){
                for (int i = 0; i < Targets.size(); i++) {
                    if(Targets.get(i) instanceof Card){
                        if(((Card) Targets.get(i)).getPosition() == Position.HAND){
                            ((Card) Targets.get(i)).getPlayer().removeFromHand(((Card) Targets.get(i)));
                            ((Card) Targets.get(i)).getPlayer().getField().addToField(((Card) Targets.get(i)));
                        }
                    }
                }
            }
        }

        if(Place1.equals("Graveyard") || Place1.equals("graveyard")){
            if(Place2.equals("Hand") || Place2.equals("hand")){
                for (int i = 0; i < Targets.size(); i++) {
                    if(Targets.get(i) instanceof Card){
                        if(((Card) Targets.get(i)).getPosition() == Position.GRAVEYARD){
                            ((Card) Targets.get(i)).getPlayer().getField().getGrave().remove(((Card) Targets.get(i)));
                            ((Card) Targets.get(i)).getPlayer().addToHand(((Card) Targets.get(i)));
                        }
                    }
                }
            }
        }

        if(Place1.equals("Deck") || Place1.equals("deck")){
            for (int i = 0; i < Targets.size(); i++) {
                if(Targets.get(i) instanceof Card){
                    Friend.removeFromDeck(((Card) Targets.get(i)), 1);
                    Friend.addToHand(((Card) Targets.get(i)));
                }
            }
        }
    }

    public String getPlace1() {
        return Place1;
    }

    public String getPlace2() {
        return Place2;
    }
}
