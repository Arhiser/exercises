package com.arhitutorials.task1;

import java.util.HashSet;

public class Task1 {
    public static void main(String[] args) {
        int[][] field1 = new int[][]{
                {1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        int[][] field2 = new int[][]{
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 1}
        };

        System.out.println(solve(field2));
    }

    public static int solve(int[][] field) {

        int count = 0;
        HashSet<Index> currentIslandCells = new HashSet<>();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == 1) {
                    count++;
                    currentIslandCells.add(new Index(j, i));

                    while (!currentIslandCells.isEmpty()) {                     // надо найти все клетки текущего острова и утопить
                        Index cell = currentIslandCells.iterator().next();      // чтоб они не мешали искать другие острова :)
                        currentIslandCells.remove(cell);
                        cell.set(field, 0);

                        if (cell.getLeftValue(field) == 1) {
                            currentIslandCells.add(cell.getLeftCell());
                        }
                        if (cell.getRightValue(field) == 1) {
                            currentIslandCells.add(cell.getRightCell());
                        }
                        if (cell.getTopValue(field) == 1) {
                            currentIslandCells.add(cell.getTopCell());
                        }
                        if (cell.getBottomValue(field) == 1) {
                            currentIslandCells.add(cell.getBottomCell());
                        }
                    }

                }
            }
        }
        return count;
    }

    // класс, который отвечает за логику работы с клетками поля
    // выделил в отдельный класс, чтоб не мешало читать основной алгоритм
    public static class Index {
        private int x;
        private int y;

        public Index(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void set(int[][] field, int value) {
            if (x < 0 || y < 0 || x >= field[0].length || y >= field.length) {
                return;
            }
            field[y][x] = value;
        }

        private int getNeighborValue(int[][] field, int shiftX, int shiftY) {
            int x = this.x + shiftX;
            int y = this.y + shiftY;
            if (x < 0 || y < 0 || x >= field[0].length || y >= field.length) {
                return 0;
            }
            return field[y][x];
        }

        public int getLeftValue(int[][] field) {
            return getNeighborValue(field, -1 , 0);
        }

        public int getRightValue(int[][] field) {
            return getNeighborValue(field, 1 , 0);
        }

        public int getTopValue(int[][] field) {
            return getNeighborValue(field, 0 , 1);
        }

        public int getBottomValue(int[][] field) {
            return getNeighborValue(field, 0 , -1);
        }

        public Index getLeftCell() {
            return new Index(x - 1 , y);
        }

        public Index getRightCell() {
            return new Index(x + 1 , y);
        }

        public Index getTopCell() {
            return new Index(x, y + 1);
        }

        public Index getBottomCell() {
            return new Index(x + 1 , y - 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Index)) return false;

            Index index = (Index) o;

            if (x != index.x) return false;
            return y == index.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
