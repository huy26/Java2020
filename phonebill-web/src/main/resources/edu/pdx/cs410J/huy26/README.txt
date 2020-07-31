This is a README file!
Huy Doan - Project 4
This is a program that print the description of phone call by inputting customer's call log
using  command line include:  [options] <args>.
Args contains: customer, callerNumber, calleeNumber, start, end.
Caller and callee number should be formatted as nnn-nnn-nnnn.
Start and end time should be formatted as MM/dd/yyyy hh:mm am/pm.
Option contains: -print, -README, -search, -port port, -localhost hostname.
And print the description of newly added phone call if option -print is present.
-README option would print README file and stop the program.
Port must be numbers.
-search option must be provided arguments: customerName, start, end.
-search will pretty print phone calls which occur during provided time.
If there is only customer name provided, the program will pretty print all phone calls of that customer.

A PhoneBillServlet that provides REST access to an PhoneBill. The servlet should be deployed in a web application named phonebill and should support the following URLs:
– http://host:port/phonebill/calls?customer=name
– http://host:port/phonebill/calls?customer=name&start=startDateTime&end=endDateTime