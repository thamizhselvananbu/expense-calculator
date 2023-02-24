package moneyforward;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import model.Transactions;
import model.WalletCsv;
import model.WalletDate;
import model.WalletJson;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author: thamizh
 * Date: 2023/12/17/ 17:30 JST
 */
public class MoneyForwardExamination {

    public static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * Method to load data from csv file based on headers row
     * map the csv file row into WalletCsv java object
     *
     * @param path csv file path
     * @return list of parsed java objects based on csv file
     * @throws IOException due to file processing or missing
     */
    static List<WalletCsv> csvToWalletCsv(String path) throws IOException {
        List<WalletCsv> walletCsvList = new CsvToBeanBuilder(new FileReader(path))
                .withType(WalletCsv.class)
                .build()
                .parse();
        return walletCsvList;
    }

    /**
     * Loaded CSV file data - parse String dates to java LocalDate
     * Divide the records based on year
     * store it in map with year as key
     *
     * @param csvList to map from String type date to LocalDate type for process
     * @return return yearly based wallet details
     */
    static Map<Integer, List<WalletDate>> walletCsvToWalletDateMapper(List<WalletCsv> csvList) {
        /**create arraylist to store date formatted csv records **/
        List<WalletDate> walletDateList = new ArrayList<>();

        /**Map each csv row into walletdate type to process data**/
        for (WalletCsv csv : csvList) {
            WalletDate walletDate = new WalletDate();
            /** parsing date **/
            walletDate.setDate(LocalDate.parse(csv.getDate(), df));
            walletDate.setDeposit(csv.getDeposit());
            walletDate.setContent(csv.getContent());

            walletDateList.add(walletDate);
        }

        /** create map to store wallet data by years **/
        Map<Integer, List<WalletDate>> walletDateMap = new HashMap<>();

        /**
         * divide csv data based on year
         * store it in map with year as key
         */
        for (WalletDate walletDate : walletDateList) {
            int year = walletDate.getDate().getYear();
            var walletDateList1 = walletDateList
                    .stream()
                    .filter(walletDate1 -> walletDate1.getDate().getYear() == year)
                    .collect(Collectors.toList());

            walletDateMap.putIfAbsent(year, walletDateList1);
        }

        return walletDateMap;
    }

    /**
     * Divide further walletdate objects by years
     * into walletdate by months
     *
     * @param walletDateByYear passing wallet details by year
     * @return
     */
    static void mapWalletDateByYearMonthResult(Map<Integer, List<WalletDate>> walletDateByYear) throws JsonProcessingException {

        Map<Integer, List<Integer>> yearBasedUniqueMonth = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (var entry : walletDateByYear.entrySet()) {
            var walletDateList = entry.getValue();
            /**
             * Filter wallet date by month
             * store it in map with years as key
             * unique month for a yeas as value
             */
            var months = walletDateList.stream()
                    .map(x -> x.getDate().getMonth().getValue())
                    .distinct()
                    .collect(Collectors.toList());
            yearBasedUniqueMonth.put(entry.getKey(), months);
        }

        /**
         * Iterate over each month based on years
         * find totalIncome for a month
         * find totalExpenditure for a month
         * store it in WalletJson class
         */
        for (var eachUniqueMonth : yearBasedUniqueMonth.entrySet()) {
            var uniqueMonthsList = eachUniqueMonth.getValue();
            var eachYear = eachUniqueMonth.getKey();

            for (var eachYearUniqueMonth : uniqueMonthsList) {

                var totalIncome = walletDateByYear.get(eachYear)
                        .stream()
                        .filter(walletDate -> walletDate.getDate().getYear() == eachYear)
                        .filter(walletDate -> walletDate.getDate().getMonth().getValue() == eachYearUniqueMonth)
                        .map(WalletDate::getDeposit)
                        .filter(deposit -> deposit > 0)
                        .reduce(0, Integer::sum);

                var totalExpenditure = walletDateByYear.get(eachYear)
                        .stream()
                        .filter(walletDate -> walletDate.getDate().getYear() == eachYear)
                        .filter(walletDate -> walletDate.getDate().getMonth().getValue() == eachYearUniqueMonth)
                        .map(WalletDate::getDeposit)
                        .filter(deposit -> deposit < 0)
                        .reduce(0, Integer::sum);

                var transactionsDateByMonth = walletDateByYear.get(eachYear)
                        .stream()
                        .filter(walletDate -> walletDate.getDate().getMonth().getValue() == eachYearUniqueMonth)
                        .map(walletDate -> {
                            Transactions tran = new Transactions();
                            tran.setAmount(walletDate.getDeposit());
                            tran.setContent(walletDate.getContent());
                            tran.setDate(String.valueOf(walletDate.getDate().format(df)));
                            return tran;
                        })
                        .collect(Collectors.toList());

                String period = String.valueOf(eachYear).concat("/").concat(String.valueOf(eachYearUniqueMonth));

                WalletJson walletJson = new WalletJson();
                walletJson.setPeriod(period);
                walletJson.setTotalIncome(totalIncome);
                walletJson.setTotalExpenditure(totalExpenditure);
                walletJson.setWalletDates(transactionsDateByMonth);

                String writeWalletJson = objectMapper.writeValueAsString(walletJson);
                System.out.println(writeWalletJson);

            }
        }
    }

    /**
     * main method - starting point of execution
     *
     * @param args
     */
    public static void main(String[] args) {

        if (!args[0].isBlank()) {
            try {
                String filePath = args[0];
                /**
                 * load csv file from a path
                 * convert csv file into java object
                 */
                var walletCsvList = csvToWalletCsv(filePath);

                /**
                 * map date in string format to LocalDate format
                 * divide the data by years
                 */
                var yearBasedWallet = walletCsvToWalletDateMapper(walletCsvList);

                /**
                 * Further divide the year based data into months
                 * finally print the out json
                 */
                mapWalletDateByYearMonthResult(yearBasedWallet);

            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Internal error:" + e.getMessage());
            }
        } else {
            System.err.println("Please pass the File path in command line arguments!");
        }
    }
}