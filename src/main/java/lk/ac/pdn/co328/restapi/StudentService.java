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
    StudentRegister sr=new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {

        Student st = sr.findStudent(id);
        String message = "Student id " +id+" is not found";  // Ideally this should be machine readable format Json or XML
        if(st==null) return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();

    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        Student st = sr.findStudent(id);
        String message = "Student id " +id+" is not found";  // Ideally this should be machine readable format Json or XML
        if(st==null) return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        String ary[]=input.split("\\s+");;
        st.setFirstName(ary[0]);
        st.setLastName(ary[1]);
        return Response.status(HttpResponseCodes.SC_OK).entity("Student id "+ id + " is updated as First name = " +
        ary[0]+" Last name= "+ary[1]+" .").build();
    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id)
    {

        Student st = sr.findStudent(id);

        String message = "Student id " +id+" is not found";  // Ideally this should be machine readable format Json or XML


        if(st==null) return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
        sr.removeStudent(id);
        return Response.status(HttpResponseCodes.SC_OK).entity("Student id " +id+" is deleted.").build();
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {

        Student alreadyRegistered=sr.findStudent(id);
        if(alreadyRegistered!=null) {
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity("This student id " + id
                    + " is already added").build();

        }
        String ary[]=input.split("\\s+");;

        Student st =new Student(id,ary[0],ary[1]);

        try {
            sr.addStudent(st);
            return Response.status(HttpResponseCodes.SC_OK).entity("Student id "+id+" is added").build();
        } catch (Exception e) {
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(e).build();
        }


    }
}


