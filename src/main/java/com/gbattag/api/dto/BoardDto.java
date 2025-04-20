package com.gbattag.api.dto;

import java.util.List;
import java.util.Objects;

public class BoardDto {
    public static class ObstacleDto {
        private Integer x;
        private Integer y;

        public ObstacleDto() {
        }

        public ObstacleDto(Integer x, Integer y) {
            this.x = x;
            this.y = y;
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

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            ObstacleDto that = (ObstacleDto) o;
            return Objects.equals(x, that.x) && Objects.equals(y, that.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private Integer width;
    private Integer height;
    private List<ObstacleDto> obstacles;

    public BoardDto() {
    }

    public BoardDto(Integer width, Integer height, List<ObstacleDto> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<ObstacleDto> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<ObstacleDto> obstacles) {
        this.obstacles = obstacles;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoardDto boardDto = (BoardDto) o;
        return Objects.equals(width, boardDto.width) && Objects.equals(height, boardDto.height) && Objects.equals(obstacles, boardDto.obstacles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, obstacles);
    }
}
