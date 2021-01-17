package by.AndreiKviatkouski.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordValidatorTest {

    @Test
    @DisplayName("\uD83D\uDE31")
    void check() {
        assertNotNull(WordValidator.check("Musk", "Musk_abc"));
    }
}