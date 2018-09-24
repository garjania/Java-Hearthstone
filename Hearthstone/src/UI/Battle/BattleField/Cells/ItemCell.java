package UI.Battle.BattleField.Cells;

import UI.DataView.Item.ItemView;
import javafx.scene.Group;

public class ItemCell extends Group {
    private ItemView item;

    public ItemCell(ItemView item) {
        this.item = item;
        getChildren().add(item);
    }

    public ItemView getItemView() {
        return item;
    }

    public void remove(){
        getChildren().remove(item);
    }
}
