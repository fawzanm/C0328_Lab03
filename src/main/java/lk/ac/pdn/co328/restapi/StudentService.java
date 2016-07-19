/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("rest")
public class StudentService
{
    StudentRegister stuRegObj;

    public StudentService()
    {
        stuRegObj = new StudentRegister();
    }
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the receiver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = stuRegObj.findStudent(id);
        if(st == null) {
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("NOT FOUND").build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        String message;
        //String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        Student st = stuRegObj.findStudent(id);
        if(st == null) {
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("NOT FOUND").build();
        }

            /*
            * The message format should be xml. Example given below.
            *
             <?xml version="1.0" encoding="UTF-8"?>
             <student>
                <id>1</id>
                <firstName>john</firstName>
                <lastName>doe</lastName>
             </student>
            *
            * */

        String newId = "";
        String newFName = "";
        String newLName = "";

        // the pattern we want to search for
        Pattern idPattern = Pattern.compile("<id>(\\d+)</id>");
        Matcher idMatcher = idPattern.matcher(input);
        // if we find a match, get the group
        if (idMatcher.find()) {
            // get the matching group
            newId = idMatcher.group(1);
        }

        // the pattern we want to search for
        Pattern fNamePattern = Pattern.compile("<firstName>([^\"]+)</firstName>");
        Matcher fNameMatcher = fNamePattern.matcher(input);
        // if we find a match, get the group
        if (fNameMatcher.find()) {
            // get the matching group
            newFName = fNameMatcher.group(1);
        }

        // the pattern we want to search for
        Pattern lNamePattern = Pattern.compile("<lastName>([^\"]+)</lastName>");
        Matcher lNameMatcher = lNamePattern.matcher(input);
        // if we find a match, get the group
        if (lNameMatcher.find()) {
            // get the matching group
            newLName = lNameMatcher.group(1);
        }

        stuRegObj.removeStudent(id);
        Student stu = new Student(Integer.parseInt(newId), newFName, newLName);
        try {
            stuRegObj.addStudent(stu);
            message = "Successfully updated";
        } catch (Exception e) {
            message = e.toString();
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message;
        Student st = stuRegObj.findStudent(id);
        if(st == null) {
            message = "ERROR: STUDENT NOT FOUND";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }
        stuRegObj.removeStudent(id);
        message = "Successfully removed";
        //String message = "{message:'FIXME : Delete service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message;
        //String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        Student st = stuRegObj.findStudent(id);
        if(st != null) {
            message = "ERROR: DUPLICATE ID";
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }

            /*
            * The message format should be xml. Example given below.
            *
             <?xml version="1.0" encoding="UTF-8"?>
             <student>
                <firstName>john</firstName>
                <lastName>doe</lastName>
             </student>
            *
            * */

        String FName = "";
        String LName = "";

        // the pattern we want to search for
        Pattern fNamePattern = Pattern.compile("<firstName>([^\"]+)</firstName>");
        Matcher fNameMatcher = fNamePattern.matcher(input);
        // if we find a match, get the group
        if (fNameMatcher.find()) {
            // get the matching group
            FName = fNameMatcher.group(1);
        }

        // the pattern we want to search for
        Pattern lNamePattern = Pattern.compile("<lastName>([^\"]+)</lastName>");
        Matcher lNameMatcher = lNamePattern.matcher(input);
        // if we find a match, get the group
        if (lNameMatcher.find()) {
            // get the matching group
            LName = lNameMatcher.group(1);
        }

        Student stu = new Student(id, FName, LName);
        try {
            stuRegObj.addStudent(stu);
            message = "Successfully added";
        } catch (Exception e) {
            message = e.toString();
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();

    }
}


