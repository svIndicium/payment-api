package hu.indicium.dev.payment.infrastructure.util;

import java.text.DecimalFormat;
import java.util.Date;

public class Util {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Util() {
    }

    public static String getFullLastName(String middleName, String lastName) {
        if (middleName == null) {
            return lastName;
        }
        return middleName + ' ' + lastName;
    }

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static char[] generateTemporaryPassword() {
        return (Util.randomAlphaNumeric(20) + "A1a!").toCharArray();
    }

    /*
     *  Convenience method to add a specified number of minutes to a Date object
     *  From: http://stackoverflow.com/questions/9043981/how-to-add-minutes-to-my-date
     *  @param  minutes  The number of minutes to add
     *  @param  beforeTime  The time that will have minutes added to it
     *  @return  A date object with the specified number of minutes added to it
     */
    public static Date addMinutesToDate(int minutes, Date beforeTime) {
        final long ONE_MINUTE_IN_MILLIS = 60000; //milliseconds

        long curTimeInMs = beforeTime.getTime();
        return new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
    }

    public static String formatCurrency(Double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(amount);
    }
}
