package de.dhbw.mh.rinne.semantic;

import java.util.Objects;

import de.dhbw.mh.rinne.RinneType;

public class TypeCheckResult {

    public enum Status {
        OK, NEEDS_CAST, INCOMPATIBLE
    }

    private final Status status;
    private final RinneType requiredType;

    private TypeCheckResult(Status status, RinneType requiredType) {
        this.status = status;
        this.requiredType = requiredType;
    }

    public static TypeCheckResult ok() {
        return new TypeCheckResult(Status.OK, null);
    }

    public static TypeCheckResult needsCast(RinneType requiredType) {
        return new TypeCheckResult(Status.NEEDS_CAST, requiredType);
    }

    public static TypeCheckResult incompatible() {
        return new TypeCheckResult(Status.INCOMPATIBLE, null);
    }

    public Status status() {
        return status;
    }

    public RinneType requiredType() {
        Objects.requireNonNull(requiredType, "requiredType is only set if status() == NEEDS_CAST");
        return requiredType;
    }

}
