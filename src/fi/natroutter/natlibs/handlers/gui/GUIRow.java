package fi.natroutter.natlibs.handlers.gui;

public enum GUIRow {

    row1(1),
    row2(2),
    row3(3),
    row4(4),
    row5(5),
    row6(6);

    private final int row;
    GUIRow(Integer row) { this.row = row; }
    public int getRow() { return (row - 1) * 9; }
    public int getInvRow() { return row* 9; }

}
