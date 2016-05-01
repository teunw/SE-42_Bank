import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.sql.SQLException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by wouter on 25-4-2016.
 */
public class RollbackTest {

    private EntityManager em;

    @Before
    public void before() {
        em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        try {
            dbc.clean();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() throws SQLException {
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        dbc.clean();
    }
    @Test
    public void rollbackTest() {
        Account account = new Account(111L);// Makes a local new account.
        em.getTransaction().begin();
        em.persist(account);//Adds account to the database
        assertNull(account.getId());// Because the object has not been refreshed the id should still be null
        em.getTransaction().rollback();// Doet een rollback
        // TODO code om te testen dat table account geen records bevat.
        // Hint: bestudeer/gebruik AccountDAOJPAImpl
        AccountDAOJPAImpl impl = new AccountDAOJPAImpl(em);
        List<Account> allAccounts =  impl.findAll();
        assertEquals("There were accounts in the database.",allAccounts.size(),0);
    }

}
