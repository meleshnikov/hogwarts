package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {

    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private int port;

    public int getPort() {
        return port;
    }

    public int foo1() {
        long t = System.currentTimeMillis();
        int result = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        logger.info("foo1: {}мс", System.currentTimeMillis() - t);
        return result;
    }

    public int foo2() {
        long t = System.currentTimeMillis();
        int result = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        logger.info("foo2: {}мс", System.currentTimeMillis() - t);
        return result;
    }

    public int foo3() {
        long t = System.currentTimeMillis();
        int result = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .mapToInt(Integer::intValue)
                .sum();
        logger.info("foo3: {}мс", System.currentTimeMillis() - t);
        return result;
    }

}
