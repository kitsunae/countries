package net.lashin.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestFilesUtil {

    public static void cleanUp(Path... paths) {
        for (Path path : paths) {
            try {
                if (Files.exists(path)) {
                    Files.walkFileTree(path, new TestFileVisitor());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
