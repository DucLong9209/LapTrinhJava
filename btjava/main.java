package btjava;
import btjava.AESEncryption;

public class main {
    public static void main(String[] args) {
        try {
            AESEncryption aes = new AESEncryption();
            String original = "Xin chào!";
            String encrypted = aes.encrypt(original);
            String decrypted = aes.decrypt(encrypted);
            System.out.println("Gốc     : " + original);
            System.out.println("Mã hóa  : " + encrypted);
            System.out.println("Giải mã : " + decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


