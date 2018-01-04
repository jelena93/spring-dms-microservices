package document.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Scope;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Scope(scopeName = "singleton")
public class ElasticClient {

    private Client client;

    public ElasticClient() {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        try {
            transportClient = transportClient
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            return;
        }
        client = transportClient;
    }

    public Client getClient() {
        return client;
    }

}
