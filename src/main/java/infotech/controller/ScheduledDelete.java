package infotech.controller;

import infotech.repo.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledDelete {

    @Autowired
    private ObjectRepository objectRepository;

    @Scheduled(fixedRate = 60000)
    public void deleteDeadRows() {
        objectRepository.deleteAllDeadRows();
    }
}
