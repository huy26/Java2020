package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
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
      assertThat(result.getTextWrittenToStandardError(),containsString("Missing callee name"));
  }
  @Test
    public void testInvalidNumber(){
      String args[] = {"caller", "callee", "asasdas", "asdasdasd", "starttime", "endtime"};
      MainMethodResult result = invokeMain(Project1.class,args);
      assertThat(result.getTextWrittenToStandardError(),containsString("Phone Number must be formatted as nnn-nnn-nnnn"));
  }
  @Test
    public void testInvalidDate(){
      String args[] = {"caller","callee","123-456-1234","123-456-1234","asd", "19:39","asdasd", "1:03"};
      MainMethodResult result = invokeMain(Project1.class,args);
      assertThat(result.getTextWrittenToStandardError(),containsString("Date is invalid. Date must be formatted as mm/dd/yyyy"));
  }
  @Test
    public void testInvalidTime(){
      String args[] = {"caller","callee","123-456-1234","123-456-1234","12/12/2020", "asdasd","12/12/2020", "asdasd"};
      MainMethodResult result = invokeMain(Project1.class,args);
      assertThat(result.getTextWrittenToStandardError(),containsString("Time is invalid. Time must be formatted as hh:mm"));
  }
}