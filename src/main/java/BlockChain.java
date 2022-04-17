import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockChain {
    private final Deque<Block> chain;

    public BlockChain() {
        this.chain = new LinkedList<>();
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return Block.builder()
                .index(0)
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
}
