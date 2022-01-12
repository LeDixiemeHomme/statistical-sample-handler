package fr.esgi.fyc.statisticalsamplehandler.elastic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.ElasticApi;
import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.StatisticalSample;
import fr.esgi.fyc.statisticalsamplehandler.elastic.web.response.BulkStatisticalSampleResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ElasticService implements IElasticService {
    private final ElasticApi elasticApi;

    public ElasticService() {
        this.elasticApi = new ElasticApi();
    }

    @Override
    public Optional<BulkStatisticalSampleResponse> sendBulkStatisticalSample(List<StatisticalSample> statisticalSampleBulk) throws IOException {
        List<String> listOfMessages = new ArrayList<>();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        for(StatisticalSample sample: statisticalSampleBulk) {
            listOfMessages.add(ow.writeValueAsString(sample));
        }
        return this.elasticApi.sendBulk(listOfMessages);
    }
}
