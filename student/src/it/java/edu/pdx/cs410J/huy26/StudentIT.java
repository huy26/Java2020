package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Integration tests for the <code>Student</code> class's main method.
 * These tests extend <code>InvokeMainTestCase</code> which allows them
 * to easily invoke the <code>main</code> method of <code>Student</code>.
 */
public class StudentIT extends InvokeMainTestCase {
  @Test
  public void invokingMainWithNoArgumentsHasExitCodeOf1() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class);
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class);
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
  public void onlyOneArgumentPrintMissingGenderToStandardError(){
    MainMethodResult result = invokeMain(Student.class, "Name");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing gender"));
    assertThat(result.getExitCode(),equalTo(1));
  }

  @Test
  public void onlyTwoArgumentPrintMissingGPAToStandardError(){
    MainMethodResult result = invokeMain(Student.class, "Name","other");
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing GPA"));
    assertThat(result.getExitCode(),equalTo(1));
  }

  @Ignore
  @Test
  public void studentCanTakeZeroClasses(){
    MainMethodResult result = invokeMain(Student.class, "Sumit", "other", "3.45");
    assertThat(result.getExitCode(),equalTo(0));
    String description = "Sumit has a GPA of 3.45 and is taking 0 classes.  "+"They say "+"This class is too much work"+"\"";
    assertThat(result.getTextWrittenToStandardOut(),containsString(description));
  }

  @Ignore
  @Test
  public void daveExampleFromAssignment(){
    String[] args = {"Dave", "male", "3.64", "Algorithms", "Operating Systems", "Java"};
    MainMethodResult result = invokeMain(Student.class, args);
    String description = "Dave has a GPA of 3.45 and is taking 3 classes: Algorithms, Operating Systems and Java.  "+"He says "+"\"This class is too much work"+"\".";
    assertThat(result.getTextWrittenToStandardOut(), containsString(description));
  }
}
