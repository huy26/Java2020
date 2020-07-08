package edu.pdx.cs410J.huy26;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Unit tests for the PhoneBill class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */

public class PhoneBillTest {
    @Test
    public void customerNameMustBeInitialized(){
        String customer = "Dave";
        PhoneBill phonebill = new PhoneBill(customer,new ArrayList<>());
        assertThat(phonebill.getCustomer(), equalTo(customer));
    }
    @Test
    public void testPhoneCalls(){
        ArrayList<PhoneCall> phoneCalls = new ArrayList<>();
        PhoneCall call = new PhoneCall("caller", "callee", "starttime", "endtime");
        PhoneBill phoneBill=new PhoneBill("Dave",phoneCalls);
        assertThat(phoneBill.getPhoneCalls(),equalTo(phoneCalls));
    }
}
