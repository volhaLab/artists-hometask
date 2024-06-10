package com.example.hometask.jobs;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for jobs testing purposes
 */
@RestController
@RequestMapping("/")
public class JobsController {

    private final CleanupTopAlbumsJob cleanupTopAlbumsJob;

    public JobsController(CleanupTopAlbumsJob cleanupTopAlbumsJob) {

        this.cleanupTopAlbumsJob = cleanupTopAlbumsJob;
    }

    @DeleteMapping("/albums")
    public void cleanupAlbums() {

        cleanupTopAlbumsJob.runJob();
    }
}
