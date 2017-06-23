package za.co.jumo;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanCalculator {

    public static void main(String[] args){
        try {
            List<String> fileContents = Files.readAllLines(Paths.get(args[0]));
            fileContents.remove(0);
            writeValuesToFile(getComputedLoanValues(fileContents));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void writeValuesToFile(Map<Triplet<String, String, String>, Pair<Integer, BigDecimal>> results) throws IOException {
        Files.deleteIfExists(Paths.get("Output.csv"));
        Path outputFile = Files.createFile(Paths.get("Output.csv"));
        List<String> outputList = new ArrayList<>();
        outputList.add("Network,Date,Product,Count,Sum");
        results.forEach((objects, objects2) -> {
            outputList.add(objects.getValue0() + ",'" + objects.getValue1() + "'," + objects.getValue2() + ","
                    + objects2.getValue0() + "," + objects2.getValue1());
        });
        Files.write(outputFile, outputList);
    }

    private static Map<Triplet<String, String, String>, Pair<Integer, BigDecimal>> getComputedLoanValues(List<String> fileContents) {
        Map<Triplet<String, String, String>, Pair<Integer, BigDecimal>> results = new HashMap<>();
        fileContents.parallelStream().forEach(s -> {
            String[] values = s.split(",");
            LocalDate loanDate = LocalDate.parse(values[2].substring(1, values[2].length() - 1), DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            Triplet<String, String, String> header = new Triplet<>(values[1]
                    , loanDate.getMonth().toString() + " " + loanDate.getYear()
                    , values[3]);
            BigDecimal loanValue = new BigDecimal(values[4]);
            results.computeIfPresent(header, (objects, objects2) -> {
                return new Pair<>(objects2.getValue0() + 1, objects2.getValue1().add(loanValue));
            });
            results.computeIfAbsent(header, objects -> {
                return new Pair<>(1, loanValue);
            });
        });
        return results;
    }
}
