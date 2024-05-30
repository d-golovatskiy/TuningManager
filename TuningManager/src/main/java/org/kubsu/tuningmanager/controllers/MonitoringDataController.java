package org.kubsu.tuningmanager.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.kubsu.tuningmanager.entities.TaskToCollect;
import org.kubsu.tuningmanager.services.TaskToCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;

import java.io.*;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/monitoring")
public class MonitoringDataController {

    @Autowired
    private final InfluxDBClient influxDBClient;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TaskToCollectService taskToCollectService;

    @Value("${monitoring.data.path}")
    String dataPath;



    @GetMapping("/data")
    public ResponseEntity<byte[]>  getData(HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("text/csv; charset=utf-8");
        String flux = "from(bucket: \"kubsu\")" +
                "  |> range(start: 0, stop: -3d)" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"airSensors\")";
        QueryApi queryApi = influxDBClient.getQueryApi();
        String tables = queryApi.queryRaw(flux);


        /*StringBuilder res = new StringBuilder();
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                res.append(String.format("%s %s: %s %s", record.getTime(),
                        record.getMeasurement(), record.getField(), record.getValue()));
            }
        }
        influxDBClient.close();*/
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add("Content-Type", "application/zip");

        responseHeaders.add("Content-Type", "application/vnd.ms-excel");
        responseHeaders.add("Content-Disposition", "attachment; filename=abc.csv");

        return new ResponseEntity<>(tables.getBytes("ISO8859-15"), responseHeaders, HttpStatus.OK);


    }

    @PostMapping(value = "/data/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<HttpStatus> postData(@PathVariable(name = "id") Long id, @RequestBody String s) throws IOException {

        File file = new File(dataPath+"task_id_"+id.toString()+".csv");
        if (!file.exists()){
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.close();
        TaskToCollect t = taskToCollectService.getTaskToCollect(id,null,null,null).get(0);
        t.setStatus("finished");
        taskToCollectService.changeTaskStatus(t.getId(),"finished");
        return ResponseEntity.ok(HttpStatus.OK);
        //return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/task")
    public Response test(@RequestParam(name = "id") Long id) throws IOException {
        TaskToCollect taskToCollect = taskToCollectService.getTaskToCollect(id,null,null,null).get(0);
        String s = mapper.writeValueAsString(taskToCollect);
        final Response response = Request.Post("http://localhost:8081/data/task")
                // .addHeader("Content-Type", "application/vnd.ms-excel")
                .addHeader("Content-Type", "application/json")
                .bodyString(mapper.writeValueAsString(taskToCollect), ContentType.APPLICATION_JSON)
                .execute();
        return response;

    }




}
