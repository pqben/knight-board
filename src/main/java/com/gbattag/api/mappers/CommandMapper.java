package com.gbattag.api.mappers;

import com.gbattag.api.dto.CommandsDto;
import com.gbattag.model.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommandMapper {

    public static final String COMMAND_TYPE_AND_ARGUMENTS_SEPARATOR = " ";
    public static final String START_ARGUMENTS_SEPARATOR = ",";

    public Collection<Command> fromCommandsDto(CommandsDto commandsDto) {
        return commandsDto.getCommands().stream().map(this::parseCommand).toList();
    }

    private Command parseCommand(String command) {
        final int spaceCharIdx = command.indexOf(COMMAND_TYPE_AND_ARGUMENTS_SEPARATOR);
        final String commandPrefix = command.substring(0, spaceCharIdx);
        final String commandSuffix = command.substring(spaceCharIdx + 1);
        return switch (commandPrefix) {
            case "START" -> parseStartCommand(command, commandSuffix);
            case "MOVE" -> parseMoveCommand(command, commandSuffix);
            case "ROTATE" -> parseRotateCommand(command, commandSuffix);
            default -> throw new IllegalArgumentException(String.format("Illegal command string [%s]", command));
        };
    }

    private Command parseStartCommand(String command, String commandSuffix) {
        try {
            final String[] xAndYAndDirection = commandSuffix.split(START_ARGUMENTS_SEPARATOR);
            final int x = Integer.parseInt(xAndYAndDirection[0]);
            final int y = Integer.parseInt(xAndYAndDirection[1]);
            final Direction direction = Direction.valueOf(xAndYAndDirection[2]);

            if (x < 0 || y < 0)
                throw new IllegalArgumentException("Coordinates in a START command cannot be negative");

            return new StartCommand(y, x, direction);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Illegal command string [%s]", command), e);
        }
    }

    private Command parseMoveCommand(String command, String numOfSteps) {
        try {
            final int steps = Integer.parseInt(numOfSteps);
            if (steps < 0)
                throw new IllegalArgumentException("Number of steps in a START command cannot be negative");

            return new MoveCommand(steps);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Illegal command string [%s]", command), e);
        }
    }

    private Command parseRotateCommand(String command, String directionString) {
        try {
            return new RotateCommand(Direction.valueOf(directionString));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Illegal command string [%s]", command), e);
        }
    }
}
