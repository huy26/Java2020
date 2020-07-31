package edu.pdx.cs410J.huy26;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.pdx.cs410J.huy26.PhoneBillURLParameters.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
        private final Map<String, PhoneBill> phoneBills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );
        String start=null;
        String end = null;

        String customer = getParameter(CUSTOMER_PARAMETER, request );
        if(customer==null){
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }
        if(request.getParameterMap().size()==3){
             start = getParameter(START_DATE_TIME,request);
            if(start==null){
                missingRequiredParameter(response,START_DATE_TIME);
                return;
            }
             end = getParameter(END_DATE_TIME,request);
            if(end==null){
                missingRequiredParameter(response,END_DATE_TIME);
                return;
            }
        }
        if(start!=null){
            Pattern timePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|am|PM|pm)$");
            Matcher matcher3 = timePattern.matcher(start);
            if (matcher3.matches() == false) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"Start Date Time is invalid format");
            }
        }
        if(end!=null){
            Pattern timePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|am|PM|pm)$");
            Matcher matcher3 = timePattern.matcher(end);
            if (matcher3.matches() == false) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"End Date Time is invalid format");
            }
        }

        PhoneBill bill = getPhoneBill(customer);
        if(bill==null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,Messages.noPhoneBillForCustomer(customer));
        }else{
            if(request.getParameterMap().size()==3) {
                PhoneBillPrettyPrinter pretty = new PhoneBillPrettyPrinter(response.getWriter());
                pretty.dumpSearch(bill,start,end);
            } else {
                PhoneBillTextDumper dumper = new PhoneBillTextDumper(response.getWriter());
                dumper.dump(bill);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request );
        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        String caller = getParameter(CALLER_NUMBER_PARAMETER, request );
        if ( caller == null) {
            missingRequiredParameter( response, CALLER_NUMBER_PARAMETER);
            return;
        }
        String callee = getParameter(CALLEE_NUMBER_PARAMETER, request);
        if(callee==null){
            missingRequiredParameter( response, CALLEE_NUMBER_PARAMETER);
            return;
        }
        String start = getParameter(START_DATE_TIME, request);
        if(start == null){
            missingRequiredParameter( response, START_DATE_TIME);
            return;
        }
        String end = getParameter(END_DATE_TIME, request);
        if(end == null){
            missingRequiredParameter( response, END_DATE_TIME);
            return;
        }
        if(callee!=null){
            Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}$");
            Matcher matcher1 = phonePattern.matcher(caller);
            if (matcher1.matches() == false) {
               response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"Phone Number must be formatted as nnn-nnn-nnnn");
            }
        }
        if(caller!=null){
            Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}$");
            Matcher matcher1 = phonePattern.matcher(callee);
            if (matcher1.matches() == false) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"Phone Number must be formatted as nnn-nnn-nnnn");
            }
        }
        if(start!=null){
            Pattern timePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|am|PM|pm)$");
            Matcher matcher3 = timePattern.matcher(start);
            if (matcher3.matches() == false) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"Start Date Time is invalid format");
            }
        }
        if(end!=null){
            Pattern timePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|am|PM|pm)$");
            Matcher matcher3 = timePattern.matcher(end);
            if (matcher3.matches() == false) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"End Date Time is invalid format");
            }
        }
        PhoneBill bill = getPhoneBill(customer);
        if(bill==null){
            bill=new PhoneBill(customer);
        }
        bill.addPhoneCall(new PhoneCall(caller, callee, start, end));
        this.phoneBills.put(customer, bill);
        PrintWriter pw = new PrintWriter(response.getWriter());
        PhoneBillTextDumper dumper = new PhoneBillTextDumper(pw);
        dumper.dumpNewestPhoneCall(bill);
        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.phoneBills.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    @VisibleForTesting
    PhoneBill getPhoneBill(String customer) {
        return this.phoneBills.get(customer);
    }

    @VisibleForTesting
    public void addPhoneBill(PhoneBill bill) {
        this.phoneBills.put(bill.getCustomer(),bill);
    }
}
