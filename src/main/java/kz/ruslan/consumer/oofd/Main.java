package kz.ruslan.consumer.oofd;

import kz.ruslan.consumer.oofd.service.CashVoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);

        CashVoucherService customerService = applicationContext.getBean(CashVoucherService.class);

        System.out.println(customerService.getCashVoucherFromOOFDAndSave(
                "https://consumer.oofd.kz/ticket/368a6a86-b0f5-4c36-a717-9da207f85b8a"
        ));

        applicationContext.close();
    }
}
