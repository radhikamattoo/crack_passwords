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

  public static void mixCases(LinkedList<String> guesses) {
      String[] guess_array = guesses.toArray(new String[guesses.size()]);
      for(int i = 0; i < guess_array.length; i++){
        String s = guess_array[i];
        StringBuilder result = new StringBuilder(s);
        int idx = 0;
        boolean toUpper = true;
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                if (toUpper) {
                    c = Character.toUpperCase(c);
                }
                toUpper = !toUpper;
            }
            result.setCharAt(idx, c);
            idx++;
        } //char for loop
        guesses.add(result.toString());

        result = new StringBuilder(s);
        idx = 0;
        toUpper = false;
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                if (toUpper) {
                    c = Character.toUpperCase(c);
                }
                toUpper = !toUpper;
            }
            result.setCharAt(idx, c);
            idx++;
        } //char for loop
        guesses.add(result.toString());
      } //guesses for loop
  }

  public static void attack(LinkedList<Password> passwords, LinkedList<String> guesses){
    final int iterations = 1;
    final int keyLength = 256;
    for(Password p : passwords){
      if(p.getPlainText() != null) continue; // Already cracked it!
      for(String guess: guesses){
        char[] guess_char = guess.toCharArray();
        String encoded = Base64.getEncoder().encodeToString(hashPassword(guess_char, p.getSalt(), iterations, keyLength));
        if(p.getHashed().equals(encoded)){
          System.out.println("CRACKED PASSWORD FOR " + p.getUsername());
          System.out.println("Password:" + guess);
          System.out.println("Password length: " + guess.length());
          System.out.println("Original encryption: " + p.getHashed());
          System.out.println("Guess encryption: " + encoded );
          p.setPlainText(guess);
          break;
        }
      } // Guess for
    } // Password for
  }

  public static void buildNumbersAndSpecial(LinkedList<String> guesses){
    int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    String[] special = {"!", "#", "$", "%", "^", "&", "*", "(", ")", ":", ";", ".", ",", "/", "?", "\\", "{", "|", "}", "`", "~", "@", "<", ">", "[", "]", "+", "-", "_", "="};

    String[] guess_array = guesses.toArray(new String[guesses.size()]);
    for(int g = 0; g < guess_array.length; g++){
      String guess = guess_array[g];
      int length = guess.length();

      for(int i = 0; i < numbers.length; i++){
        for(int j = 0; j < numbers.length; j++){
          String number_1 = Integer.toString(numbers[i]);
          String number_2 = Integer.toString(numbers[i]) + Integer.toString(numbers[j]);
          guesses.add(guess + number_1);
          guesses.add(guess + number_2);
          guesses.add(guess + number_1 + number_2);
        }
      }
      // replace s with $
      String original_s = guess.replace("s", "$");
      guesses.add(original_s);
      original_s = guess.replace("S", "$");
      guesses.add(original_s);

      // replace a with @
      String original_a = guess.replace("a", "@");
      guesses.add(original_a);
      original_a = guess.replace("A", "@");
      guesses.add(original_a);

      // replace b with 8
      String original_b = guess.replace("b", "8");
      guesses.add(original_b);
      original_b = guess.replace("B", "8");
      guesses.add(original_b);

      //replace c with (
      String original_c = guess.replace("c", "(");
      guesses.add(original_c);
      original_c = guess.replace("C", "(");
      guesses.add(original_c);

      //replace d with 6
      String original_d = guess.replace("d", "6");
      guesses.add(original_d);
      original_d = guess.replace("D", "8");
      guesses.add(original_d);

      //replace e with 3
      String original_e = guess.replace("e", "3");
      guesses.add(original_e);
      original_e = guess.replace("E", "3");
      guesses.add(original_e);

      //replace h with #
      String original_h = guess.replace("h", "#");
      guesses.add(original_h);
      original_h = guess.replace("H", "#");
      guesses.add(original_h);

      //replace o with 0
      String original_o = guess.replace("o", "0");
      guesses.add(original_o);
      original_o = guess.replace("O", "0");
      guesses.add(original_o);

      //replace t with +
      String original_t = guess.replace("t", "+");
      guesses.add(original_t);
      original_t = guess.replace("T", "+");
      guesses.add(original_t);

      //replace f with #
      String original_f = guess.replace("f", "#");
      guesses.add(original_f);
      original_f = guess.replace("F", "#");
      guesses.add(original_f);

      //replace i with 1
      String original_i = guess.replace("i", "1");
      guesses.add(original_i);
      original_i = guess.replace("I", "1");
      guesses.add(original_i);

      //replace i with !
      String original_i_ex = guess.replace("i", "!");
      guesses.add(original_i_ex);
      original_i_ex = guess.replace("I", "!");
      guesses.add(original_i_ex);

      //replace l with 1
      String original_l = guess.replace("l", "1");
      guesses.add(original_l);
      original_l = guess.replace("L", "1");
      guesses.add(original_l);

      //replace l with 1
      String original_i_1 = guess.replace("l", "i");
      guesses.add(original_i_1);
      original_i_1 = guess.replace("L", "i");
      guesses.add(original_i_1);

      //replace q with 9
      String original_q = guess.replace("q", "9");
      guesses.add(original_q);
      original_q = guess.replace("Q", "9");
      guesses.add(original_q);

      //replace s with 5
      String original_s_5 = guess.replace("s", "5");
      guesses.add(original_s_5);
      original_s_5 = guess.replace("S", "5");
      guesses.add(original_s_5);

      //replace w with uu
      String original_w = guess.replace("w", "uu");
      guesses.add(original_w);
      original_w = guess.replace("W", "uu");
      guesses.add(original_w);

      //replace x with %
      String original_x = guess.replace("x", "%");
      guesses.add(original_x);
      original_x = guess.replace("X", "%");
      guesses.add(original_x);

      //replace y with ?
      String original_y = guess.replace("y", "?");
      guesses.add(original_y);
      original_y = guess.replace("Y", "?");
      guesses.add(original_y);

      // replace everything!
      String original_super = guess.replace("o", "0").replace("h", "#").replace("e", "3").replace("d", "6").replace("c", "(").replace("b", "8").replace("a", "@").replace("s", "$");
      guesses.add(original_super);
    }

  }

  public static void permutation(String prefix, String str, LinkedList<String> toAdd) {
      int n = str.length();
      if (n == 0) { toAdd.add(prefix); }
      else {
          for (int i = 0; i < n; i++){
              permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), toAdd);
            }
      }
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
    LinkedList<String> permutations = new LinkedList<String>();
    LinkedList<String> toAdd = new LinkedList<String>();

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
        guesses.add(p.getUsername());
        guesses.add(p.getUsername().toUpperCase());
        guesses.add(new StringBuilder(p.getUsername()).reverse().toString());
        guesses.add(new StringBuilder(p.getUsername()).reverse().toString().toUpperCase());

      }

      // Read in dictionary file
      filename = "guesses.txt";
      // filename = "bruteforcedb.txt";

      line = "";
      reader = new FileReader(filename);
      buff = new BufferedReader(reader);

      while((line = buff.readLine()) != null){
        String original = line;
        System.out.println("Word to check: " +  original);
        String reverse = new StringBuffer(original).reverse().toString();
        String no_vowels = original.replaceAll("[AEIOUaeiou]", "");
        guesses.add(original);
        guesses.add(reverse);
        guesses.add(no_vowels);


        Iterator it = guesses.iterator();
        while(it.hasNext()){
          String s = (String)it.next();

          permutation("", s, toAdd);

          Iterator it_add = toAdd.iterator();
          while(it_add.hasNext()){
            String perm = (String)it_add.next();
            if(perm.equals(s)) continue;
            permutations.add(perm);
          }
          toAdd.clear();
        }
        System.out.println("\t Finished permutations, building numbers and special characters");
        // FIXME
        mixCases(guesses);
        buildNumbersAndSpecial(guesses);

        System.out.println("\t Guesses size: " + guesses.size());
        System.out.println("\t Permutations size: " + permutations.size());
        System.out.println("\t Attacking passwords...");

        attack(passwords, guesses);
        attack(passwords, permutations);
        guesses.clear();
        permutations.clear();

      } //line for
      for(Password p : passwords){
        System.out.println(p.getUsername() + "::" + p.getPlainText());
      }
    }catch(FileNotFoundException e){
      System.out.println("Couldn't find file. Please make sure it is in the current directory");
    }catch(IOException e){
      System.out.println("IO Exception");
    }
  } // main

}
