package Interface;

import Battle.*;

public class Next{
    private Interface anInterface;
    private Battle battle;

    Next(Interface anInterface){
        this.anInterface = anInterface;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public void start() {
        if(anInterface.getEditDeck().start().equals("Start"))
            battle.start();
    }
}
