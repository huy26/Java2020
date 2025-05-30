package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * This class represents a <code>TextDumper</code>
 */
public class TextDumper implements PhoneBillDumper {
    private final File file;

    /**
     * Creates a new <code>TextDumper</code>
     *
     * @param file Path directory to the text file.
     */

    public TextDumper(File file) {
        this.file = file;
    }

    /**
     *The function dump the PhoneBill to the text file.
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        try {
            FileWriter writer = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(bill.getCustomer());
            TreeSet<PhoneCall> phoneCalls = (TreeSet<PhoneCall>) bill.getPhoneCalls();
            for (PhoneCall itr:phoneCalls) {
                printWriter.print(itr.getCaller()+" ");
                printWriter.print(itr.getCallee() + " ");
                printWriter.print(itr.getNormalStartString() + " ");
                printWriter.println(itr.getNormalEndString());
            }
            printWriter.close();

        } catch (IOException ex) {
            System.err.println(ex);
            throw ex;
        }
    }
}
