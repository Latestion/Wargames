package me.latestion.wargames.game.enums;

public enum WarSize {

    FFA("FFA"), T_2("2"), T_3("3"), T_4("4");

    private final String s;

    WarSize(String s) {
        this.s = s;
    }

    public String getString() {
        return s;
    }

    public int getSizeAsInt() {
        if (this == WarSize.FFA) {
            return 1;
        }
        return Integer.parseInt(getString());
    }

}
