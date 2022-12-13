package si.fri.prpo.skupina8.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina8.Anotacije.BeleziKlice;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeIzdelkovZrno;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Path("izdelki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzdelkiVir {

    @Context
    protected UriInfo uriInfo;


    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @GET
    @BeleziKlice
    public Response vrniIzdelke(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Izdelek> izdelki = izdelkiZrno.pridobiIzdelke(query);
        Long izdelkiCount = izdelkiZrno.steviloIzdelkov(query);

        return Response.ok(izdelki).header("X-Total-Count",izdelkiCount).build();
    }


    @GET
    @Path("{id}")
    @BeleziKlice
    public Response vrniIzdelek(@PathParam("id") int id){

        Izdelek izdelek = izdelkiZrno.getIzdelek(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(izdelek).build();
    }

    @GET
    @Path("kategorije/{kategorija_id}")
    @BeleziKlice
    public Response vrniIzdelkeVKategoriji(@PathParam("kategorija_id") int kategorija_id){
        List<Izdelek> izdelki = upravljanjeIzdelkovZrno.vrniSeznamIzdelkovVKategoriji(kategorija_id); // pridobi izdelke
        return Response.ok(Response.Status.OK).entity(izdelki).build();
    }

    @GET
    @Path("popular")
    @BeleziKlice
    public Response vrniIzdelkePopularne(){
        List<Izdelek> izdelki = upravljanjeIzdelkovZrno.vrniNajpopularnejše(); // pridobi izdelke


        return Response.status(Response.Status.OK).entity(izdelki).build();
    }


    @POST
    @BeleziKlice
    public Response dodajIzdelek(Izdelek izdelek) {

        return Response
                .status(Response.Status.CREATED)
                .entity(izdelkiZrno.dodajIzdelek(izdelek))
                .build();
    }

    @PUT
    @Path("{id}")
    @BeleziKlice
    public Response posodobiIzdelek(@PathParam("id") int id, Izdelek izdelek){

        return Response
                .status(Response.Status.OK)
                .entity(izdelkiZrno.updateIzdelek(id, izdelek))
                .build();
    }


    @DELETE
    @Path("{id}")
    @BeleziKlice
    public Response odstraniIzdelek(@PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(izdelkiZrno.izbrisiIzdelek(id))
                .build();
    }
}
