package de.dhbw.mh.rinne.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import de.dhbw.mh.rinne.AstBuilder;
import de.dhbw.mh.rinne.LoggingErrorListener;
import de.dhbw.mh.rinne.antlr.RinneLexer;
import de.dhbw.mh.rinne.antlr.RinneParser;
import de.dhbw.mh.rinne.ast.AstNode;
import de.dhbw.mh.rinne.ast.AstPrinter;
import de.dhbw.mh.rinne.codegen.BytecodeGenerator;
import de.dhbw.mh.rinne.semantic.TypeChecker;
import de.dhbw.mh.rinne.semantic.UsageChecker;
import de.dhbw.mh.rinne.semantic.VariableResolver;

class EndToEndTest {

    private static final File TEST_ROOT = new File("e2e");
    private static final String SOURCE_FILENAME = "source.gr";
    private static final String EXPECTED_AST_FILENAME = "ast.txt";
    private static final String EXPECTED_BYTECODE_FILENAME = "bytecode.txt";
    private static final String EXPECTED_ERRORS_FILENAME = "errors.txt";

    static Stream<String> testCasesProvider() {
        File[] directories = TEST_ROOT.listFiles(File::isDirectory);
        if (directories == null) {
            return Stream.empty();
        }
        return Arrays.stream(directories).map(File::getAbsolutePath);
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    void verifyAst(String testDirectoryPath) {
        File testDirectory = new File(testDirectoryPath);
        File sourceFile = new File(testDirectory, SOURCE_FILENAME);
        File expectedAstFile = new File(testDirectory, EXPECTED_AST_FILENAME);

        assumeTrue(sourceFile.exists(), "Test skipped because '" + SOURCE_FILENAME + "' does not exist.");

        if (!expectedAstFile.exists()) {
            fail("Missing '" + EXPECTED_AST_FILENAME + "' in directory: " + testDirectory.getName());
        }

        try {
            LoggingErrorListener dummyListener = new LoggingErrorListener();
            String sourceCode = readFile(sourceFile);
            String expectedAst = readFile(expectedAstFile);
            var ast = executeCompilerPipeline(sourceCode, dummyListener, dummyListener);
            String actualAst = ast.accept(new AstPrinter());

            assertThat(normalized(actualAst)).isEqualTo(normalized(expectedAst));
        } catch (IOException e) {
            fail("Error reading files in " + testDirectory.getName() + ": " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    void verifyBytecode(String testDirectoryPath) {
        File testDirectory = new File(testDirectoryPath);
        File sourceFile = new File(testDirectory, SOURCE_FILENAME);
        File expectedBytecodeFile = new File(testDirectory, EXPECTED_BYTECODE_FILENAME);

        assumeTrue(sourceFile.exists(), "Test skipped because '" + SOURCE_FILENAME + "' does not exist.");
        assumeTrue(expectedBytecodeFile.exists(),
                "Test skipped because '" + EXPECTED_BYTECODE_FILENAME + "' does not exist.");

        try {
            LoggingErrorListener dummyListener = new LoggingErrorListener();
            String sourceCode = readFile(sourceFile);
            String expectedBytecode = readFile(expectedBytecodeFile);
            var ast = executeCompilerPipeline(sourceCode, dummyListener, dummyListener);
            String bytecode = ast.accept(new BytecodeGenerator());

            assertThat(normalized(bytecode)).isEqualTo(normalized(expectedBytecode));
        } catch (IOException e) {
            fail("Error reading files in " + testDirectory.getName() + ": " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    void compareErrors(String testDirectoryPath) {
        File testDirectory = new File(testDirectoryPath);
        File sourceFile = new File(testDirectory, SOURCE_FILENAME);
        File expectedErrorsFile = new File(testDirectory, EXPECTED_ERRORS_FILENAME);

        assumeTrue(sourceFile.exists(), "Test skipped because '" + SOURCE_FILENAME + "' does not exist.");
        assumeTrue(expectedErrorsFile.exists(),
                "Test skipped because '" + EXPECTED_ERRORS_FILENAME + "' does not exist.");

        try {
            LoggingErrorListener lexerErrors = new LoggingErrorListener("lexer error at ");
            LoggingErrorListener parserErrors = new LoggingErrorListener("parser error at ");

            String sourceCode = readFile(sourceFile);
            String expectedErrors = readFile(expectedErrorsFile);
            var ast = executeCompilerPipeline(sourceCode, lexerErrors, parserErrors);

            LoggingErrorListener semanticErrors = new LoggingErrorListener("semantic error at ");
            VariableResolver variableResolver = new VariableResolver();
            UsageChecker usageChecker = new UsageChecker();
            TypeChecker typeChecker = new TypeChecker();
            variableResolver.addErrorListener(semanticErrors);
            usageChecker.addErrorListener(semanticErrors);
            typeChecker.addErrorListener(semanticErrors);
            ast.accept(variableResolver);
            ast.accept(usageChecker);
            ast.accept(typeChecker);

            List<String> allErrors = Stream
                    .of(lexerErrors.messages(), parserErrors.messages(), semanticErrors.messages())
                    .flatMap(List::stream).collect(Collectors.toList());

            String actualErrors = String.join("\n", allErrors);
            assertThat(normalized(actualErrors)).isEqualTo(normalized(expectedErrors));
        } catch (IOException e) {
            fail("Error reading files in " + testDirectory.getName() + ": " + e.getMessage());
        }
    }

    private AstNode executeCompilerPipeline(String sourceCode, LoggingErrorListener lexerErrors,
            LoggingErrorListener parserErrors) {
        var input = CharStreams.fromString(sourceCode);
        var lexer = new RinneLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(lexerErrors);
        var tokens = new CommonTokenStream(lexer);
        var parser = new RinneParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(parserErrors);
        var parseTree = parser.program();
        var astBuilder = new AstBuilder();
        astBuilder.addErrorListener(parserErrors);

        return astBuilder.visit(parseTree);
    }

    private static String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    private static String normalized(String text) {
        return text.replace("\r\n", "\n").replace("\r", "\n").trim();
    }

}
