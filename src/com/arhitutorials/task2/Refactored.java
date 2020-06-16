package com.arhitutorials.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Refactored {

    public static void main(String[] args) {
        final int minYear = 2009;
        final int maxYear = 2010;
        ArrayList<String> years = new ArrayList<>();
        for (int i = minYear; i <= maxYear; i++) {
            years.add(Integer.toString(i));
        }
        try {
            Map<String, StatItem> stats = loadStats(years);
            writeStats(stats);
        } catch (IOException e) {
            System.out.println("Ааааа! Все пропалоооо!");
        }
    }

    private static class StatItem {
        public int count;
        public int total;

        public StatItem(int count, int total) {
            this.count = count;
            this.total = total;
        }
    }

    private static Map<String, StatItem> loadStats(List<String> years) throws IOException {
        TreeMap<String, StatItem> stats = new TreeMap<>();
        File file = new File(".\\src\\com\\arhitutorials\\task2\\1.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String year = parts[2];
                String count = parts[3];
                if (years.contains(year)) {
                    if (stats.containsKey(year)) {
                        StatItem item = stats.get(year);
                        item.count++;
                        item.total += Integer.parseInt(count);
                        stats.put(year, item);
                    } else {
                        StatItem item = new StatItem(1, Integer.parseInt(count));
                        stats.put(year, item);
                    }
                }
            }
        }
        return stats;
    }

    private static void writeStats(Map<String, StatItem> stats) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".\\src\\com\\arhitutorials\\task2\\statistika.txt")))) {
            int i = 0;
            StringBuilder sb = new StringBuilder();
            for (String year : stats.keySet()) {
                StatItem item = stats.get(year);
                sb.setLength(0);
                sb.append(i + 1).append(' ')
                        .append(year).append(' ')
                        .append(item.count > 0 ? ((double) item.total) / item.count : 0)
                        .append('\n');
                writer.write(sb.toString());
                writer.flush();
                i++;
            }
        }
    }
}
