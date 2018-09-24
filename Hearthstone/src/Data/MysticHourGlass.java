package Data;

import Player.Player;
import Battle.Level.*;

public class MysticHourGlass{
    private Player player;

    public MysticHourGlass(Player player){
        this.player = player;
    }

    public void doSpell(Level level){
        level.setCurrentTurn(player.getThisLevel().getTurns().get(0));
        level.getTurns().clear();
        level.getTurns().add(player.getThisLevel().getCurrentTurn());
    }
}
