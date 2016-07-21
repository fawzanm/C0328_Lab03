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
    StudentRegister studentRegister = new StudentRegister();
    @GET
    @Path("student/{id}")
    //Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = new Student(id, "dummy", "dummy");
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
        String message;// Ideally this should be machine readable format Json or XML
        Student student = studentRegister.findStudent(id);
        if(student==null){
            message = "There is no student with this ID!";
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }else{
            synchronized (studentRegister) {
                studentRegister.removeStudent(id);
                message = "Student with ID = " + id + " was removed from the database.";
            }
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, Student student)
    {
        String message;
        // Ideally this should be machine readable format Json or XML
        Student isStudent = studentRegister.findStudent(id);
        if(isStudent==null){
            synchronized (studentRegister) {
                try {
                    studentRegister.addStudent(student);
                    message = "Student added Successfully";
                } catch (Exception e) {
                    message = e.toString();
                    return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
                }
            }
        }
        else{
            message = "The ID you have entered is already there. Please check the ID.";
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        }
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


