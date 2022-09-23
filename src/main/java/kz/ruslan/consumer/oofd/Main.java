package kz.ruslan.consumer.oofd;

import kz.ruslan.consumer.oofd.domain.CashVoucher;
import kz.ruslan.consumer.oofd.service.ScannerCashVoucher;

import java.io.IOException;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) {
        try {
            ScannerCashVoucher scannerCashVoucher = new ScannerCashVoucher("" +
                    "https://consumer.oofd.kz/ticket/...");
            CashVoucher cashVoucher = scannerCashVoucher.getCashVoucher();
            System.out.println(cashVoucher);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
