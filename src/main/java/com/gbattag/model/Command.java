package com.gbattag.model;

public interface Command {
    KnightStatusAndCommandResult execute(Board board, KnightStatus knightStatus);
}
