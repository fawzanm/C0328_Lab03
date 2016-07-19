/*
 * CO328 Lab3 web services excercise
 * Author - Himesh Karunarathna
 */

package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import org.jboss.resteasy.util.HttpResponseCodes;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
 
@Path("rest")
public class StudentService
{
    private StudentRegister studentReg = new StudentRegister();
    @GET
    @Path("student/{id}")
    // Uncommenting this will let the receiver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        Student st = studentReg.findStudent(id);
        if (st == null) {
            String message = "{message:'ERROR: Not Found details of the Student '}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }
        else {
            return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
        }
    }
    
    @POST
    @Path("student/{id}")
    @Consumes("application/xml")

    public Response modifyStudent(@PathParam("id") int id, String input)
    {
        // MESSAGE FORMAT -> <FisrtName> <LastName>
        String message;
        Student st = studentReg.findStudent(id);
        String in_data = input.replaceAll("\\s+","");
        String [] names = in_data.split(" ");
        if(names.length != 2){
            message = "{message:'Bad format . Please send First name and Last name saperated with space(' ') '}";
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();

        }


        if(st==null) {
            message = "{message:'ERROR: Update was not successful '}";  // Ideally this should be machine readable format Json or XML
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
        }
        else{
            st.setFirstName(names[0]);
            st.setLastName(names[1]);
            studentReg.removeStudent(id);
            try {
                studentReg.addStudent(st);
                message = "{message:'OK: Update was successful '}";
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
            } catch (Exception e) {
                e.printStackTrace();
                message = "{message:'ERROR: Update was not successful '}";
                return Response.status(HttpResponseCodes.SC_NOT_MODIFIED).entity(message).build();
            }
        }

    }
    
    @DELETE
    @Path("student/{id}")
    public Response deleteStudent(@PathParam("id") int id) {
        String message = "{message:'REMOVED : Delete service done'}";  // Ideally this should be machine readable format Json or XML
        if (studentReg.findStudent(id) == null) {
            message = "{message:'ERROR : Student not found in database'}";
            return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();

        } else {
            studentReg.removeStudent(id);
            return Response.status(HttpResponseCodes.SC_OK).entity(message).build();

        }
    }
    
    @PUT
    @Path("student/{id}")
    @Consumes("application/xml")
    public Response addStudent(@PathParam("id") int id, String input)
    {
        // MESSAGE FORMAT -> <FisrtName> <LastName>
        Student st = new Student();
        st.setId(id);
        String message;
        String in_data = input.replaceAll("\\s+","");
        String [] names = in_data.split(" ");
        if(names.length != 2){
            message = "{message:'Bad format . Please send First name and Last name saperated with space(' ') '}";
            return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();

        }

        st.setFirstName(names[0]);
        st.setLastName(names[1]);
            try {
                studentReg.addStudent(st);
                message = "{message:'OK: Update was successful '}";
                return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
            } catch (Exception e) {
                e.printStackTrace();
                message = "{message:'ERROR: Update was not successful '}";
                return Response.status(HttpResponseCodes.SC_EXPECTATION_FAILED).entity(message).build();
            }

    }
}


