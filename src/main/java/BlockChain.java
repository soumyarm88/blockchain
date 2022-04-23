import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockChain {
    private final Deque<Block> chain;
    private final int difficulty;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public BlockChain(int difficulty) {
        this.chain = new LinkedList<>();
        this.difficulty = difficulty;
        createGenesisBlock();
    }

    private void createGenesisBlock() {
        Block genesisBlock = Block.builder()
                .index(0)
                .timeStamp(new Date())
                .data("GenesisBlock")
                .previousHash(null)
                .build();

        genesisBlock.mineBlock(difficulty);
        chain.add(genesisBlock);
    }

    public Block getLatestBlock() {
        return chain.peekLast();
    }

    public void addBlock(Block block) {
        block.setPreviousHash(chain.peekLast().getHash());
        block.mineBlock(difficulty);
        chain.add(block);
    }

    public boolean isChainValid() {
        Iterator<Block> iterator = chain.iterator();

        Block previousBlock = iterator.next();
        Block block;
        while (iterator.hasNext()) {
            block = iterator.next();
            if (!StringUtils.equals(block.getPreviousHash(), previousBlock.getHash())) {
                return false;
            }
            if (!StringUtils.equals(block.calculateHash(), block.getHash())) {
                return false;
            }
            previousBlock = block;
        }

        return true;
    }

    public String printChain() {
        return gson.toJson(chain);
    }

    public static BlockChain loadChain(String chainString, int difficulty) {
        Type blockList = new TypeToken<LinkedList<Block>>(){}.getType();
        Deque<Block> chain = new Gson().fromJson(chainString, blockList);

        return new BlockChain(chain, difficulty);
    }
}
