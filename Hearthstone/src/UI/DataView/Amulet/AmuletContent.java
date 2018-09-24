package UI.DataView.Amulet;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class AmuletContent extends Group {
    private AmuletView amulet;
    private AmuletInfo amuletInfo;
    private boolean showingInfo = false;

    public AmuletContent(AmuletView amulet) {
        this.amulet = amulet;
        amuletInfo = new AmuletInfo(amulet.getAmulet());
        amuletInfo.setLayoutX(10);
        amuletInfo.setLayoutY(10);
        getChildren().add(amulet);
        setClick();
    }

    private void setClick(){
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!amuletInfo.isClickingButton()){
                    if (showingInfo) {
                        amulet.getChildren().remove(amuletInfo);
                        ScaleTransition transition = new ScaleTransition(Duration.millis(500), amulet);
                        transition.setToX(1);
                        transition.setToY(1);
                        transition.play();
                        amulet.addContents();
                    } else {
                        amulet.removeContent();
                        ScaleTransition transition = new ScaleTransition(Duration.millis(500), amulet);
                        transition.setToX(1.5);
                        transition.setToY(1.5);
                        transition.play();
                        amulet.getChildren().add(amuletInfo);

                    }
                    showingInfo = !showingInfo;
                }
                else
                    amuletInfo.setClickingButton(false);
            }
        });
    }

    public void refresh(){
        amuletInfo.refreshInfo();
    }
}
