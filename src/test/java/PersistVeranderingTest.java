import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by wouter on 25-4-2016.
 */
public class PersistVeranderingTest {

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
        public void persistVeranderingTest() {
            Long expectedBalance = 400L;
            Account account = new Account(114L);
            em.getTransaction().begin();
            em.persist(account);
            account.setBalance(expectedBalance);
            em.getTransaction().commit();
            assertEquals(expectedBalance, account.getBalance());

            //TODO: verklaar de waarde van account.getBalance
            Long acId = account.getId();
            account = null;
            EntityManager em2 = emf.createEntityManager();
            em2.getTransaction().begin();
            Account found = em2.find(Account.class, acId);

            //TODO: verklaar de waarde van found.getBalance
            assertEquals(expectedBalance, found.getBalance());
        }

}
