package com.gbattag.model;

public class StartCommand implements Command {
    private final BoardPosition boardPosition;
    private final Direction direction;

    public StartCommand(int row, int column, Direction direction) {
        this.boardPosition = new BoardPosition(row, column);
        this.direction = direction;
    }

    public BoardPosition getBoardPosition() {
        return boardPosition;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public KnightStatusAndCommandResult execute(Board board, KnightStatus knightStatus) {
        final int row = boardPosition.getRow();
        final int column = boardPosition.getColumn();

        if (!knightStatus.equals(KnightStatus.KNIGHT_INITIAL_STATUS)) {
            // a START command has already been executed
            return new KnightStatusAndCommandResult(knightStatus, CommandResult.GENERIC_ERROR);
        } else if (!board.isOutOfTheBoard(boardPosition) && board.isEmptyPosition(boardPosition)) {
            final KnightStatus newKnightStatus = new KnightStatus(new BoardPosition(row, column), direction);
            return new KnightStatusAndCommandResult(newKnightStatus, CommandResult.SUCCESS);
        } else {
            return new KnightStatusAndCommandResult(knightStatus, CommandResult.INVALID_START_POSITION);
        }
    }
}
