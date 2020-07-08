package edu.pdx.cs410J.huy26;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {


  /**
   * Main program that parses the command line, creates a
   * <code>PhoneCall</code>, and prints a description of the new phone call to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) throws IOException {
    // Refer to one of Dave's classes so that we can be sure it is on the classpath
    //args = new String[]{"-README","Dave", "123-456-7897", "123-456-7845", "11/05/2020", "05:30", "11/05/2020", "05:37"};

    boolean print = false;
    int firstArgPos = 0;
    if(args.length==0){
      printErrorMessageAndExit("Missing command line arguments");
    }
    if (args.length > 0) {
      if (args[0] == "-README") {
        InputStream readme = Project1.class.getResourceAsStream("README.txt");
        printREADME(readme);
      }
      if (args[0] == "-print") {
        print = true;
        if(args.length>1)
          firstArgPos =1;
        else {
          printErrorMessageAndExit("Missing argument");
        }
      }

    }
    if (args.length > 1) {
      if (args[1] == "-README") {
        InputStream readme = Project1.class.getResourceAsStream("README.txt");
        printREADME(readme);
      } else if (args.length < 7) {
        printErrorMessageAndExit("Missing argument");
      }
      if (args[1] == "-print") {
        print = true;
        if(args.length>1)
          firstArgPos =2;
        else {
          printErrorMessageAndExit("Missing argument");
        }
      }
    }
    if(firstArgPos +6>=args.length){
      printErrorMessageAndExit("Missing argument");
    }
    for (int i = firstArgPos; i < args.length; i++) {
      if (i==firstArgPos+1 || i==firstArgPos+2) {
        Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}$");
        Matcher matcher1 = phonePattern.matcher(args[i]);
        if (matcher1.matches() == false) {
          printErrorMessageAndExit("Phone Number must be formatted as nnn-nnn-nnnn");
        }
      } else if (i==firstArgPos+3 || i==firstArgPos+5) {
        Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}$");
        Matcher matcher2 = datePattern.matcher(args[i]);
        if (matcher2.matches() == false) {
          String message = "Date is invalid. Date must be formatted as mm/dd/yyyy";
          printErrorMessageAndExit(message);
        }
      }else if(i==firstArgPos+4 || i==firstArgPos+6){
        Pattern timePattern = Pattern.compile("\\d{1,2}:\\d{1,2}$");
        Matcher matcher3 = timePattern.matcher(args[i]);
        if (matcher3.matches() == false ) {
          printErrorMessageAndExit("Time is invalid. Time must be formatted as hh:mm");
        }
      }
    }
    PhoneCall call = new PhoneCall(args[firstArgPos+1],args[firstArgPos+2],args[firstArgPos+3]+" "+args[firstArgPos+4],args[firstArgPos+5]+" "+args[firstArgPos+6]);
    if(print){
      System.out.println(call.toString());
    }

    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(0);
  }

  /**
   * Print error message and exit the program
   *
   */
  @VisibleForTesting
  static void printErrorMessageAndExit(String message) {
    System.err.println(message);
    System.exit(1);
  }

  /**
   *
   * Print README if -README option is present and exit the program
   */
  public static void printREADME(InputStream readme) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
    try {
      while (reader.ready()){
        System.out.println(reader.readLine());
      }
    } catch (IOException ex){
      System.err.println(ex);
      throw ex;
    }finally {
      if (reader!=null){
        reader.close();
      }
    }
    System.exit(0);

  }

}