package org.kubsu.tuning.controllers;


import org.kubsu.tuning.services.AlarmService;
import org.kubsu.tuning.domain.entities.Alarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/v1/alarms")
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping("")
    public List<Alarm> getAlarms(@RequestParam(value = "alarm_id", required = false) Long alarmId,
                                 @RequestParam(value = "dateStart", required = false) String dateStart,
                                 @RequestParam(value = "dateEnd", required = false) String dateEnd,
                                 @RequestParam(value = "sys_id", required = false) Long sysId){
        Timestamp dateStartTimestamp = null;
        Timestamp dateEndTimestamp  = null;
        if (dateStart!=null){
            dateStartTimestamp = Timestamp.valueOf(dateStart);
        }
        if (dateEnd!=null){
            dateEndTimestamp = Timestamp.valueOf(dateEnd);
        }

        return alarmService.getAlarms(alarmId, dateStartTimestamp,dateEndTimestamp, sysId);
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> postAlarm(@RequestBody Alarm alarm){
        return alarmService.postAlarm(alarm);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchAlarm(@RequestBody Alarm alarm){
        return alarmService.patchAlarm(alarm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAlarm(@PathVariable(name="id") Long id){
        return alarmService.deleteAlarm(id);
    }
}
