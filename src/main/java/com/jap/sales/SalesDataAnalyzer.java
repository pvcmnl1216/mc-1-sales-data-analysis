package com.jap.sales;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesDataAnalyzer {
    public static void main(String[] args) {
        SalesDataAnalyzer salesDataAnalyzer = new SalesDataAnalyzer();
        List<SalesRecord> salesRecordList = salesDataAnalyzer.readFile("src/main/resources/purchase_details.csv");
        for (SalesRecord salesRecord : salesRecordList) {
            System.out.println("salesRecord = " + salesRecord);
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

    public List<SalesRecord> readFile(String fileName) {
        List<SalesRecord> salesRecordList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] String = line.split(" ");
                String date = String[0];
                int customerID = Integer.parseInt(String[1]);
                int productCategory = Integer.parseInt(String[2]);
                String paymentMethod = String[3];
                double amount = Double.parseDouble(String[4]);
                double timeOnSite = Double.parseDouble(String[5]);
                int clicksInSite = Integer.parseInt(String[6]);

                salesRecordList.add(new SalesRecord(date, customerID, productCategory, paymentMethod, amount, timeOnSite, clicksInSite));

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return salesRecordList;
    }

    public List<SalesRecord> getAllCustomersSortedByPurchaseAmount(List<SalesRecord> salesData) {
        salesData.sort((o1, o2) -> (int) (o2.getAmount() - o1.getAmount()));
        return salesData;
    }

    public SalesRecord getTopCustomerWhoSpentMaxTimeOnSite(List<SalesRecord> salesData) {
        salesData.sort((o1, o2) -> (int) (o2.getTime_on_site() - o1.getTime_on_site()));
        return salesData.get(0);
    }
}

