/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService
{
	StudentRegister stud_reg=new StudentRegister();
    
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = stud_reg.findStudent(id);
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'Updated : Updated Student'}";  
        
		try{
            register.removeStudent(id);
			register.addStudent(input);
        }
        catch (Exception e){
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(e.toString()).build();
        }
		
		return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = null;  

		Student st = stud_reg.findStudent(id);
		if(st == null){
			message = "{message:'FIXME : No student by entered Id'}"; 
		}else{
			stud_reg.removeStudent(id);
			message = "{message:'Deleted : Deleted Student'}";
		}
        
		return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
		String message = null; 
		
		Student st = newReg.findStudent(id);
        if(st == null) {
            try {
                String stud[] = input.split(" ");
                Student students = new Student(id, stu[0], stu[1]);
                stud_reg.addStudent(students);
                message = "{message:'Added : Student Added'}";
            }catch (Exception e) {
                message = "{message:'FIXME : Error when adding student'}";
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }else{
            message = "{message:'FIXME : Id Already Existed'}"; 
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


