package com.gbattag.model;

import java.util.Arrays;
import java.util.Collection;

public class Board {
    private final boolean[][] emptySquares;

    public Board(int rows, int columns, Collection<BoardPosition> obstaclePositions) {
        this.emptySquares = new boolean[rows][columns];

        for (boolean[] row : this.emptySquares)
            Arrays.fill(row, true);

        initObstacles(obstaclePositions);
    }

    private void initObstacles(Collection<BoardPosition> obstaclePositions) {
        for (BoardPosition p : obstaclePositions) {
            emptySquares[p.getRow()][p.getColumn()] = false;
        }
    }

    public int getColumns() {
        return emptySquares[0].length;
    }

    public int getRows() {
        return emptySquares.length;
    }

    public boolean isOutOfTheBoard(BoardPosition boardPosition) {
        final int rows = this.getRows();
        final int columns = this.getColumns();

        final int row = boardPosition.getRow();
        final int column = boardPosition.getColumn();

        return row < 0 || row >= rows || column < 0 || column >= columns;
    }

    public boolean isEmptyPosition(BoardPosition boardPosition) {
        return emptySquares[boardPosition.getRow()][boardPosition.getColumn()];
    }

    @Override
    public String toString() {
        final StringBuilder res = new StringBuilder();
        for (boolean[] row : this.emptySquares) {
            res.append(Arrays.toString(row));
        }

        return res.toString();
    }
}
