package me.dausech.accbackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import me.dausech.accbackend.model.Account;
import me.dausech.accbackend.model.AccountRepository;
import me.dausech.accbackend.model.AccountType;
import me.dausech.accbackend.model.Transaction;
import me.dausech.accbackend.model.TransactionRepository;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes={AccbackendApplication.class})
public class DbTests {

	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private TransactionRepository trnRepo;
	
	@Test
	@WithMockUser(username="douglas",roles={"USER","ADMIN"})
	public void saveNewAccount() {
		Account account = new Account();
		account.setAccountNumber("1.0.0.1");
		account.setDescription("Assets");
		account.setAccountType(AccountType.Asset);
		
		createAccount(account);		
		assertThat(account.getId() != null);
	}
	
	public Account createAccount(Account account) {		
		accRepo.save(account);
		return account;
	}
	
	@Test
	@WithMockUser(username="douglas",roles={"USER","ADMIN"})
	public void saveNewTransaction() {
		Account acct = new Account();
		acct.setAccountNumber("1.0.0.10");
		acct.setDescription("Cash");
		acct.setAccountType(AccountType.Asset);		
				
		Account debitAccount = createAccount(acct);
		
		acct.setId(null);
		acct.setAccountNumber("3.1.1.1");
		acct.setDescription("Car Expenses");
		acct.setAccountType(AccountType.Expense);
		Account creditAccount = createAccount(acct);
		
		Transaction transaction = new Transaction();
		transaction.setDate(new Date());
		transaction.setDebitAccount(debitAccount);
		transaction.setCreditAccount(creditAccount);
		transaction.setValue(new BigDecimal("123.45"));
		trnRepo.save(transaction);
		assertThat(transaction.getId() != null);
	}

}
