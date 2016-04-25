import bank.domain.Account;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Teun on 25-4-2016.
 */
public class PersistCommitTest {

    private EntityManager em;

    @Before
    public void before() throws SQLException {
        em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
        DatabaseCleaner dc = new DatabaseCleaner(em);
        dc.clean();
        em = Persistence.createEntityManagerFactory("bankPU").createEntityManager();
    }

    @Test
    public void commitTest() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);

        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());

        assertTrue(account.getId() > 0L);

    }


}
