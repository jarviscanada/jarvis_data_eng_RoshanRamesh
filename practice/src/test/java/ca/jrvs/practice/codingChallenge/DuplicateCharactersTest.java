package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DuplicateCharactersTest {

    @Test
    public void findDuplicateTest(){
        DuplicateCharacters duplicateCharacters = new DuplicateCharacters();
        List<String> result = Arrays.asList(duplicateCharacters.findDuplicate("A black cat"));
        assertEquals(2, result.size());
        assertTrue(result.contains('a'));
        assertTrue(result.contains('c'));
    }

}