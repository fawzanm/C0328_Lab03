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
    public StudentRegister studentRegister;
    public Student student;

    public StudentService(){
        studentRegister = new StudentRegister();
    }

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the receiver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = studentRegister.findStudent(id);

        if(st == null){
            return Response.status(HttpResponseCodes.SC_OK).entity("\"Student not found.\"").build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        String message = "{message:'FIXME : Delete service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response addStudent(@PathParam("id") int id, String input)
    {
        //String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        //request form required:
        //first_name:<first name>,last_name:<last name>
        if(studentRegister.findStudent(id)!= null){
            return Response.status(HttpResponseCodes.SC_OK).entity("\"Student id already exists.Try with another id.\" ").build();

        }else {
            String[] tokens = input.split(",");
            if (tokens.length != 2) {
                return Response.status(HttpResponseCodes.SC_OK).entity("\"Request format is wrong.usage: first_name:<first name>,last_name:<last name>\" ").build();
            }

            tokens[0] = tokens[0].replace("first_name:", "");
            tokens[1] = tokens[1].replace("last_name:", "");

            student = new Student(id, tokens[0], tokens[1]);

            try {
                studentRegister.addStudent(student);
            } catch (Exception e) {
                return Response.status(HttpResponseCodes.SC_OK).entity("\"Student is not added successfully.\" ").build();

            }
            return Response.status(HttpResponseCodes.SC_OK).entity("\"Student is added successfully.\" ").build();

        }
    }
}


