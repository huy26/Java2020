package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.TreeSet;

/**
 * This class represents a <code>PrettyPrinter</code>
 */
public class PrettyPrinter implements PhoneBillDumper {
    private final File file;

    /**
     * Creates a new <code>PrettyPrinter</code>
     *
     * @param file Path directory to the pretty text file.
     */

    public PrettyPrinter(File file) {
        this.file = file;
    }

    /**
     *The function dump the PhoneBill in formal textual presentation format to the pretty text file.
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        try {
            FileWriter writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("Customer: "+bill.getCustomer());
            TreeSet<PhoneCall> phoneCalls = (TreeSet<PhoneCall>) bill.getPhoneCalls();
            for (PhoneCall itr:phoneCalls) {
                printWriter.print("Phone call from "+itr.getCaller()+" ");
                printWriter.print("to "+itr.getCallee() + " ");
                printWriter.print("from " +itr.getStartTimeString() + " ");
                printWriter.print("to "+itr.getEndTimeString()+ ". ");
                long diff = itr.getEndTime().getTime() - itr.getStartTime().getTime();
                long diffMinutes = diff / (60 * 1000) % 60;
                printWriter.println("Duration: "+ diffMinutes + " minutes");
            }
            printWriter.close();

        } catch (IOException ex) {
            System.err.println(ex);
            throw ex;
        }
    }
}
