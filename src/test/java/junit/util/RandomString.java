package junit.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
    public String nextEMail() {

            for (int idx = 0; idx < buf.length / 2; ++idx) {
                buf[idx] = symbols[random.nextInt(symbols.length)];
            }
        String email =  new String(buf);
            return email + "@Laca.de";
    }
    public LocalDate nextDate() {

        Random random = new Random();
        int day  = random.nextInt(30) + 1;
        int month  = random.nextInt(12) + 1;
        int year  = random.nextInt(500) + 1950;
        LocalDate date = LocalDate.of(year,month,day);
        return date;
    }
    public int nextInt() {

     Random random = new Random();

     return random.nextInt(99999);
    }


    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public RandomString() {
        this(21);
    }

}