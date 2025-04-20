package com.gbattag.service;

import com.gbattag.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.gbattag.model.CommandResult.*;
import static com.gbattag.model.Direction.*;
import static com.gbattag.model.KnightStatus.KNIGHT_INITIAL_STATUS;
import static com.gbattag.testdata.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class CommandExecutorServiceTest {
    private final CommandExecutorService commandExecutorService = new CommandExecutorService();

    @ParameterizedTest(name = "{0}")
    @MethodSource("singletonStartCommandTestDataProvider")
    @MethodSource("singletonRotateCommandTestDataProvider")
    @MethodSource("singletonMoveCommandTestDataProvider")
    @MethodSource("singletonMoveCommandWithObstaclesTestDataProvider")
    @MethodSource("multipleMovesTestDataProvider")
    @MethodSource("acceptanceTestDataProvider")
    void testSingletonStartCommand(String testLabel, Board board, KnightStatus knightStatus, Collection<Command> commands, KnightStatusAndCommandResult expectedResult) {
        KnightStatusAndCommandResult result = commandExecutorService.executeCommands(board, knightStatus, commands);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> singletonStartCommandTestDataProvider() {
        return Stream.of(
                Arguments.of("Should execute a START cmd with GENERIC_ERROR (Board already initialized)",
                        BOARD1x1,
                        _0_0_SOUTH,
                        List.of(new StartCommand(0, 0, SOUTH)),
                        new KnightStatusAndCommandResult(_0_0_SOUTH, GENERIC_ERROR)
                ),
                Arguments.of("Should execute a START cmd with INVALID_START_POSITION (col >= numOfCols)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 1, SOUTH)),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, INVALID_START_POSITION)
                ),
                Arguments.of("Should execute a START cmd with INVALID_START_POSITION (row >= numOfRows)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(1, 0, SOUTH)),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, INVALID_START_POSITION)
                ),
                Arguments.of("Should execute a START cmd with INVALID_START_POSITION (col >= numOfCols && row >= numOfRows)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(2, 2, SOUTH)),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, INVALID_START_POSITION)
                ),
                Arguments.of("Should execute a START cmd with INVALID_START_POSITION (square with obstacle)",
                        BOARD1x1_WITHOUT_EMPTY_SQUARES,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(2, 2, SOUTH)),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, INVALID_START_POSITION)
                ),
                Arguments.of("Should execute a START cmd with SUCCESS",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, SOUTH)),
                        new KnightStatusAndCommandResult(_0_0_SOUTH, SUCCESS)
                )
        );
    }

    private static Stream<Arguments> singletonRotateCommandTestDataProvider() {
        return Stream.of(
                Arguments.of("Should execute a ROTATE cmd with GENERIC_ERROR (Board not initialized)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new RotateCommand(WEST)),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, GENERIC_ERROR)
                ),
                Arguments.of("Should execute a ROTATE cmd with SUCCESS",
                        BOARD1x1,
                        _0_0_SOUTH,
                        List.of(new RotateCommand(WEST)),
                        new KnightStatusAndCommandResult(_0_0_WEST, SUCCESS)
                )
        );
    }

    private static Stream<Arguments> singletonMoveCommandTestDataProvider() {
        return Stream.of(
                Arguments.of("Should execute a MOVE cmd with GENERIC_ERROR (Board not initialized)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new MoveCommand(2)),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, GENERIC_ERROR)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD error",
                        BOARD1x1,
                        _0_0_SOUTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_0_0_SOUTH, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS (steps=0)",
                        BOARD2x3,
                        _0_0_NORTH,
                        List.of(new MoveCommand(0)),
                        new KnightStatusAndCommandResult(_0_0_NORTH, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (0,0) and moving NORTH (steps=1)",
                        BOARD2x3,
                        _0_0_NORTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_1_0_NORTH, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (0,0) and moving EAST (steps=2)",
                        BOARD2x3,
                        _0_0_EAST,
                        List.of(new MoveCommand(2)),
                        new KnightStatusAndCommandResult(_0_2_EAST, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD when starting at (0,0) and moving SOUTH (steps=1)",
                        BOARD2x3,
                        _0_0_SOUTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_0_0_SOUTH, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD when starting at (0,0) and moving WEST (steps=1)",
                        BOARD2x3,
                        _0_0_WEST,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_0_0_WEST, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (0,2) and moving NORTH (steps=1)",
                        BOARD2x3,
                        _0_2_NORTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_1_2_NORTH, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (0,2) and moving EAST (steps=1)",
                        BOARD2x3,
                        _0_2_EAST,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_0_2_EAST, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD when starting at (0,2) and moving SOUTH (steps=1)",
                        BOARD2x3,
                        _0_2_SOUTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_0_2_SOUTH, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD when starting at (0,2) and moving WEST (steps=2)",
                        BOARD2x3,
                        _0_2_WEST,
                        List.of(new MoveCommand(2)),
                        new KnightStatusAndCommandResult(_0_0_WEST, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (1,0) and moving NORTH (steps=1)",
                        BOARD2x3,
                        _1_0_NORTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_1_0_NORTH, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (1,0) and moving EAST (steps=2)",
                        BOARD2x3,
                        _1_0_EAST,
                        List.of(new MoveCommand(2)),
                        new KnightStatusAndCommandResult(_1_2_EAST, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (1,0) and moving SOUTH (steps=1)",
                        BOARD2x3,
                        _1_0_SOUTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_0_0_SOUTH, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD when starting at (1,0) and moving WEST (steps=1)",
                        BOARD2x3,
                        _1_0_WEST,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_1_0_WEST, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD when starting at (1,2) and moving NORTH (steps=1)",
                        BOARD2x3,
                        _1_2_NORTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_1_2_NORTH, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with OUT_OF_THE_BOARD when starting at (1,2) and moving EAST (steps=1)",
                        BOARD2x3,
                        _1_2_EAST,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_1_2_EAST, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (1,2) and moving SOUTH (steps=1)",
                        BOARD2x3,
                        _1_2_SOUTH,
                        List.of(new MoveCommand(1)),
                        new KnightStatusAndCommandResult(_0_2_SOUTH, SUCCESS)
                ),
                Arguments.of("Should execute a MOVE cmd with SUCCESS when starting at (1,2) and moving WEST (steps=2)",
                        BOARD2x3,
                        _1_2_WEST,
                        List.of(new MoveCommand(2)),
                        new KnightStatusAndCommandResult(_1_0_WEST, SUCCESS)
                )
        );
    }

    private static Stream<Arguments> singletonMoveCommandWithObstaclesTestDataProvider() {
        return Stream.of(
                Arguments.of("Should stay in the same position (SOUTH obstacle)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        _2_1_SOUTH,
                        List.of(new MoveCommand(10)),
                        new KnightStatusAndCommandResult(_2_1_SOUTH, SUCCESS)
                ),
                Arguments.of("Should stay in the same position (WEST obstacle)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        _1_2_WEST,
                        List.of(new MoveCommand(10)),
                        new KnightStatusAndCommandResult(_1_2_WEST, SUCCESS)
                ),
                Arguments.of("Should stay in the same position (NORTH obstacle)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        _0_1_NORTH,
                        List.of(new MoveCommand(10)),
                        new KnightStatusAndCommandResult(_0_1_NORTH, SUCCESS)
                ),
                Arguments.of("Should stay in the same position (EAST obstacle)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        _1_0_EAST,
                        List.of(new MoveCommand(10)),
                        new KnightStatusAndCommandResult(_1_0_EAST, SUCCESS)
                )
        );
    }

    private static Stream<Arguments> multipleMovesTestDataProvider() {
        return Stream.of(
                Arguments.of("Should move to (1,0) NORTH",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, SOUTH),
                                new RotateCommand(NORTH),
                                new MoveCommand(1)
                        ),
                        new KnightStatusAndCommandResult(_1_0_NORTH, SUCCESS)
                ),
                Arguments.of("Should move to (1,0) NORTH (rotate-rotate-move)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, SOUTH),
                                new RotateCommand(NORTH),
                                new RotateCommand(NORTH),
                                new MoveCommand(1)
                        ),
                        new KnightStatusAndCommandResult(_1_0_NORTH, SUCCESS)
                ),
                Arguments.of("Should move to (1,0) NORTH (rotate-move-move)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, SOUTH),
                                new RotateCommand(NORTH),
                                new MoveCommand(1),
                                new MoveCommand(1)
                        ),
                        new KnightStatusAndCommandResult(_2_0_NORTH, SUCCESS)
                ),
                Arguments.of("Should move to (1,0) NORTH (rotate-move-rotate)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, SOUTH),
                                new RotateCommand(NORTH),
                                new MoveCommand(1),
                                new RotateCommand(EAST)
                        ),
                        new KnightStatusAndCommandResult(_1_0_EAST, SUCCESS)
                ),
                Arguments.of("Should move to (1,0) NORTH (rotate-move-rotate-move)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, SOUTH),
                                new RotateCommand(NORTH),
                                new MoveCommand(1),
                                new RotateCommand(EAST),
                                new MoveCommand(10)
                        ),
                        new KnightStatusAndCommandResult(_1_0_EAST, SUCCESS)
                ),
                Arguments.of("Should move to (2,2) NORTH (rotate-move-rotate-move)",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, SOUTH),
                                new RotateCommand(EAST),
                                new MoveCommand(2),
                                new RotateCommand(NORTH),
                                new MoveCommand(2)
                        ),
                        new KnightStatusAndCommandResult(_2_2_NORTH, SUCCESS)
                )
        );
    }

    private static Stream<Arguments> acceptanceTestDataProvider() {
        return Stream.of(
                Arguments.of("Acceptance Test",
                        BOARD3x3_ACCEPTANCE_TEST,
                        KNIGHT_INITIAL_STATUS,
                        List.of(new StartCommand(0, 0, NORTH),
                                new MoveCommand(4),
                                new RotateCommand(EAST),
                                new MoveCommand(4),
                                new RotateCommand(SOUTH),
                                new MoveCommand(4)
                        ),
                        new KnightStatusAndCommandResult(_0_4_SOUTH, SUCCESS)
                )
        );
    }
}