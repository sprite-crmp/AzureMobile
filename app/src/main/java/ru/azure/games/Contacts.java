package ru.azure.games;

import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class Contacts implements Serializable {
        @Exclude
        private String courseName;

        public Contacts() {
            // empty constructor required for Firebase.
        }

        // Constructor for all variables.
        public Contacts(String courseName) {
            this.courseName = courseName;
        }

        // getter methods for all variables.
        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

    }

