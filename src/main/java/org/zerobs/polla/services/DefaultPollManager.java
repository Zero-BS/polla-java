package org.zerobs.polla.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerobs.polla.entities.db.Option;
import org.zerobs.polla.entities.db.Poll;
import org.zerobs.polla.entities.db.Tag;
import org.zerobs.polla.exception.CustomException;
import org.zerobs.polla.exception.CustomRuntimeException;
import org.zerobs.polla.repositories.EntityRepository;
import org.zerobs.polla.utilities.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.zerobs.polla.exception.RuntimeExceptionType.*;

@Service
@Slf4j
public class DefaultPollManager implements PollManager {
    @Autowired
    private EntityRepository<Poll> pollRepository;
    @Autowired
    private LocationManager locationManager;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private OptionManager optionManager;

    @Override
    public void add(Poll poll) throws CustomException {
        validateAndInitialize(poll);

        List<Option> savedOptions = new ArrayList<>();
        try {
            savedOptions = optionManager.add(poll.getOptions(), poll.getId());
            poll.setTags(tagManager.add(poll.getTags()));
            pollRepository.save(poll);
        } catch (Exception e) {
            optionManager.delete(savedOptions);
            tagManager.delete(poll.getTags().stream().filter(Tag::isNewTag).collect(toList()));
            throw new CustomException(e.getMessage(), e);
        }
    }

    private void validateAndInitialize(Poll poll) {
        if (poll == null)
            throw new CustomRuntimeException(EMPTY_POLL);
        poll.setId(UUID.randomUUID().toString());
        poll.setVotes(0);

        validatePollProperties(poll);
        validateDates(poll);
        validateLocations(poll);
        validateOptions(poll);
        validateTags(poll);

        poll.setCreatedBy(Utils.getUserId());
        poll.setCreatedOn(System.currentTimeMillis());
        poll.setUpdatedOn(poll.getCreatedOn());
    }

    private void validatePollProperties(Poll poll) {
        if (StringUtils.isBlank(poll.getTitle()))
            throw new CustomRuntimeException(EMPTY_POLL_TITLE);
        poll.setTitle(poll.getTitle().trim());
        if (poll.getTitle().length() < 3)
            throw new CustomRuntimeException(SMALL_POLL_TITLE);
        if (poll.getTitle().length() > 200)
            throw new CustomRuntimeException(LARGE_POLL_TITLE);
        if (poll.getDescription() != null) {
            poll.setDescription(poll.getDescription().trim());
            if (poll.getDescription().length() > 10000)
                throw new CustomRuntimeException(LARGE_POLL_DESCRIPTION);
        }
    }

    private void validateDates(Poll poll) {
        if (poll.getExpiresOn() != null && System.currentTimeMillis() >= poll.getExpiresOn())
            throw new CustomRuntimeException(EXPIRED_POLL);
        if (poll.getPublishOn() != null && poll.getPublishOn() >= poll.getExpiresOn())
            throw new CustomRuntimeException(SMALL_PUBLISH_DATE);
    }

    private void validateLocations(Poll poll) {
        if (poll.getLocationsOpen() != null)
            for (Integer locationId : poll.getLocationsOpen())
                if (!locationManager.validate(locationId))
                    throw new CustomRuntimeException(INVALID_LOCATION_ID, new String[]{String.valueOf(locationId)});
    }

    private void validateTags(Poll poll) {
        poll.setTags(Utils.cleanList(poll.getTags()));
        if (poll.getTags().size() > 5)
            throw new CustomRuntimeException(LARGE_TAGS);
    }

    private void validateOptions(Poll poll) {
        poll.setOptions(Utils.cleanStringList(poll.getOptions()));
        if (poll.getOptions().size() < 2)
            throw new CustomRuntimeException(SMALL_OPTIONS);
        if (poll.getOptions().size() > 100)
            throw new CustomRuntimeException(LARGE_OPTIONS);
    }
}
