import com.getontop.transactions.models.Transactions;
import com.getontop.transactions.models.enums.TransactionStatus;
import com.getontop.transactions.models.requestObject.TransactionRequest;
import com.getontop.transactions.repository.TransactionsRepository;
import com.getontop.transactions.service.TransactionsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) // Run the test with MockitoJUnitRunner
public class TransactionServiceTest {

    /*
    * In the first test case ( testSaveTransactionWithPositiveAmount ),
    * we are testing the scenario where the amount is positive. We create a  TransactionRequest
    * object with a positive amount, and then call the  saveTransaction  method.
    * We use the  verify  method from Mockito to ensure that the  saveAndFlush  method of the
    * transactionsRepository  is called exactly once.
    In the second test case ( testSaveTransactionWithNegativeAmount ),
    * we are testing the scenario where the amount is negative.
    * We create a  TransactionRequest  object with a negative amount, and then call the
    *   saveTransaction  method. Again, we use the  verify  method to ensure that the
    *   saveAndFlush  method is called exactly once. Additionally,
    * we use the  assertEquals  method to check that the transaction status is set to "FAILED" as expected.

        These test cases cover both positive and negative scenarios
        *  for the  saveTransaction  method, ensuring that it functions correctly.
    * */

    @Mock
    private TransactionsRepository transactionsRepository; // Mock the transactionsRepository


    @InjectMocks
    private TransactionsService transactionsService; // Inject the transactionsService with the mocked transactionsRepository

    @Before
    public void setUp() {
        // Set up any required mock objects or data before each test case
    }

    @Test
    public void testSaveTransactionWithPositiveAmount() {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        request.setAmount(100.0);
        request.setUserId("1");
        request.setTransactionType("TopUp");
        request.setFee("10");
        request.setTransactionStatus("SUCCESS");
        request.setAccountNumber("123456789");

        // Act
        transactionsService.saveTransaction(request);

        // Assert
        verify(transactionsRepository, times(1)).saveAndFlush(any(Transactions.class));
    }

    @Test
    public void testSaveTransactionWithNegativeAmount() {
        // Arrange
        TransactionRequest request = new TransactionRequest();
        request.setAmount(-100.0);
        request.setUserId("1");
        request.setTransactionType("Withdraw");
        request.setFee("0");
        request.setTransactionStatus("FAILED");
        request.setAccountNumber("123456789");

        // Act
        transactionsService.saveTransaction(request);

        // Assert
        verify(transactionsRepository, times(1)).saveAndFlush(any(Transactions.class));
        assertEquals(TransactionStatus.FAILED.getValue(), request.getTransactionStatus());
    }
}