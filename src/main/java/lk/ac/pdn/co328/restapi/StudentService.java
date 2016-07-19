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
    @Produces( MediaType.APPLICATION_JSON)
    public Response viewStudent(@PathParam("id") int id) {
        Student st ;

        synchronized (register){
            st = register.findStudent(id);
        }

        if(register==null){
            String message = id + " is not found in database";
            Message m= new Message();
            m.setMessage(message);
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(m).build();
        }

        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }




    @POST
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        Message m;
        synchronized (register){
            try{
                register.removeStudent(id);
                register.addStudent(input);
            }
            catch (Exception e){
                String message =id + " is not found in database";
                m = new Message();
                m.setMessage(message);
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(m).build();
            }
        }
        String message = "Modification done";  // Ideally this should be machine readable format Json or XML
        m = new Message();
        m.setMessage(message);
        return Response.status(HttpResponseCodes.SC_OK).entity(m).build();
    }



    @DELETE
    @Path("student/{id}")
    @Produces( MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") int id)
    {
        synchronized (register){
            register.removeStudent(id);
        }
        String message = "Student removed";  // Ideally this should be machine readable format Json or XML
        Message m = new Message();
        m.setMessage(message);
        return Response.status(HttpResponseCodes.SC_OK).entity(m).build();
    }



    @PUT
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response addStudent(@PathParam("id") int id, Student input) {

        synchronized (register) {
            try {
                register.addStudent(input);
            } catch (Exception e) {
                return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(e).build();
            }
        }
        String message = "Added to the database";  // Ideally this should be machine readable format Json or XML
        Message m = new Message();
        m.setMessage(message);
        return Response.status(HttpResponseCodes.SC_OK).entity(m).build();
    }
}


