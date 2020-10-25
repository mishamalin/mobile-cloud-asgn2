package org.magnum.mobilecloud.video.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepo extends CrudRepository<Video, Long> {

}
