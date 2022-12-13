package si.fri.prpo.skupina8.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina8.Anotacije.BeleziKlice;
import si.fri.prpo.skupina8.CeneVTrgovinah;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kategorija;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;
import si.fri.prpo.skupina8.Zrna.KategorijeZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("kategorije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KategorijeVir {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private KategorijeZrno kategorijeZrno;

    @GET
    @BeleziKlice
    public Response vreniKategorije(){

        List<Kategorija> kategorije = kategorijeZrno.getKategorije(); // pridobi izdelke
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kategorija> kategorija1 = kategorijeZrno.pridobiKategorije(query);
        Long kategorijeCount = kategorijeZrno.steviloKategorij(query);

        return Response.ok(kategorija1).header("X-Total-Count",kategorijeCount).build();
    }


    @GET
    @Path("{id}")
    @BeleziKlice
    public Response vrniKategorijo(@PathParam("id") int id){

        Kategorija kategorija = kategorijeZrno.getKategorija(id); // pridobi izdelke

        return Response.status(Response.Status.OK).entity(kategorija).build();
    }

    @POST
    @BeleziKlice
    public Response dodajKategorijo(Kategorija kategorija) {

        return Response
                .status(Response.Status.CREATED)
                .entity(kategorijeZrno.dodajKategorija(kategorija))
                .build();
    }

    @PUT
    @Path("{id}")
    @BeleziKlice
    public Response posodobiKategorijo(@PathParam("id") int id, Kategorija kategorija){

        return Response
                .status(Response.Status.OK)
                .entity(kategorijeZrno.updateKategorija(id, kategorija))
                .build();
    }

    @DELETE
    @Path("{id}")
    @BeleziKlice
    public Response odstraniKategorijo(@PathParam("id") int id){

        return Response.status(Response.Status.OK)
                .entity(kategorijeZrno.izbrisiKategorija(id))
                .build();
    }

}
