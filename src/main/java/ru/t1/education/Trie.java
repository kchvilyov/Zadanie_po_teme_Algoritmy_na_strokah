package ru.t1.education;

import java.util.*;

/**
 * Префиксное дерево (Trie) для хранения строк.
 * Поддерживает вставку слов, поиск полного слова и поиск по префиксу.
 */
public class Trie {
    // Корневой узел дерева
    private final TrieNode root;

    // Узел префиксного дерева
    private static class TrieNode {
        // Дочерние узлы: символ -> узел
        Map<Character, TrieNode> children;
        // Признак конца слова
        boolean isEndOfWord;

        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    /**
     * Конструктор: инициализирует пустое дерево с корневым узлом.
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Вставляет слово в префиксное дерево.
     *
     * @param word слово для вставки
     */
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        TrieNode current = root;

        // Проходим по каждому символу слова
        for (char c : word.toCharArray()) {
            // Если у текущего узла нет ребёнка с таким символом — создаём
            current.children.putIfAbsent(c, new TrieNode());
            // Переходим к следующему узлу
            current = current.children.get(c);
        }

        // Помечаем последний узел как конец слова
        current.isEndOfWord = true;
    }

    /**
     * Проверяет, содержится ли слово в дереве.
     *
     * @param word слово для поиска
     * @return true, если слово найдено
     */
    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }

        TrieNode node = findNode(word);
        // Слово найдено, если узел существует и помечен как конец слова
        return node != null && node.isEndOfWord;
    }

    /**
     * Проверяет, существует ли в дереве слово с заданным префиксом.
     *
     * @param prefix префикс для поиска
     * @return true, если найдено хотя бы одно слово с таким префиксом
     */
    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    /**
     * Возвращает все слова, начинающиеся с заданного префикса.
     *
     * @param prefix префикс
     * @return список слов, начинающихся с префикса
     */
    public List<String> getWordsWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        if (prefix == null) return result;

        // Находим узел, соответствующий префиксу
        TrieNode node = findNode(prefix);
        if (node == null) {
            return result; // префикса нет в дереве
        }

        // Собираем все слова от этого узла
        collectWords(node, new StringBuilder(prefix), result);
        return result;
    }

    /**
     * Вспомогательный метод: находит узел, соответствующий строке (слову или префиксу).
     *
     * @param s строка для поиска
     * @return узел или null, если путь прерывается
     */
    private TrieNode findNode(String s) {
        TrieNode current = root;
        for (char c : s.toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                return null; // путь прерывается
            }
        }
        return current;
    }

    /**
     * Рекурсивно собирает все слова, начинающиеся с данного узла.
     *
     * @param node   текущий узел
     * @param prefix префикс (накопленная строка)
     * @param result список для добавления слов
     */
    private void collectWords(TrieNode node, StringBuilder prefix, List<String> result) {
        if (node.isEndOfWord) {
            result.add(prefix.toString());
        }

        // Рекурсивно обходим всех детей
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            // Добавляем текущий символ к накопленной строке
            prefix.append(entry.getKey());
            collectWords(entry.getValue(), prefix, result);
            // Откатываемся: удаляем последний символ префикса,
            // потому что мы перешли к следующему ребёнку
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}