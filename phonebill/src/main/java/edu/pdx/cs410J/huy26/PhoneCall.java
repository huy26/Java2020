package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class represents a <code>PhoneCall</code>
 */

public class PhoneCall extends AbstractPhoneCall {
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
  this.end= end;
 }


 @Override
 public String getCaller() {
  return this.caller;
 }

 @Override
 public String getCallee() {
  return this.callee;
 }

 @Override
 public String getStartTimeString() {
   return  this.start;
 }

 @Override
 public String getEndTimeString() {
  return this.end;
 }

}

