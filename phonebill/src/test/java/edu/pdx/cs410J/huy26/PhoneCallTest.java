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

  @Test(expected = UnsupportedOperationException.class)
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall("caller", "callee", "callernumber", "calleeNumber", "starttime", "endtime");
    call.getStartTimeString();
  }

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    String callee="Callee";
    PhoneCall call = new PhoneCall("caller", callee, "callernumber", "calleeNumber", "starttime", "endtime");
    assertThat(call.getCallee(), equalTo(callee));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall("caller", "callee", "callernumber", "calleeNumber", "starttime", "endtime");
    assertThat(call.getStartTime(), is(nullValue()));
  }

  
}
