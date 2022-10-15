package kz.ruslan.consumer.oofd.repository;

import kz.ruslan.consumer.oofd.domain.CashVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashVoucherRepository extends JpaRepository<CashVoucher,Long> {
    CashVoucher findByFP(String FP);
}
