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
import lk.ac.pdn.co328.studentSystem.StudentRegister;

@Path("rest")
public class StudentService
{    
	StudentRegister database=new StudentRegister();
	

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student student = database.findStudent(id);
		if(student!=null) 
			return Response.status(HttpResponseCodes.SC_FOUND).entity(student).build();
		else
			return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("No results for "+id).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        Student student = database.findStudent(id);
        if(student==null)
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("No results for "+id).build();
		else{
			database.removeStudent(id);
			try {
				String s[] = input.split("\r\n");
				String names[] = s[3].split(",");
				Student new_student = new Student(id, names[0], names[1]);
				database.addStudent(new_student);
				return Response.status(HttpResponseCodes.SC_OK).entity("Modification is done").build();
			} catch (Exception ex) {
				return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(ex.toString()).build();
			}
		}
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        Student student = database.findStudent(id);
        if(student==null)
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("No results for "+id).build();
        else{
			database.removeStudent(id);
			return Response.status(HttpResponseCodes.SC_OK).entity("Records removed for "+id).build();
		}
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        try {
            String s[] = input.split("\r\n");
            String names[] = s[3].split(",");
            Student new_student = new Student(id, names[0], names[1]);
            database.addStudent(new_student);
            return Response.status(HttpResponseCodes.SC_OK).entity("Records is added for "+id+"\n"+new_student).build();
        } catch (Exception e) {
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(e.toString()).build();
        }
    }
}


