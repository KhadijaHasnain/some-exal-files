import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface HashInterface<Key, Value> {
    Value get(Key key);
    void put(Key key, Value value);
    int getCollisions();
    void printTable(PrintWriter fout);
}

class LinearProbingHash<Key, Value> implements HashInterface<Key, Value> {

    private class Record {
        Key key;
        Value value;

        public Record(Key key1, Value value1) {
            key = key1;
            value = value1;
        }
    }

    private List<Record> table;
    private int collisions;

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % table.size();
    }

    public LinearProbingHash(int initialSize) {
        table = new ArrayList<>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            table.add(null);
        }
        collisions = 0;
    }

    @Override
    public Value get(Key key) {
        int index = lookUp(key);
        if (index >= table.size()) return null;
        Record p = table.get(index);
        return p != null ? p.value : null;
    }

    @Override
    public void put(Key key, Value value) {
        int index = lookUp(key);
        if (index >= table.size()) {
            throw new RuntimeException("Table is full");
        }
        Record p = table.get(index);
        if (p == null) {
            table.set(index, new Record(key, value));
        } else {
            p.value = value;
        }
    }

    @Override
    public int getCollisions() {
        return collisions;
    }

    @Override
public void printTable(PrintWriter fout) {
    for (int i = 0; i < table.size(); i++) {
        Record p = table.get(i);
        fout.print("[" + i + "]: ");
        if (p != null) {
            fout.println("Key: " + p.key + ", Value: " + p.value);
        } else {
            fout.println("Empty");
        }
    }
}


    private int lookUp(Key key) {
        int index = hash(key);
        while (true) {
            Record p = table.get(index);
            if (p == null || p.key.equals(key)) {
                return index;
            }
            collisions++;
            index = (index + 1) % table.size();
        }
    }
}

class QuadraticProbingHash<Key, Value> implements HashInterface<Key, Value> {

    private class Record {
        Key key;
        Value value;

        public Record(Key key1, Value value1) {
            key = key1;
            value = value1;
        }
    }

    private List<Record> table;
    private int collisions;
    private static final int doubleFactor = 181;

    public QuadraticProbingHash(int initialSize) {
        table = new ArrayList<>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            table.add(null);
        }
        collisions = 0;
    }

    @Override
    public Value get(Key key) {
        int index = lookUp(key);
        if (index >= table.size()) return null;
        Record p = table.get(index);
        return p != null ? p.value : null;
    }

    @Override
    public void put(Key key, Value value) {
        int index = lookUp(key);
        if (index >= table.size()) {
            throw new RuntimeException("Table is full");
        }
        Record p = table.get(index);
        if (p == null) {
            table.set(index, new Record(key, value));
        } else {
            p.value = value;
        }
    }

    @Override
    public int getCollisions() {
        return collisions;
    }

    @Override
public void printTable(PrintWriter fout) {
    for (int i = 0; i < table.size(); i++) {
        Record p = table.get(i);
        fout.print("[" + i + "]: ");
        if (p != null) {
            fout.println("Key: " + p.key + ", Value: " + p.value);
        } else {
            fout.println("Empty");
        }
    }
}


    private int hash2(Key key) {
        return doubleFactor - (key.hashCode() & 0x7fffffff) % doubleFactor;
    }

    private int lookUp(Key key) {
        int index = hash2(key);
        int i = 0;
        while (true) {
            Record p = table.get(index);
            if (p == null || p.key.equals(key)) {
                return index;
            }
            collisions++;
            i++;
            index = (index + f(i, key)) % table.size();
        }
    }

    private int f(int i, Key key) {
        return i * i;
    }

    class DoubleHashingProbing<Key, Value> implements HashInterface<Key, Value> {

    private class Record {
        Key key;
        Value value;

        public Record(Key key1, Value value1) {
            key = key1;
            value = value1;
        }
    }

    private List<Record> table;
    private int collisions;
    private static final int doubleFactor = 181;

    public DoubleHashingProbing(int initialSize) {
        table = new ArrayList<>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            table.add(null);
        }
        collisions = 0;
    }

    @Override
    public Value get(Key key) {
        int index = lookUp(key);
        if (index >= table.size()) return null;
        Record p = table.get(index);
        return p != null ? p.value : null;
    }

    @Override
    public void put(Key key, Value value) {
        int index = lookUp(key);
        if (index >= table.size()) {
            throw new RuntimeException("Table is full");
        }
        Record p = table.get(index);
        if (p == null) {
            table.set(index, new Record(key, value));
        } else {
            p.value = value;
        }
    }

    @Override
    public int getCollisions() {
        return collisions;
    }

    @Override
public void printTable(PrintWriter fout) {
    for (int i = 0; i < table.size(); i++) {
        Record p = table.get(i);
        fout.print("[" + i + "]: ");
        if (p != null) {
            fout.println("Key: " + p.key + ", Value: " + p.value);
        } else {
            fout.println("Empty");
        }
    }
}


    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % table.size();
    }

    private int hash2(Key key) {
        return doubleFactor - (key.hashCode() & 0x7fffffff) % doubleFactor;
    }

    private int lookUp(Key key) {
        int index = hash(key);
        int step = hash2(key);

        while (true) {
            Record p = table.get(index);
            if (p == null || p.key.equals(key)) {
                return index;
            }
            collisions++;
            index = (index + step) % table.size();
        }
    }
}
}

public class Driver {
    private static final int TABLE_SIZE = 191;

    public static void main(String[] args) {
        String[] inputFiles = {"in150.txt", "in160.txt", "in170.txt", "in180.txt"};
    
        for (String inputFile : inputFiles) {
            try {
                String outputFilePrefix = "out" + inputFile.substring(2, 5);
                testFile(inputFile, outputFilePrefix + "_collisions.txt", outputFilePrefix + "_tables.txt");
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
    }
    

    private static void testFile(String inputFilename, String outputFilename1, String outputFilename2) {
        // System.out.println("Input file " + inputFilename + ", output files " + outputFilename1 + " and " + outputFilename2);
    
        List<Integer> data = readData(inputFilename);
    
        try (PrintWriter fout = new PrintWriter(new FileWriter(outputFilename1));
             PrintWriter fout2 = new PrintWriter(new FileWriter(outputFilename2))) {
    
            fout.println("*** Random Order Start ***\n");
            testData(data, fout, fout2);
    
            Collections.sort(data);
            fout.println("*** Ascending Order Start ***\n");
            testData(data, fout, fout2);
    
            Collections.reverse(data);
            fout.println("*** Descending Order Start ***\n");
            testData(data, fout, fout2);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    private static List<Integer> readData(String inputFile) {
        List<Integer> data = new ArrayList<>();
        try (BufferedReader fin = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = fin.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    try {
                        int key = Integer.parseInt(token.trim());
                        data.add(key);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing integer: " + token);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    

    private static void testData(List<Integer> data, PrintWriter fout, PrintWriter fout2) {
        QuadraticProbingHash<Integer, Integer> qph = new QuadraticProbingHash<>(TABLE_SIZE);

        for (Integer key : data) {
            testInputKey(key, qph, fout, fout2);
        }

        fout.println("\nQuadratic " + qph.getCollisions() + " collisions");
        qph.printTable(fout2);

        fout.println("\n*** End ***\n");
    }

    private static void testInputKey(Integer key, HashInterface<Integer, Integer> qph,
                                     PrintWriter fout, PrintWriter fout2) {
        int previousCollisions = qph.getCollisions();

        qph.put(key, key * 2);

        Integer retrievedValue = qph.get(key);
        String retrievedText = retrievedValue != null ? String.valueOf(retrievedValue) : "null";

        fout.println(key + " : " + key * 2 + " -> " + retrievedText +
                ", collisions " + (qph.getCollisions() - previousCollisions) + "\n");

        if (retrievedValue == null || !retrievedValue.equals(key * 2)) {
            fout.println("Retrieved value " + retrievedText + " does not match stored value " + key * 2 +
                    " for key " + key + "\n");
            throw new RuntimeException("value mismatch");
        }
    }
}
