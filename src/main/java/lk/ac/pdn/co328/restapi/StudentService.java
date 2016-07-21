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
    private StudentRegister register = new StudentRegister();
    public StudentService() {
    }
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
   // public Response viewStudent(@PathParam("id") int id, String input) {
       // Student st = new Student(id, "dummy", "dummy");
    public Response viewStudent(@PathParam("id") int id)
    {
        Student st = register.findStudent(id);
        if(st == null)
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity("\"No such student!\"").build();

        return Response.status(HttpResponseCodes.SC_OK).entity("\"Student modified successfully!\"").build();

    }

    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        //String message = "{message:'FIXME : Update service is not yet implemented'}";
        // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();

        if(register.findStudent(id) == null)
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\"No such student. Use PUT to add student..\"").build();


        input = input.replaceAll("\\s+","");
        String[] data = input.split(",");
        if(data.length != 2)
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\"Body format not recognized.\"").build();

        data[0] = data[0].replace("firstname=","");
        data[1] = data[1].replace("lastname=","");

        if(data[0].contains("=") || data[1].contains("="))
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\"Body format not recognized.\"").build();

        Student st = register.findStudent(id);
        st.setFirstName(data[0]);
        st.setLastName(data[1]);

        return Response.status(HttpResponseCodes.SC_OK).entity("\"Student modified successfully!\"").build();


    }

    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {
        //String message = "{message:'FIXME : Delete service is not yet implemented'}";
        // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        if(register.findStudent(id) == null)
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(" \"No such student\"").build();
        register.removeStudent(id);
        return Response.status(HttpResponseCodes.SC_OK).entity("\"Successfully deleted the student\"").build();

    }

    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        //String message = "{message:'FIXME : Add service is not yet implemented'}";
        // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
        input = input.replaceAll("\\s+","");
        String[] data = input.split(",");
        if(data.length != 2)
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\"Body format not recognized. \"").build();

        data[0] = data[0].replace("firstname=","");
        data[1] = data[1].replace("lastname=","");

        if(data[0].contains("=") || data[1].contains("="))
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("\"Request body format not recognized.\"").build();

        try
        {
            register.addStudent(new Student(id, data[0], data[1]));
        }
        catch (Exception e)
        {
            return Response.status(HttpResponseCodes.SC_CONFLICT).entity("\"Student already exist.\"").build();
        }

        return Response.status(HttpResponseCodes.SC_OK).entity("\"Student added successfully!\"").build();

    }
}


