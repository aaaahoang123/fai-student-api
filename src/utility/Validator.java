package utility;

import com.googlecode.objectify.ObjectifyService;
import entity.Student;
import entity.error.ErrorResource;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static Validator getInstance(){
        return new Validator();
    }
    public List<ErrorResource> validateStudent(Student student) {
        List<ErrorResource> listError = new ArrayList<>();

        // validate rollnumber
        if (checkNull(student.getRollNumber())) {
            listError.add(ErrorResource.getInstance("400", "Invalid Rollnumber", "Rollnumber not null."));
        } else if (!checkCharacter(student.getRollNumber(), "[A-Za-z0-9]+")) {
            listError.add(ErrorResource.getInstance("400", "Invalid Rollnumber", "Rollnumber include only: 0-9,A-Z, a-z"));
        } else if (!checkLength(student.getRollNumber(), 6)) {
            listError.add(ErrorResource.getInstance("400", "Invalid Rollnumber", "Rollnumber must have at least 6 character."));
        }

        // validate name
        if (checkNull(student.getName())) {
            listError.add(ErrorResource.getInstance("400", "Invalid Name", "Name not null"));
        } else if (!checkCharacter(student.getName(), "[\\p{L}\\s]+")) {
            listError.add(ErrorResource.getInstance("400", "Invalid Name", "Name include only: a-z, A-Z, space"));
        } else if (!checkLength(student.getName(), 5)) {
            listError.add(ErrorResource.getInstance("400", "Invalid Name", "Name must have least at 5 character"));
        }

        // validate gender
        if (student.getGender() != 0 && student.getGender() != 1) {
            listError.add(ErrorResource.getInstance("400", "Invalid Gender", "Gender must 0 or 1"));
        }

        // validate email
        if(checkNull(student.getEmail())){
            listError.add(ErrorResource.getInstance("400", "Invalid Email","Email not null"));
        }else if(!checkCharacter(student.getEmail(), "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$")){
            listError.add(ErrorResource.getInstance("400", "Invalid Email", "Email invalid, ex valid: example@has.com"));
        }

        //validate phone
        if(checkNull(student.getPhone())){
            listError.add(ErrorResource.getInstance("400","Invalid Phone", "Phone not null"));
        }else if(!checkCharacter(student.getPhone(),"[0-9\\s]+")){
            listError.add(ErrorResource.getInstance("400", "Invalid Phone", "Phone include only 0-9,space"));
        }else if(!checkLength(student.getPhone(),10)){
            listError.add(ErrorResource.getInstance("400", "Invalid Phone","Phone must have least at 10 character"));
        }

        //validate address
        if(checkNull(student.getAddress())){
            listError.add(ErrorResource.getInstance("400","Invalid Address", "Address not null"));
        }else if(!checkCharacter(student.getAddress(),"[\\p{L}0-9\\s\\/\\-]+")){
            listError.add(ErrorResource.getInstance("400", "Invalid Address", "Address include only A-Z,a-z,0-9,space,-,/"));
        }else if(!checkLength(student.getAddress(),10)){
            listError.add(ErrorResource.getInstance("400", "Invalid Address","Address must have least at 10 character"));
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
