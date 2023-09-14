package eu.pierrebeitz;

import java.util.Random;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataOutput;
import io.quarkus.logging.Log;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import jakarta.ws.rs.core.MediaType;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@TopCommand
@Command(name = "trello", mixinStandardHelpOptions = true)
public class EntryCommand implements Runnable {
    private static final Random RANDOM = new Random();

    private final TrelloRestClient restClient;

    @Parameters(index= "0", paramLabel = "<id>", description = "The size of the attachment to generate")
    String cardId;

    @Parameters(index= "1", paramLabel = "<size>", description = "The size of the attachment to generate")
    int attachmentSize;

    public EntryCommand(@RestClient TrelloRestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void run() {
        byte[] arr = new byte[attachmentSize];
        RANDOM.nextBytes(arr);
        var attachment = restClient.uploadAttachmentOnCard(cardId, arr);
        Log.infof("Trello answered with %s", attachment);
    }

}
