package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.BinaryOperation;
import de.dhbw.mh.rinne.RinneType;
import de.dhbw.mh.rinne.UnaryOperation;

abstract class BaseTypeChecker extends AstSemanticVisitor<RinneType> {

    public static TypeCheckResult checkUnaryOperation(RinneType type, UnaryOperation op) {
        if (op.isNegation() && type.isBoolean()) {
            return TypeCheckResult.ok();
        }
        return TypeCheckResult.incompatible();
    }

    public static TypeCheckResult checkBinaryOperation(RinneType lhs, BinaryOperation op, RinneType rhs) {
        if (op.isArithmetic()) {
            if (lhs.isNumeric() && lhs == rhs) {
                return TypeCheckResult.ok();
            }
            if (lhs.isNumeric() && rhs.isNumeric()) {
                var supertype = RinneType.covering(lhs, rhs);
                return TypeCheckResult.needsCast(supertype.get());
            }
        }
        if (op.isConcatenation()) {
            if (lhs == RinneType.SCHNUR && rhs == RinneType.SCHNUR) {
                return TypeCheckResult.ok();
            }
        }
        if (op.checksEquality()) {
            if (lhs == rhs) {
                return TypeCheckResult.ok();
            }
            if (lhs.isNumeric() && rhs.isNumeric()) {
                var supertype = RinneType.covering(lhs, rhs);
                return TypeCheckResult.needsCast(supertype.get());
            }
        }
        if (op.isComparison()) {
            if (lhs.isNumeric() && lhs == rhs) {
                return TypeCheckResult.ok();
            }
            if (lhs.isNumeric() && rhs.isNumeric()) {
                var supertype = RinneType.covering(lhs, rhs);
                return TypeCheckResult.needsCast(supertype.get());
            }
        }
        if (op.isAssignment()) {
            if (lhs == null && rhs == null) {
                return TypeCheckResult.incompatible();
            }
            if (lhs == rhs) {
                return TypeCheckResult.ok();
            }
            if (lhs == null) {
                return TypeCheckResult.ok();
            }
            if (rhs == null) {
                return TypeCheckResult.ok();
            }
            if (lhs.isNumeric() && rhs.isNumeric()) {
                var supertype = RinneType.covering(lhs, rhs);
                return TypeCheckResult.needsCast(supertype.get());
            }
        }
        return TypeCheckResult.incompatible();
    }

}
