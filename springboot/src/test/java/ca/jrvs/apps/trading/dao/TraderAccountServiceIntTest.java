package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderAccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

    private TraderAccountView savedView;

    @Autowired
    private TraderAccountService traderAccountService;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private AccountDao accountDao;

    @Before
    public void addOne() {
        Trader savedTrader = new Trader();
        savedTrader.setFirstName("James");
        savedTrader.setLastName("Mcgill");
        savedTrader.setDob(new Date(1997, 10, 8));
        savedTrader.setCountry("Canada");
        savedTrader.setEmail("james.mcgill@gmail.ca");
        savedView = traderAccountService.createTraderAndAccount(savedTrader);
    }

    @After
    public void deleteOne() {
        accountDao.deleteById(savedView.getAccount().getId());
        traderDao.deleteById(savedView.getTrader().getId());
    }

    @Test
    public void deleteTraderById() {
        try {
            traderAccountService.deleteTraderById(savedView.getTrader().getId());
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }

        try {
            traderAccountService.deleteTraderById(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deposit() {
        traderAccountService.deposit(savedView.getTrader().getId(), 50.0);
        assertEquals((Double) 50.0,
                accountDao.findById(savedView.getAccount().getId()).get().getAmount());
        try {
            traderAccountService.deposit(null, 50.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            traderAccountService.deposit(savedView.getTrader().getId(), 0.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            traderAccountService.deposit(-50, 50.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void withdraw() {
        traderAccountService.withdraw(savedView.getTrader().getId(), 50.0);
        assertEquals((Double) (-50.0),
                accountDao.findById(savedView.getAccount().getId()).get().getAmount());
        try {
            traderAccountService.withdraw(null, 50.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            traderAccountService.withdraw(savedView.getTrader().getId(), 0.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            traderAccountService.withdraw(-50, 50.0);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}