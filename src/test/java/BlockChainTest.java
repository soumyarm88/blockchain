import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockChainTest {
    @Test
    public void testNewChainHasGenesisBlockOnly() {
        BlockChain blockChain = new BlockChain();
        assertEquals("GenesisBlock", blockChain.getLatestBlock().getData());
        assertTrue(blockChain.isChainValid());
    }

    @Test
    public void testAddingFewBlocks() {
        String firstData = "First piece of data!!";
        String secondData = "I was just a bit late!!";
        BlockChain blockChain = new BlockChain();

        blockChain.addBlock(Block.builder().index(1).data(firstData).build());
        assertEquals(firstData, blockChain.getLatestBlock().getData());

        blockChain.addBlock(Block.builder().index(2).data(secondData).build());
        assertEquals(secondData, blockChain.getLatestBlock().getData());

        assertTrue(blockChain.isChainValid());
    }
}
