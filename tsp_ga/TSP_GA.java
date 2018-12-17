package tsp_ga;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class TSP_GA {

    public static void main(String[] args) {

        //save to csv
        String savedTo = "D:\\KULIAH\\Smes 7\\OKH\\FP\\datasets\\hasil_TSP_GA.csv";
        generateCsvFile(savedTo);

    }

    private static void generateCsvFile(String sFileName) {
        long start = System.nanoTime();
        boolean runtime = true;
        int gen = 100;
        int time = 1;

        try {

            //declare csv file
            String destination = "D:\\KULIAH\\Smes 7\\OKH\\FP\\datasets\\";
            String savedTo = "D:\\KULIAH\\Smes 7\\OKH\\FP\\datasets\\hasil_TSP_GA.csv";
            String big1, big2, medium1, medium2, small1, small2, hidden1, hidden2;
            big1 = destination + "big1.csv";
            big2 = destination + "big2.csv";
            medium1 = destination + "medium1.csv";
            medium2 = destination + "medium2.csv";
            small1 = destination + "small1.csv";
            small2 = destination + "small2.csv";
            hidden1 = destination + "hiddeninstance1.csv";
            hidden2 = destination + "hiddeninstance2.csv";

            //Choosing csv file
            File file = new File(hidden1);

            //create new object        
            FileWriter writer = new FileWriter(sFileName);

            //Create array
            try (Scanner inputStream = new Scanner(file)) {
                //Defining array
                int isi = inputStream.nextInt();
                City a[] = new City[isi];

                //Read CSV
                int index = 0;
                while (inputStream.hasNext()) {
                    String b = inputStream.next();
                    String row[] = b.split(",");

                    a[index] = new City(index, Integer.parseInt(row[0]), Integer.parseInt(row[1]));
                    TourManager.addCity(a[index]);
                    index++;
                }
            } catch (FileNotFoundException e) {
            }

            // Initialize population
            int popSize = 20;
            Population pop = new Population(popSize, true);
            for (int i = 0; i < popSize; i++) {
                System.out.println("Tour ke" + i + pop.getTour(i));
                writer.append("Tour ke" + i + pop.getTour(i));
                writer.append("\n");
            }
            System.out.println("");
            System.out.println("Initial Tour: " + pop.getFittest());
            System.out.println("Initial distance: " + pop.getFittest().getDistance());
            System.out.println("");

            writer.append("\n");
            writer.append("Initial Tour: " + pop.getFittest());
            writer.append("\n");
            writer.append("Initial distance: " + pop.getFittest().getDistance());
            writer.append("\n");

            writer.flush();

            // Evolve population for 100 generations
            pop = GA.evolvePopulation(pop);

            if (runtime == false) {
                for (int y = 0; y < gen; y++) {
                    pop = GA.evolvePopulation(pop);
                    System.out.println("Generasi ke" + y + "\n" + pop.getFittest()
                            + "Distance = " + pop.getFittest().getDistance());
                    writer.append("Generasi ke" + y + "\n" + pop.getFittest()
                            + "Distance = " + pop.getFittest().getDistance());
                    writer.append("\n");
                }
            } else {
                long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(time);
                for (int y = 0; stop > System.nanoTime(); y++) {
                    pop = GA.evolvePopulation(pop);
                    System.out.println("Generasi ke" + y + "\n" + pop.getFittest()
                            + "Distance = " + pop.getFittest().getDistance());
                    writer.append("Generasi ke" + y + "\n" + pop.getFittest()
                            + "Distance = " + pop.getFittest().getDistance());
                    writer.append("\n");
                }
            }

            // Print final results
            System.out.println("\n");
            System.out.println("Finished");
            System.out.println("Final distance: " + pop.getFittest().getDistance());
            System.out.print("Solution");
            System.out.println(pop.getFittest());
            System.out.println("Saved to = " + savedTo);

            writer.append("\n");
            writer.append("Finished");
            writer.append("\n");
            writer.append("Final distance: " + pop.getFittest().getDistance());
            writer.append("\n");
            writer.append("Solution:");
            writer.append("\n");
            writer.append(" " + pop.getFittest());
            writer.append("\n");

            //calculate running time
            long end = System.nanoTime();
            NumberFormat formatter = new DecimalFormat("#0.00000");
            String execTime = "Execution time is " + formatter.format((end - start) / 1000000000d) + " seconds";
            System.out.println(execTime);
            writer.append("\n\n");
            writer.append(execTime);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
