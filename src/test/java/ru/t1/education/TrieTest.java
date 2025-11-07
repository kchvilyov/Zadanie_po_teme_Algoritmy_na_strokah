package ru.t1.education;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void testPrefixTree() {
        final var trie = new Trie();

        // Поиск до вставки
        assertFalse(trie.search("cat"), "Prefix tree doesn't contain word 'cat'");

        // Вставка слов
        trie.insert("cat");
        trie.insert("dog");
        trie.insert("mouse");
        trie.insert("catalog");
        trie.insert("catacomb");
        trie.insert("caterpillar");

        // Поиск полных слов
        assertTrue(trie.search("cat"), "Prefix tree contains word 'cat'");
        assertTrue(trie.search("dog"), "Prefix tree contains word 'dog'");

        // Поиск всех слов с префиксом "cat"
        var words = trie.getWordsWithPrefix("cat");
        var expected = Set.of("cat", "catalog", "catacomb", "caterpillar");

        assertEquals(expected, Set.copyOf(words),
                "Prefix tree search by prefix should return " + expected + ", but got " + words);
    }

    @Test
    void startsWith() {
        final var trie = new Trie();

        // Проверка до вставки
        assertFalse(trie.startsWith("cat"), "До вставки префикса 'cat' не должно быть");

        // Вставка слов
        trie.insert("cat");
        trie.insert("catalog");
        trie.insert("dog");

        // Проверка существования префиксов
        assertTrue(trie.startsWith("cat"), "Префикс 'cat' должен существовать");
        assertTrue(trie.startsWith("c"), "Префикс 'c' должен существовать");
        assertTrue(trie.startsWith("catalog"), "Префикс 'catalog' должен существовать");

        // Проверка отсутствующих префиксов
        assertFalse(trie.startsWith("car"), "Префикса 'car' нет в дереве");
        assertFalse(trie.startsWith("doge"), "Префикса 'doge' нет в дереве");
        assertFalse(trie.startsWith("xyz"), "Префикса 'xyz' нет в дереве");

        // Проверка пустого префикса — по логике, он есть в любом дереве
        assertTrue(trie.startsWith(""), "Пустой префикс всегда существует");
    }
}