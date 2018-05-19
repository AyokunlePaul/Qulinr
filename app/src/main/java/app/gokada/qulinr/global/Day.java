package app.gokada.qulinr.global;

public class Day {
    public enum DAY{

        MONDAY("monday"),
        TUESDAY("tuesday"),
        WEDNESDAY("wednesday"),
        THURSDAY("thursday"),
        FRIDAY("friday"),
        SATURDAY("saturday"),
        SUNDAY("sunday");

        private String day;
        DAY(String day){
            this.day = day;
        }

        @Override
        public String toString() {
            return this.day;
        }
    }
}
