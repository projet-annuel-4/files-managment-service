package fr.esgi.filesmanagement.model;

import java.util.Arrays;
import java.util.Objects;

public enum FileType {
    LAST_FILE(1L, "last"),
    ACTUAL_FILE(2L, "actual"),
    PATCH_FILE(3L, "patch");

    private final Long id;
    private final String name;

    FileType(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static FileType fromName(String s) throws IllegalArgumentException {
        return Arrays
                .stream(FileType.values())
                .filter(v -> Objects.equals(v.getName(),s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
    }
}
