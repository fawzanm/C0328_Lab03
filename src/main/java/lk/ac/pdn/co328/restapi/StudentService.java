/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
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
    private StudentRegister sr;
    public StudentService(){
        sr = new StudentRegister();
    }
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student tem = sr.findStudent(id);

        if (tem == null){
            String message = "{\"message\":\"Student not found\"}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else {
            return Response.status(HttpResponseCodes.SC_FOUND).entity(tem).build();
        }
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {

        String message;
        Student tem = sr.findStudent(id);
        if (tem == null){
            message = "{\"message\":\"Student not found! \"}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }else{
            sr.removeStudent(id);
            try {
                String[] names = input.split(",");
                sr.addStudent(new Student(id, names[0], names[1]));
                message = "{\"message\":\"Student modified ! \"}";
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
            }catch(Exception e){
                message = "{\"message\":\"ERROR : "+e+"\"}";
                return Response.status(HttpResponseCodes.SC_EXPECTATION_FAILED).entity(message).build();
            }
        }
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message;
        Student tem = sr.findStudent(id);
        if (tem == null){
            message = "{\"message\":\"Student already removed ! \"}";
            return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        }else{
            sr.removeStudent(id);
            message = "{\"message\":\"Student  removed ! \"}";
            return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        }

    }
    //input should given as firstname,lastname
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response addStudent(@PathParam("id") int id, String input)
    {   String message ;
        String [] fAndL = input.split(",");

        try {
            sr.addStudent(new Student(id, fAndL[0], fAndL[1]));
            message = "{\"message\":\"Student added \"}";
            return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        }catch(Exception e){
             message = "{\"message\":\"ERROR : "+e+"\"}";
            return Response.status(HttpResponseCodes.SC_EXPECTATION_FAILED).entity(message).build();
        }

    }
}


