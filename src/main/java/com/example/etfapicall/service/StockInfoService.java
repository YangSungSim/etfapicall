package com.example.etfapicall.service;

import com.example.etfapicall.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StockInfoService {

    public List<Stock> findStockInfo(HashMap stockParam) throws IOException, ParseException {
        final String SYM = stockParam.get("ID").toString();

        Calendar cal = Calendar.getInstance();
        int YYYY = Integer.parseInt(stockParam.get("FROM_DT").toString().substring(0, 4));
        int mm =  Integer.parseInt(stockParam.get("FROM_DT").toString().substring(4, 6));
        int dd = Integer.parseInt(stockParam.get("FROM_DT").toString().substring(6, 8));
        cal.set(Calendar.YEAR,  YYYY);
        cal.set(Calendar.MONTH, mm-1  );
        cal.set(Calendar.DAY_OF_MONTH, dd);

        Date period1 = datechange(cal);


        Calendar cal1 = Calendar.getInstance();
        YYYY = Integer.parseInt(stockParam.get("TO_DT").toString().substring(0, 4));
        mm =  Integer.parseInt(stockParam.get("TO_DT").toString().substring(4, 6));
        dd = Integer.parseInt(stockParam.get("TO_DT").toString().substring(6, 8));

        cal1.set(Calendar.YEAR, YYYY);
        cal1.set(Calendar.MONTH, mm-1);
        cal1.set(Calendar.DAY_OF_MONTH,  dd);
        Date period2 = datechange(cal1);

        String interval="1d";
        long strDate = (period1.getTime());
        strDate= strDate/1000;

        long strDate1 = (period2.getTime());
        strDate1= strDate1/1000;

        String link="https://query1.finance.yahoo.com/v7/finance/download/"+SYM+"?period1="+strDate+"&period2="+strDate1+"&interval="+interval+"&events=history";

        URL url = new URL(link);
        URLConnection urlConn = url.openConnection();
        InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
        BufferedReader buf = new BufferedReader(inStream);


        ArrayList totalArray = new ArrayList();

        String line =buf.readLine();
        int count = 0;
        while(line != null) {

            if (count > 0) {

                LocalDate date = LocalDate.parse(line.split(",")[0], DateTimeFormatter.ISO_DATE);
                Double open = Double.parseDouble(line.split(",")[1]);
                Double high = Double.parseDouble(line.split(",")[2]);
                Double low = Double.parseDouble(line.split(",")[3]);
                Double close = Double.parseDouble(line.split(",")[4]);
                Double adjClose = Double.parseDouble(line.split(",")[5]);
                Double volume = Double.parseDouble(line.split(",")[6]);

                Stock collected = new Stock(date, open, high, low, close, adjClose, volume);
                totalArray.add(collected);
            }
            count++;

            line=buf.readLine();
        }

        return totalArray;

    }

    public static Date datechange(Calendar cal) throws ParseException {

        Date dateOne =cal.getTime();

        /*String a = dateOne.toString();

        String b[] = a.split(" ");
        String c = b[1]+" "+b[2]+" "+b[5];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd ",
                Locale.KOREA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setTime(sdf.parse(c));
        dateOne=cal.getTime();*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd ",
                Locale.KOREA);
        sdf.format(dateOne);

        return dateOne;

    }


}
