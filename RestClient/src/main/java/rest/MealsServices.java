package rest;

import Entities.Meal;
import Services.SessionManager;


import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/meals")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MealsServices {

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManager;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addMeal(Meal newMeal) {
        try {
            sessionManager.addMeal(newMeal);
        } catch (Exception e) {
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }
}
