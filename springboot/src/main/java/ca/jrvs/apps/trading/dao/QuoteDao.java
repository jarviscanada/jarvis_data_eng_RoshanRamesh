package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    public static final String TABLE_NAME = "quote";
    public static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    /**
     * @throws DataAccessException for unexpected SQL result or SQL exception failure
     */
    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())) {
            int updateRowNo = updateOne(quote);
            if (updateRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    /**
     * helper method that saves one quote
     */
    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    /**
     * helper method that updates one quote
     */
    private int updateOne(Quote quote) {
        String update_sql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?," +
                " ask_price=?, ask_size=? WHERE ticker=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }

    private Object[] makeUpdateValues(Quote quote) {
        Object[] updateValues = new Object[6];
        updateValues[0] = quote.getLastPrice();
        updateValues[1] = quote.getBidPrice();
        updateValues[2] = quote.getBidSize();
        updateValues[3] = quote.getAskPrice();
        updateValues[4] = quote.getAskSize();
        updateValues[5] = quote.getTicker();
        return updateValues;
    }

   // @Override
   // public <S extends Quote> S save(S entity) {
   //     return null;
   // }

    @Override
    public <S extends Quote> Iterable<S> saveAll(Iterable<S> quotes) {
        quotes.forEach(quote -> save(quote));
        return quotes;
    }

    @Override
    public Optional<Quote> findById(String ticker) {
        Quote quote = null;
        String select = "SELECT * FROM" + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        try {
            quote = jdbcTemplate.queryForObject
                    (select, BeanPropertyRowMapper.newInstance(Quote.class), ticker);
        } catch (EmptyResultDataAccessException ex) {
            logger.debug("Can't find quote id:" + ticker, ex);
        }
        return Optional.of(quote);
    }

    @Override
    public boolean existsById(String ticker) {
        String select = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        boolean result = jdbcTemplate.queryForObject(select, Integer.class, ticker) > 0;
        return result;
    }

    @Override
    public Iterable<Quote> findAll() {
        String select = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(select, BeanPropertyRowMapper.newInstance(Quote.class));
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    }

    @Override
    public void deleteById(String id) {
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        jdbcTemplate.update(deleteSql, id);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.update(deleteSql);
    }
}
