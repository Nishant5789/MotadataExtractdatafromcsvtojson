package org.example;

import java.util.regex.*;

 class ExtractCISControlColumns {
     private String control;
     private String description;
     private String input; // Member variable for the input string

     public ExtractCISControlColumns(String input) {
         this.input = input;
     }

     public void extractControlAndDescription() {
         // Regular expressions to match CONTROL and DESCRIPTION
         Pattern controlPattern = Pattern.compile("CONTROL:([^ ]+)");
         Pattern descriptionPattern = Pattern.compile("DESCRIPTION:([^.]+)");

         Matcher controlMatcher = controlPattern.matcher(input);
         if (controlMatcher.find()) {
             this.control = controlMatcher.group(1);
         }

         Matcher descriptionMatcher = descriptionPattern.matcher(input);
         if (descriptionMatcher.find()) {
             this.description = descriptionMatcher.group(1); // Update the description value
         }
     }

     // Getters
     public String getControl() {
         return control;
     }

     public String getDescription() {
         return description;
     }

     @Override
     public String toString() {
         return "ExtractCISControlColumns{" +
                 "control='" + control + '\'' +
                 ", description='" + description + '\'' +
                 '}';
     }
 }
