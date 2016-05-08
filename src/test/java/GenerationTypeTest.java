import bank.dao.AccountDAOJPAImpl;
import bank.domain.Account;
import bank.domain.generationtyped.AccountGenerationTypeSequence;
import bank.domain.generationtyped.AccountGenerationTypeTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Teun on 08/05/2016.
 */
public class GenerationTypeTest {

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
    public void commitTest() {
        Account account = new Account();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Logger.getLogger("GenerationTypeTest").info(account.getId().toString());

        AccountGenerationTypeSequence accountGenerationTypeSequence = new AccountGenerationTypeSequence();
        em.getTransaction().begin();
        em.persist(accountGenerationTypeSequence);
        em.getTransaction().commit();
        Logger.getLogger("GenerationTypeTest").info(accountGenerationTypeSequence.getId().toString());

        AccountGenerationTypeTable accountGenerationTypeTable = new AccountGenerationTypeTable();
        em.getTransaction().begin();
        em.persist(accountGenerationTypeTable);
        em.getTransaction().commit();
        Logger.getLogger("GenerationTypeTest").info(accountGenerationTypeTable.getId().toString());

    }

}
