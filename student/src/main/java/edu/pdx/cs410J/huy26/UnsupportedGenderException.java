package edu.pdx.cs410J.huy26;

public class UnsupportedGenderException extends RuntimeException {
    public UnsupportedGenderException(String genderString) {
        super(genderString);
    }
}
