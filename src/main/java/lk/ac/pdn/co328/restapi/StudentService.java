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

    StudentRegister StReg;
    public StudentService(){
        StReg = new StudentRegister();
    }

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = new Student(id, "dummy", "dummy");
        st = StReg.findStudent(id);

        if (st == null){
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(id + " student is not found").build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        Student st = StReg.findStudent(id);

        if (st == null){
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(id + " student is not found").build();
        }
        message = "";
        try{
            StReg.removeStudent(id);

            String[] data = input.split(",");
            StReg.addStudent(new Student(id, data[0], data[1]));
            message = id + "student is changed";
        }
        catch (Exception e){
            message = e.toString();
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = "{message:'FIXME : Delete service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        Student st = StReg.findStudent(id);

        if (st == null){
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(id + " student is not found").build();
        }
        StReg.removeStudent(id);
        return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(id + " student is not found").build();
    }
    
    @PUT
    @Path("student/{id}")
    @Produces("text/html")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 

        message = "";
        try{
            StReg.removeStudent(id);

            String[] data = input.split(",");
            StReg.addStudent(new Student(id, data[0], data[1]));
            message = id + "student is added";
        }
        catch (Exception e){
            message = e.toString();
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }

        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


