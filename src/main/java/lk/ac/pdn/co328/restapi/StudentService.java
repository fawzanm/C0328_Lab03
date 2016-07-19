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

@Path("rest")
public class StudentService {
    public StudentRegister sr = null;
    public StudentService() {
        sr = new StudentRegister();
    }
    @GET
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    public Response viewStudent(@PathParam("id") int id) {
        Student st = sr.findStudent(id);
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }

    @POST
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input) {
        String message = "";
        sr.removeStudent(id);

        try {
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
            Student student = new Student((Integer.parseInt(tempid)), fname, lname);
            sr.addStudent(student);
            message = "Student " + id + " is edited.";
        } catch (Exception e) {
            message = "Error!!! Editing failed.";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @DELETE
    @Path("student/{id}")
    @Produces("text/html")
    public Response deleteStudent(@PathParam("id") int id) {
        sr.removeStudent(id);
        String message = "Student " + id + " is deleted!!!";  
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input) {
        String message = "";

        try {
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
            Student student = new Student((Integer.parseInt(tempid)), fname, lname);
            sr.addStudent(student);
            message = "Student " + id + " is added.";
        } catch (Exception e) {
            message = "Error!!! Adding failed.";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}

