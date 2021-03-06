package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract public String getIdColumnName();

    abstract Class<T> getEntityClass();

    /**
     * Save an entity and update auto-generated integer ID
     *
     * @param entity to be saved
     * @return saved entity
     */
    @Override
    public <S extends T> S save(S entity) {
        if (existsById(entity.getId())) {
            if (updateOne(entity) != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(entity);
        }
        return entity;
    }

    private <S extends T> void addOne(S entity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(newId.intValue());
    }

    abstract public int updateOne(T entity);

    @Override
    public Optional<T> findById(Integer id) {
        Optional<T> entity = Optional.empty();
        String select = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ?";
        try {
            entity = Optional.ofNullable(getJdbcTemplate().queryForObject(select,
                    BeanPropertyRowMapper.newInstance(getEntityClass()), id));
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find trader id: " + id, e);
        }
        return entity;
    }

    @Override
    public boolean existsById(Integer id) {
        T count = null;
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        try {
            count = getJdbcTemplate()
                    .queryForObject(selectSql,
                            BeanPropertyRowMapper.newInstance(getEntityClass()), id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find entity id:" + id, e);
        }
        return count != null;
    }

    @Override
    public List<T> findAll() {
        String select = "SELECT * FROM " + getTableName();
        return getJdbcTemplate()
                .query(select, BeanPropertyRowMapper.newInstance(getEntityClass()));
    }

    @Override
    public List<T> findAllById(Iterable<Integer> ids) {
        List<T> allVal = new ArrayList<>();
        ids.forEach(element -> allVal.add(findById(element).get()));
        return allVal;
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid ID");
        }
        String delete = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        getJdbcTemplate().update(delete, id);
    }

    @Override
    public long count() {
        String countSql = "SELECT COUNT(*) FROM " + getTableName();
        return getJdbcTemplate().queryForObject(countSql, long.class);
    }

    @Override
    public void deleteAll() {
        String delete = "DELETE FROM " + getTableName();
        getJdbcTemplate().update(delete);
    }

}
