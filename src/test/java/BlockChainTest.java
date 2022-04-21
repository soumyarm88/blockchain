import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BlockChainTest {
    public static final String RESOURCES_PATH = "/Users/soumyarm/IdeaProjects/BlockChain/src/test/resources";

    @Test
    public void testNewChainHasGenesisBlockOnly() {
        BlockChain blockChain = new BlockChain();

        assertEquals("GenesisBlock", blockChain.getLatestBlock().getData());
        assertTrue(blockChain.isChainValid());
    }

    @Test
    public void testAddingFewBlocks() {
        String firstData = "First";
        String secondData = "Second";
        String thirdData = "Third";
        BlockChain blockChain = new BlockChain();

        // TimeUnit.SECONDS.sleep(2);
        Block previousBlock = blockChain.getLatestBlock();
        blockChain.addBlock(Block.builder().index(1).timeStamp(new Date()).data(firstData)
                .previousHash(previousBlock.getHash()).build());

        previousBlock = blockChain.getLatestBlock();
        blockChain.addBlock(Block.builder().index(2).timeStamp(new Date()).data(secondData)
                .previousHash(previousBlock.getHash()).build());

        previousBlock = blockChain.getLatestBlock();
        blockChain.addBlock(Block.builder().index(3).timeStamp(new Date()).data(thirdData)
                .previousHash(previousBlock.getHash()).build());

        assertEquals(thirdData, blockChain.getLatestBlock().getData());
        assertTrue(blockChain.isChainValid());

        // System.out.println(blockChain.printChain());
    }

    @Test
    public void checkOriginalChainIsValid() {
        BlockChain blockChain = loadChainFromStorage("SampleValidChain.js");
        assertTrue(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedChainIsInvalid() {
        BlockChain blockChain = loadChainFromStorage("InvalidChainWithMissingBlock.js");
        assertFalse(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedDataIsInvalid() {
        BlockChain blockChain = loadChainFromStorage("InvalidChainWithModifiedData.js");
        assertFalse(blockChain.isChainValid());
    }

    private BlockChain loadChainFromStorage(String fileName) {
        Path path = Path.of(RESOURCES_PATH,fileName);
        String serializedChain = null;
        try {
            serializedChain = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BlockChain.loadChain(serializedChain);
    }
}
