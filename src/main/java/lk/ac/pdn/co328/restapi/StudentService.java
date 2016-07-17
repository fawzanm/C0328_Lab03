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
public class StudentService {

	private static StudentRegister reg = new StudentRegister();

	@GET
	@Path("student/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewStudent(@PathParam("id") int id) {
		Student st;
		synchronized (reg) {
			st = reg.findStudent(id);
		}

		if (st == null) {
			Message message = new Message();
			message.setMessage(id + " is not valid Reg No");
			return Response.status(HttpResponseCodes.SC_NOT_FOUND).entity(message).build();
		}
		return Response.status(HttpResponseCodes.SC_FOUND).entity(st).build();
	}

	@POST
	@Path("student/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyStudent(@PathParam("id") int id, Student input) {
		synchronized (reg) {
			try {
				// remove the student first
				reg.removeStudent(id);
				reg.addStudent(input);
			} catch (Exception e) {
				Message message = new Message();
				message.setMessage(e.getMessage());
				return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
			}
		}

		return Response.status(HttpResponseCodes.SC_OK).entity(input).build();
	}

	@DELETE
	@Path("student/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("id") int id) {
		synchronized (reg) {
			// remove the student first
			reg.removeStudent(id);
		}
		Message message = new Message();
		message.setMessage("removed !!");
		return Response.status(HttpResponseCodes.SC_OK).entity(message).build();
	}

	@PUT
	@Path("student/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStudent(@PathParam("id") int id, Student input) {
		synchronized (reg) {
			try {
				reg.addStudent(input);
			} catch (Exception e) {
				Message message = new Message();
				message.setMessage(e.getMessage());
				return Response.status(HttpResponseCodes.SC_BAD_REQUEST).entity(message).build();
			}
		}
		return Response.status(HttpResponseCodes.SC_OK).entity(input).build();
	}
}
