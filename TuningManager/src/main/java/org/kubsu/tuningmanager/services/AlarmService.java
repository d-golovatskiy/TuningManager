package org.kubsu.tuningmanager.services;


import org.kubsu.tuningmanager.entities.Alarm;
import org.kubsu.tuningmanager.repositories.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;


    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public List<Alarm> getAlarms(Long alarmId, Timestamp dateStart, Timestamp dateEnd, Long sysId){
        List<Alarm> alarms;
        if (alarmId!=null){
            return alarmRepository.findById(alarmId).stream().toList();
        }
        else {
            alarms = alarmRepository.findAll();
            if (sysId!=null){
                alarms = alarms.stream().filter(e->e.getSysId().equals(sysId)).toList();
            }
            if (dateStart!=null){
                alarms = alarms.stream().filter(e->e.getDateStart().before(dateStart)).collect(Collectors.toList());
            }
            if (dateEnd!=null){
                alarms = alarms.stream().filter(e->e.getDateEnd().after(dateEnd)).collect(Collectors.toList());
            }
            return alarms;
        }

    }

    public ResponseEntity<HttpStatus> postAlarm(Alarm alarm){
        alarmRepository.save(alarm);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    public ResponseEntity<HttpStatus> patchAlarm(Alarm alarm){
        if(!alarmRepository.findById(alarm.getId()).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else {
            alarmRepository.save(alarm);
        }
        return ResponseEntity.ok(HttpStatus.OK);

    }

    public ResponseEntity<HttpStatus> deleteAlarm(Long id){
        alarmRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
