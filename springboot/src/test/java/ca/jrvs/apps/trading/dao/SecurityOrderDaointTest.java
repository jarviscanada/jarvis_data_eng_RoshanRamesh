package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaointTest {

    private SecurityOrder securityOrder = new SecurityOrder();
    private Trader trader = new Trader();
    private Account account = new Account();
    private Quote quote = new Quote();

    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private QuoteDao quoteDao;


    @Before
    public void insert() {
        trader.setFirstName("James");
        trader.setLastName("McGill");
        trader.setCountry("Canada");
        trader.setEmail("james.mcgill@gmail.com");
        trader.setDob(Date.valueOf(LocalDate.of(1997, 8, 10)));
        traderDao.save(trader);
        account.setTraderId(1);
        account.setAmount(50.0);
        accountDao.save(account);

        quote.setAskPrice(8d);
        quote.setAskSize(8L);
        quote.setBidPrice(8.5d);
        quote.setBidSize(8L);
        quote.setId(("aapl"));
        quote.setLastPrice(7.5d);
        quoteDao.save(quote);

        securityOrder.setAccountId(1);
        securityOrder.setTicker("aapl");
        securityOrder.setStatus("Completed");
        securityOrder.setSize(10);
        securityOrder.setPrice(50d);
        securityOrder.setNotes("Notes");
        securityOrderDao.save(securityOrder);

    }

    @After
    public void remove() {
        securityOrderDao.deleteById(securityOrder.getId());

    }

    @Test
    public void findByIdTest() {
        assertEquals(Optional.of(securityOrder), securityOrderDao.findById(1));
    }

    @Test
    public void existsByIdTest() {
        assertTrue(securityOrderDao.existsById(1));
    }


    @Test
    public void countTest() {
        assertEquals(1, securityOrderDao.count());
    }


    @Test
    public void deleteAllTest() {
        securityOrderDao.deleteAll();
        assertEquals(0, securityOrderDao.count());
    }

}