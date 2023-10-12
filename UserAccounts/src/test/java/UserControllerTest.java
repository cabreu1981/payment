import com.getontop.clients.TransactionsHistory.TransactionsClient;
import com.getontop.clients.TransactionsHistory.models.Transactions;

import com.getontop.clients.TransactionsHistory.models.enums.TransactionStatus;
import com.getontop.clients.TransactionsHistory.models.enums.TransactionType;
import com.getontop.useraccounts.controllers.UserController;

import com.getontop.useraccounts.models.User;
import com.getontop.useraccounts.models.UserWithTransactions;
import com.getontop.useraccounts.repository.UserRepository;
import com.getontop.useraccounts.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionsClient transactionsClient;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private UserService userService;

    private User user;
    private Transactions transaction;

    @Before
    public void setup() {
        user = new User(1, "name", "surName", "nationalId", "bankName", "accountNumber", "routingNumber");
        transaction = new Transactions(
                1, 2, 100.0, "1234567890", 15.,
                TransactionType.TopUp,
                TransactionStatus.SUCCESS,
                LocalDateTime.now());
    }

    @Test
    public void testGetUserByAccountNumber_Positive() {
        List<Transactions> transactions = new ArrayList<>();
        transactions.add(transaction);

        when(userRepository.findUserByaccountNumber("1234567890")).thenReturn(Optional.of(user));
        when(transactionsClient.getTransactionsByAccount("1234567890")).thenReturn(transactions);

        ResponseEntity responseEntity = userService.getUserByAccountNumber("1234567890");

        assertEquals(ResponseEntity.ok(new UserWithTransactions(user, transactions)), responseEntity);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserByAccountNumber_UserNotFound() {
        when(userRepository.findUserByaccountNumber("1234567890")).thenReturn(Optional.empty());

        userController.getUserByAccountNumber("1234567890");
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserByAccountNumber_TransactionsNotFound() {
        when(userRepository.findUserByaccountNumber("1234567890")).thenReturn(Optional.of(user));
        when(transactionsClient.getTransactionsByAccount("1234567890")).thenReturn(null);

        userController.getUserByAccountNumber("1234567890");
    }
}