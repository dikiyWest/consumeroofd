package kz.ruslan.consumer.oofd.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import kz.ruslan.consumer.oofd.domain.CashVoucher;
import kz.ruslan.consumer.oofd.domain.Purchase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ScannerCashVoucher {
    private final WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
    private final HtmlPage pageConsumer;
    private final CashVoucher cashVoucher;

    public ScannerCashVoucher(String url) throws IOException {
        setWebClientOptions();
        pageConsumer = webClient.getPage(url);
        cashVoucher = new CashVoucher();
    }



    private void setWebClientOptions(){
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.setCssErrorHandler(new SilentCssErrorHandler());
        webClient.waitForBackgroundJavaScript(7000);
    }

    public final CashVoucher getCashVoucher(){
        if(isValid()) {
            HtmlParagraph cashVoucherHeaderInfo1 = pageConsumer.getFirstByXPath("/html/body/app-root/block-ui/app-ticket/div/div/div/div[2]/div/div/div[2]/app-ticket-header/div/div/p[1]");
            HtmlParagraph cashVoucherHeaderInfo2 = pageConsumer.getFirstByXPath("/html/body/app-root/block-ui/app-ticket/div/div/div/div[2]/div/div/div[2]/app-ticket-header/div/div/p[2]");
            HtmlElement totalCost = pageConsumer.getFirstByXPath("/html/body/app-root/block-ui/app-ticket/div/div/div/div[2]/div/div/div[2]/div[3]/div[2]/h2");

            ArrayList<String> listParagraph = new ArrayList<String>();
            listParagraph.addAll(Arrays.asList(cashVoucherHeaderInfo1.asNormalizedText().split("\n")));
            listParagraph.addAll(Arrays.asList(cashVoucherHeaderInfo2.asNormalizedText().split("\n")));

            cashVoucher.setCompanyName(listParagraph.get(0));
            cashVoucher.setTotalSum(Double.valueOf(totalCost.getTextContent()
                    .replaceAll("₸", "")
                    .replaceAll(" ", "")
                    .replaceAll(",", ".")));
            for (String s : listParagraph) {
                if (s.contains("ИИН/БИН: ")) {
                    String iin = s.replaceAll("ИИН/БИН: ", "");
                    cashVoucher.setIIN(iin);
                } else if (s.contains("ФП: ")) {
                    String fp = s.replaceAll("ФП: ", "");
                    cashVoucher.setFP(fp);
                } else if (s.contains("Дата: ")) {
                    cashVoucher.setDate(s.replaceAll("Дата: ", "").replaceAll(" ", ""));
                }
            }
            setPurchases();
        }
        return new CashVoucher(cashVoucher);
    }

    private void setPurchases(){
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        int i = 2;
        while (true) {
            List<HtmlDivision> appitems = pageConsumer.getByXPath("/html/body/app-root/block-ui/app-ticket/div/div/div/div[2]/div/div/div[2]/app-ticket-items/div[" + i + "]/div/div");
            if (appitems.size() == 0) break;

            for (HtmlDivision domElement : appitems) {
                Purchase purchase = new Purchase();
                for (int j = 2; j <= 6; j++) {
                    if (j != 3) {
                        HtmlDivision position = domElement.getFirstByXPath("div[1]/div[" + j + "]");

                        if (position != null) {
                            switch (j) {
                                case 2:
                                    purchase.setName(position.getTextContent());
                                    break;
                                case 4:

                                    String cost = position.getTextContent()
                                            .replaceAll("₸", "")
                                            .replaceAll(" ", "")
                                            .replaceAll(",", ".");
                                    if (cost.length() > 0)
                                        purchase.setCost(Double.parseDouble(cost));
                                    break;
                                case 5:

                                    String count = position.getTextContent()
                                            .replaceAll("₸", "")
                                            .replaceAll(" ", "")
                                            .replaceAll(",", ".");
                                    if (count.length() > 0)
                                        purchase.setCount(Double.parseDouble(count));
                                    break;
                                case 6:
                                    String totalItemCost = position.getTextContent()
                                            .replaceAll("₸", "")
                                            .replaceAll(" ", "")
                                            .replaceAll(",", ".");
                                    if (totalItemCost.length() > 0)
                                        purchase.setTotalCost(Double.parseDouble(totalItemCost));
                                    break;
                            }
                        }
                    }
                }
                purchases.add(purchase);
            }
            i++;
        }
        cashVoucher.setItems(purchases);
    }

    private boolean isValid(){
        if(pageConsumer.getBaseURL().toString().contains("https://consumer.oofd.kz:443/"))
            return true;
        return false;

    }
}
