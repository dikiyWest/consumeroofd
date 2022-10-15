package kz.ruslan.consumer.oofd.service;

import kz.ruslan.consumer.oofd.domain.CashVoucher;
import kz.ruslan.consumer.oofd.repository.CashVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CashVoucherService {

    @Autowired
    CashVoucherRepository cashVoucherRepository;


    public CashVoucher getCashVoucherFromOOFDAndSave(String url){
        ScannerCashVoucher scannerCashVoucher = null;
        try {
            scannerCashVoucher = new ScannerCashVoucher(url);
            //Если существует запись по фискальному признаку. Возвращаем значение с базы
            CashVoucher cashVoucherFromDb = getCashVoucherByFP(scannerCashVoucher.getCashVoucher().getFP());
            if(cashVoucherFromDb != null)
                return cashVoucherFromDb;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cashVoucherRepository.save(scannerCashVoucher.getCashVoucher());
    }

    public CashVoucher getCashVoucherByFP(String FP){
        return cashVoucherRepository.findByFP(FP);
    }

}

