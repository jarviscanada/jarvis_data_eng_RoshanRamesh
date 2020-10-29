package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TraderAccountService {

    private final TraderDao traderDao;
    private final AccountDao accountDao;
    private final PositionDao positionDao;
    private final SecurityOrderDao securityOrderDao;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
                                PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    public TraderAccountView createTraderAndAccount(Trader trader) {
        if (trader.getFirstName() == null || trader.getLastName() == null || trader.getDob() == null ||
                trader.getCountry() == null || trader.getEmail() == null) {
            throw new IllegalArgumentException("Trader cannot have null fields");
        }
        Trader savedTrader = traderDao.save(trader);

        Account account = new Account();
        account.setTraderId(trader.getId());
        account.setAmount(0.0);
        Account savedAccount = accountDao.save(account);

        return new TraderAccountView(savedTrader, savedAccount);
    }

    public void deleteTraderById(Integer traderId) {
        if (!traderDao.existsById(traderId)) {
        throw new IllegalArgumentException("TraderID could not be found : " + traderId);
    }

        Account traderAccount = accountDao.findById(traderId).get();
        List<Position> traderPositions = positionDao.findAllByAccountId(traderAccount.getId());
        if (traderAccount.getAmount() != 0.0) {
            throw new IllegalArgumentException("Trader Account cannot be deleted : " + traderId);
        } else {
            securityOrderDao.deleteById(traderAccount.getId());
            accountDao.deleteById(traderAccount.getId());
            traderDao.deleteById(traderId);
        }
    }

    public Account deposit(Integer traderId, Double fund) {
        if (traderId == null) {
            throw new IllegalArgumentException("Trader id cannot be null");
        }
        if (!traderDao.existsById(traderId)) {
            throw new IllegalArgumentException("Trader id " + traderId + "does not exist");
        }
        if (fund <= 0) {
            throw new IllegalArgumentException("Fund amount to be deposited must be greater than 0");
        }

        Account traderAccount = accountDao.findById(traderId).get();
        traderAccount.setAmount(traderAccount.getAmount() + fund);
        accountDao.updateOne(traderAccount);

        return accountDao.save(traderAccount);
    }

    public Account withdraw(Integer traderId, Double fund) {
        if (traderId == null) {
            throw new IllegalArgumentException("Trader id cannot be null");
        }
        if (!traderDao.existsById(traderId)) {
            throw new IllegalArgumentException("Trader id " + traderId + "does not exist");
        }
        if (fund <= 0) {
            throw new IllegalArgumentException("Fund amount to be deposited must be greater than 0");
        }

        Account traderAccount = accountDao.findById(traderId).get();
        traderAccount.setAmount(traderAccount.getAmount() - fund);
        accountDao.updateOne(traderAccount);

        return accountDao.save(traderAccount);
    }

}
