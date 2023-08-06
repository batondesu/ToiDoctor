package com.toier.toidoctor.enums;

public enum Sex {
    MALE {
        public String toString() {
            return "male";
        }
    },
    FEMALE {
        @Override
        public String toString() {
            return "female";
        }
    };

    public static Sex getSex(String s) {
        return (s.equals(MALE.toString()) ? MALE : FEMALE);
    }
}
