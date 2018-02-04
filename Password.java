//
/*
 * Name: Radhika Mattoo
 * Description: Task 2, Password Cracking Program
*/
import java.util.*;
import java.io.*;
import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64.Decoder;

public class Password {
  String username;
  byte[] salt;
  int iterations;
  String hashed;
  String plainText;

  public Password(String username, byte[] salt, int iterations, String hashed){
    this.username = username;
    this.salt = salt;
    this.iterations = iterations;
    this.hashed = hashed;
  }

  public String getUsername(){
    return this.username;
  }

  public byte[] getSalt(){
    return this.salt;
  }

  public int getIterations(){
    return this.iterations;
  }

  public String getHashed(){
    return this.hashed;
  }

  public void setPlainText(String plainText){
    this.plainText = plainText;
  }

  public String getPlainText(){
    return this.plainText;
  }

  public String toString(){
    String p = "";
    p += "Username: " + this.getUsername() + "\n";
    p += "Salt: " + this.getSalt() + "\n";
    p += "Iterations: " + this.getIterations() + "\n";
    p += "Hashed Password: " + this.getHashed() + "\n";
    return p;
  }

  public static void attack(LinkedList<Password> passwords, LinkedList<String> guesses){
    final int iterations = 1;
    final int keyLength = 256;
    for(Password p : passwords){
      if(p.plainText != null) continue; // Already cracked it!
      for(String guess: guesses){
        char[] guess_char = guess.toCharArray();
        String encoded = Base64.getEncoder().encodeToString(hashPassword(guess_char, p.salt, iterations, keyLength));
        if(p.hashed.equals(encoded)){
          System.out.println("CRACKED PASSWORD FOR " + p.username);
          System.out.println("Password:" + guess);
          System.out.println("Password length: " + guess.length());
          System.out.println("Original encryption: " + p.hashed);
          System.out.println("Guess encryption: " + encoded );
          System.out.println();
          p.plainText = guess;
          break;
        }
      } // Guess for
    } // Password for
  }

  public static void changeCase(LinkedList<String> guesses){
    guesses.add(guesses.get(0).toUpperCase());
    guesses.add(guesses.get(1).toUpperCase());
    guesses.add(guesses.get(2).toUpperCase());
  }

  public static void buildNumbersAndSpecial(LinkedList<String> guesses){
    int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    String[] special = {"!", "#", "$", "%", "^", "&", "*", "(", ")", ":", ";", ".", ",", "/", "?", "\\", "{", "|", "}", "`", "~", "@", "<", ">", "[", "]", "+", "-", "_", "="};

    String original = guesses.get(0);
    String reverse = guesses.get(1);
    String no_vowels = guesses.get(2);

    for(int i = 0; i < numbers.length; i++){
      for(int j = 0; j < numbers.length; j++){
        for(int a = 0; a < numbers.length; a++){
          for(int b = 0; b < numbers.length; b++){
            String number_1 = Integer.toString(numbers[i]);
            String number_2 = Integer.toString(numbers[i]) + Integer.toString(numbers[j]);
            String number_3 = Integer.toString(numbers[i]) + Integer.toString(numbers[j]) +  Integer.toString(numbers[a]);
            String number_4 = Integer.toString(numbers[i]) + Integer.toString(numbers[j]) +  Integer.toString(numbers[a]) + Integer.toString(numbers[b]);

            guesses.add(original + number_1);
            guesses.add(original + number_2);
            guesses.add(original + number_3);
            guesses.add(original + number_4);

            guesses.add(reverse + number_1);
            guesses.add(reverse + number_2);
            guesses.add(reverse + number_3);
            guesses.add(reverse + number_4);

            guesses.add(no_vowels + number_1);
            guesses.add(no_vowels + number_2);
            guesses.add(no_vowels + number_3);
            guesses.add(no_vowels + number_4);

            guesses.add(number_1 + original);
            guesses.add(number_2 + original);
            guesses.add(number_3 + original);
            guesses.add(number_4 + original);

            guesses.add(number_1 + reverse);
            guesses.add(number_2 + reverse);
            guesses.add(number_3 + reverse);
            guesses.add(number_4 + reverse);

            guesses.add(number_1 + no_vowels);
            guesses.add(number_2 + no_vowels);
            guesses.add(number_3 + no_vowels);
            guesses.add(number_4 + no_vowels);
            // String original_num = original + number;
            // String reverse_num = reverse + number;
            // String no_vowels_num = no_vowels + number;
            // guesses.add(original_num);
            // guesses.add(reverse_num);
            // guesses.add(no_vowels_num);
          }
        }
      }
    }
    for(int i = 0; i < special.length; i++){
      guesses.add(original + special[i]);
      guesses.add(reverse + special[i]);
      guesses.add(no_vowels + special[i]);

      guesses.add(special[i] + original);
      guesses.add(special[i] + reverse);
      guesses.add(special[i] + no_vowels);
    }
    // replace s with $
    String original_s = original.replace("s", "$");
    String reverse_s = reverse.replace("s", "$");
    String no_vowel_s = no_vowels.replace("s", "$");

    guesses.add(original_s);
    guesses.add(reverse_s);
    guesses.add(no_vowel_s);

    // replace a with @
    String original_a = original.replace("a", "@");
    String reverse_a = reverse.replace("a", "@");
    String no_vowel_a = no_vowels.replace("a", "@");

    guesses.add(original_a);
    guesses.add(reverse_a);
    guesses.add(no_vowel_a);

    // replace b with 8
    String original_b = original.replace("b", "8");
    String reverse_b = reverse.replace("b", "8");
    String no_vowel_b = no_vowels.replace("b", "8");

    guesses.add(original_b);
    guesses.add(reverse_b);
    guesses.add(no_vowel_b);

    //replace c with (
    String original_c = original.replace("c", "(");
    String reverse_c = reverse.replace("c", "(");
    String no_vowel_c = no_vowels.replace("c", "(");

    guesses.add(original_c);
    guesses.add(reverse_c);
    guesses.add(no_vowel_c);

    //replace d with 6
    String original_d = original.replace("d", "6");
    String reverse_d = reverse.replace("d", "6");
    String no_vowel_d = no_vowels.replace("d", "6");

    guesses.add(original_d);
    guesses.add(reverse_d);
    guesses.add(no_vowel_d);

    //replace e with 3
    String original_e = original.replace("e", "3");
    String reverse_e = reverse.replace("e", "3");
    String no_vowel_e = no_vowels.replace("e", "3");

    guesses.add(original_e);
    guesses.add(reverse_e);
    guesses.add(no_vowel_e);

    //replace h with #
    String original_h = original.replace("h", "#");
    String reverse_h = reverse.replace("h", "#");
    String no_vowel_h = no_vowels.replace("h", "#");

    guesses.add(original_h);
    guesses.add(reverse_h);
    guesses.add(no_vowel_h);

    //replace o with 0
    String original_o = original.replace("o", "0");
    String reverse_o = reverse.replace("o", "0");
    String no_vowel_o = no_vowels.replace("o", "0");

    guesses.add(original_o);
    guesses.add(reverse_o);
    guesses.add(no_vowel_o);

    //replace t with +
    String original_t = original.replace("t", "+");
    String reverse_t = reverse.replace("t", "+");
    String no_vowel_t = no_vowels.replace("t", "+");

    guesses.add(original_t);
    guesses.add(reverse_t);
    guesses.add(no_vowel_t);
  }

  // Taken from: https://www.owasp.org/index.php/Hashing_Java
  public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength ) {
    try {
        SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
        PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
        SecretKey key = skf.generateSecret( spec );
        byte[] res = key.getEncoded( );
        return res;

    } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
        throw new RuntimeException( e );
    }
  }

  public static void main(String[] args){
    LinkedList<Password> passwords = new LinkedList<Password>();
    LinkedList<String> guesses = new LinkedList<String>();

    String filename = "pswd.txt";
    String line = "";
    try{
      // Read in Password objects
      FileReader reader = new FileReader(filename);
      BufferedReader buff = new BufferedReader(reader);
      line = buff.readLine(); // first line is informational
      while((line = buff.readLine()) != null){

        String[] split = line.replaceAll("\\s", "").split(":");

        String username = split[0];
        byte[] salt = Base64.getDecoder().decode(split[1]);
        int iterations = Integer.parseInt(split[2]);
        String hashed = split[3];
        // byte[] hashed = Base64.getDecoder().decode(split[3]);

        Password p = new Password(username, salt, iterations, hashed);
        passwords.add(p);
      }

      filename = "words.txt";
      line = "";
      // Read in dictionary file
      reader = new FileReader(filename);
      buff = new BufferedReader(reader);
      int count = 0;
      while((line = buff.readLine()) != null){
        count++;
        String original = line;
        String reverse = new StringBuffer(line).reverse().toString();
        String no_vowels = line.replaceAll("[AEIOUaeiou]", "");
        guesses.add(original);
        guesses.add(reverse);
        guesses.add(no_vowels);

        buildNumbersAndSpecial(guesses);
        changeCase(guesses);
        System.out.println("Number of guesses is: " + guesses.size());

        attack(passwords, guesses);
        System.out.println("Moving on to next word");
      } //line for
      for(Password p : passwords){
        System.out.println(p.username + "::" + p.plainText);
      }
    }catch(FileNotFoundException e){
      System.out.println("Couldn't find file. Please make sure it is in the current directory");
    }catch(IOException e){
      System.out.println("IO Exception");
    }
  } // main

}
