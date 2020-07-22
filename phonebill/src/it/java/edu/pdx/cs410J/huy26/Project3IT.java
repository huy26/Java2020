package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.core.StringContains;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 * Integration tests for the <code>Project3</code> class's main method.
 * These tests extend <code>InvokeMainTestCase</code> which allows them
 * to easily invoke the <code>main</code> method of <code>Project3</code>.
 */
public class Project3IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project3} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain(Project3.class, args);
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
      MainMethodResult result = invokeMain(Project3.class,"caller");
      assertThat(result.getExitCode(),equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
  }
  @Test
    public void testInvalidNumber(){
      String args[] = {"caller", "asasdas", "asdasdasd", "starttime","asdasdasd","pm", "endtime","656556565","pm"};
      MainMethodResult result = invokeMain(Project3.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Phone Number must be formatted as nnn-nnn-nnnn"));
  }

  @Test
    public void testInvalidDate(){
      String args[] = {"caller","123-456-1234","123-456-1234","asd", "19:39","pm","asdasd", "1:03","pm"};
      MainMethodResult result = invokeMain(Project3.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Date is invalid. Date must be formatted as mm/dd/yyyy"));
  }

  @Test
    public void testInvalidTime(){
      String args[] = {"caller","123-456-1234","123-456-1234","12/12/2020", "asdasd","pm","12/12/2020", "asdasd","pm"};
      MainMethodResult result = invokeMain(Project3.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Time is invalid. Time must be formatted as hh:mm"));
  }
  @Test
  public void readMeIsTheFirstOption() {
    String args[] = {"-README"};
    MainMethodResult result = invokeMain(Project3.class, args);
    assertThat(result.getExitCode(), equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(), containsString("This is a README file!"));
  }

  @Test
  public void readMeIsTheSecondOption(){
    String args[] = {"-print","-README"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("This is a README file!"));
  }

  @Test
  public void printIsTheFirstOptionWithoutArgument(){
    String args[] = {"-print"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
  }

  @Test
  public void notEnoughArgument(){
    String args[] ={"-print","caller","123-456-1234","123-456-1234","12/12/2020", "asdasd","12/12/2020"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));
  }
  @Test
  public void redundentArgument() {
    String args[] ={"caller","123-456-1234","123-456-7895","12/12/2020", "5:30","pm","12/12/2020","5:35","pm","fred"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Unknown command line argument"));
  }
  @Test
  public void unknowncommandLineArgument(){
    String args[]={"-fred","Test6","123-456-7890","234-567-8901","03/03/2020","12:00","04/04/2020","16:00"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Unknown command line option"));
  }

  @Test
  public void testParserFunction() throws IOException {
//    File file = new File("D:\\Download\\cmder\\PhoneBillText\\test.txt");
//    PhoneBill bill = new PhoneBill("",new ArrayList<PhoneCall>());
    String [] args = new String[]{"-textFile","test","Dave","123-456-7897","123-456-7895","11/5/2020","5:20","pm","11/5/2020","5:30","pm"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Customer name does not match the file"));
    assertThat(result.getExitCode(),equalTo(1));
  }
  @Test
  public void testTextFileAndPrint() throws IOException {
//    File file = new File("D:\\Download\\cmder\\PhoneBillText\\test.txt");
//    PhoneBill bill = new PhoneBill("",new ArrayList<PhoneCall>());
    String [] args = new String[]{"-print","-textFile","test","customer","123-456-7897","123-456-7895","11/5/2020","5:20","pm","11/5/2020","5:30","pm"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/20, 5:20 PM to 11/5/20, 5:30 PM"));
  }
  @Test
  public void testTextFileAndREADME() throws IOException {
    String [] args = new String[]{"-textFile","test","-README","customer","123-456-7897","123-456-7895","11/5/2020","5:20","pm","11/5/2020","5:30","pm"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("This is a README file!"));
  }
  @Test
  public void exampleFromAssignment(){
    String[] args = {"-print","Dave","123-456-7897","123-456-7895","12/12/2020", "5:27","am","12/12/2020","5:30","am"};
    MainMethodResult result = invokeMain(Project3.class, args);
    String description = "Phone call from 123-456-7897 to 123-456-7895 from 12/12/20, 5:27 AM to 12/12/20, 5:30 AM";
    assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(description));
  }

  @Ignore
  @Test
  public void testMissingArgument() throws IOException {
    String [] args = new String[]{"-textFile","test1","customer","123-456-7897","123-456-7895","11/5/2020","5:20","pm","11/5/2020","5:30","pm"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing argument in text file"));
    assertThat(result.getExitCode(),equalTo(1));
  }

  @Ignore
  @Test
  public void testRedundantArgument() throws IOException {
    String [] args = new String[]{"-textFile","test1","customer","123-456-7897","123-456-7895","11/5/2020","5:20","pm","11/5/2020","5:30","pm"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Redundant argument in text file"));
    assertThat(result.getExitCode(),equalTo(1));
  }
  @Ignore
  @Test
  public void testDirectory() throws IOException {
    String [] args = new String[]{"-textFile","huy26","-print", "Project3","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/2020 5:20 to 11/5/2020 5:30"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Ignore
  @Test
  public void testDirectory2() throws IOException {
    String [] args = new String[]{"-textFile","huy26/huy26","-print", "Project3","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/2020 5:20 to 11/5/2020 5:30"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Ignore
  @Test
  public void testDirectory3() throws IOException {
    String [] args = new String[]{"-textFile","huy26/huy26.txt","-print", "Project3","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7897 to 123-456-7895 from 11/5/2020 5:20 to 11/5/2020 5:30"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Ignore
  @Test
  public void invalidDateTimeFile() throws IOException {
    String [] args = new String[]{"-textFile","huy26/test.txt","-print", "Project3","123-456-7897","123-456-7895","11/5/2020","5:20","pm","11/5/2020","5:30","pm"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Date or Time is invalid format"));
    assertThat(result.getExitCode(),equalTo(1));
  }
  @Ignore
  @Test
  public void invalidPhoneNumber() throws IOException {
    String [] args = new String[]{"-textFile","huy26/test1.txt","-print", "Project3","123-456-7897","123-456-7895","11/5/2020","5:20","11/5/2020","5:30"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardError(),containsString("Phone number from file is invalid"));
    assertThat(result.getExitCode(),equalTo(1));
  }
    @Test
  public void writeInorderTextFile() throws IOException {
    String [] args = new String[]{"-textFile","test.txt","-print", "customer","123-456-7847","123-456-7815","11/5/2020","4:13","PM","11/5/2020","5:30","PM"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7847 to 123-456-7815 from 11/5/20, 4:13 PM to 11/5/20, 5:30 PM"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Ignore
  @Test
  public void testSinglePrettyPrintOption() {
    String [] args = new String[]{"-pretty","-", "customer","123-456-7847","123-456-7815","11/5/2020","4:13","PM","11/5/2020","5:30","PM"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7847 to 123-456-7815 from 11/5/20, 4:13 PM to 11/5/20, 5:30 PM"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Test
  public void printPrettyFile(){
    String [] args = new String[]{"-pretty","pretty.txt","-print", "customer","123-456-7847","123-456-7815","11/5/2020","4:13","PM","11/5/2020","5:30","PM"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7847 to 123-456-7815 from 11/5/20, 4:13 PM to 11/5/20, 5:30 PM"));
    assertThat(result.getExitCode(),equalTo(0));
  }
  @Ignore
  @Test
  public void printPrettyFile1(){
    String [] args = new String[]{"-pretty","huy26/pretty.txt","-textFile","huy26/test.txt","-print", "Project2","123-456-7847","123-456-7815","11/5/2020","4:13","PM","11/5/2020","5:30","PM"};
    MainMethodResult result = invokeMain(Project3.class,args);
    assertThat(result.getTextWrittenToStandardOut(),containsString("Phone call from 123-456-7847 to 123-456-7815 from 11/5/20, 4:13 PM to 11/5/20, 5:30 PM"));
    assertThat(result.getExitCode(),equalTo(0));
  }
}