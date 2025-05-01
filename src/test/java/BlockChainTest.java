import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BlockChainTest {
    public static final String RESOURCES_PATH = "/Users/soumyarm/IdeaProjects/BlockChain/src/test/resources";
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
        BlockChain blockChain = loadChainFromStorage("SampleValidChain.json");
        assertTrue(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedChainIsInvalid() {
        BlockChain blockChain = loadChainFromStorage("InvalidChainWithMissingBlock.json");
        assertFalse(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedDataIsInvalid() {
        BlockChain blockChain = loadChainFromStorage("InvalidChainWithModifiedData.json");
        assertFalse(blockChain.isChainValid());
    }

    @Test
    public void checkModifiedDateIsInvalid() {
        BlockChain blockChain = loadChainFromStorage("InvalidChainWithModifiedDate.json");
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
