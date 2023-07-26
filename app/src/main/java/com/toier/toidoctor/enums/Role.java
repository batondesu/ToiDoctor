package com.toier.toidoctor.enums;

import androidx.annotation.NonNull;

public enum Role {
    PATIENT {
        @Override
        public String toString() {
            return "patient";
        }
    },
    DOCTOR {
        public String toString() {
            return "doctor";
        }
    }
}
