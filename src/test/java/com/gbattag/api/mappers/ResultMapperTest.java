package com.gbattag.api.mappers;

import com.gbattag.api.dto.ResultDto;
import com.gbattag.model.CommandResult;
import com.gbattag.model.Direction;
import com.gbattag.model.KnightStatusAndCommandResult;
import org.junit.jupiter.api.Test;

import static com.gbattag.testdata.TestData._0_1_EAST;
import static org.assertj.core.api.Assertions.assertThat;

class ResultMapperTest {
    private final ResultMapper resultMapper = new ResultMapper();

    @Test
    public void shouldMapSuccessfulResult() {
        final ResultDto resultDto = resultMapper.fromCommandResult(new KnightStatusAndCommandResult(_0_1_EAST, CommandResult.SUCCESS));

        assertThat(resultDto.getPosition()).hasValue(new ResultDto.ResultPositionDto(1, 0, Direction.EAST.name()));
        assertThat(resultDto.getStatus()).isEqualTo(CommandResult.SUCCESS.name());
    }

    @Test
    public void shouldMapUnSuccessfulResult() {
        final ResultDto resultDto = resultMapper.fromCommandResult(new KnightStatusAndCommandResult(_0_1_EAST, CommandResult.GENERIC_ERROR));

        assertThat(resultDto.getPosition()).isEmpty();
        assertThat(resultDto.getStatus()).isEqualTo(CommandResult.GENERIC_ERROR.name());
    }
}