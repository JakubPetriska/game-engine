package com.onion.tests;

import com.onion.utilities.TimeBasedUniqueKeyGenerator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jakub Petriska on 19. 2. 2015.
 */
public class TimeBasedUniqueKeyGeneratorTest {

    @Test
    public void keysShouldBeUnique() {
        // Generate keys
        String[] keys = new String[10];
        for(int i = 0; i < keys.length; ++i) {
            keys[i] = TimeBasedUniqueKeyGenerator.generateKey();
        }

        // Test uniqueness
        for(int i = 0; i < keys.length; ++i) {
            for(int j = i + 1; j < keys.length; ++j) {
                assertNotEquals("Generator generated duplicate keys",
                        keys[i], keys[j]);
            }
        }
    }
}
