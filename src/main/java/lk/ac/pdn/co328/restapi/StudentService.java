package lk.ac.pdn.co328.restapi;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;
import org.jboss.resteasy.util.HttpResponseCodes;
 
@Path("rest")
public class StudentService {
    StudentRegister register_ = new StudentRegister();

    @GET
    @Path("student/{id}")
    // Uncommenting this will let the reciver know that you are sending a json
    @Produces( MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML )
    public Response viewStudent(@PathParam("id") int id) {
        register_.findStudent(id);
        Student st = new Student(id, "dummy", "dummy");
        return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
    }
    
    @POST
    @Path("student/{id}")
    //@Consumes("application/xml")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    //@Produces({ MediaType.APPLICATION_FORM_URLENCODED })
    @Produces({MediaType.APPLICATION_JSON})
    //public Response modifyStudent(@PathParam("id") int id,@MatrixParam("first") String firstname, @MatrixParam("last") String lastname){
    public Response modifyStudent(@PathParam("id") int id,String input){
        Student st = null;
        try {
            //st = new Student(id,firstname, lastname) ;
            register_.addStudent(st);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String message = "{message:'FIXME : Updated'}";  // Ideally this should be machine readable format Json or XML
        //return Response.status(HttpResponseCodes.SC_OK).entity(st).build();
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
    
    @DELETE
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") int id) {
        String message;
        try{
            register_.removeStudent(id);
            message = "{message:'FIXME : Deleted'}";
        }catch(Exception e){
            message = "{message:'FIXME : Error'}";
            e.printStackTrace();
        }
        // Ideally this should be machine readable format Json or XML
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }


    //GET http://host.com/library/book;id="id";first="Name";last="Lastname"
    @PUT
    @Path("student/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Consumes("application/xml")
    //public Response addStudent(@PathParam("id") int id, String input) {
     public Response addStudent(@MatrixParam("id") int id,@MatrixParam("first") String firstname, @MatrixParam("last") String lastname){
        Student st = null;
        String message;
        try {
            st = new Student(id,firstname,lastname) ;
            register_.addStudent(st);

        }catch (Exception e){
            e.printStackTrace();
        }
          // Ideally this should be machine readable format Json or XML
        message = "{message:'FIXME : Added'}";
        return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
    }
}


