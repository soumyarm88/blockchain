import com.google.common.hash.Hashing;
import lombok.Builder;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Data
public class Block {
    private final int index;
    private final Instant timeStamp;
    private final String data;
    private final String hash;
    private String previousHash;

    @Builder
    public Block(int index, String data, String previousHash) {
        this.index = index;
        this.timeStamp = Instant.now();
        this.data = data;
        this.hash = calculateHash();
        this.previousHash = previousHash;
    }

    String calculateHash() {
        return Hashing.sha256()
                .hashString(this.index + this.timeStamp.toEpochMilli() + this.data, StandardCharsets.UTF_8)
                .toString();
    }
}
