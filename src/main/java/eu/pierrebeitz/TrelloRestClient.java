package eu.pierrebeitz;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestForm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MultivaluedMap;

@Path("/cards")
@RegisterRestClient(configKey = "trello-rest-client")
@RegisterClientHeaders(TrelloRestClient.TrelloRestClientHeadersFactory.class)
public interface TrelloRestClient {

    @POST
    @Path("/{idCard}/attachments")
    TrelloAttachment uploadAttachmentOnCard(@PathParam("idCard") String cardId, @RestForm byte[] file);

    @ApplicationScoped
    public class TrelloRestClientHeadersFactory implements ClientHeadersFactory {

        @ConfigProperty(name = "trello.apiKey")
        String apiKey;
        @ConfigProperty(name = "trello.token")
        String token;

        @Override
        public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, MultivaluedMap<String, String> clientOutgoingHeaders) {
            clientOutgoingHeaders.putIfAbsent("Authorization", List.of(String.format("OAuth oauth_consumer_key=\"%s\", oauth_token=\"%s\"", apiKey, token)));
            return clientOutgoingHeaders;
        }
    }
}
