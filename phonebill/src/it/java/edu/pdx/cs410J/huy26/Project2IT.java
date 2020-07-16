package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.core.StringContains;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project2} main class.
 * Integration tests for the <code>Project2</code> class's main method.
 * These tests extend <code>InvokeMainTestCase</code> which allows them
 * to easily invoke the <code>main</code> method of <code>Project2</code>.
 */
public class Project2IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project2} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain(Project2.class, args);
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }
  @Test
  public void testOneArgument(){
      MainMethodResult result = invokeMain(Project2.class,"caller");
      assertThat(result.getExitCode(),equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
  }
  @Test
    public void testInvalidNumber(){
      String args[] = {"caller", "asasdas", "asdasdasd", "starttime","asdasdasd", "endtime","656556565"};
      MainMethodResult result = invokeMain(Project2.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Phone Number must be formatted as nnn-nnn-nnnn"));
  }

  @Test
    public void testInvalidDate(){
      String args[] = {"caller","123-456-1234","123-456-1234","asd", "19:39","asdasd", "1:03"};
      MainMethodResult result = invokeMain(Project2.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Date is invalid. Date must be formatted as mm/dd/yyyy"));
  }

  @Test
    public void testInvalidTime(){
      String args[] = {"caller","123-456-1234","123-456-1234","12/12/2020", "asdasd","12/12/2020", "asdasd"};
      MainMethodResult result = invokeMain(Project2.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Time is invalid. Time must be formatted as hh:mm"));
  }
  @Test
  public void readMeIsTheFirstOption() {
    String args[] = {"-README"};
    MainMethodResult result = invokeMain(Project2.class, args);
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
  }

  @Test
  public void readMeIsTheSecondOption(){
    String args[] = {"-print","-README"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("This is a README file!"));
  }

  @Test
  public void printIsTheFirstOptionWithoutArgument(){
    String args[] = {"-print"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
  }

  @Test
  public void notEnoughArgument(){
    String args[] ={"-print","caller","123-456-1234","123-456-1234","12/12/2020", "asdasd","12/12/2020"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
  }
  @Test
  public void redundentArgument() {
    String args[] ={"caller","123-456-1234","123-456-7895","12/12/2020", "5:30","12/12/2020","5:35","fred"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Unknown command line argument"));
  }
  @Test
  public void unknowncommandLineArgument(){
    String args[]={"-fred","Test6","123-456-7890","234-567-8901","03/03/2020","12:00","04/04/2020","16:00"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Unknown command line option"));
  }

  @Test
  public void testParserFunction() throws IOException {
//    File file = new File("D:\\Download\\cmder\\PhoneBillText\\test.txt");
//    PhoneBill bill = new PhoneBill("",new ArrayList<PhoneCall>());
    String [] args = new String[]{"-textFile","test","Dave","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Customer name does not match the file"));
    assertThat(result.getExitCode(),equalTo(1));
  }
  @Test
  public void testTextFileAndPrint() throws IOException {
//    File file = new File("D:\\Download\\cmder\\PhoneBillText\\test.txt");
//    PhoneBill bill = new PhoneBill("",new ArrayList<PhoneCall>());
    String [] args = new String[]{"-print","-textFile","test","customer","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/2020 5:20 to 11/5/2020 5:30"));
  }
  @Test
  public void testTextFileAndREADME() throws IOException {
    String [] args = new String[]{"-textFile","test","-README","customer","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("This is a README file!"));
  }
  @Test
  public void exampleFromAssignment(){
    String[] args = {"-print","Dave","123-456-7897","123-456-7895","12/12/2020", "5:27","12/12/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class, args);
    String description = "Phone call from 123-456-7897 to 123-456-7895 from 12/12/2020 5:27 to 12/12/2020 5:30";
    assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(description));
  }

  @Ignore
  @Test
  public void testMissingArgument() throws IOException {
    String [] args = new String[]{"-textFile","test1","customer","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing argument in text file"));
    assertThat(result.getExitCode(),equalTo(1));
  }

  @Ignore
  @Test
  public void testRedundantArgument() throws IOException {
    String [] args = new String[]{"-textFile","test1","customer","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Redundant argument in text file"));
    assertThat(result.getExitCode(),equalTo(1));
  }

  @Test
  public void testDirectory() throws IOException {
    String [] args = new String[]{"-textFile","huy26","-print", "Project2","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/2020 5:20 to 11/5/2020 5:30"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Test
  public void testDirectory2() throws IOException {
    String [] args = new String[]{"-textFile","huy26/huy26","-print", "Project2","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/2020 5:20 to 11/5/2020 5:30"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Test
  public void testDirectory3() throws IOException {
    String [] args = new String[]{"-textFile","huy26/huy26.txt","-print", "Project2","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/2020 5:20 to 11/5/2020 5:30"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Test
  public void invalidDateTimeFile() throws IOException {
    String [] args = new String[]{"-textFile","huy26/test.txt","-print", "Project2","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Date or Time is invalid format"));
    assertThat(result.getExitCode(),equalTo(1));
  }
  @Test
  public void invalidPhoneNumber() throws IOException {
    String [] args = new String[]{"-textFile","huy26/test1.txt","-print", "Project2","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project2.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Phone number from file is invalid"));
    assertThat(result.getExitCode(),equalTo(1));
  }
}