package fr.esgi.fyc.statisticalsamplehandler.elastic.service;

import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.StatisticalSample;
import fr.esgi.fyc.statisticalsamplehandler.elastic.web.response.BulkStatisticalSampleResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IElasticService {
//    Optional<ElasticResponse> sendStatisticalSample(StatisticalSample statisticalSample);
    Optional<BulkStatisticalSampleResponse> sendBulkStatisticalSample(List<StatisticalSample> statisticalSampleBulk) throws IOException;
}
