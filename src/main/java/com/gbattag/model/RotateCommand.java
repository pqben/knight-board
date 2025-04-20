package com.gbattag.model;

public class RotateCommand implements Command {
    private final Direction direction;

    public RotateCommand(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public KnightStatusAndCommandResult execute(Board board, KnightStatus knightStatus) {
        if (knightStatus.equals(KnightStatus.KNIGHT_INITIAL_STATUS)) {
            // a START command has never been executed
            return new KnightStatusAndCommandResult(knightStatus, CommandResult.GENERIC_ERROR);
        }

        final KnightStatus newKnightStatus = new KnightStatus(knightStatus.getKnightPosition(), direction);
        return new KnightStatusAndCommandResult(newKnightStatus, CommandResult.SUCCESS);
    }
}
