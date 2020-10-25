package org.magnum.mobilecloud.video;


import com.google.gson.Gson;
import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit.http.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import static org.magnum.mobilecloud.video.client.VideoSvcApi.VIDEO_DURATION_SEARCH_PATH;
import static org.magnum.mobilecloud.video.client.VideoSvcApi.VIDEO_TITLE_SEARCH_PATH;


@Controller
public class VideoController {

    @Autowired
    private VideoRepo videoRepo;


    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList(){
        List<Video> videos = new ArrayList<>();
        for (Video v : videoRepo.findAll())
            videos.add(v);
        return videos;
    }

    @RequestMapping(value="/video", method = RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video v){
        return videoRepo.save(v);
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Video> getVideoById(
            @PathVariable long id) {
        Video v = videoRepo.findOne(id);
        if (v != null)
            return new ResponseEntity<>(v, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/like", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String>  likeVideo(@PathVariable long id,  Principal p){
        Video v = videoRepo.findOne(id);
        String userId = p.getName();

        if (v == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<String> likedBy = v.getLikedBy();
        if (likedBy.contains(userId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        likedBy.add(userId);
        v.setLikes(v.getLikes() + 1);
        videoRepo.save(v);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH + "/{id}/unlike", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String>  unlikeVideo(@PathVariable long id,  Principal p){
        Video v = videoRepo.findOne(id);
        String userId = p.getName();

        if (v == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<String> likedBy = v.getLikedBy();
        if (!likedBy.contains(userId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        v.setLikes(v.getLikes() - 1);
        likedBy.remove(userId);
        videoRepo.save(v);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value=VIDEO_TITLE_SEARCH_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> findByTitle(@RequestParam("title") String title) {
        return videoRepo.findByName(title);

    }

    @RequestMapping(value = VIDEO_DURATION_SEARCH_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> findByDurationLessThan(@RequestParam("duration") long duration) {
        return videoRepo.findByDurationLessThan(duration);
    }

}
