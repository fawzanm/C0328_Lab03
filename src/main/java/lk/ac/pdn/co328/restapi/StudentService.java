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

    private static StudentRegister reg = new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st;
        //reg = new StudentRegister();
        st = reg.findStudent(id);
        if (st==null){
            System.out.println("Something is wrong");
            String message;
            message = id + "is not valid, Enter valid one plz...";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        //String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        String message;
        synchronized (reg) {
            try {
                reg.removeStudent(id);
                reg.addStudent(input);
                message = "Details updated successfully";
            }
            catch (Exception e){
                message = e.getMessage();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        /*
        String message = "{message:'FIXME : Delete service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        reg = new StudentRegister();
        */
        String msg = String.format("Student %d is removed",id);
        String emsg = String.format("Student %d is in here",id);
        synchronized (reg){
            try {
                reg.removeStudent(id);
            }
            catch (Exception e){
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(emsg).build();
            }
        }
        /*
        reg.removeStudent(id);
        String message = "{message:'FIXME : Delete service is implemented'}";
        */
        return Response.status(HttpResponseCodes.SC_OK).entity(msg).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, Student input) throws Exception {
        /*
        String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        reg = new StudentRegister();
        Student s1 = new Student(id, "Dhanika", "Thathsara");
        String wmsg = "{message:'FIXME : Update service is not yet implemented'}";
        */
        String msg = String.format("Student who has ID %d is added successfully",id);
        synchronized (reg){
            try {
                reg.addStudent(input);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("Something is Wrong").build();
            }
        }

        //String message = "{message:'FIXME : Add service is implemented'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(msg).build();
    }
}


