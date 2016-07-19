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
public class StudentService{ 
 private StudentRegister register = null;
  public StudentService(){
   register = new StudentRegister() ;
  }
    @GET
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = register.findStudent(id);
        if (st==null) return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(st).build();
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = "{message:'FIXME : Delete service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


