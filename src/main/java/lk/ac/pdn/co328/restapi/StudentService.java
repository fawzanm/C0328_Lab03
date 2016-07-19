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
        Student student = null;
        try {
            student = register.findStudent(id);
            String fname = student.getFirstName();
            String lname = student.getLastName();
            student = new Student(id, fname, lname);

        } catch (Exception ex) {
            System.out.println("Error in searching student : " + ex.getMessage());
        }

        if(student ==null) return Response.status(HttpResponseCodes.SC_FOUND).entity("message: 'No id found or id searching error'").build();
        return Response.status(HttpResponseCodes.SC_FOUND).entity(student).build();

    }

    @POST
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        try {
            register.removeStudent(id);
            register.addStudent(input);

        }catch (Exception ex){
            String error = "{message:'Error in Updating Process'}";
            return Response.status(HttpResponseCodes.SC_OK).entity(error).build();
        }
        String ok = "{message:'Updating Process Sucessfull'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(ok).build();
    }
    
    @DELETE
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") int id)
    {
        try {
            register.removeStudent(id);
            System.out.println("OK");
        }catch (Exception ex){
            String error ="{message:'Error in Deleting Process may be no user in such id exists'}";
            return Response.status(HttpResponseCodes.SC_OK).entity(error).build();
        }
        String ok ="{'message':'deletion sucessfull'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(ok).build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@PathParam("id") int id, Student input)
    {
        try {
            register.addStudent(input);

        }catch (Exception ex){
            String error = "{message:'Error in Adding Process may be already used id'}";
            return Response.status(HttpResponseCodes.SC_OK).entity(error).build();
        }
        String ok = "{message:'Adding Process Sucessfull'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(ok).build();

    }
}


