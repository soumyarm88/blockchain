package tech.soumyarm88.lib.testutil;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceLoader {
    private final static String SRC = "src";
    private final static String TEST = "test";
    private final static String RESOURCES = "resources";

    private static Path getResourcePath(String fileName) {
        return Paths.get(SRC, TEST, RESOURCES, fileName);
    }

    public static String load(String fileName) {
        Path path = getResourcePath(fileName);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testResourceLoading() {
        assertTrue(getResourcePath("SampleValidChain.json").toFile().getAbsolutePath()
                .endsWith("src/test/resources/SampleValidChain.json"));
    }
}
