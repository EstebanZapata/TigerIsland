package game.tile;

public enum Terrain {
    JUNGLE(true),
    LAKE(true),
    GRASSLANDS(true),
    ROCKY(true),

    VOLCANO(false);

    private boolean isHabitable;

    Terrain(boolean isHabitable) {
        this.isHabitable = isHabitable;
    }

    public boolean isHabitable() {
        return this.isHabitable;
    }

}
