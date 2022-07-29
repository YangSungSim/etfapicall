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

@Controller
@RequiredArgsConstructor
public class StockController {

    private final StockInfoService stockInfoService;

    @GetMapping
    public String homeStock() {
        return "stock/stockhome";
    }

    //프론트엔드에서 값을 입력해서 가져오고 싶을때
    @GetMapping("/stock")
    public String stockInfo2(@RequestParam String id,
                             @RequestParam String fromDt,
                             @RequestParam String toDt,
                            Model model) throws IOException, ParseException {

        HashMap<String, String> RequestHash2 = new HashMap<String, String>();
        RequestHash2.put("ID", id);
        RequestHash2.put("FROM_DT", fromDt);
        RequestHash2.put("TO_DT", toDt);
        List<Stock> allData = stockInfoService.findStockInfo(RequestHash2);

        model.addAttribute("stock", allData);

        return "stock/showstock";
    }
}
