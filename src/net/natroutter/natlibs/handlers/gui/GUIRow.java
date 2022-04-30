package net.natroutter.natlibs.handlers.gui;

public enum GUIRow {

    GUIRow1(1),
    GUIRow2(2),
    GUIRow3(3),
    GUIRow4(4),
    GUIRow5(5),
    GUIRow6(6);

    private int row;
    GUIRow(Integer row) { this.row = row; }
    public int getRow() { return (row - 1) * 9; }
    public int getInvRow() { return row* 9; }

}
