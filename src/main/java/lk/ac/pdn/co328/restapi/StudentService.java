/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService
{    
    StudentRegister stRegister = new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        String message;
        Student st = stRegister.findStudent(id);
        if(st == null){
            message = "The ID " + id + " is not found";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else{
            return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
        }

    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        String message; // Ideally this should be machine readable format Json or XML
        Student st = stRegister.findStudent(id);
        if(st == null){
            message = "There is no student to update in the database";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else synchronized (stRegister) {
            try {
                stRegister.removeStudent(id);
                stRegister.addStudent(input);
                message = "Student updated Successfully";
            } catch (Exception e) {
                message = e.getMessage();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message;// Ideally this should be machine readable format Json or XML
        Student st = stRegister.findStudent(id);
        if(st == null){
            message = "There is no student with this ID!";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else synchronized (stRegister) {
                stRegister.removeStudent(id);
                message = "Student with ID = " + id + " was removed from the database.";
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, Student st)
    {
        String message;
        // Ideally this should be machine readable format Json or XML
        Student isStudent = stRegister.findStudent(id);
        synchronized (stRegister) {
            try {
                stRegister.addStudent(st);
                message = "Student added Successfully";
            } catch (Exception e) {
                message = e.toString();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


