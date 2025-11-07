package ru.t1.education;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void testPrefixTree() {
        final var trie = new Trie();

        var actual = trie.search("cat");
        assertFalse(actual, "Prefix tree doesn't contains word 'cat' (actual: true)");

        trie.insert("cat");
        trie.insert("dog");
        trie.insert("mouse");
        trie.insert("catalog");
        trie.insert("catacomb");
        trie.insert("caterpillar");

        actual = trie.search("cat");
        assertTrue(actual, "Prefix tree contains word 'cat' (actual: false)");

        actual = trie.search("dog");
        assertTrue(actual, "Prefix tree contains word 'dog' (actual: false)");

        var words = trie.startWith("cat");
        var expected = Set.of("cat", "catalog", "catacomb", "caterpillar");

        assertEquals(expected, Set.copyOf(words), "Prefix tree search by prefix have to return '" + expected + "' (actual: '" + words + "')");
    }
}