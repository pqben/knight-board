package com.gbattag;

import com.gbattag.service.KnightBoardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KnightBoardApplication implements CommandLineRunner {
    private final KnightBoardService knightBoardService;

    public KnightBoardApplication(KnightBoardService knightBoardService) {
        this.knightBoardService = knightBoardService;
    }

    public static void main(String[] args) {
        SpringApplication.run(KnightBoardApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(knightBoardService.downloadBoardDataAndExecuteCommands());
    }
}
