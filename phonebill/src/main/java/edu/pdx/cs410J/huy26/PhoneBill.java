package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private final String customerName;
    private final ArrayList<PhoneCall> phoneCalls;

    public PhoneBill(String customerName, ArrayList<PhoneCall> phoneCalls) {
        this.customerName = customerName;
        this.phoneCalls = phoneCalls;
    }

    @Override
    public String getCustomer() {
        return this.customerName;
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
        phoneCalls.add(call);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return (Collection<PhoneCall>) phoneCalls;
    }
}
