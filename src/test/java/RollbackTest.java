import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.sql.SQLException;

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
    }

    @After
    public void after() throws SQLException {
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        dbc.clean();
    }
    @Test
    public void rollbackTest() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
        // TODO code om te testen dat table account geen records bevat. Hint: bestudeer/gebruik AccountDAOJPAImpl
    }

}
