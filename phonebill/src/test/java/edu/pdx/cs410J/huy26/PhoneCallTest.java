package edu.pdx.cs410J.huy26;

import org.junit.Ignore;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class PhoneCallTest {

  @Ignore
  @Test(expected = UnsupportedOperationException.class)
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall("caller", "callee", "starttime", "endtime");
    call.getStartTimeString();
  }

  @Ignore
  @Test(expected = UnsupportedOperationException.class)
  public void getEndTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall("caller", "callee", "starttime", "endtime");
    call.getEndTimeString();
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    String callee="Callee";
    PhoneCall call = new PhoneCall("caller", callee, "starttime", "endtime");
    assertThat(call.getCallee(), equalTo(callee));
  }
  @Test
  public void initiallyAllPhoneCallsHaveTheSameCaller() {
    String caller="Caller";
    PhoneCall call = new PhoneCall(caller, "callee", "starttime", "endtime");
    assertThat(call.getCaller(), equalTo(caller));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall("caller", "callee", "starttime", "endtime");
    assertThat(call.getStartTime(), is(nullValue()));
  }

  @Test
  public void initiallyStartTime(){
    String start="starttime";
    PhoneCall call = new PhoneCall("caller", "callee", start, "endtime");
    assertThat(call.getStartTimeString(), equalTo(start));
  }
  @Test
  public void initiallyEndTime(){
    String end="endtime";
    PhoneCall call = new PhoneCall("caller", "callee", "start", end);
    assertThat(call.getEndTimeString(), equalTo(end));
  }
  @Test
  public void callerAndCalleeCannotBeTheSame(){
    PhoneCall call = new PhoneCall("calle","call","start","end");
    assertTrue("Caller and caller number cannot be the same",call.getCallee()!=call.getCaller());
  }
}
