package com.gbattag.model;

public class MoveCommand implements Command {
    private final int steps;

    public MoveCommand(int steps) {
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    @Override
    public KnightStatusAndCommandResult execute(Board board, KnightStatus knightStatus) {
        if (knightStatus.equals(KnightStatus.KNIGHT_INITIAL_STATUS)) {
            // a START command has never been executed
            return new KnightStatusAndCommandResult(knightStatus, CommandResult.GENERIC_ERROR);
        }

        return executeAllSteps(board, knightStatus);
    }

    private KnightStatusAndCommandResult executeAllSteps(Board board, KnightStatus knightStatus) {
        CommandResult status = CommandResult.SUCCESS;
        KnightStatus currentKnightStatus = knightStatus;
        int remainingSteps = steps;

        KnightStatusAndCommandResult result = new KnightStatusAndCommandResult(knightStatus, CommandResult.SUCCESS);
        while (status == CommandResult.SUCCESS && remainingSteps > 0) {
            result = nextStep(board, currentKnightStatus);

            status = result.commandResult();
            currentKnightStatus = result.knightStatus();
            --remainingSteps;
        }

        return result;
    }

    private KnightStatusAndCommandResult nextStep(Board board, KnightStatus knightStatus) {
        final Direction knightDirection = knightStatus.getKnightDirection();
        final BoardPosition newBoardPosition = fetchNextPosition(knightStatus);

        if (board.isOutOfTheBoard(newBoardPosition)) {
            return new KnightStatusAndCommandResult(knightStatus, CommandResult.OUT_OF_THE_BOARD);
        } else if (board.isEmptyPosition(newBoardPosition))
            // if no obstacle the knight moves to the new cell
            return new KnightStatusAndCommandResult(new KnightStatus(newBoardPosition, knightDirection), CommandResult.SUCCESS);
        else
            // if the cell contains an obstacle the knight stays in the old position
            return new KnightStatusAndCommandResult(knightStatus, CommandResult.SUCCESS);
    }

    private BoardPosition fetchNextPosition(KnightStatus knightStatus) {
        final int row = knightStatus.getKnightPosition().getRow();
        final int column = knightStatus.getKnightPosition().getColumn();
        final Direction knightDirection = knightStatus.getKnightDirection();

        return switch (knightDirection) {
            case NORTH -> new BoardPosition(row + 1, column);
            case SOUTH -> new BoardPosition(row - 1, column);
            case EAST -> new BoardPosition(row, column + 1);
            case WEST -> new BoardPosition(row, column - 1);
        };
    }
}
