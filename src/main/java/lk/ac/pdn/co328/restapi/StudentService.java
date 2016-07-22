/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 *
 * E12026_lab03
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService
{

    private StudentRegister StuReg;
    public StudentService(){
        StuReg = new StudentRegister();
    }
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student temp = StuReg.findStudent(id);
        if (temp == null){
            String message = "{\"message\":\"StudentID is invalid.\"}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else {
            return Response.status(HttpResponseCodes.SC_FOUND).entity(temp).build();
        }

    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {

        Student temp = StuReg.findStudent(id);
        String message;

        if (temp == null){
            message = "{\"message\":\"StudentID is not invalid. \"}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else{
            StuReg.removeStudent(id);
            try {
                String name[] = input.split(",");
                Student st = new Student(id, name[0], name[1]);
                StuReg.addStudent(st);
                message = "{\"message\":\"Student modified successfully! \"}";
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
            }catch(Exception e){
                message = "{\"message\":\"ERROR : "+e+"\"}";
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {

        Student temp = StuReg.findStudent(id);
        String message;
        if (temp != null){
            StuReg.removeStudent(id);
            message = "{\"message\":\"StudentID is removed successfully.\"}";
            return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        }else{
            message = "{\"message\":\"StudentID is not found.\"}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
            //@Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response addStudent(@PathParam("id") int id, String input)
    {
        Student std = StuReg.findStudent(id);
        String message ;
        if( std==null ){
            message = "{\"message\":\"StudentID is not invalid. \"}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else {

            try {

                String FLname[] = input.split(",");
                Student st = new Student(id, FLname[0], FLname[1]);
                StuReg.addStudent(st);
                message = "{\"message\":\"Student added successfully.\"}";
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
            } catch (Exception e) {
                message = "{\"message\":\"ERROR : " + e + "\"}";
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
            }
        }
    }
}


