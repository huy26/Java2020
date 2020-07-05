package edu.pdx.cs410J.huy26;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall("caller", "callee", "callernumber", "calleeNumber", "starttime", "endtime");  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if(args.length==0) {
      System.err.println("Missing command line arguments");
    }
    else if (args.length==1){
      System.err.println("Missing callee name");
    }
    else if (args.length>=2) {
      Pattern pattern = Pattern.compile("^[0-9]\\d{3}-\\d{3}-\\d{4}$");
      Matcher matcher1 = pattern.matcher(args[2]);
      Matcher matcher2 = pattern.matcher(args[3]);
      if (matcher1.matches() == false || matcher2.matches() == false) {
        System.err.println("Phone Number must be formatted as nnn-nnn-nnnn");
      }
    }
    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }

}