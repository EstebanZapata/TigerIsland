package tile;

public class Location {
    private int row;
    private int col;
    private int height;

    public Location(int row, int col, int height) {
        this.row = row;
        this.col = col;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}
