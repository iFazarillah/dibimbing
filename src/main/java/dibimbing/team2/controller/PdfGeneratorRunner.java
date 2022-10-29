package dibimbing.team2.controller;

//import dev.simplesolution.pdf.entity.Customer;
//import dev.simplesolution.pdf.entity.QuoteItem;
//import dev.simplesolution.pdf.service.PdfGenerateService;
import dibimbing.team2.model.Transaksi;
import dibimbing.team2.service.PdfGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfGeneratorRunner implements CommandLineRunner {

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> data = new HashMap<>();

        List<Transaksi> quoteItems = new ArrayList<>();
        Transaksi transaksi = new Transaksi();
        transaksi.setStatus(transaksi.getStatus());
        transaksi.setPembeli(transaksi.getPembeli());
        transaksi.setBarang(transaksi.getBarang());
        transaksi.setHarga(transaksi.getHarga());
        transaksi.setQty(transaksi.getQty());
        transaksi.setTotalHarga(transaksi.getTotalHarga());
        transaksi.setStatus(transaksi.getStatus());

        quoteItems.add(transaksi);

        data.put("quoteItems", quoteItems);

        pdfGenerateService.generatePdfFile("template", data, "quotation.pdf");
    }
}