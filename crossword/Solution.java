package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        List<Word> list = detectAllWords(crossword, "home", "same");
        for (Word w : list) {
            System.out.println(w);
        }
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> list = new ArrayList<>();
        for (String word : words) {
            Word w = new Word(word);
            int n = 1;
            int[][] ch = new int[word.length()][2]; // массив координат искомого слова
            String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "", s6 = "", s7 = "", s8 = "";
            for (int i = 0; i < crossword.length; i++) {
                if (word.equals(s1)) break;
                if (word.equals(s2)) break;
                if (word.equals(s3)) break;
                if (word.equals(s4)) break;
                if (word.equals(s5)) break;
                if (word.equals(s6)) break;
                if (word.equals(s7)) break;
                if (word.equals(s8)) break;

                for (int j = 0; j < crossword[i].length; j++) {
                    if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                        s1 = String.valueOf((char) crossword[i][j]);
                        s2 = String.valueOf((char) crossword[i][j]);
                        s3 = String.valueOf((char) crossword[i][j]);
                        s4 = String.valueOf((char) crossword[i][j]);
                        s5 = String.valueOf((char) crossword[i][j]);
                        s6 = String.valueOf((char) crossword[i][j]);
                        s7 = String.valueOf((char) crossword[i][j]);
                        s8 = String.valueOf((char) crossword[i][j]);
                    }

                    while (true) {
                        if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (j + n < crossword[i].length) {
                                    s1 += String.valueOf((char) crossword[i][j + n]);
                                    if (word.contains(s1)) {
                                        ch[n][0] = i;
                                        ch[n][1] = j + n;
                                        n++;
                                    } else break;
                                } else break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s1)) { n = 0; break; }
                    n = 1;

                    while (true) {
                        if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (i + n < crossword.length) {
                                    s2 += String.valueOf((char) crossword[i + n][j]);
                                    if (word.contains(s2)) {
                                        ch[n][0] = i + n;
                                        ch[n][1] = j;
                                        n++;
                                    } else break;
                                } else break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s2)) { n = 0; break; }
                    n = 1;

                    while (true) {
                        if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (j - n >= 0) {
                                    s3 += String.valueOf((char) crossword[i][j - n]);
                                    if (word.contains(s3)) {
                                        ch[n][0] = i;
                                        ch[n][1] = j - n;
                                        n++;
                                    } else break;
                                } else break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s3)) { n = 0; break; }
                    n = 1;

                    while (true) {
                        if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (i - n >= 0) {
                                    s4 += String.valueOf((char) crossword[i - n][j]);
                                    if (word.contains(s4)) {
                                        ch[n][0] = i - n;
                                        ch[n][1] = j;
                                        n++;
                                    } else break;
                                } else break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s4)) { n = 0; break; }
                    n = 1;

                    while (true) {
                        if (word.indexOf(crossword[i][j]) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (i + n < crossword.length && j + n < crossword[i].length) {
                                    s5 += String.valueOf((char) crossword[i + n][j + n]);
                                    if (word.contains(s5)) {
                                        ch[n][0] = i + n;
                                        ch[n][1] = j + n;
                                        n++;
                                    } else break;
                                } else break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s5)) { n = 0; break; }
                    n = 1;

                    while (true) {
                        if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (i + n < crossword.length && j - n >= 0) {
                                    s6 += String.valueOf((char) crossword[i + n][j - n]);
                                    if (word.contains(s6)) {
                                        ch[n][0] = i + n;
                                        ch[n][1] = j - n;
                                        n++;
                                    } else break;
                                } else break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s6)) { n = 0; break; }
                    n = 1;

                    while (true) {
                        if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (i - n >= 0 && j - n >= 0) {
                                    s7 += String.valueOf((char) crossword[i - n][j - n]);
                                    if (word.contains(s7)) {
                                        ch[n][0] = i - n;
                                        ch[n][1] = j - n;
                                        n++;
                                    } else break;
                                } else  break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s7)) { n = 0; break; }
                    n = 1;

                    while (true) {
                        if (word.indexOf(String.valueOf((char)crossword[i][j])) == 0) {
                            ch[0][0] = i;
                            ch[0][1] = j;
                            if (n < word.length()) {
                                if (i - n >= 0 && j + n < crossword[i].length) {
                                    s8 += String.valueOf((char) crossword[i - n][j + n]);
                                    if (word.contains(s8)) {
                                        ch[n][0] = i - n;
                                        ch[n][1] = j + n;
                                        n++;
                                    } else break;
                                } else break;
                            } else break;
                        } else break;
                    }

                    if (word.equals(s8)) { n = 0; break; }
                    n = 1;
                }
            }
            w.setStartPoint(ch[0][1], ch[0][0]);
            w.setEndPoint(ch[ch.length - 1][1], ch[ch.length - 1][0]);
            list.add(w);
        }

        return list;
    }

    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}
