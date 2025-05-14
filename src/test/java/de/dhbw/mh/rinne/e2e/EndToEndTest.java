package de.dhbw.mh.rinne.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.dhbw.mh.rinne.AstBuilder;
import de.dhbw.mh.rinne.antlr.RinneLexer;
import de.dhbw.mh.rinne.antlr.RinneParser;
import de.dhbw.mh.rinne.ast.AstPrinter;

class EndToEndTest {

    private static final File TEST_ROOT = new File("e2e");
    private static final String SOURCE_FILENAME = "source.gr";
    private static final String EXPECTED_OUTPUT_FILENAME = "out.txt";

    static Stream<String> testCasesProvider() {
        File[] directories = TEST_ROOT.listFiles(File::isDirectory);
        if (directories == null) {
            return Stream.empty();
        }
        return Arrays.stream(directories).map(File::getAbsolutePath);
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    void runE2ETest(String testDirectoryPath) {
        File testDirectory = new File(testDirectoryPath);
        File sourceFile = new File(testDirectory, SOURCE_FILENAME);
        File expectedOutputFile = new File(testDirectory, EXPECTED_OUTPUT_FILENAME);

        assumeTrue(sourceFile.exists(), "Test skipped because '" + SOURCE_FILENAME + "' does not exist.");

        if (!expectedOutputFile.exists()) {
            fail("Missing '" + EXPECTED_OUTPUT_FILENAME + "' in directory: " + testDirectory.getName());
        }

        try {
            String sourceCode = readFile(sourceFile);
            String expectedOutput = readFile(expectedOutputFile);
            String output = executeCompilerPipeline(sourceCode);

            assertThat(normalized(output)).isEqualTo(normalized(expectedOutput));
        } catch (IOException e) {
            fail("Error reading files in " + testDirectory.getName() + ": " + e.getMessage());
        }
    }

    private String executeCompilerPipeline(String sourceCode) {
        var input = CharStreams.fromString(sourceCode);
        var lexer = new RinneLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new RinneParser(tokens);
        var parseTree = parser.program();

        var ast = new AstBuilder().visit(parseTree);
        return ast.accept(new AstPrinter());
    }

    private static String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    private static String normalized(String text) {
        return text.replace("\r\n", "\n").replace("\r", "\n").trim();
    }

}
