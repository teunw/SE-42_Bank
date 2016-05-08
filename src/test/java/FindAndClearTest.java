import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by Teun on 08/05/2016.
 */
public class FindAndClearTest {

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
    public void findAndClear() {
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();

        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertEquals(accF1, accF2);
        // De accounts zijn niet hetzelfde omdat de em cache leeggemaakt word, waardoor deze opnieuw de data ophaalt in plaats van het hetzelfde object te gebruiken
    }

}
