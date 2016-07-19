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
 
@Path("rest")
public class StudentService
{
    StudentRegister register;

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        //Student st = new Student(id, "dummy", "dummy");
         register =new StudentRegister();
        Student st;// =new Student();
        st = register.findStudent(id);

        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
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
        register = new StudentRegister();
        register.removeStudent(id);
        String message = "{message:'FIXed : Delete service is implemented'}";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    //@Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    @Consumes("application/xml,application/json")
    public Response addStudent(@PathParam("id") int id, String input) throws Exception
    {
    System.out.print("wfdyaweygfawifhuirah");
        register = new StudentRegister();
        Student st1 = new Student(id, "sadasd", "dummy");

        register.addStudent(st1);

        String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


