package com.mybank.rest;

import com.mybank.model.Status;
import com.mybank.model.Transact;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniJoin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/transaction")
@ApplicationScoped
public class MyBankResource {

    private static final Logger logger = LoggerFactory.getLogger(MyBankResource.class);

    @GET
    public Uni<List<Transact>> get() {
        return Transact.listAll(Sort.by("account_iban"));
    }

    @GET
    @Path("/id/{id}")
    public Uni<Transact> getSingle(Long id) {
        return Transact.findById(id);
    }

    @GET
    //@Path("/{accountIban}")
    @Path("/search")
    @Operation(summary = "Search Transactions")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Found transaction", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PanacheQuery.class))
        }),
        @APIResponse(responseCode = "204", description = "Not Found this transaction, please verify this.", content = {
            @Content(mediaType = "application/text", schema = @Schema(implementation = String.class))
        }),
        @APIResponse(responseCode = "500", description = "Error transaction", content = @Content(mediaType = "application/text", schema = @Schema(implementation = String.class)))
    })
    public Uni<List<Transact>> getSingleAccountIban( @Parameter(description = "Account Iban", example = "ES9820385778983000760236") @QueryParam("accountIban") final String accountIban, @Parameter(description = "Sort by amount", example = "ASC") @QueryParam("sortamount") final String sortamount) {

        Uni<List<Transact>> uni = Transact.findByAccountIban(accountIban, sortamount);
        logger.info("Logger works well.");
        return uni;
    }

    // curl --header "Content-Type: application/json" --request POST --data '{"name":"peach"}' http://localhost:8080/fruits
    @POST
    public Uni<Response> create(Transact transaction) {
        return Panache.<Transact>withTransaction(transaction::persist)
            .onItem().transform(inserted -> Response.created(URI.create("/transaction/" + inserted.id)).build());
    }



/**
 * status
 * @return***************************************************/

    @GET
    @Path("/status/")
    @Operation(summary = "Search Status")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Found status", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PanacheQuery.class))
        }),
        @APIResponse(responseCode = "204", description = "Not Found this transaction, please verify this.", content = {
            @Content(mediaType = "application/text", schema = @Schema(implementation = String.class))
        }),
        @APIResponse(responseCode = "500", description = "Error transaction", content = @Content(mediaType = "application/text", schema = @Schema(implementation = String.class)))
    })
    // , @Parameter(description = "Channel: CLIENT, ATM, INTERNAL", example = "CLIENT") @QueryParam("channel") final Enum channel
    public Uni<List<PanacheEntityBase>> getSingleStatus(@Parameter(description = "Reference", example = "12345D") @QueryParam("reference") final String reference, @Parameter(description = "Channel: CLIENT, ATM, INTERNAL", example = "CLIENT") @QueryParam("channel") final String channel) {

        Uni<List<PanacheEntityBase>> uni = Status.list("reference",reference);
        //uni.await();

        uni.subscribeAsCompletionStage();
        UniJoin.Builder<Status> items = Uni.join().builder();

        /*
        for (Status item : uni) {
            Status sta = new Status();
            builder.add(sta.convertStatus(item));
        }

         */


        //return builder.joinAll().andCollectFailures().flatMap(itemList -> do whatever you need ...)
        //uni.subscribeAsCompletionStage();


        if(channel.equalsIgnoreCase("")){
            //etc....
        }

        //UniIfNoItem<List<PanacheEntityBase>> ds=  uni.ifNoItem();


        return uni;
    }

    @GET
    @Path("/statusAll")
    public Uni<List<Status>> getStatus() {
        return Status.listAll();
    }

}