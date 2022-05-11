package com.logrecord.component;

import com.logrecord.service.IPersistenceService;
import org.springframework.stereotype.Component;

/**
 * @author yeyu
 * @since 2022-05-11 14:49
 */
@Component
public class PersistenceService implements IPersistenceService {
    @Override
    public void saveLog() {
        System.out.println("save log ..");
    }
}
