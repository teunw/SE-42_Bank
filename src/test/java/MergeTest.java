import bank.dao.AccountDAO;
import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by wouter on 25-4-2016.
 * import static org.junit.Assert.*;
 * <p>
 * /**
 * Created by Teun on 25-4-2016.
 */
public class MergeTest {

    private EntityManager em;
    private EntityManagerFactory emf;

    private AccountDAOJPAImpl accountDAOJPA;

    @Before
    public void before() throws SQLException {
        emf = Persistence.createEntityManagerFactory("bankPU");
        em = emf.createEntityManager();

        DatabaseCleaner dbc = new DatabaseCleaner(em);
        dbc.clean();
        em = emf.createEntityManager();

        accountDAOJPA = new AccountDAOJPAImpl(em);

    }

    @After
    public void after() throws SQLException {
        em = emf.createEntityManager();
        DatabaseCleaner dbc = new DatabaseCleaner(em);
        dbc.clean();
    }

    @Test
    public void mergeTest() {
        AccountDAO accountDAO = new AccountDAOJPAImpl(em);
        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);

// scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1); //zet het balans van het lokale acc object
        em.persist(acc);          //zet het veranderde object in de orm
        em.getTransaction().commit();//zorgt ervoor dat de transactie ook echt doorgevoerd word
        assertEquals(acc.getBalance(), balance1);//kijkt of het balans overeen komt met die uit de database
        assertNotNull(accountDAO.find(acc.getId()));//kijkt of het object nog steeds in de database zit

// scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);//merged het account met die uit de database
        acc.setBalance(balance2a);//zet het balans van het acc object
        acc9.setBalance(balance2a + balance2a);//zet het balans van het acc9 object
        em.getTransaction().commit();
        assertEquals((Object) acc9.getBalance(), acc.getBalance() * 2);//kijkt of de veranderingen ook echt door zijn gevoerd


// scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        em.persist(acc);
        acc2 = em.merge(acc);
        assertTrue(em.contains(acc)); // Omdat account gemerged is met acc2 bevat de entity manager de originele waardes van acc
        assertTrue(em.contains(acc2)); // Account 2 is ook aanwezig in de entity manager, deze word gebruikt tijdens de merge
        assertEquals(acc, acc2);  // Door de merge zijn de twee objecten samengevoegd tot een object, dus zijn ze hetzelfde
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();
        assertEquals(acc.getId(), acc2.getId());


// scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650L);
        assertEquals((Long) 650L, account2.getBalance()); // De balance is van tevoren gezet op dit aantal
        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);
        assertSame(account, account2);  // Account 1 en 2 zijn gemerged, daarom zijn ze hetzelfde
        assertTrue(em.contains(account2));  // Door de merge is dit object in de entitymanager gezet
        assertFalse(em.contains(tweedeAccountObject));  // De samengevoegde objecten komen niet in de Entity Manager voor omdat hier nooit iets mee gebeurd is binnen de em.
        tweedeAccountObject.setBalance(850l);
        assertEquals((Long) 650L, account.getBalance());  // Door de merge is de balance hetzelfde
        assertEquals((Long) 650L, account2.getBalance());  // Door de merge is de balance hetzelfde
        em.getTransaction().commit();
        em.close();

    }

}