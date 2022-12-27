package pt.ipleiria.estg.dei.ei.dae.gobs.security;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private static final Charset defaultCharset = Charset.defaultCharset();

    public String hash(String content) throws NoSuchAlgorithmException {
        ByteBuffer contentBuffer = defaultCharset.encode(content);
        byte[] contentBytes = contentBuffer.array();

        MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
        mdEnc.update(contentBytes);

        return new BigInteger(1, mdEnc.digest()).toString(16);
    }
}
