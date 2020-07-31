package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.PhoneBillDumper;
import org.apache.groovy.json.internal.IO;

import java.io.IOException;
import java.io.PrintWriter;
/**
 * This class represents a <code>PhoneBillTextDumper</code>
 */
public class PhoneBillTextDumper implements PhoneBillDumper<PhoneBill> {
    private final PrintWriter writer;

    /**
     * Creates a new <code>PhoneBillTextDumper</code>
     *
     * @param writer type of print writer
     */
    public PhoneBillTextDumper(PrintWriter writer) {
        this.writer=writer;
    }

    /**
     * Dump the phone bill to the server as normal text.
     *
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        this.writer.println(bill.getCustomer());
        PhoneCall newestCall=null;
        for(PhoneCall call:bill.getPhoneCalls()){
            this.writer.println(call.getCaller()+" "+call.getCallee()+" "+call.getNormalStartString()+ " "+call.getNormalEndString());
        }
    }

    /**
     * Dump the newest phone call added.
     * @param bill
     * @throws IOException
     */
    public void dumpNewestPhoneCall(PhoneBill bill) throws IOException{
        this.writer.println(bill.getCustomer());
        PhoneCall newestCall=null;
        for(PhoneCall call:bill.getPhoneCalls()){
            newestCall=call;
        }
        this.writer.println("Phone call added: "+newestCall.getCaller()+" "+newestCall.getCallee()+" "+newestCall.getNormalStartString()+ " "+newestCall.getNormalEndString());

    }
}
