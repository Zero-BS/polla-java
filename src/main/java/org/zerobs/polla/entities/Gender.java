package org.zerobs.polla.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum Gender {
    FEMALE("F"),
    MALE("M"),
    OTHER("O");

    private final String dbString;

    public static Gender fromDBString(String dbString) {
        return EnumSet.allOf(Gender.class).stream().filter(gender -> gender.getDbString().equals(dbString)).findFirst()
                .orElse(null);
    }
}
