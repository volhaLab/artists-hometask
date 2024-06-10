package com.example.hometask.jobs;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.hometask.artists.AlbumsRepository;

import jakarta.transaction.Transactional;

@Component
public class CleanupTopAlbumsJob {

    private static final Logger logger = LoggerFactory.getLogger(CleanupTopAlbumsJob.class);

    private final AlbumsRepository albumsRepository;

    public CleanupTopAlbumsJob(AlbumsRepository albumsRepository) {

        this.albumsRepository = albumsRepository;
    }

    /**
     * Removes all albums that have been created a week ago.
     * Job runs weekly.
     */
    @Scheduled(cron = "0 0 0 * * 0")
    @Transactional
    public void runJob() {

        logger.info("Started job - removing albums");
        int deletedCount = albumsRepository.deleteOld(LocalDateTime.now().minusWeeks(1));
        logger.info("Completed job - removing {} albums", deletedCount);
    }
}
