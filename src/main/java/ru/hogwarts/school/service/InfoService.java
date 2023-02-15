package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class InfoService {

    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private int port;

    private static final int n = 1_000_000;

    public int getPort() {
        return port;
    }

    public long foo1() {
        long t = System.currentTimeMillis();
        long result = Stream.iterate(1, a -> a + 1)
                .limit(n)
                .reduce(0, (a, b) -> a + b);
        logger.info("foo1: {}мс", System.currentTimeMillis() - t);
        return result;
    }

    public long foo2() {
        long t = System.currentTimeMillis();
        long result = Stream.iterate(1, a -> a + 1)
                .limit(n)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        logger.info("foo2: {}мс", System.currentTimeMillis() - t);
        return result;
    }

    public long foo3() {
        long t = System.currentTimeMillis();
        long result = LongStream.range(1, n + 1).sum();
        logger.info("foo3: {}мс", System.currentTimeMillis() - t);
        return result;
    }

    public long foo4() {
        long t = System.currentTimeMillis();
        long result = arithmeticProgressionSum(1, n, n);
        logger.info("foo4: {}мс", System.currentTimeMillis() - t);
        return result;
    }

    private long arithmeticProgressionSum(int from, int to, int n) {
        return (long) (from + to) * n / 2;
    }

}
