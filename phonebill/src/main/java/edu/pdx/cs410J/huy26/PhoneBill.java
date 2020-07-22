package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class represents a <code>PhoneBill</code>
 */

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private final String customer;
    private TreeSet<PhoneCall> phoneCalls;

    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customer
     *        The customer's name
     * @param phoneCalls
     *        The list of phone calls that customer has taken. The customer may take zero or more phone calls.
     */

    public PhoneBill(String customer, TreeSet<PhoneCall> phoneCalls) {
        this.customer = customer;
        this.phoneCalls = phoneCalls;
    }


    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall call) { phoneCalls.add(call); }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.phoneCalls;
    }
}
