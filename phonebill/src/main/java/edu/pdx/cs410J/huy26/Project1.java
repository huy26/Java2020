package edu.pdx.cs410J.huy26;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {


  public static void main(String[] args) {
    // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if (args.length == 0) {
      System.err.println("Missing command line arguments");
    } else if (args.length == 1) {
      System.err.println("Missing callee name");
    } else if (args.length >= 2&& args.length<=6) {
      Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}$");
      Matcher matcher1 = phonePattern.matcher(args[2]);
      Matcher matcher2 = phonePattern.matcher(args[3]);
      if (matcher1.matches() == false || matcher2.matches() == false) {
        System.err.println("Phone Number must be formatted as nnn-nnn-nnnn");
      }
    } else if (args.length > 6) {
      Pattern datePattern = Pattern.compile("[1-12]\\d{1,2}/[1-31]\\d{1,2}/\\d{4}$");
      Matcher matcher3 = datePattern.matcher(args[4]);
      Matcher matcher4 = datePattern.matcher(args[6]);
      if (matcher3.matches() == false || matcher4.matches() == false) {
        System.err.println("Date is invalid. Date must be formatted as mm/dd/yyyy");
      }

      Pattern timePattern = Pattern.compile("[0-23]\\d{1,2}:[0-59]\\d{1,2}$");
      Matcher matcher5 = timePattern.matcher(args[5]);
      Matcher matcher6 = timePattern.matcher(args[7]);
      if (matcher5.matches() == false || matcher6.matches() == false) {
        System.err.println("Time is invalid. Time must be formatted as hh:mm");
      }
    }

    //PhoneCall call = new PhoneCall(args[0], args[1], args[2], args[3], args[4] +" "+ args[5], args[6]+" " +args[7]);
    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }



}