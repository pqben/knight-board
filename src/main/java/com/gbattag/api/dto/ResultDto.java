package com.gbattag.api.dto;

import java.util.Objects;
import java.util.Optional;

public class ResultDto {
    public static class ResultPositionDto {
        private Integer x;
        private Integer y;
        private String direction;

        public ResultPositionDto() {
        }

        public ResultPositionDto(Integer x, Integer y, String direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;

            ResultPositionDto that = (ResultPositionDto) o;
            return Objects.equals(x, that.x) &&
                    Objects.equals(y, that.y) &&
                    Objects.equals(direction, that.direction);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }

        @Override
        public String toString() {
            return String.format("{x=%d,y=%d,direction=%s}", x, y, direction);
        }
    }

    private Optional<ResultPositionDto> position;
    private String status;

    public ResultDto() {
    }

    public ResultDto(ResultPositionDto position, String status) {
        this.position = Optional.ofNullable(position);
        this.status = status;
    }

    public ResultDto(String status) {
        this.position = Optional.empty();
        this.status = status;
    }

    public Optional<ResultPositionDto> getPosition() {
        return position;
    }

    public void setPosition(ResultPositionDto position) {
        this.position = Optional.ofNullable(position);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return position.map(position ->
                        String.format("{position={x=%d,y=%d,direction=%s},status=%s}",
                                position.x,
                                position.y,
                                position.direction,
                                status)
                )
                .orElse(String.format("{position=None,status=%s}", status));
    }
}
