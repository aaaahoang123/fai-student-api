package utility;

import com.googlecode.objectify.ObjectifyService;
import design_java_rest.entity.RESTError;
import entity.Student;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static Validator getInstance(){
        return new Validator();
    }
    public List<RESTError> validateStudent(Student student) {
        List<RESTError> listError = new ArrayList<>();

        // validate rollnumber
        if (checkNull(student.getRollNumber())) {
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Rollnumber").setDetail("Rollnumber not null.").build());
        } else if (!checkCharacter(student.getRollNumber(), "[A-Za-z0-9]+")) {
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Rollnumber").setDetail("Rollnumber include only: 0-9,A-Z, a-z").build());
        } else if (!checkLength(student.getRollNumber(), 6)) {
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Rollnumber").setDetail("Rollnumber must have at least 6 character.").build());
        }

        // validate name
        if (checkNull(student.getName())) {
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Name").setDetail("Name not null").build());
        } else if (!checkCharacter(student.getName(), "[\\p{L}\\s]+")) {
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Name").setDetail("Name include only: a-z, A-Z, space").build());
        } else if (!checkLength(student.getName(), 5)) {
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Name").setDetail("Name must have least at 5 character").build());
        }

        // validate gender
        if (student.getGender() != 0 && student.getGender() != 1 && student.getGender() != 2) {
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Gender").setDetail("Gender must 0 or 1 or 2").build());
        }

        // validate email
        if(checkNull(student.getEmail())){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Email").setDetail("Email not null").build());
        }else if(!checkCharacter(student.getEmail(), "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$")){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Email").setDetail("Email invalid, ex valid: example@has.com").build());
        }

        //validate phone
        if(checkNull(student.getPhone())){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Phone").setDetail("Phone not null").build());
        }else if(!checkCharacter(student.getPhone(),"[0-9\\s]+")){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Phone").setDetail("Phone include only 0-9,space").build());
        }else if(!checkLength(student.getPhone(),10)){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Phone").setDetail("Phone must have least at 10 character").build());
        }

        //validate address
        if(checkNull(student.getAddress())){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Address").setDetail("Gender must 0 or 1 or 2").build());
        }else if(!checkCharacter(student.getAddress(),"[\\p{L}0-9\\s\\/\\-]+")){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Address").setDetail("Address include only A-Z,a-z,0-9,space,-,/").build());
        }else if(!checkLength(student.getAddress(),10)){
            listError.add(new RESTError.Builder().setCode("400").setTitle("Invalid Address").setDetail("Address must have least at 10 character").build());
        }

        //validate birthday
        return listError;
    }

    public boolean checkNull(String txt) {
        String regex = "[\\s]+";
        if (txt == null || txt.isEmpty() || txt.matches(regex)) {
            return true;
        }
        return false;
    }

    public boolean checkLength(String txt, int length) {
        if (txt.length() < length) {
            return false;
        }
        return true;
    }

    public boolean checkCharacter(String txt, String regex) {
        if (!txt.matches(regex)) {
            return false;
        }
        return true;
    }

    public static boolean checkRollnumerExist(String rollNumber) {
        Student st = ObjectifyService.ofy().load().type(Student.class).filter("rollNumber", rollNumber).first().now();
        if (st != null) {
            return true;
        }
        return false;
    }
}
