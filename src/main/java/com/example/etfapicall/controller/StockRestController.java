package com.example.etfapicall.controller;

import com.example.etfapicall.domain.Stock;
import com.example.etfapicall.service.StockInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockRestController {

    private final StockInfoService stockInfoService;

    //json 으로 return 받고자할때
    @GetMapping("/stock/{id}/{fromDt}/{toDt}")
    public List<Stock> stockInfo(@PathVariable("id") String id,
                            @PathVariable("fromDt") String fromDt,
                            @PathVariable("toDt") String toDt) throws IOException, ParseException {

        HashMap<String, String> RequestHash = new HashMap<String, String>();
        RequestHash.put("ID", id);
        RequestHash.put("FROM_DT", fromDt);
        RequestHash.put("TO_DT", toDt);
        List<Stock> allData = stockInfoService.findStockInfo(RequestHash);

        return allData;
    }
}
