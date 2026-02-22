package com.resume.ResumeMatcher.controller;

import com.resume.ResumeMatcher.model.Resume;
import com.resume.ResumeMatcher.service.ResumeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Resume> upload(@RequestParam("file") MultipartFile file) throws IOException{

      return ResponseEntity.ok(resumeService.uploadResume(file));
    }
    
  @PostMapping("/match")
    public ResponseEntity<String> match(@RequestParam Long resumeId, @RequestParam String jobDescription){

        Resume resume = resumeService.getResumeById(resumeId);

        String score = resumeService.matchScore(resumeId, jobDescription);
        return ResponseEntity.ok(score);
    }
    
    @PostMapping("/{id}/optimize")
    public ResponseEntity<String> optimizeResume(
            @PathVariable Long id,
            @RequestBody String jobDescription
    ){
        String optimizedResume = resumeService.optimizeResume(id, jobDescription);

        return ResponseEntity.ok(optimizedResume);
    }
}
