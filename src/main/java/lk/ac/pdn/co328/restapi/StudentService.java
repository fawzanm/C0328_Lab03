/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;

//import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;

@Path("rest")
public class StudentService {
    public StudentRegister sRegister = null;

    public StudentService() {
        sRegister = new StudentRegister();
    }

    @GET
    @Path("student/{id}")//how we get parameter id
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces(MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    public Response viewStudent(@PathParam("id") int id) {
        Student st = sRegister.findStudent(id);
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }

    @POST
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input) {
        String message = "";
        sRegister.removeStudent(id);

        try {
            int tempID = 0;
            String tempid = "";
            String fname = "";
            String lname = "";
            input = input.substring(75);
            while (input.charAt(0) != '<') {
                fname = fname + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            input = input.substring(16);

            while (input.charAt(0) != '<') {
                tempid = tempid + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            input = input.substring(15);
            while (input.charAt(0) != '<') {
                lname = lname + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            tempID = Integer.parseInt(tempid);
            Student student = new Student(tempID, fname, lname);
            sRegister.addStudent(student);
            message = "Student " + id + " is edited successfully...";
        } catch (Exception e) {
            message = "Error occured. Student edit failed!!!";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @DELETE
    @Path("student/{id}")
    @Produces("text/html")
    public Response deleteStudent(@PathParam("id") int id) {
        sRegister.removeStudent(id);
        String message = "Student " + id + " is deleted!!!";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input) {
        String message = "";

        try {
            int tempID = 0;
            String tempid = "";
            String fname = "";
            String lname = "";
            input = input.substring(75);
            while (input.charAt(0) != '<') {
                fname = fname + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            input = input.substring(16);

            while (input.charAt(0) != '<') {
                tempid = tempid + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            input = input.substring(15);
            while (input.charAt(0) != '<') {
                lname = lname + Character.toString(input.charAt(0));
                input = input.substring(1);
            }
            tempID = Integer.parseInt(tempid);
            Student student = new Student(tempID, fname, lname);
            sRegister.addStudent(student);
            message = "Student " + id + " is added successfully...";
        } catch (Exception e) {
            message = "Error occured. Student add failed!!!";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}

