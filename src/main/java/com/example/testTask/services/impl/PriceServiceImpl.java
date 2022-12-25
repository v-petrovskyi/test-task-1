package com.example.testTask.services.impl;

import com.example.testTask.entity.Price;
import com.example.testTask.exeptions.CurrencyException;
import com.example.testTask.repositories.PriceRepository;
import com.example.testTask.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository repository;

    @Autowired
    public PriceServiceImpl(PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Price add(Price price) {
        return repository.save(price);
    }

    @Override
    public Price getMin(String currency) throws CurrencyException {
        if (currency.equals("BTC/USD") || currency.equals("ETH/USD") || currency.equals("XRP/USD")) {
            String[] split = currency.split("/");
            return repository.getFirstByCurr1AndCurr2OrderByLpriceAsc(split[0], split[1]);
        } else {
            throw new CurrencyException("currency " + currency + " not exist");
        }
    }

    @Override
    public Price getMax(String currency) throws CurrencyException {
        if (currency.equals("BTC/USD") || currency.equals("ETH/USD") || currency.equals("XRP/USD")) {
        String[] split = currency.split("/");
        return repository.getFirstByCurr1AndCurr2OrderByLpriceDesc(split[0], split[1]);
        } else {
            throw new CurrencyException("currency " + currency + " not exist");
        }
    }

    @Override
    public List<Price> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Price> getSelectedPageOfCurrencies(String currency, int page, int size) {
        String[] split = currency.split("/");
        List<Price> priceList = repository.getPriceByCurr1AndCurr2OrderByLpriceAsc(split[0], split[1]);
        int fromIndex = (page * size) - size;
        int toIndex = fromIndex + size;
        return priceList.subList(fromIndex, toIndex);
    }
}
