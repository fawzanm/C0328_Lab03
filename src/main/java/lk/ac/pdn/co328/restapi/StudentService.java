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
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")

public class StudentService
{
    private StudentRegister register;
    StudentService(){
        register = new StudentRegister();
    }

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {

        Student st = register.findStudent(id);
        if(st == null) {
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("\"That student not exits!\"").build();
        }

        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {

        if(register.findStudent(id) == null)
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\"No such student.Use PUT to add.\"").build();
        input = input.replaceAll("\\s+","");
        String[] data = input.split(",");

        if(data.length != 2)
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\" Requested body format is not recognized.usage:firstname = <first name>, lastname = <last name> = <last name>\"").build();

        data[0] = data[0].replace("firstname=","");
        data[1] = data[1].replace("lastname=","");

        if(data[0].contains("=") || data[1].contains("="))
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\"Request body format not recognized. usage: firstname = <first name>, lastname = <last name>\"").build();


        Student st =  register.findStudent(id);
        st.setFirstName(data[0]);
        st.setLastName(data[1]);

        String message = "{message:'FIXME : Update service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity("\"Request body format not recognized. usage: firstname = <first name>, lastname = <last name>\"").build();
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
    public Response addStudent(@PathParam("id") int id, String input)
    {
        String message = "{message:'FIXME : Add service is not yet implemented'}";  // Ideally this should be machine readable format Json or XML 
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


