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
    private static StudentRegister Register = new StudentRegister();

    Student stdn = new Student(5,"Jayani","Sumudini");
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        try{
            Register.addStudent(stdn);
        }catch (Exception e){
            e.printStackTrace();
        }

        Student std = Register.findStudent(id);
        if(std == null){
            return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Id is not valid.").build();
        }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(std).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
        if(input == null) {
            try {
                Register.addStudent(input);
            } catch (Exception p) {
                p.printStackTrace();
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Cant added the student.").build();
            }
        }
        else{
            Register.removeStudent(id);
            try {
                Register.addStudent(input);
            } catch (Exception p) {
                p.printStackTrace();
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Student is modified.").build();
            }
        }
        String message = "{message:'FIXME : Updated by E/12/333'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        if ((Register.findStudent(id) != (null))) {
            try {
                Register.removeStudent(id);
                String message = "{message:'FIXME :Delete completed'}";  // Ideally this should be machine readable format Json or XML
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
            } catch (Exception p) {
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Student can't remove.").build();
            }
        }else {
            return Response.status(HttpResponseCodes.SC_FOUND).entity("Invalid ID.").build();
        }
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, Student input)
    {
        if (input != (null)) {
            try {
                Register.addStudent(input);
                String message = "{message:'FIXME : Added'}";
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();

            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Cant added the student.").build();
            }
        }else{
            return Response.status(HttpResponseCodes.SC_FOUND).entity("Error.Student is not valid").build();
        }
    }
}
