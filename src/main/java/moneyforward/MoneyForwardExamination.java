package moneyforward;

import com.opencsv.bean.CsvToBeanBuilder;
import model.WalletCsv;
import model.WalletDate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoneyForwardExamination {

    public static void main(String[] args) {

        String file_path = "/Users/anbuthamizhselvan/Desktop/test_wallet.csv";

        try{
            List<WalletCsv> walletCsvList = csvToWalletCsv(file_path);
            Map<Integer, List<WalletDate>> yearBasedWallet = walletCsvToWalletDateMapper(walletCsvList);



        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    static List<WalletCsv> csvToWalletCsv(String path) throws IOException {
        List<WalletCsv> walletCsvList = new CsvToBeanBuilder(new FileReader(path))
                .withType(WalletCsv.class)
                .build()
                .parse();
        return walletCsvList;
    }

    static Map<Integer, List<WalletDate>> walletCsvToWalletDateMapper(List<WalletCsv> csvList) {
        List<WalletDate> walletDateList = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        for ( WalletCsv csv: csvList ) {
            WalletDate walletDate = new WalletDate();
            walletDate.setDate(LocalDate.parse(csv.getDate(), df));
            walletDate.setDeposit(csv.getDeposit());
            walletDate.setContent(csv.getContent());

            walletDateList.add(walletDate);
        }

        Map<Integer, List<WalletDate>> walletDateMap = new HashMap<>();

        for ( WalletDate walletDate: walletDateList) {
            int year = walletDate.getDate().getYear();
            List<WalletDate> walletDateList1 = walletDateList
                    .stream()
                    .filter(walletDate1 -> walletDate1.getDate().getYear() == year)
                    .collect(Collectors.toList());

            walletDateMap.putIfAbsent(year, walletDateList1);
        }

        return walletDateMap;
    }
}
