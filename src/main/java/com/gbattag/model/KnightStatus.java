package com.gbattag.model;

import java.util.Objects;

public class KnightStatus {
    public static KnightStatus KNIGHT_INITIAL_STATUS = new KnightStatus(new BoardPosition(-1, -1), Direction.SOUTH);

    private final BoardPosition knightPosition;
    private final Direction knightDirection;

    public KnightStatus(BoardPosition knightPosition, Direction knightDirection) {
        this.knightPosition = knightPosition;
        this.knightDirection = knightDirection;
    }

    public Direction getKnightDirection() {
        return knightDirection;
    }

    public BoardPosition getKnightPosition() {
        return knightPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KnightStatus that = (KnightStatus) o;
        return Objects.equals(knightPosition, that.knightPosition) && Objects.equals(knightDirection, that.knightDirection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knightPosition, knightDirection);
    }

    @Override
    public String toString() {
        return String.format("{knightPosition=%s,knightDirection=%s}", knightPosition, knightDirection);
    }
}
