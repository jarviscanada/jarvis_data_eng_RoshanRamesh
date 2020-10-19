package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoIntTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    private Trader trader = new Trader();
    private Account account = new Account();


    @Before
    public void insert() {
        trader.setFirstName("James");
        trader.setLastName("McGill");
        trader.setCountry("Canada");
        trader.setDob(Date.valueOf(LocalDate.of(1997, 8, 10)));
        trader.setEmail("james.mcgill@gmail.com");
        traderDao.save(trader);
        account.setTraderId(1);
        account.setAmount(50d);
        accountDao.save(account);
    }

    @After
    public void remove() {
        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void findByIdTest() {
        assertEquals(Optional.of(account), accountDao.findById(1));
    }

    @Test
    public void existsByIdTest() {
        assertTrue(accountDao.existsById(1));
    }

}