package com.gbattag.api.mappers;

import com.gbattag.api.dto.ResultDto;
import com.gbattag.model.BoardPosition;
import com.gbattag.model.CommandResult;
import com.gbattag.model.KnightStatus;
import com.gbattag.model.KnightStatusAndCommandResult;
import org.springframework.stereotype.Service;

@Service
public class ResultMapper {
    public ResultDto fromCommandResult(KnightStatusAndCommandResult result) {
        final KnightStatus knightStatus = result.knightStatus();
        final BoardPosition position = knightStatus.getKnightPosition();
        final String direction = knightStatus.getKnightDirection().name();
        final CommandResult commandResult = result.commandResult();

        if (commandResult == CommandResult.SUCCESS)
            return new ResultDto(new ResultDto.ResultPositionDto(position.getColumn(), position.getRow(), direction), commandResult.name());
        else
            return new ResultDto(commandResult.name());
    }
}
