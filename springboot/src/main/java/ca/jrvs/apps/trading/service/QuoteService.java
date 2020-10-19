package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + "is invalid"));
    }

    public List<Quote> updateMarketData() {
        List<Quote> quotes = findAllQuotes();
        for (Quote quote : quotes) {
            IexQuote iexQuote = findIexQuoteByTicker(String.valueOf(quote.getTicker()));
            quote = buildQuoteFromIexQuote(iexQuote);
        }
        saveQuotes(quotes);
        return quotes;
    }

    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setAskSize(iexQuote.getIexAskSize());
        quote.setAskPrice(iexQuote.getIexAskPrice());
        quote.setLastPrice(iexQuote.getLatestPrice());
        quote.setBidPrice(iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize());
        if (quote.getAskPrice() == null) {
            quote.setAskPrice(0.0);
        }
        if (quote.getAskSize() == null) {
            quote.setAskSize(0L);
        }
        if (quote.getBidPrice() == null) {
            quote.setBidPrice(0.0);
        }
        if (quote.getBidSize() == null) {
            quote.setBidSize(0L);
        }
        return quote;
    }

    public List<Quote> saveQuotes(List<Quote> quotes) {
        quoteDao.saveAll(quotes);
        return quotes;
    }

    public Quote saveQuote(String ticker) {
        Quote quote = buildQuoteFromIexQuote(findIexQuoteByTicker(ticker));
        return saveQuote(quote);
    }

    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    public List<Quote> findAllQuotes() {
        return (List<Quote>) quoteDao.findAll();
    }
}
