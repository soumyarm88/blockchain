import com.google.common.hash.Hashing;
import lombok.*;

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

    @Builder
    public Block(int index, Date timeStamp, String data, String previousHash) {
        this.index = index;
        this.timeStamp = timeStamp;
        this.data = data;
        this.previousHash = Optional.ofNullable(previousHash).orElse("");
        this.hash = calculateHash();
    }

    String calculateHash() {
        //System.out.println(this.index + this.getPreviousHash() + this.timeStamp.getTime() + this.data);

        return Hashing.sha256().hashString(
                this.index + this.getPreviousHash() + this.timeStamp.getTime() + this.data, StandardCharsets.UTF_8)
                .toString();
    }
}
