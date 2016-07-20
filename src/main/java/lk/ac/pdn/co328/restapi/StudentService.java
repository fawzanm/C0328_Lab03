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
{   StudentRegister stdreg = new StudentRegister();

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json

    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st;
        st=stdreg.findStudent(id);
      if(st==null) {
        String message = "{message:'Invalid Student'}";
        return Response.status(HttpResponseCodes.SC_FOUND).entity(message).build();
      }
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }

    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, Student input)
    {
      try {
        stdreg.removeStudent(id);
        stdreg.addStudent(input);
      } catch (Exception e) {
        String message1 = "{message1:'Error!!'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(message1).build();
      }
      String message2 = "{message2:'Successful'}";  // Ideally this should be machine readable format Json or XML
      return Response.status(HttpResponseCodes.SC_OK).entity(message2).build();
    }

    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {   stdreg.removeStudent(id);
        String message = "{message:'Remove is successful'}";  // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }



    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, Student input)
    {   try{
          stdreg.addStudent(input);
        }catch (Exception e){
          String message1 = "{message1:'Error!!'}";
          return Response.status(HttpResponseCodes.SC_OK).entity(message1).build();
        }
      String message2 = "{message2:'Successful'}";  // Ideally this should be machine readable format Json or XML
      return Response.status(HttpResponseCodes.SC_OK).entity(message2).build();
    }
}


