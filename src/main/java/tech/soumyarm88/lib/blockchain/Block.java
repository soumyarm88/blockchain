package tech.soumyarm88.lib.blockchain;

import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Block {
    private int index;
    private Date timeStamp;
    private String data;
    private String hash;
    private String previousHash;
    private int nonce;

    @Builder
    public Block(int index, Date timeStamp, String data, String previousHash) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.data = data;
        this.previousHash = Optional.ofNullable(previousHash).orElse("");
        this.nonce = 0;
    }

    String calculateHash() {
        return Hashing.sha256().hashString(
                this.index + this.previousHash + this.timeStamp.toString() + this.data + this.nonce, StandardCharsets.UTF_8)
                .toString();
    }

    void mineBlock(int difficulty) {
        String prefix = "0".repeat(difficulty);
        while (StringUtils.isNotBlank(prefix) && !StringUtils.startsWith(this.hash, prefix)) {
            this.nonce++;
            this.hash = calculateHash();
        }
    }
}
