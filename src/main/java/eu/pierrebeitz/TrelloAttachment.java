package eu.pierrebeitz;

import java.net.URI;

public record TrelloAttachment(String id, String name, URI url, byte[] data, boolean isUpload, boolean isCover) {
}
