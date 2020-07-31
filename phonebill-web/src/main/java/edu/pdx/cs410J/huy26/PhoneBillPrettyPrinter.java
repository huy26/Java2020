package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * This class represents a <code>PhoneBillPrettyPrinter</code>
 */
public class PhoneBillPrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    /**
     * Creases a new <code>PhoneBillPrettyPrinter</code>
     * @param pw type of print writer
     */
    public PhoneBillPrettyPrinter(PrintWriter pw) {
        this.writer = pw;
    }
    /**
     *The function dump the PhoneBill in formal textual presentation format.
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        this.writer.println("Customer: "+bill.getCustomer());
        for(PhoneCall call:bill.getPhoneCalls()){
            long diff = call.getEndTime().getTime() - call.getStartTime().getTime();
            long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            this.writer.println("Phone call from "+call.getCaller()+" to "+call.getCallee()+" from "+call.getStartTimeString()+" to "+call.getEndTimeString() +". Duration: "+diffMinutes+" minutes");
        }
        this.writer.flush();
    }
    /**
     *The function dump the PhoneBill in formal textual presentation format when the search is present.
     */

    /**
     *
     * @param bill Phone bill of the customer
     * @param start Start time provided
     * @param end End time provided
     */
    public void dumpSearch(PhoneBill bill, String start, String end){
        Date startTime=null;
        Date endTime = null;
        try {
            startTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(start);
        } catch (ParseException e) {
            this.writer.println("The input start time is invalid format");
            this.writer.flush();
            return;
        }
        try {
            endTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(end);
        } catch (ParseException e) {
            this.writer.println("The input end time is invalid format");
            this.writer.flush();
            return;
        }
        int count =0;
        this.writer.println("Customer: "+bill.getCustomer());
        for (PhoneCall call:bill.getPhoneCalls()){
            if(call.getStartTime().getTime()-startTime.getTime()>=0 && endTime.getTime()-call.getStartTime().getTime()>=0) {
                count++;
                long diff = call.getEndTime().getTime() - call.getStartTime().getTime();
                long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                this.writer.println("Phone call from " + call.getCaller() + " to " + call.getCallee() + " from " + call.getStartTimeString() + " to " + call.getEndTimeString() + ". Duration: " + diffMinutes + " minutes");
            }
        }
        if (count==0){
            this.writer.println("No phone call between " + start +" and " + end);
        }
        this.writer.flush();
    }
}
