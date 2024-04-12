package com.cleanhub.codingchallenge.repository.impl;

import com.cleanhub.codingchallenge.db.CustumerDB;
import com.cleanhub.codingchallenge.domain.OrderQuantityHistory;
import com.cleanhub.codingchallenge.repository.OrderHistoryRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository {

    private final CustumerDB custumerDB = CustumerDB.getInstance();

    @Override
    public void addCustomerOrder(String customerName, double quantity, LocalDate date) {
        custumerDB.addCustQuantity(customerName, quantity, date);
    }

    @Override
    public List<OrderQuantityHistory> getCustomerQuantityByDateGt(String startDate) {
        return custumerDB.getCustomerQuantityByDate(LocalDate.parse(startDate), "start_date");
    }

    @Override
    public List<OrderQuantityHistory> getCustomerQuantityByDateLt(String endDate) {
        return custumerDB.getCustomerQuantityByDate(LocalDate.parse(endDate), "end_date");
    }

    @Override
    public Map<String, TreeMap<LocalDate, Double>> getAllData() {
        return this.custumerDB.getAllData();
    }
}
