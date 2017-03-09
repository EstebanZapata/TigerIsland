
public enum Terrain {
    JUNGLE(true),
    LAKE(true),
    GRASSLANDS(true),
    ROCKY(true),

    VOLCANO(false);

    private final boolean isHabitable;

    Terrain(boolean isHabitable) {
        this.isHabitable = isHabitable;
    }

    public boolean isHabitable() {
        return this.isHabitable;
    }

}
