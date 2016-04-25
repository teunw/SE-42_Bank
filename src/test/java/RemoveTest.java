import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by wouter on 25-4-2016.
 */
public class RemoveTest {
    private EntityManager em;
    private EntityManagerFactory emf;

    @Before
    public void before() {
        emf = Persistence.createEntityManagerFactory("bankPU");
        em = emf.createEntityManager();
    }

    @After
    public void after() throws SQLException {
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        dbc.clean();
    }
    @Test
    public void removeTest() {
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
//Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId());
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);
//TODO: verklaar bovenstaande asserts

    }

}
