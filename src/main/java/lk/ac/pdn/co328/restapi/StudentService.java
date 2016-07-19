/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService
{    
    private static StudentRegister register = new StudentRegister(); 
    @GET
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    // Uncommenting this will let the reciver know that you are sending a json
    public Response viewStudent(@PathParam("id") int id) {

           Student st;
           st = register.findStudent(id);
           
    
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();

          
    }
    
    @POST
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        String message = "{message:'FIXME : Student recorde has been updated sucessfully'}";  // Ideally this should be machine readable format Json or XML 
        
        try {
            register.removeStudent(id);
            register.addStudent(input);
        } catch (Exception ex) {
            Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = "{message:'Added : Student has been deleted'}";  // Ideally this should be machine readable format Json or XML
        register.removeStudent(id);
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
   
    @PUT
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@PathParam("id") int id, Student input)
    {
        
            String message = "{message:'Added : New Student has been added'}";  // Ideally this should be machine readable format Json or XML
        try {
            register.addStudent(input);
        } catch (Exception ex) {
            Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();

    }
}


