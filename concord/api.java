/*
  Authored by:
  James Krolik

  I wrote this while reverse engineering the API Key creation routine in the extra mile
  Add a new user to Concord and authenticate as the new user.

  Update with your user UUID from the creation step.  If a sample is needed, this UUID aligns with the program below.
  INSERT INTO USERS (user_id, username, is_admin, user_type, is_disabled) VALUES ('c303282d-f2e6-46ca-a04a-35d3d873712d', 'jim', 't', 'LOCAL', 'f');

*/

import java.util.Base64;
import java.util.Base64.Encoder;
import java.security.SecureRandom;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class api {

        public static void main(String args[])  {

                String userUUID = "c303282d-f2e6-46ca-a04a-35d3d873712d"; //Your user UUID
                UUID keyId = UUID.randomUUID();;
                String apiKey = "AnyKey";
//              byte[] ab = new byte[16];
                byte[] ab = apiKey.getBytes();
                SecureRandom rnd = new SecureRandom();
                String slash = "/";
//              rnd.nextBytes(ab);
//              System.out.println("Random Bytes => " + ab);

                Encoder e = Base64.getEncoder().withoutPadding();
                System.out.println("B64 encoded string => " + e.encodeToString(ab));

                MessageDigest md;

                try { 
                        md = MessageDigest.getInstance("SHA-256");
                }
                catch (NoSuchAlgorithmException f) {
                        throw new RuntimeException(f);
                }

                byte[] c = Base64.getDecoder().decode(ab);
                c = md.digest(c);
                String encodedKey = Base64.getEncoder().withoutPadding().encodeToString(c);

                System.out.println("SHA-256 => " + encodedKey);
                System.out.println("Final Insert Statement:");
                System.out.println("INSERT INTO api_keys (user_id, key_id, api_key, key_name) VALUES ('" + userUUID + "', '" + keyId + "', '" + encodedKey + "', 'key-1');\n");
                System.out.println("Remember to access the site at http:" + slash + slash + "concord:8001/#/login?useApiKey=true with your key: => " + apiKey);

        }
}
