package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 * This class represents a <code>PhoneBill</code>
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {

    private final String customer;
    private TreeSet<PhoneCall> calls = new TreeSet<>();

    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customer
     *        The customer's name
     */
    public PhoneBill(String customer) {
        this.customer = customer;
    }

    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall phoneCall) {
        this.calls.add(phoneCall);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
