package com.mybank.rest;

import com.mybank.model.Fruit;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/fruits")
@ApplicationScoped
public class FruitResource {

    @GET
    public Uni<List<Fruit>> get() {
        return Fruit.listAll(Sort.by("name"));
    }

    // https://quarkus.io/guides/getting-started-reactive mirar codigo de abajo. aquií lo explica.
    // para el post: curl --header "Content-Type: application/json" --request POST --data '{"name":"peach"}' http://localhost:8080/fruits
    @POST
    public Uni<Response> create(Fruit fruit) {
        return Panache.<Fruit>withTransaction(fruit::persist)
            .onItem().transform(inserted -> Response.created(URI.create("/fruits/" + inserted.id)).build());
    }

}