import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Teun on 25-4-2016.
 */
public class FlushTest {
    private EntityManager em;

    @Before
    public void before() throws SQLException {
        em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
    }

    @After
    public void after() throws SQLException {
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        dbc.clean();
    }

    @Test
    public void flushTest() {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        assertEquals(expected, account.getId());
        em.getTransaction().begin();
        em.persist(account);
        em.flush();
        em.getTransaction().commit();
        assertNotEquals(expected, account.getId());

    }

}
