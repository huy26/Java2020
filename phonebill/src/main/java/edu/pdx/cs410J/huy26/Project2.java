package edu.pdx.cs410J.huy26;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {


  /**
   * Main program that parses the command line, creates a
   * <code>PhoneCall</code>, and prints a description of the new phone call to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) throws IOException, ParserException {
    // Refer to one of Dave's classes so that we can be sure it is on the classpath
    //args = new String[]{"-print"};
    boolean print = false;
    boolean textFileAvailable = false;
    PhoneBill phoneBill;
    int firstArgPos = 0;
    String path = "";
    String fileName ="";
    if(args.length==0){
      printErrorMessageAndExit("Missing command line arguments");
    }
    if (args.length == 1) {
      if(args[0].equals("-README")==false)
        printErrorMessageAndExit("Missing command line arguments");
    }

    if (args.length > 1) {
      if (args[1].equals( "-README") ){
        InputStream readme = Project2.class.getResourceAsStream("README.txt");
        printREADME(readme);
      } else if (args.length < 7) {
        printErrorMessageAndExit("Missing command line arguments");
      }
    }
    Pattern optionPattern = Pattern.compile("^-.*");
    for(int i=0;i<4;i++){
      Matcher matcher = optionPattern.matcher(args[i]);
      if(matcher.matches()==true){
        if(args[i].equals("-print")||args[i].equals("-README")||args[i].equals("-textFile")){
          if (args[i].equals("-README")){
            InputStream readme = Project2.class.getResourceAsStream("README.txt");
            printREADME(readme);
            firstArgPos++;
          }
          else if(args[i].equals("-print")){
            print=true;
            firstArgPos++;
          }
          else if (args[i].equals("-textFile")){
            String [] tokens = args[i+1].split("/");
            path=System.getProperty("user.dir");
            for(int j=0;j<tokens.length-1;j++){
              path=path + "/"+tokens[i];
            }
            fileName=tokens[tokens.length-1];
            if(!fileName.matches(".*.txt$"))
              fileName+=".txt";
            firstArgPos +=2;
            i++;
            textFileAvailable=true;
          }
        }
        else {
          printErrorMessageAndExit("Unknown command line option");
        }
      }
    }
    if(firstArgPos +6>=args.length){
      printErrorMessageAndExit("Missing command line arguments");
    } else if(firstArgPos+7<args.length){
      printErrorMessageAndExit("Unknown command line argument");
    }
    for (int i = firstArgPos; i < args.length; i++) {
      if (i==firstArgPos+1 || i==firstArgPos+2) {
        Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}$");
        Matcher matcher1 = phonePattern.matcher(args[i]);
        if (matcher1.matches() == false) {
              for (String arg : args) {
                System.out.println(arg);
              }
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

    //args = new String[]{"customer", "123-456-7897", "123-456-7895", "12/12/2020", "5:27", "12/12/2020", "5:30"};
    if(textFileAvailable){
      File file = new File(path);
      if(!file.exists()){
        file.mkdir();
      }
      path=path+"/"+fileName;
      file=new File(path);
      TextParser parser=new TextParser(file,args,firstArgPos);
      parser.parse();
    }
    if(print){
      System.out.println(call.toString());
    }
    System.exit(0);
  }

  /**
   * Print error message and exit the program
   */
  @VisibleForTesting
  static void printErrorMessageAndExit(String message) {
    System.err.println(message);
    System.exit(1);
  }

  /**
   * Print README and exit the program
   */

  public static void printREADME(InputStream readme) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
    try {
      while (reader.ready()) {
        System.out.println(reader.readLine());
      }
    } catch (IOException ex) {
      System.err.println(ex);
      throw ex;
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
    System.exit(0);
  }
}