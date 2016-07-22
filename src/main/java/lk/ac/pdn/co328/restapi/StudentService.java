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
   public static final String success="<result>success</result>";
   public static final String fail ="<result>failure</result>";     
  StudentRegister register = new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = new Student(id, "dummy", "dummy");
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
	Student std = register.findStudent(id);
	if(std!=null) 
 return Response.status(HttpResponseCodes.SC_FOUND).entity(std).build();
		else
return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Don't exist "+id).build();
    }
 

    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
	Student std=register.findStudent(id);
	if(std==null)
           return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Don't exist "+id).build();
		else{
			register.removeStudent(id);
			try {
				String temp[] = input.split("\r\n");
				String name[] = temp[3].split(",");
				Student student = new Student(id, name[0], name[1]);
				register.addStudent(student);
				return Response.status(HttpResponseCodes.SC_OK).entity("Modified!").build();
			} catch (Exception ex) {
				return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(ex.toString()).build();
			}
		}    


}
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = "{message:'FIXME : Delete service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
     Student std = register.findStudent(id);
       if(std==null)
   return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("Don't exist! "+id).build();
        else{
			register.removeStudent(id);
	return Response.status(HttpResponseCodes.SC_OK).entity("Removed  "+id).build();
		}   
 }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
	 try {
            String temp[] = input.split("\r\n");
            String name[] = temp[3].split(",");
            Student student = new Student(id, name[0], name[1]);
            register.addStudent(new_student);
return Response.status(HttpResponseCodes.SC_OK).entity("Added  "+id+"\n"+new_student).build();
        } catch (Exception e) {
return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(e.toString()).build();
        }   

 }
}


