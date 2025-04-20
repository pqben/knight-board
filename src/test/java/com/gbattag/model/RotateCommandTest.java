package com.gbattag.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gbattag.model.CommandResult.GENERIC_ERROR;
import static com.gbattag.model.CommandResult.SUCCESS;
import static com.gbattag.model.Direction.NORTH;
import static com.gbattag.model.KnightStatus.KNIGHT_INITIAL_STATUS;
import static com.gbattag.testdata.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class RotateCommandTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("rotateCommandTestDataProvider")
    void testRotateCommand(String testLabel, Board board, KnightStatus knightStatus, Command command, KnightStatusAndCommandResult expectedResult) {
        KnightStatusAndCommandResult result = command.execute(board, knightStatus);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> rotateCommandTestDataProvider() {
        return Stream.of(
                Arguments.of("Should return GENERIC_ERROR (not started)",
                        BOARD1x1,
                        KNIGHT_INITIAL_STATUS,
                        new RotateCommand(NORTH),
                        new KnightStatusAndCommandResult(KNIGHT_INITIAL_STATUS, GENERIC_ERROR)
                ),
                Arguments.of("Should return SUCCESS",
                        BOARD1x1,
                        _0_0_SOUTH,
                        new RotateCommand(NORTH),
                        new KnightStatusAndCommandResult(_0_0_NORTH, SUCCESS)
                )
        );
    }
}