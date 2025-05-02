package tech.soumyarm88.lib.blockchain;

import org.junit.jupiter.api.Test;
import tech.soumyarm88.lib.testutil.ResourceLoader;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockChainTest {
    public static final String RESOURCES_PATH = "resources";
    public static int DIFFICULTY = 5;

    @Test
    public void testNewChainHasGenesisBlockOnly() {
        BlockChain blockChain = new BlockChain(DIFFICULTY);

        assertEquals("GenesisBlock", blockChain.getLatestBlock().getData());
        assertTrue(blockChain.isChainValid());
    }

    @Test
    public void testAddingFewBlocks() {
        BlockChain blockChain = new BlockChain(DIFFICULTY);

        blockChain.addBlock(Block.builder().index(1).timeStamp(new Date()).data("First").build());
        blockChain.addBlock(Block.builder().index(2).timeStamp(new Date()).data("Second").build());
        blockChain.addBlock(Block.builder().index(3).timeStamp(new Date()).data("Third").build());

        assertTrue(blockChain.isChainValid());

        System.out.println(blockChain.printChain());
    }

    @Test
    public void checkOriginalChainIsValid() {
        BlockChain blockChain = loadChain("SampleValidChain.json");
        assertTrue(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedChainIsInvalid() {
        BlockChain blockChain = loadChain("InvalidChainWithMissingBlock.json");
        assertFalse(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedDataIsInvalid() {
        BlockChain blockChain = loadChain("InvalidChainWithModifiedData.json");
        assertFalse(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedDateIsInvalid() {
        BlockChain blockChain = loadChain("InvalidChainWithModifiedDate.json");
        assertFalse(blockChain.isChainValid());
    }

    private BlockChain loadChain(String fileName) {
        String serializedChain = ResourceLoader.load(fileName);
        return BlockChain.loadChain(serializedChain);
    }
}
