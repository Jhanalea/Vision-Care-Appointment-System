package com.visioncare.model;

public enum SecretQuestions {
    MAIDEN_NAME("What Is Your Mothers Maiden Name?"),
    PET("What Is The Name Of Your First Pet?"),
    FATHER("What Is Your Fathers Middle Name?");

    private final String secret_ques;

    SecretQuestions(String secret_ques) {
        this.secret_ques = secret_ques;
    }

    public String SecretQuestions() { return secret_ques; }

    @Override public String toString() { return secret_ques; }


}
