package com.gbattag.service;

import com.gbattag.model.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommandExecutorService {
    public KnightStatusAndCommandResult executeCommands(Board board, Collection<Command> commands) {
        return this.executeCommands(board, KnightStatus.KNIGHT_INITIAL_STATUS, commands);
    }

    public KnightStatusAndCommandResult executeCommands(Board board, KnightStatus knightStatus, Collection<Command> commands) {
        KnightStatus currKnightStatus = knightStatus;
        KnightStatusAndCommandResult newKnightStatusAndResult = null;
        for (final Command command : commands) {
            newKnightStatusAndResult = command.execute(board, currKnightStatus);

            if (newKnightStatusAndResult.commandResult() != CommandResult.SUCCESS)
                return newKnightStatusAndResult;

            currKnightStatus = newKnightStatusAndResult.knightStatus();
        }

        return newKnightStatusAndResult;
    }
}
