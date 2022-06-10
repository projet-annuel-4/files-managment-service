package fr.esgi.filesManagment.model;

import com.amazonaws.services.workdocs.model.ActivityType;

import java.util.Arrays;

public enum FileType {
    FILE(1L, "file"),
    PATCH(2L, "patch");

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
    public static ActivityType fromName(String s) throws IllegalArgumentException {
        return Arrays
                .stream(ActivityType.values())
                .filter(v -> v.name().equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown value: " + s));
    }
}
