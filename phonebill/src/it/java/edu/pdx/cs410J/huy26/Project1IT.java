package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.core.StringContains;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 * Integration tests for the <code>Project1</code> class's main method.
 * These tests extend <code>InvokeMainTestCase</code> which allows them
 * to easily invoke the <code>main</code> method of <code>Project1</code>.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
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
      MainMethodResult result = invokeMain(Project1.class,"caller");
      assertThat(result.getExitCode(),equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Missing argument"));
  }
  @Test
    public void testInvalidNumber(){
      String args[] = {"caller", "asasdas", "asdasdasd", "starttime","asdasdasd", "endtime","asdasdasd"};
      MainMethodResult result = invokeMain(Project1.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Phone Number must be formatted as nnn-nnn-nnnn"));
  }

  @Test
    public void testInvalidDate(){
      String args[] = {"caller","123-456-1234","123-456-1234","asd", "19:39","asdasd", "1:03"};
      MainMethodResult result = invokeMain(Project1.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Date is invalid. Date must be formatted as mm/dd/yyyy"));
  }

  @Test
    public void testInvalidTime(){
      String args[] = {"caller","123-456-1234","123-456-1234","12/12/2020", "asdasd","12/12/2020", "asdasd"};
      MainMethodResult result = invokeMain(Project1.class,args);
      assertThat(result.getExitCode(), equalTo(1));
      assertThat(result.getTextWrittenToStandardError(),containsString("Time is invalid. Time must be formatted as hh:mm"));
  }
  @Test
  public void readMeIsTheFirstOption(){
    String args[] = {"-README"};
    MainMethodResult result = invokeMain(Project1.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("This is a README file!"));
  }
  @Test
  public void readMeIsTheSecondOption(){
    String args[] = {"-print","-README"};
    MainMethodResult result = invokeMain(Project1.class,args);
    assertThat(result.getExitCode(),equalTo(0));
    assertThat(result.getTextWrittenToStandardOut(),containsString("This is a README file!"));
  }

  @Test
  public void printIsTheFirstOptionWithoutArgument(){
    String args[] = {"-print"};
    MainMethodResult result = invokeMain(Project1.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing argument"));
  }

  @Test
  public void notEnoughArgument(){
    String args[] ={"-print","caller","123-456-1234","123-456-1234","12/12/2020", "asdasd","12/12/2020"};
    MainMethodResult result = invokeMain(Project1.class,args);
    assertThat(result.getExitCode(),equalTo(1));
    assertThat(result.getTextWrittenToStandardError(),containsString("Missing argument"));
  }

  @Test
  public void daveExampleFromAssignment(){
    String[] args = {"-print","caller","123-456-1234","123-456-1234","12/12/2020", "5:27","12/12/2020","5:30"};
    MainMethodResult result = invokeMain(Project1.class, args);
    String description = "Phone call from 123-456-1234 to 123-456-1234 from 12/12/2020 5:27 to 12/12/2020 5:30";
    assertThat(result.getTextWrittenToStandardOut(), StringContains.containsString(description));
  }
}