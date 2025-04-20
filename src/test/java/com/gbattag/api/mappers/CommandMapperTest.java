package com.gbattag.api.mappers;

import com.gbattag.api.dto.CommandsDto;
import com.gbattag.model.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandMapperTest {
    private final CommandMapper commandMapper = new CommandMapper();

    @Test
    public void shouldMapSuccessfullyAStartCommand() {
        Collection<Command> commands = commandMapper.fromCommandsDto(new CommandsDto(List.of("START 3,7,SOUTH")));
        Command cmd = commands.stream().toList().get(0);

        assertThat(cmd).isInstanceOf(StartCommand.class);
        assertThat(((StartCommand) cmd).getBoardPosition()).isEqualTo(new BoardPosition(7, 3));
        assertThat(((StartCommand) cmd).getDirection()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void shouldThrowAnExceptionWhenInvalidStartCommand() {
        assertThatThrownBy(() -> commandMapper.fromCommandsDto(new CommandsDto(List.of("START 3,7"))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowAnExceptionWhenNegativeXStartCommand() {
        assertThatThrownBy(() -> commandMapper.fromCommandsDto(new CommandsDto(List.of("START -3,7,SOUTH"))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowAnExceptionWhenNegativeYStartCommand() {
        assertThatThrownBy(() -> commandMapper.fromCommandsDto(new CommandsDto(List.of("START 3,-7,SOUTH"))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldMapSuccessfullyAMoveCommand() {
        Collection<Command> commands = commandMapper.fromCommandsDto(new CommandsDto(List.of("MOVE 3")));
        Command cmd = commands.stream().toList().get(0);

        assertThat(cmd).isInstanceOf(MoveCommand.class);
        assertThat(((MoveCommand) cmd).getSteps()).isEqualTo(3);
    }

    @Test
    public void shouldThrowAnExceptionWhenInvalidMoveCommand() {
        assertThatThrownBy(() -> commandMapper.fromCommandsDto(new CommandsDto(List.of(" MOVE 3"))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowAnExceptionWhenNegativeNumOfStepsInMoveCommand() {
        assertThatThrownBy(() -> commandMapper.fromCommandsDto(new CommandsDto(List.of("MOVE -3"))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldMapSuccessfullyARotateCommand() {
        Collection<Command> commands = commandMapper.fromCommandsDto(new CommandsDto(List.of("ROTATE NORTH")));
        Command cmd = commands.stream().toList().get(0);

        assertThat(cmd).isInstanceOf(RotateCommand.class);
        assertThat(((RotateCommand) cmd).getDirection()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void shouldThrowAnExceptionWhenInvalidRotateCommand() {
        assertThatThrownBy(() -> commandMapper.fromCommandsDto(new CommandsDto(List.of("ROTATE NOR"))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}