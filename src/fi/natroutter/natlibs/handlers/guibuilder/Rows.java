package fi.natroutter.natlibs.handlers.guibuilder;

public enum Rows {

    row1(1),
    row2(2),
    row3(3),
    row4(4),
    row5(5),
    row6(6);

    private int rows;
    Rows(int rows) {
        this.rows = rows;
    }

    public int getRow(){
        return this.rows;
    }

    public int getSlots() {
        return (rows - 1) * 9;
    }

}
