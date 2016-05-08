package bank.domain.generationtyped;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "AccountTable")
@NamedQueries({
        @NamedQuery(name = "Account.getAll", query = "select a from Account as a"),
        @NamedQuery(name = "Account.count", query = "select count(a) from Account as a"),
        @NamedQuery(name = "Account.findByAccountNr", query = "select a from Account as a where a.accountNr = :accountNr")
})
@Table(name = "account", schema = "account")
public class AccountGenerationTypeTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Account")
    private Long Id;
    @Column(unique = true)
    private Long accountNr;
    private Long balance;
    private Long threshold;

    public AccountGenerationTypeTable() {
    }

    public AccountGenerationTypeTable(Long accountNr) {
        balance = 0L;
        threshold = 0L;
        this.accountNr = accountNr;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(Long accountNr) {
        this.accountNr = accountNr;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    public Boolean add(Long amount) {
        if (balance + amount >= threshold) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountGenerationTypeTable)) return false;

        AccountGenerationTypeTable account = (AccountGenerationTypeTable) o;

        if (getId() != null ? !getId().equals(account.getId()) : account.getId() != null) return false;
        if (getAccountNr() != null ? !getAccountNr().equals(account.getAccountNr()) : account.getAccountNr() != null)
            return false;
        if (getBalance() != null ? !getBalance().equals(account.getBalance()) : account.getBalance() != null)
            return false;
        return getThreshold() != null ? getThreshold().equals(account.getThreshold()) : account.getThreshold() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getAccountNr() != null ? getAccountNr().hashCode() : 0);
        result = 31 * result + (getBalance() != null ? getBalance().hashCode() : 0);
        result = 31 * result + (getThreshold() != null ? getThreshold().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Id=" + Id +
                ", accountNr=" + accountNr +
                ", balance=" + balance +
                ", threshold=" + threshold +
                '}';
    }
}
