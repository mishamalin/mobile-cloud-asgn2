package org.magnum.mobilecloud.video;


import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
public class VideoController {

    @Autowired
    private VideoRepo videoRepo;


    @RequestMapping(value="/video", method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideo(){
        List<Video> videos = new ArrayList<>();
        for (Video v : videoRepo.findAll())
            videos.add(v);
        return videos;
    }

    @RequestMapping(value="/video", method = RequestMethod.POST)
    public @ResponseBody Video setVideo(@RequestBody Video v){
        return videoRepo.save(v);
    }

    @RequestMapping(value="/video/{id}/data", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity setVideoData(
            @PathVariable long id, @RequestParam("data") MultipartFile videoData) throws IOException {

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value="/video/{id}/data", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity getVideoData(
            @PathVariable long id, HttpServletResponse response) throws IOException {

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
