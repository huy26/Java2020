package edu.pdx.cs410J.huy26;

import com.google.inject.internal.cglib.reflect.$FastConstructor;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) throws IOException, ParseException {
        String hostName = null;
        String portString = null;
        String customer = null;
        String caller = null;
        String callee = null;
        String start = null;
        String end = null;
        boolean hostOption = false;
        boolean portOption = false;
        boolean searchOption = false;
        boolean printOption = false;
        boolean readmeOption = false;
        int firstPos = 0;
        for (int i = 0;i<args.length;i++) {
            if(args[i].equals("-README")){
                readmeOption=true;
                firstPos++;
            }else if(args[i].equals("-host")){
                hostOption=true;
                i++;
                if (i<args.length) {
                    hostName = args[i];
                    firstPos += 2;
                }

            }else if(args[i].equals("-port")){
                portOption=true;
                i++;
                if (i<args.length) {
                    portString = args[i];
                    firstPos += 2;
                }
            }else if(args[i].equals("-search")){
                searchOption=true;
                firstPos++;
            }else if(args[i].equals("-print")){
                printOption=true;
                firstPos++;
            }
        }

        if(readmeOption){
            InputStream readme = Project4.class.getResourceAsStream("README.txt");
            printREADME(readme);
        }

        for(int i =firstPos;i<args.length;i++){
            if(customer==null){
                customer=args[i];
            } else if(caller==null&&searchOption==false) {
                    caller=args[i];
            } else if(callee==null&&searchOption==false){
                    callee=args[i];
            }
            else if(start==null){
                if(i+2<args.length) {
                    start = args[i] + " " + args[i + 1] + " " + args[i + 2];
                    i+=2;
                }else {
                    usage("Missing command line argument");
                }
            }else if(end==null){
                if(i+2<args.length) {
                    end = args[i] + " " + args[i + 1] + " " + args[i + 2];
                    i+=2;
                }else {
                    usage("Missing command line argument");
                }
            }else{
                usage("Extraneous command line argument: " + args[i]);
            }
        }
        checkSyntax(caller,callee,start,end);

     if (hostName == null) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        if(customer==null){
            usage("Missing command line argument");
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);


        String message;
        try {
            if (customer == null) {

            } else if (caller == null&&searchOption==false) {
                try {
                    PhoneBill bill = client.getPhoneBill(customer);

                    PrintWriter pw = new PrintWriter(System.out, true);
                    PhoneBillPrettyPrinter pretty = new PhoneBillPrettyPrinter(pw);
                    pretty.dump(bill);
                } catch (ParserException e) {
                    System.err.println("Could not parse response!");
                    System.exit(1);
                } catch (PhoneBillRestClient.PhoneBillRestException ex){
                    switch (ex.gethttpStatusCode()){
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            System.err.println("No phone bill for customer "+customer);
                            System.exit(1);
                            return;
                        default:

                    }
                }

            } else {
                if(searchOption){
                    try {
                        if(start==null){
                            usage("Missing command line argument");
                        } else if(end==null){
                            usage("Missing command line argument");
                        } else {
                            Date startTime = null;
                            Date endTime = null;
                            startTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(start);
                            endTime=new SimpleDateFormat("MM/dd/yyyy hh:mm a").parse(end);
                            int compare = startTime.compareTo(endTime);
                            if(compare > 0 ){
                                usage("End Date Time must occur after Start Date Time");
                            }
                        }
                        PhoneBill bill = client.getPhoneBill(customer);
                        PrintWriter pw = new PrintWriter(System.out, true);
                        PhoneBillPrettyPrinter pretty = new PhoneBillPrettyPrinter(pw);
                        pretty.dumpSearch(bill,start,end);
                    } catch (ParserException e) {
                        e.printStackTrace();
                    } catch (PhoneBillRestClient.PhoneBillRestException ex){
                        switch (ex.gethttpStatusCode()) {
                            case HttpURLConnection.HTTP_NOT_FOUND:
                                System.err.println("No phone bill for customer "+customer);
                                System.exit(1);
                                return;
                            default:
                        }
                    }

                } else {
                    if(printOption){
                        System.out.println(customer);
                        System.out.println("Phone added: "+ caller+" " + callee+ " "+start+ " "+end);
                    }
                    client.addPhoneCall(customer, caller, callee, start, end);
                }
                //message = Messages.definedWordAs(customer, caller);
            }

        } catch ( IOException ex ) {
            error("While contacting server: " + ex);
            return;
        }
        System.exit(0);

    }


    public static void checkSyntax(String caller, String callee, String start, String end) throws ParseException {
        if(caller!=null){
            Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}$");
                    Matcher matcher1 = phonePattern.matcher(caller);
                    if (matcher1.matches() == false) {
                        usage("Phone Number must be formatted as nnn-nnn-nnnn");
                    }
        }
        if(callee!=null){
            Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}$");
            Matcher matcher1 = phonePattern.matcher(callee);
            if (matcher1.matches() == false) {
                usage("Phone Number must be formatted as nnn-nnn-nnnn");
            }
        }
        if(start != null){
            Pattern timePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|am|PM|pm)$");
                    Matcher matcher3 = timePattern.matcher(start);
                    if (matcher3.matches() == false) {
                        usage("Start Date Time is invalid. Date Time must be formatted as MM/dd/yyyy hh:mm am/pm");
                    }
        }
        if(end!=null){
            Pattern timePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|am|PM|pm)$");
            Matcher matcher3 = timePattern.matcher(end);
            if (matcher3.matches() == false) {
                usage("Start Date Time is invalid. Date Time must be formatted as MM/dd/yyyy hh:mm am/pm");
            }
        }
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 [option]] <args>");
        err.println("args are (in this oder):");
        err.println("  customer             Person whose phone bill we're modeling");
        err.println("  callerNumber         Phone number of caller");
        err.println("  calleeNumber         Phone number of person who was asked");
        err.println("  start                Date and time call began");
        err.println("  end                  Date and time call ended");
        err.println("options are (options may appear in any order):");
        err.println("   -host hostname      Host computer on which the server runs");
        err.println("   -port port          Port on which server is listening");
        err.println("   -search             Phone calls should be search for");
        err.println("   -print              Prints a description of the new phone call");
        err.println("   -README             Prints a README for this project and exits");
        err.println();

        System.exit(1);
    }

    /**
     * Print README and exit the program
     */
    public static void printREADME(InputStream readme) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        try {
            while (reader.ready()) {
                System.out.println(reader.readLine());
            }
        } catch (IOException ex) {
            System.err.println(ex);
            throw ex;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        System.exit(0);
    }
}