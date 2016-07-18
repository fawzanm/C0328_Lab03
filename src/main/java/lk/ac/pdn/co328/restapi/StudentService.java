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

    StudentRegister sr;

    public StudentService() {
        sr = new StudentRegister();
    }

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces(MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    public Response viewStudent(@PathParam("id") int id) {
        Student st = sr.findStudent(id);
        if(st==null)
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Error : Student with ID " + id + " not found").build();
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }

    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input) {
        Student st = sr.findStudent(id);
        if(st==null)
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Error : Student with ID " + id + " not found").build();
        sr.removeStudent(id);
        String message = "";
        try {
            String s[] = input.split("\r\n");
            String names[] = s[3].split(",");
            Student std = new Student(id, names[0], names[1]);
            sr.addStudent(std);
            message = "Success : Student with ID " + id + " updated";
        } catch (Exception e) {
            message = e.toString();
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }

    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id) {
        Student st = sr.findStudent(id);
        if(st==null)
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Error : Student with ID " + id + " not found").build();
        sr.removeStudent(id);
        return Response.status(HttpResponseCodes.SC_OK).entity("Error : Student with ID " + id + " not found").build();
    }

    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input) {
        String message = "";
        try {
            String s[] = input.split("\r\n");
            String names[] = s[3].split(",");
            Student st = new Student(id, names[0], names[1]);
            sr.addStudent(st);
            message = "Success : Student with ID " + id + " added";
        } catch (Exception e) {
            message = e.toString();
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}
