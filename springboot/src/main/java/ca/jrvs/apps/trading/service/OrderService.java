package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dto.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final AccountDao accountDao;
    private final SecurityOrderDao securityOrderDao;
    private final QuoteDao quoteDao;
    private final PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderdao, QuoteDao quoteDao,
                        PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderdao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        if (orderDto.getSize() == 0 || orderDto.getTicker() == null) {
            throw new IllegalArgumentException("Null fields are invalid & Size>0");
        }

        Quote quote = quoteDao.findById(orderDto.getTicker()).orElseThrow(() ->
                new IllegalArgumentException("Unknown Trader account"));
        Account account = accountDao.findById(orderDto.getAccountId()).orElseThrow(() ->
                new IllegalArgumentException("Unknown accountId"));

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(orderDto.getAccountId());
        securityOrder.setSize(orderDto.getSize());
        securityOrder.setStatus("Starting");
        securityOrder.setTicker(orderDto.getTicker());

        if (orderDto.getSize() > 0) {
            securityOrder.setPrice(quote.getAskPrice());
            handleBuyMarketOrder(orderDto, securityOrder, account);
        } else {
            securityOrder.setPrice(quote.getBidPrice());
            handleSellMarketOrder(orderDto, securityOrder,
                    account);
        }
        return securityOrderDao.save(securityOrder);
    }

    protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
                                        Account account) {
        Double price = securityOrder.getPrice() * securityOrder.getSize();
        if (account.getAmount() >= price) {
            account.setAmount(account.getAmount() - price);

            accountDao.save(account);
            securityOrder.setStatus("FILLED");
            securityOrder
                    .setNotes(" security successfully bought @ " + price);
        } else {
            securityOrder.
                    setStatus("CANCELLED");
            securityOrder.setNotes("Insufficient balance");
        }
    }

    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
                                         Account account) {
        Quote quote = quoteDao.findById(marketOrderDto.getTicker()).get();
        securityOrder.setPrice(quote.getBidPrice());
        Position position = positionDao.findById(account.getId(), marketOrderDto.getTicker()).get();
        if (position.getPosition() - securityOrder.getSize() < 0) {
            securityOrder.setStatus("CANCELLED");
            securityOrder.setNotes("Insufficient position.");
        } else {
            securityOrder.setStatus("FILLED");
            account.setAmount(account.getAmount() + securityOrder.getSize() * securityOrder.getPrice());
            accountDao.save(account);
        }
        securityOrderDao.save(securityOrder);
    }

}
