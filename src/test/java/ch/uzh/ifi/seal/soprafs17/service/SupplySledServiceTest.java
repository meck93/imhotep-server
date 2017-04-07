package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.repository.SupplySledRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the SupplySledResource REST resource.
 *
 * @see SupplySledService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SupplySledServiceTest {

    @Autowired
    private SupplySledService supplySledService;

    @Autowired
    private SupplySledRepository supplySledRepository;

    public void createSupplySled() {
        // TODO: test supplySledService.createSupplySled()
        /* Player testPlayer = new Player();
        *  SupplySled testSupplySled = supplySledService.createSupplySled(testPlayer);
        *   Assert.assertEquals(testSupplySled, supplySledRepository.findOne(1L));
        */
    }

    @Test
    public void addStone() {
        //Stone testStone = new Stone();
        // TODO: test supplySledService.addStone()
    }
}
