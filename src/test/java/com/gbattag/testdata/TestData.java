package com.gbattag.testdata;

import com.gbattag.model.Board;
import com.gbattag.model.BoardPosition;
import com.gbattag.model.KnightStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.gbattag.model.Direction.*;

public class TestData {
    public static final Board BOARD1x1 = new Board(1, 1, List.of());
    public static final Board BOARD1x1_WITHOUT_EMPTY_SQUARES = new Board(1, 1, createObstacles(0, 0));
    public static final Board BOARD2x3 = new Board(2, 3, List.of());
    public static final Board BOARD3x3_OBSTACLE_IN_THE_MIDDLE = new Board(3, 3, createObstacles(1, 1));
    public static final Board BOARD3x3_OBSTACLE_IN_2_1 = new Board(3, 3, createObstacles(2, 1));
    public static final Board BOARD3x3_ACCEPTANCE_TEST = new Board(5, 5, createObstacles(0, 2, 1, 2, 2, 2, 3, 2));

    public static final KnightStatus _0_0_SOUTH = new KnightStatus(new BoardPosition(0, 0), SOUTH);
    public static final KnightStatus _0_0_NORTH = new KnightStatus(new BoardPosition(0, 0), NORTH);
    public static final KnightStatus _0_0_EAST = new KnightStatus(new BoardPosition(0, 0), EAST);
    public static final KnightStatus _0_0_WEST = new KnightStatus(new BoardPosition(0, 0), WEST);

    public static final KnightStatus _0_1_SOUTH = new KnightStatus(new BoardPosition(0, 1), SOUTH);
    public static final KnightStatus _0_1_NORTH = new KnightStatus(new BoardPosition(0, 1), NORTH);
    public static final KnightStatus _0_1_EAST = new KnightStatus(new BoardPosition(0, 1), EAST);
    public static final KnightStatus _0_1_WEST = new KnightStatus(new BoardPosition(0, 1), WEST);

    public static final KnightStatus _0_2_SOUTH = new KnightStatus(new BoardPosition(0, 2), SOUTH);
    public static final KnightStatus _0_2_NORTH = new KnightStatus(new BoardPosition(0, 2), NORTH);
    public static final KnightStatus _0_2_EAST = new KnightStatus(new BoardPosition(0, 2), EAST);
    public static final KnightStatus _0_2_WEST = new KnightStatus(new BoardPosition(0, 2), WEST);


    public static final KnightStatus _1_0_SOUTH = new KnightStatus(new BoardPosition(1, 0), SOUTH);
    public static final KnightStatus _1_0_NORTH = new KnightStatus(new BoardPosition(1, 0), NORTH);
    public static final KnightStatus _1_0_EAST = new KnightStatus(new BoardPosition(1, 0), EAST);
    public static final KnightStatus _1_0_WEST = new KnightStatus(new BoardPosition(1, 0), WEST);

    public static final KnightStatus _1_1_SOUTH = new KnightStatus(new BoardPosition(1, 1), SOUTH);
    public static final KnightStatus _1_1_NORTH = new KnightStatus(new BoardPosition(1, 1), NORTH);
    public static final KnightStatus _1_1_EAST = new KnightStatus(new BoardPosition(1, 1), EAST);
    public static final KnightStatus _1_1_WEST = new KnightStatus(new BoardPosition(1, 1), WEST);

    public static final KnightStatus _1_2_SOUTH = new KnightStatus(new BoardPosition(1, 2), SOUTH);
    public static final KnightStatus _1_2_NORTH = new KnightStatus(new BoardPosition(1, 2), NORTH);
    public static final KnightStatus _1_2_EAST = new KnightStatus(new BoardPosition(1, 2), EAST);
    public static final KnightStatus _1_2_WEST = new KnightStatus(new BoardPosition(1, 2), WEST);

    public static final KnightStatus _2_0_NORTH = new KnightStatus(new BoardPosition(2, 0), NORTH);
    public static final KnightStatus _2_1_SOUTH = new KnightStatus(new BoardPosition(2, 1), SOUTH);
    public static final KnightStatus _2_2_NORTH = new KnightStatus(new BoardPosition(2, 2), NORTH);

    public static final KnightStatus _0_4_SOUTH = new KnightStatus(new BoardPosition(0, 4), SOUTH);

    private static Collection<BoardPosition> createObstacles(int... cords) {
        return Stream.iterate(0, index -> index + 1 < cords.length, index -> index + 2)
                .map(index -> new BoardPosition(cords[index], cords[index + 1]))
                .toList();
    }
}
