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
	
	private static StudentRegister register = new StudentRegister();
	
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
	
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        // Student st = new Student(id, "dummy", "dummy");
    	Student st = register.findStudent(id);
    	
    	if(st == null){
    		return Response.status(HttpResponseCodes.SC_FOUND).entity("Student not found").build();
    		
    	}else{
    		return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    	}
    	
        
    }
    
    @POST
	
    @Path("student/{id}")
    //@Consumes("application/xml")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        
    	synchronized (register) {
			try {
				// remove the student first
				register.removeStudent(id);
				register.addStudent(input);
			} catch (Exception e) {
				
				return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("Error occured").build();
			}
		}

		return Response.status(HttpResponseCodes.SC_OK).entity("modified successfully").build();
    }
    
    @DELETE
	
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = String.format("The student of %d is removed from the system",id);
        String errmsg = String.format("Student of %d not found in the system", id);
        synchronized(register){
        	try{
        	register.removeStudent(id);
        	}catch(Exception e){
        	e.printStackTrace();
        	return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(errmsg).build();
        	}
        }
    	
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
	
    @PUT
    @Path("student/{id}")
    //@Consumes("application/xml")
    
	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@PathParam("id") int id, Student input)
    {
        //String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
    	String message = String.format("Student of %d is added successfully", id);
    	
    	synchronized(register){
    		
	    	try {
			register.addStudent(input);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("corrupted").build();
			}
    	}
    	
    	return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
	
}


