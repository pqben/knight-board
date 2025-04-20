package com.gbattag.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gbattag.model.CommandResult.*;
import static com.gbattag.model.Direction.NORTH;
import static com.gbattag.model.KnightStatus.KNIGHT_INITIAL_STATUS;
import static com.gbattag.testdata.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class StartCommandTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("startCommandTestDataProvider")
    void testStartCommand(String testLabel, Board board, KnightStatus knightStatus, Command command, KnightStatusAndCommandResult expectedResult) {
        KnightStatusAndCommandResult result = command.execute(board, knightStatus);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> startCommandTestDataProvider() {
        return Stream.of(
                Arguments.of("Should return GENERIC_ERROR (already started)",
                        BOARD1x1,
                        _0_0_NORTH,
                        new StartCommand(0, 0, NORTH),
                        new KnightStatusAndCommandResult(_0_0_NORTH, GENERIC_ERROR)
                ),
                Arguments.of("Should return INVALID_START_POSITION (obstacle overlap)",
                        BOARD1x1_WITHOUT_EMPTY_SQUARES,
                        KNIGHT_INITIAL_STATUS,
                        new StartCommand(0, 0, NORTH),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, INVALID_START_POSITION)
                ),
                Arguments.of("Should return INVALID_START_POSITION (off the board)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        new StartCommand(1, 1, NORTH),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, INVALID_START_POSITION)
                ),
                Arguments.of("Should return (0,0) NORTH",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        new StartCommand(0, 0, NORTH),
                        new KnightStatusAndCommandResult(_0_0_NORTH, SUCCESS)
                )
        );
    }
}