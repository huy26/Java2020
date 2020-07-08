package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.lang.Human;

import java.util.ArrayList;

/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {

  private final double gpa;
  private final ArrayList<String> classes;
  private final Gender gender;

  /**                                                                               
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male" or "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);

    if(name.equals("")){
      throw new IllegalArgumentException(" Name cannot be empty");
    }

    if(gpa > 4.0){
      throw new IllegalArgumentException("GPA cannot be greater than 4.0");
    }
    this.gpa = gpa;
    this.classes = classes;
    this.gender = Gender.getGenderForString(gender);
  }

  enum Gender {
    FEMALE("She says"), MALE("He says"), OTHER("They say");

    private final String pronounSays;

    Gender(String pronounSays) {
      this.pronounSays = pronounSays;
    }


    public static Gender getGenderForString(String genderString) {
      switch (genderString) {
        case "female":
          return Gender.FEMALE;
        case "male":
          return Gender.MALE;

        case "other":
          return Gender.OTHER;
        default:
          throw new UnsupportedGenderException(genderString);
      }
    }


    public String getPronounSays() {
      return this.pronounSays;
    }
  };
  /**
   * All students say "This class is too much work"
   * */
    @Override public String says() {
      return "This class is too much work";
    }
    /**
   * Returns a <code>String</code> that describes this
   * * <code>Student</code>.
   * */
   public String toString() {
     int numClasses = this.classes.size();
     return this.getName() +" has a GPA of " + this.gpa + " and is taking " + numClasses + " class" + (numClasses !=1 ? "es" : "") + (numClasses==0? '.':": " + listOfClasses() + ".") + "  " + this.gender.getPronounSays() + " \"" +  this.says() + "\".";
   }

  private String listOfClasses() {
     StringBuilder sb = new StringBuilder();
     int numClasses = this.classes.size();
     sb.append(String.join(", ", this.classes.subList(0, numClasses -1)));
     if (numClasses>1) {
       if (numClasses >2){
         sb.append(","); }
       sb.append(" and "); }
     sb.append(this.classes.get(numClasses -1));
     return sb.toString();
   }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    if(args.length == 0){
      String message = "Missing command line arguments";
      printErrorMessageAndExit(message);

    }else if(args.length==1) {
      printErrorMessageAndExit("Missing gender");
    }else if (args.length==2) {
      printErrorMessageAndExit("Missing GPA");
    }
    double gpa =  Double.parseDouble(args[2]);
    ArrayList<String> classes = new ArrayList<>();
    for(int i = 3;i<args.length;i++){
      classes.add(args[i]);
    }
   try {
     Student student = new Student(args[0], classes, gpa, args[1]);
     System.out.println(student);
     System.exit(0);
   } catch (UnsupportedGenderException ex) {
     printErrorMessageAndExit("Unsupported Gender: " + ex.getMessage());
   }

  }

  private static void printErrorMessageAndExit(String message) {
    System.err.println(message);
    System.exit(1);
  }

}