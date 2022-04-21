import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockChain {
    private final Deque<Block> chain;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public BlockChain() {
        this.chain = new LinkedList<>();
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return Block.builder()
                .index(0)
                .timeStamp(new Date())
                .data("GenesisBlock")
                .previousHash(null)
                .build();
    }

    public Block getLatestBlock() {
        return chain.peekLast();
    }

    public void addBlock(Block block) {
        block.setPreviousHash(chain.peekLast().getHash());
        chain.add(block);
    }

    public boolean isChainValid() {
        Iterator<Block> iterator = chain.iterator();

        Block previousBlock = iterator.next();
        Block block;
        while (iterator.hasNext()) {
            block = iterator.next();
            if (!Objects.equals(block.getPreviousHash(), previousBlock.getHash())) {
                return false;
            }
            previousBlock = block;
        }

        return true;
    }

    public String printChain() {
        return gson.toJson(chain);
    }

    public static BlockChain loadChain(String chainString) {
        Type blockList = new TypeToken<LinkedList<Block>>(){}.getType();
        Deque<Block> chain = new Gson().fromJson(chainString, blockList);

        return new BlockChain(chain);
    }
}
