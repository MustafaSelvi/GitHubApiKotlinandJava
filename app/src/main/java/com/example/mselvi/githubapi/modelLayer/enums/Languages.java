package com.example.mselvi.githubapi.modelLayer.enums;

public enum Languages {
    java("language:java"),
    kotlin("language:kotlin"),
    all("language:java+language:kotlin");

    private final String name;

    Languages(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
