package jaboye2448;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void testProductGetter(){
        Product test = new Product("Mediocre Aluminum Knife white 9");

        assertEquals(84926, test.getQuantity());
    }
    @Test
    public void testUserGetter(){
        User test = new User("test user", "an address", "some address", 111111111111111111L);

        assertEquals("an address", test.getBillingAddress());
    }
}
