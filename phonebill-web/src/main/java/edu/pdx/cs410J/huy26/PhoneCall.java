package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
    private final String caller;

    public PhoneCall(String caller) {
        this.caller = caller;
    }

    @Override
    public String getCaller() {
        return this.caller;
    }

    @Override
    public String getCallee() {
        return null;
    }

    @Override
    public String getStartTimeString() {
        return null;
    }

    @Override
    public String getEndTimeString() {
        return null;
    }
}
