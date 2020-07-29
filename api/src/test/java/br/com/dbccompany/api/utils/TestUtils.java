package br.com.dbccompany.api.utils;

import java.util.Random;

public abstract class TestUtils {

    public static String randomText(final int size){
        final int leftLimit = 97;
        final int rightLimit = 122;
        final Random random = new Random();
        final StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

}
