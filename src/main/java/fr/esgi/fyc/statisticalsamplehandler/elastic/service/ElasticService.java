package fr.esgi.fyc.statisticalsamplehandler.elastic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.ElasticApi;
import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.StatisticalSample;
import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.StatisticalSampleElastic;
import fr.esgi.fyc.statisticalsamplehandler.elastic.web.response.BulkStatisticalSampleResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ElasticService implements IElasticService {
    private final ElasticApi elasticApi;

    public ElasticService() {
        this.elasticApi = new ElasticApi();
    }

    @Override
    public Optional<BulkStatisticalSampleResponse> sendBulkStatisticalSample(List<StatisticalSample> statisticalSampleBulk) throws IOException {
        Map<String, String> mapOfMessages = new HashMap<>();
        ObjectWriter ow = new ObjectMapper().registerModule(new JodaModule()).writer().withDefaultPrettyPrinter();
        for(StatisticalSample sample: statisticalSampleBulk) {
            mapOfMessages.put("index_" + sample.getMeasurementName(), ow.writeValueAsString(new StatisticalSampleElastic(sample)));
        }
        return this.elasticApi.sendBulk(mapOfMessages);
    }
}
