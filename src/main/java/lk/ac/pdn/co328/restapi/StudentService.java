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
    StudentRegister Sreg;
 
     public StudentService() {
         Sreg = new StudentRegister();
     }
	
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
		
        Student st = Sreg.findStudent(id);
        if(st==null)
        return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Student " + id + " is not found").build();		
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
		
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
		Student st = Sreg.findStudent(id);
		if(st==null)
		return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Student" + id + " is not found").build();
	    Sreg.removeStudent(id);
		
		String message = "";
		try{
			String x[] = input.split("\r\n");
			String n[] = s[3].split(",");
			Student st = new Student(id, n[0], n[1]);
			Sreg.addStudent(st);
			message = "Student " + id + " is successfully updated";
		}
		catch (Exception e) {
			message = e.toString();
			return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
		}
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();		
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
		Student st = Sreg.findStudent(id);
		if(st==null)
		return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Student " + id + " is not found").build();
	    Sreg.removeStudent(id);
		
		return Response.status(HttpResponseCodes.SC_OK).entity("Student " + id + " is not found").build();
		       
    }
    
    @PUT
    @Path("student/{id}")
	@Produces("text/html")
    @Consumes("application/xml")

    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message = "";
		
		try{
			String x[] = input.split("\r\n");
			String n[] = s[3].split(",");
			Student st = new Student(id, n[0], n[1]);
			Sreg.addStudent(st);
			message = "Student " + id + " is successfully added";
		}
		catch(Exception e){
			message = e.toString();
			return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
		}
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


