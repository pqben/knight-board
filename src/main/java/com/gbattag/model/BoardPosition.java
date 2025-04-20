package com.gbattag.model;

import java.util.Objects;

public class BoardPosition {
    private final int row;
    private final int column;

    public BoardPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        final BoardPosition p = (BoardPosition) o;
        return row == p.row && column == p.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return String.format("{row=%d, column=%d}", row, column);
    }
}
