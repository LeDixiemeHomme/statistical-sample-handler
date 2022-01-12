package fr.esgi.fyc.statisticalsamplehandler.elastic.web;

import fr.esgi.fyc.statisticalsamplehandler.elastic.customException.MissingElasticResponseException;
import fr.esgi.fyc.statisticalsamplehandler.elastic.domain.StatisticalSample;
import fr.esgi.fyc.statisticalsamplehandler.elastic.service.ElasticService;
import fr.esgi.fyc.statisticalsamplehandler.elastic.web.response.BulkStatisticalSampleResponse;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.common.compress.NotXContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/elastic")
public class ElasticController {
    private final ElasticService elasticService;

    @Autowired
    public ElasticController(ElasticService elasticService) {
        this.elasticService = elasticService;
    }

    @ApiOperation("Send a bulk of statistical sample to an elastic cluster.")
    @PostMapping(value = "/send/samples", produces = "application/json")
    public ResponseEntity<BulkStatisticalSampleResponse> postStatisticalSample(@RequestBody List<StatisticalSample> statisticalSampleBulk) {
        Optional<BulkStatisticalSampleResponse> optionalBulkStatisticalSampleResponse;
        try {
            optionalBulkStatisticalSampleResponse = this.elasticService.sendBulkStatisticalSample(statisticalSampleBulk);
        } catch (NotXContentException | IOException notXContentException) {
            notXContentException.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, notXContentException.getMessage());
        }

        try {
            if (!optionalBulkStatisticalSampleResponse.isPresent())
                //TODO get the wrong one or make another exception for an entire bulk
                throw new MissingElasticResponseException(statisticalSampleBulk.get(0));
        } catch (MissingElasticResponseException missingElasticResponseException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, missingElasticResponseException.getMessage());
        }

        BulkStatisticalSampleResponse elasticResponse = optionalBulkStatisticalSampleResponse.get();
        return new ResponseEntity<>(elasticResponse, HttpStatus.OK);
    }
}
