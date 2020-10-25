package org.magnum.mobilecloud.video;


import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit.http.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
public class VideoController {

    @Autowired
    private VideoRepo videoRepo;


    @RequestMapping(value="/video", method = RequestMethod.GET)
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

    @RequestMapping(value="/video/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Video> getVideoById(
            @PathVariable long id) throws IOException {
        Video v = videoRepo.findOne(id);
        if (v != null)
            return new ResponseEntity<>(v, HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
