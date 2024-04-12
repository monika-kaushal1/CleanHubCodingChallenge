package com.cleanhub.codingchallenge.db;


import com.cleanhub.codingchallenge.domain.OrderQuantityHistory;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CustumerDB {

    private static CustumerDB custumerDB;

    private Map<String, TreeMap<LocalDate, Double>> customerQuantityMap;

    private CustumerDB() {
        this.customerQuantityMap = new ConcurrentHashMap<>();
    }

    public static synchronized CustumerDB getInstance() {
        if (custumerDB == null) {
            custumerDB = new CustumerDB();
        }
        return custumerDB;
    }

    public synchronized void addCustQuantity(String companyName, double quantity, LocalDate date) {
        customerQuantityMap.computeIfAbsent(companyName, k -> new TreeMap<>(new DateComparator())).put(date != null ? date : LocalDate.now(), quantity);
    }

    public synchronized List<OrderQuantityHistory> getCustomerQuantityByDate(LocalDate date, String dateKind) {
        Set<String> customerNames = customerQuantityMap.keySet();
        List<OrderQuantityHistory> orderQuantityHistoryList = new ArrayList<>();
        for (String customerName : customerNames) {
            OrderQuantityHistory orderQuantityHistory = new OrderQuantityHistory();
            TreeMap<LocalDate, Double> treeMap = customerQuantityMap.get(customerName);

            Map.Entry<LocalDate, Double> entry = dateKind.equals("start_date") ? treeMap.ceilingEntry(date) : treeMap.floorEntry(date);

            if (entry != null) {
                orderQuantityHistory.setQuantity(entry.getValue());
                orderQuantityHistory.setCustomerName(customerName);
                orderQuantityHistory.setDate(entry.getKey());

                orderQuantityHistoryList.add(orderQuantityHistory);
            }
        }
        return orderQuantityHistoryList;
    }

    public Map<String, TreeMap<LocalDate, Double>> getAllData() {
        return this.customerQuantityMap;
    }

    private static class DateComparator implements Comparator<LocalDate> {
        @Override
        public int compare(LocalDate o1, LocalDate o2) {
            return o1.compareTo(o2);
        }
    }
}
