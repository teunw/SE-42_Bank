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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertFalse;

/**
 * Created by wouter on 25-4-2016.
import static org.junit.Assert.*;

/**
 * Created by Teun on 25-4-2016.
 */
public class MergeTest {

    private EntityManager em;
    private EntityManagerFactory emf;

    @Before
    public void before() {
        emf = Persistence.createEntityManagerFactory("bankPU");
        em = emf.createEntityManager();
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
        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);

// scenario 1
        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.persist(acc);
        em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.


// scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a + balance2a);
        em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
// HINT: gebruik acccountDAO.findByAccountNr


// scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        em.persist(acc);
        acc2 = em.merge(acc);
        assertTrue(em.contains(acc)); // verklaar
        assertTrue(em.contains(acc2)); // verklaar
        assertEquals(acc, acc2);  //verklaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();
//TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
//TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.


// scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650l);
        assertEquals((Long) 650L, account2.getBalance());  //verklaar
        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);
        assertSame(account, account2);  //verklaar
        assertTrue(em.contains(account2));  //verklaar
        assertFalse(em.contains(tweedeAccountObject));  //verklaar
        tweedeAccountObject.setBalance(850l);
        assertEquals((Long) 650L, account.getBalance());  //verklaar
        assertEquals((Long) 650L, account2.getBalance());  //verklaar
        em.getTransaction().commit();
        em.close();

    }

}
//        em.getTransaction().begin();
//        Account acc = new Account();
//        Account acc2 = new Account();
//        Account acc3 = new Account();
//
//        Long balance1 = 100L;
//        em.persist(acc3);
//        em.persist(acc2);
//        em.persist(acc);
//        acc.setBalance(balance1);
//        em.getTransaction().commit();
//
//        assertEquals(acc.getId() + 1, (Object) acc2.getId());
//        assertEquals(acc.getId() + 2, (Object) acc3.getId());
