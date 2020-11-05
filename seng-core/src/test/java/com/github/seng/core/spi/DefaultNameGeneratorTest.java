package com.github.seng.core.spi;

import com.github.seng.common.spi.DefaultNameGenerator;
import com.github.seng.common.spi.NameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultNameGeneratorTest {

    @Test
    void generate() {
        NameGenerator nameGenerator = new DefaultNameGenerator();
        String generate = nameGenerator.generate(DefaultNameGeneratorTest.class);
        assertEquals("defaultNameGeneratorTest", generate);
    }
}