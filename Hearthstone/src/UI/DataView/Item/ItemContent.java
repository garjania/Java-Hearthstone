package UI.DataView.Item;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ItemContent extends Group {
    private ItemView item;
    private ItemInfo itemInfo;
    private boolean showingInfo = false;

    public ItemContent(ItemView item) {
        this.item = item;
        itemInfo = new ItemInfo(item.getItem());
        itemInfo.setLayoutX(10);
        itemInfo.setLayoutY(10);
        getChildren().add(item);
        setClick();
    }

    private void setClick(){
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!itemInfo.isClickingButton()){
                    if (showingInfo) {
                        item.getChildren().remove(itemInfo);
                        ScaleTransition transition = new ScaleTransition(Duration.millis(500), item);
                        transition.setToX(1);
                        transition.setToY(1);
                        transition.play();
                        item.addContents();
                    } else {
                        item.removeContent();
                        ScaleTransition transition = new ScaleTransition(Duration.millis(500), item);
                        transition.setToX(1.5);
                        transition.setToY(1.5);
                        transition.play();
                        item.getChildren().add(itemInfo);

                    }
                    showingInfo = !showingInfo;
                }
                else
                    itemInfo.setClickingButton(false);
            }
        });
    }

    public void refresh(){
        itemInfo.refreshInfo();
    }
}
