package tech.soumyarm88.lib.blockchain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest {
    @Test
    public void testBlock() {
        Block block = Block.builder()
                .index(1)
                .timeStamp(new Date(1000))
                .data("TestData")
                .previousHash("TestPreviousHash")
                .build();

        block.mineBlock(0);

        assertEquals(1, block.getIndex());
        assertEquals("Wed Dec 31 16:00:01 PST 1969", block.getTimeStamp().toString());
        assertEquals("TestData", block.getData());
        assertEquals("TestPreviousHash", block.getPreviousHash());
        assertEquals(0, block.getNonce());
        assertEquals("cf6953b645cb6ce0219011765ac25843526b4204b13b40552ee599b36f1c16a0", block.calculateHash());

        block.mineBlock(2);

        assertEquals(1, block.getIndex());
        assertEquals("Wed Dec 31 16:00:01 PST 1969", block.getTimeStamp().toString());
        assertEquals("TestData", block.getData());
        assertEquals("TestPreviousHash", block.getPreviousHash());
        assertEquals(147, block.getNonce());
        assertEquals("00713fb20ca2dc4780af708224e2a5f70c7de74014faef048903461aadb5e122", block.calculateHash());
    }
}
