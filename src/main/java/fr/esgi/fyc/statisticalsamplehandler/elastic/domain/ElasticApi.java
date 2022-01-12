package fr.esgi.fyc.statisticalsamplehandler.elastic.domain;

import fr.esgi.fyc.statisticalsamplehandler.elastic.web.response.BulkStatisticalSampleResponse;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository
public class ElasticApi {
    private final RestHighLevelClient restHighLevelClient;
    private final Properties elasticProperties = new Properties();

    public ElasticApi() {
        try {
            File file = ResourceUtils.getFile("./config.elastic.xml");
            InputStream in = new FileInputStream(file);
            this.elasticProperties.loadFromXML(in);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        String elasticServer = this.elasticProperties.getProperty("elastic.server");
        String elasticUsername = this.elasticProperties.getProperty("elastic.username");
        String elasticPassword = this.elasticProperties.getProperty("elastic.password");

        // Create a credential provider that will be used by the restHighLevelClient to connect
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(elasticUsername, elasticPassword);
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        this.restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticServer.split(":")[0], Integer.parseInt(elasticServer.split(":")[1]), "https"))
                        .setHttpClientConfigCallback(httpClientBuilder ->
                                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
    }

    public Optional<BulkStatisticalSampleResponse> sendBulk(List<String> data) throws IOException {
        String elasticIndex = this.elasticProperties.getProperty("elastic.index");
        BulkRequest bulkRequest = new BulkRequest();
        for(String entry: data) {
            bulkRequest.add(new IndexRequest(elasticIndex,"mail").source(entry, XContentType.JSON));
        }

        BulkResponse bulkResponse = this.restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        BulkStatisticalSampleResponse bulkStatisticalSampleResponse =
                new BulkStatisticalSampleResponse(bulkResponse.buildFailureMessage(), bulkResponse.getIngestTookInMillis(), bulkResponse.hasFailures());

        return Optional.of(bulkStatisticalSampleResponse);
    }
}
