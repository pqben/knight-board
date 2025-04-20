package com.gbattag.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gbattag.model.CommandResult.*;
import static com.gbattag.model.KnightStatus.KNIGHT_INITIAL_STATUS;
import static com.gbattag.testdata.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoveCommandTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("moveCommandTestDataProvider")
    void testMoveCommand(String testLabel, Board board, KnightStatus knightStatus, Command command, KnightStatusAndCommandResult expectedResult) {
        KnightStatusAndCommandResult result = command.execute(board, knightStatus);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> moveCommandTestDataProvider() {
        return Stream.of(
                Arguments.of("Should return GENERIC_ERROR (not started)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        new MoveCommand(2),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, GENERIC_ERROR)
                ),
                Arguments.of("Should return OUT_OF_THE_BOARD",
                        BOARD1x1,
                        _0_0_NORTH,
                        new MoveCommand(1),
                        new KnightStatusAndCommandResult(_0_0_NORTH, OUT_OF_THE_BOARD)
                ),
                Arguments.of("Should return (0,2) EAST",
                        BOARD3x3_OBSTACLE_IN_THE_MIDDLE,
                        _0_0_EAST,
                        new MoveCommand(2),
                        new KnightStatusAndCommandResult(_0_2_EAST, SUCCESS)
                ),
                Arguments.of("Should return (1,1) NORTH",
                        BOARD3x3_OBSTACLE_IN_2_1,
                        _0_1_NORTH,
                        new MoveCommand(10),
                        new KnightStatusAndCommandResult(_1_1_NORTH, SUCCESS)
                )
        );
    }
}