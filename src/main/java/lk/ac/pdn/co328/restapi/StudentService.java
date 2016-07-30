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
public class StudentService
{
    StudentRegister streg = new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = streg.findStudent(id);
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input) {
        String [] inputs = input.split("=");
        Student st = new Student(id, inputs[1], "");
        streg.removeStudent(id);
        String message;
        try {
            streg.addStudent(st);
            message = "{message:'Student data updated'}";
        } catch (Exception e) {
            e.printStackTrace();
            message = "{message:'Student is not in the database'}";
        }
          // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        streg.removeStudent(id);
        String message = "{message:'Student is removed'}";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input) {
        String [] inputs = input.split("=");
        Student st = new Student(id, inputs[1], "");
        String message;
        try {
            streg.addStudent(st);
            message = "{message:'Student "+ input +" is added'}";
        } catch (Exception e) {
            message = "{message:'Student "+ input +" is already in the system'}";
        }
          // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


