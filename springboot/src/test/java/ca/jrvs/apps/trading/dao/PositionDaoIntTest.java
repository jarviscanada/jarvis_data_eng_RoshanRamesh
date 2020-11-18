package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class PositionDaoIntTest {

    private SecurityOrder savedSecurityOrder;
    private Quote savedQuote;
    private Account savedAccount;
    private Trader savedTrader;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private QuoteDao quoteDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Test
    public void findById() {
        Position position = positionDao
                .findById(savedSecurityOrder.getAccountId(), savedSecurityOrder.getTicker()).get();
        assertEquals(savedSecurityOrder.getSize(), position.getPosition());
    }

    @Test
    public void findAll() {
        List<Position> testList = positionDao.findAll();
        assertEquals(savedSecurityOrder.getSize(), testList.get(0).getPosition());
    }

    @Test
    public void findAllByAccountId() {
        List<Position> testList = positionDao.findAllByAccountId(savedAccount.getId());
        assertEquals(savedSecurityOrder.getSize(), testList.get(0).getPosition());
    }

    @Test
    public void count() {
        assertEquals(1, positionDao.count());
    }
}