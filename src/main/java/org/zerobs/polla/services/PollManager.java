package org.zerobs.polla.services;

import org.zerobs.polla.entities.db.Poll;
import org.zerobs.polla.exception.CustomException;

public interface PollManager {
    void add(Poll poll) throws CustomException;
}
