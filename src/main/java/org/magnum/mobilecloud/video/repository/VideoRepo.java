package org.magnum.mobilecloud.video.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VideoRepo extends CrudRepository<Video, Long> {
    Collection<Video> findByName(String name);
    Collection<Video> findByDurationLessThan(Long duration);
}
