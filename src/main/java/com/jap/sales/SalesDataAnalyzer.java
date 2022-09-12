package com.jap.sales;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SalesDataAnalyzer {
    public List<SalesRecord> readFile(String fileName) {
        List<SalesRecord> salesRecordList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                String[] strings = line.split(",");
                String date = strings[0];
                int customerId = Integer.parseInt(strings[1]);
                int productCategory = Integer.parseInt(strings[2]);
                String paymentMethod = strings[3];
                double amount = Double.parseDouble(strings[4]);
                double timeOnSite = Double.parseDouble(strings[5]);
                int clicksInSite = Integer.parseInt(strings[6]);

                salesRecordList.add(new SalesRecord(date,customerId,productCategory,paymentMethod,amount,timeOnSite,clicksInSite));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salesRecordList;
    }
    public List<SalesRecord> getAllCustomersSortedByPurchaseAmount(List<SalesRecord> salesData) {
        salesData.sort(((o1, o2) -> (int) (o2.getAmount() -o1.getAmount())));
        return salesData;
    }
    public SalesRecord getTopCustomerWhoSpentMaxTimeOnSite(List<SalesRecord> salesData){
        salesData.sort((o1, o2) -> (int) (o2.getTime_on_site() - o1.getTime_on_site()));
        return salesData.get(0);
    }

    public static void main(String[] args) {
        SalesDataAnalyzer salesDataAnalyzer = new SalesDataAnalyzer();
        List<SalesRecord> salesRecordList = salesDataAnalyzer.readFile("src/main/resources/purchase_details.csv");
        for (SalesRecord salesRecord : salesRecordList) {
            System.out.println(salesRecord);
        }
        System.out.println("\n ++++++++++++++++++++++++++++++++++++++++ \n");
        List<SalesRecord> salesRecordList1 = salesDataAnalyzer.getAllCustomersSortedByPurchaseAmount(salesRecordList);
        for (SalesRecord salesRecord : salesRecordList1) {
            System.out.println(salesRecord);
        }
        System.out.println("\n ++++++++++++++++++++++++++++++++++++++++ \n");
        SalesRecord salesRecordList2 = salesDataAnalyzer.getTopCustomerWhoSpentMaxTimeOnSite(salesRecordList1);
        System.out.println(salesRecordList2);
    }

}