package by.AndreiKviatkouski.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordValidatorTest {

    @Test
    void check() {
        assertNotNull(WordValidator.check("Musk", "Musk_abc"));
    }
}