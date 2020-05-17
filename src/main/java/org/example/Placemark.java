package org.example;

public class Placemark {

        public Object type;
        public String name;
        public String description;
        public String xCoordinates;
        public String yCoordinates;

        public Placemark(Object type, String name, String description, String xCoordinates, String yCoordinates) {
            this.type = type;
            this.name = name;
            this.description = description;
            this.xCoordinates = xCoordinates;
            this.yCoordinates = yCoordinates;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getxCoordinates() {
            return xCoordinates;
        }

        public void setxCoordinates(String xCoordinates) {
            this.xCoordinates = xCoordinates;
        }

        public String getyCoordinates() {
            return yCoordinates;
        }

        public void setyCoordinates(String yCoordinates) {
            this.yCoordinates = yCoordinates;
        }
    }
