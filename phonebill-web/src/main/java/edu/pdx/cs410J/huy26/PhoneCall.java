package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents a <code>PhoneCall</code>
 */

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
    private final String caller;
    private final String callee;
    private final String start;
    private final String end;

    /**
     * Creates a new <code>PhoneCall</code>
     * @param caller
     *        The caller's number
     * @param callee
     *        The callee's number
     * @param start
     *        The start time of the phone call
     * @param end
     *        The end time of the phone call
     */

    public PhoneCall(String caller, String callee, String start, String end) {
        this.caller = caller;
        this.callee = callee;
        this.start = start;
        this.end = end;
    }

    @Override
    public String getCaller() {
        return this.caller;
    }

    @Override
    public String getCallee() {
        return this.callee;
    }

    public Date getStartTime() {
        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(this.start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getEndTime() {
        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(this.end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    @Override
    public String getStartTimeString() {
        Date date = getStartTime();
        Locale locale= new Locale("en","US");
        String formattedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).format(date);
        return formattedDate;
    }

    @Override
    public String getEndTimeString() {
        Locale locale= new Locale("en","US");
        String formattedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, locale).format(this.getEndTime());
        return formattedDate;
    }
    public String getNormalStartString(){
        return this.start;
    }

    public String getNormalEndString(){
        return this.end;
    }

    @Override
    public int compareTo(PhoneCall o) {
        int compare= this.getStartTime().compareTo(o.getStartTime());
        if (compare == 0){
            compare=this.caller.compareTo(o.caller);
        }
        return compare;
    }
}
