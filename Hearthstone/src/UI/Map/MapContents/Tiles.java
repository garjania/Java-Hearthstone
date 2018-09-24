package UI.Map.MapContents;


public class Tiles {
    private Integer tileNumber;
    private Boolean canGo = false;
    private boolean canFight = false;
    private int[] passableTiles = {232, 377, 2582, 1557, 1658};
    private int[] battleTiles = {597, 598, 596, 588, 605, 621};

    public Tiles(Integer tileNumber) {
        this.tileNumber = tileNumber;
        for (int i = 0; i < passableTiles.length; i++) {
            if(Math.abs(tileNumber - passableTiles[i]) < 3){
                canGo = true;
                break;
            }
        }

        for (int i = 0; i < battleTiles.length; i++) {
            if(Math.abs(tileNumber - battleTiles[i]) <= 3){
                canFight = true;
                break;
            }
        }
    }

    public Boolean canGo() {
        return canGo;
    }

    public boolean CanFight() {
        return canFight;
    }

    public Integer getTileNumber() {
        return tileNumber;
    }
}
