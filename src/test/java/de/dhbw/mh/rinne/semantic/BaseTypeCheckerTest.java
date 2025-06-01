package de.dhbw.mh.rinne.semantic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.dhbw.mh.rinne.BinaryOperation;
import de.dhbw.mh.rinne.RinneType;
import de.dhbw.mh.rinne.UnaryOperation;

class BaseTypeCheckerTest {

    @DisplayName("Unary operations on valid types return OK")
    @ParameterizedTest(name = "{index}: op={0}, type={1}")
    @MethodSource("provideValidUnaryOperands")
    void testValidUnaryOperations(UnaryOperation op, RinneType type) {
        var result = BaseTypeChecker.checkUnaryOperation(type, op);
        assertThat(result.status()).isSameAs(TypeCheckResult.Status.OK);
    }

    @DisplayName("Unary operations on invalid types return INCOMPATIBLE")
    @ParameterizedTest(name = "{index}: op={0}, type={1}")
    @MethodSource("provideInvalidUnaryOperands")
    void testInvalidUnaryOperations(UnaryOperation op, RinneType type) {
        var result = BaseTypeChecker.checkUnaryOperation(type, op);
        assertThat(result.status()).isSameAs(TypeCheckResult.Status.INCOMPATIBLE);
    }

    @DisplayName("Binary operations on identical valid types return OK")
    @ParameterizedTest(name = "{index}: op={0}, lhs={1}")
    @MethodSource("provideValidTypeOperatorPairs")
    void testBinaryOperationsOnSameTypeReturnOk(BinaryOperation op, RinneType type) {
        var result = BaseTypeChecker.checkBinaryOperation(type, op, type);
        assertThat(result.status()).isSameAs(TypeCheckResult.Status.OK);
    }

    @DisplayName("Binary operations on incompatible types return INCOMPATIBLE")
    @ParameterizedTest(name = "{index}: op={0}, lhs={1}, rhs={2}")
    @MethodSource("provideIncompatibleBinaryOperands")
    void testBinaryOperationsOnIncompatibleTypes(BinaryOperation op, RinneType lhs, RinneType rhs) {
        var result = BaseTypeChecker.checkBinaryOperation(lhs, op, rhs);
        assertThat(result.status()).isSameAs(TypeCheckResult.Status.INCOMPATIBLE);
    }

    @DisplayName("Binary operations requiring cast return NEEDS_CAST with correct target types")
    @ParameterizedTest(name = "{index}: op={0}, lhs={1}, rhs={2}, cast={3}")
    @MethodSource("provideOperandsNeedingCast")
    void testBinaryOperationsNeedingCast(BinaryOperation op, RinneType lhs, RinneType rhs, RinneType cast) {
        var result = BaseTypeChecker.checkBinaryOperation(lhs, op, rhs);
        assertThat(result.status()).isSameAs(TypeCheckResult.Status.NEEDS_CAST);
        assertThat(result.requiredType()).isSameAs(cast);
    }

    @DisplayName("Binary operations accepting nulls return OK")
    @ParameterizedTest(name = "{index}: op={0}, lhs={1}, rhs={2}")
    @MethodSource("provideAcceptableNulls")
    void testAcceptableNulls(BinaryOperation op, RinneType lhs, RinneType rhs) {
        var result = BaseTypeChecker.checkBinaryOperation(lhs, op, rhs);
        assertThat(result.status()).isSameAs(TypeCheckResult.Status.OK);
    }

    static Stream<Arguments> provideValidUnaryOperands() {
        return Stream.of(Arguments.of(UnaryOperation.NOT, RinneType.WAHRHEITSWERT));
    }

    static Stream<Arguments> provideInvalidUnaryOperands() {
        return Stream.of(Arguments.of(UnaryOperation.NOT, RinneType.FLIEßZAHL),
                Arguments.of(UnaryOperation.NOT, RinneType.GANZZAHL),
                Arguments.of(UnaryOperation.NOT, RinneType.SCHNUR));
    }

    static Stream<Arguments> provideAcceptableNulls() {
        return Stream.of(Arguments.of(BinaryOperation.ASSIGN, null, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ASSIGN, null, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.ASSIGN, null, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ASSIGN, null, RinneType.WAHRHEITSWERT));
    }

    static Stream<Arguments> provideValidTypeOperatorPairs() {
        return Stream.of(Arguments.of(BinaryOperation.ADD, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ADD, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.ADD, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.GANZZAHL),

                Arguments.of(BinaryOperation.EQUAL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.EQUAL, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.EQUAL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.EQUAL, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.LESS_THAN, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.GANZZAHL),

                Arguments.of(BinaryOperation.ASSIGN, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.WAHRHEITSWERT));
    }

    static Stream<Arguments> provideIncompatibleBinaryOperands() {
        return Stream.of(Arguments.of(BinaryOperation.ADD, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ADD, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.ADD, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ADD, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.ADD, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ADD, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.ADD, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.ADD, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ADD, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.ADD, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ADD, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.SUBTRACT, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.SCHNUR, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.MULTIPLY, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.SCHNUR, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.DIVIDE, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.SCHNUR, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.EQUAL, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.EQUAL, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.EQUAL, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.EQUAL, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.EQUAL, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.EQUAL, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.EQUAL, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.EQUAL, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.EQUAL, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.EQUAL, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),

                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),

                Arguments.of(BinaryOperation.LESS_THAN, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.SCHNUR, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.SCHNUR, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.SCHNUR, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.SCHNUR, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.WAHRHEITSWERT, RinneType.WAHRHEITSWERT),

                Arguments.of(BinaryOperation.ASSIGN, RinneType.FLIEßZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.FLIEßZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.GANZZAHL, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.GANZZAHL, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.SCHNUR, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.SCHNUR, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.SCHNUR, RinneType.WAHRHEITSWERT),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.WAHRHEITSWERT, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.WAHRHEITSWERT, RinneType.GANZZAHL),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.WAHRHEITSWERT, RinneType.SCHNUR),
                Arguments.of(BinaryOperation.ASSIGN, null, null));
    }

    static Stream<Arguments> provideOperandsNeedingCast() {
        return Stream.of(
                Arguments.of(BinaryOperation.ADD, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ADD, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.SUBTRACT, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.SUBTRACT, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.MULTIPLY, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.MULTIPLY, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.DIVIDE, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.DIVIDE, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.EQUAL, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.EQUAL, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.NOT_EQUAL, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.LESS_THAN, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_THAN, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.LESS_EQUAL, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.GANZZAHL, RinneType.FLIEßZAHL,
                        RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_THAN, RinneType.FLIEßZAHL, RinneType.GANZZAHL,
                        RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.GANZZAHL, RinneType.FLIEßZAHL,
                        RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.GREATER_EQUAL, RinneType.FLIEßZAHL, RinneType.GANZZAHL,
                        RinneType.FLIEßZAHL),

                Arguments.of(BinaryOperation.ASSIGN, RinneType.FLIEßZAHL, RinneType.GANZZAHL, RinneType.FLIEßZAHL),
                Arguments.of(BinaryOperation.ASSIGN, RinneType.GANZZAHL, RinneType.FLIEßZAHL, RinneType.FLIEßZAHL));
    }

}
