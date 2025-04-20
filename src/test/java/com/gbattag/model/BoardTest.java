package com.gbattag.model;

import org.junit.jupiter.api.Test;

import static com.gbattag.testdata.TestData.BOARD1x1_WITHOUT_EMPTY_SQUARES;
import static com.gbattag.testdata.TestData.BOARD2x3;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {
    @Test
    public void shouldSuccessfullyGetColumns() {
        assertThat(BOARD2x3.getColumns()).isEqualTo(3);
    }

    @Test
    public void shouldSuccessfullyGetRows() {
        assertThat(BOARD2x3.getRows()).isEqualTo(2);
    }

    @Test
    public void shouldSuccessfullyCheckThatIsOutOfTheBoardWhenRowNegative() {
        assertThat(BOARD2x3.isOutOfTheBoard(new BoardPosition(-1, 0))).isTrue();
    }

    @Test
    public void shouldSuccessfullyCheckThatIsOutOfTheBoardWhenRowAboveMaxRow() {
        assertThat(BOARD2x3.isOutOfTheBoard(new BoardPosition(2, 0))).isTrue();
    }

    @Test
    public void shouldSuccessfullyCheckThatIsOutOfTheBoardWhenColumnNegative() {
        assertThat(BOARD2x3.isOutOfTheBoard(new BoardPosition(0, -2))).isTrue();
    }

    @Test
    public void shouldSuccessfullyCheckThatIsOutOfTheBoardWhenColumnAboveMaxColumn() {
        assertThat(BOARD2x3.isOutOfTheBoard(new BoardPosition(0, 3))).isTrue();
    }

    @Test
    public void shouldSuccessfullyCheckIfEmpty() {
        assertThat(BOARD2x3.isEmptyPosition(new BoardPosition(0, 0))).isTrue();
    }

    @Test
    public void shouldSuccessfullyCheckIfNotEmpty() {
        assertThat(BOARD1x1_WITHOUT_EMPTY_SQUARES.isEmptyPosition(new BoardPosition(0, 0))).isFalse();
    }
}