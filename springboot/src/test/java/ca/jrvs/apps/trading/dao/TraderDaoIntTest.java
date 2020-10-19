package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderDaoIntTest {

    @Autowired
    private TraderDao traderDao;

    private Trader savedTrader;

    @Before
    public void insertOne() {
        savedTrader = new Trader();
        savedTrader.setFirstName("James");
        savedTrader.setLastName("McGill");
        savedTrader.setDob(new Date(1997, 8, 10));
        savedTrader.setCountry("Canada");
        savedTrader.setEmail("james.mcgill@gmail.com");
        traderDao.save(savedTrader);
    }

    @After
    public void deleteOne() {
        traderDao.deleteAll();
    }

    @Test
    public void findAllById() {
        List<Trader> traders = Lists
                .newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId())));
        assertEquals(1, traders.size());
        assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
    }
}