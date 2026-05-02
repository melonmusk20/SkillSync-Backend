package com.resume.ResumeMatcher.service;

import com.resume.ResumeMatcher.model.Resume;
import com.resume.ResumeMatcher.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final AiService aiService;


    public Resume uploadResume(MultipartFile file) throws IOException {

        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String extractedText = stripper.getText(document);
        document.close();

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setFilePath("N/A");
        resume.setExtractedText(extractedText);

        return resumeRepository.save(resume);
    }

    public String matchScore(Long resumeId, String jobDescription) {
        Resume resume = getResumeById(resumeId);
        return aiService.getMatchAnalysis(resume.getExtractedText(), jobDescription);
    }

    public Resume getResumeById(Long resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    public String optimizeResume(Long resumeId, String jobDescription) {
        Resume resume = getResumeById(resumeId);
        return aiService.optimizeResume(resume.getExtractedText(), jobDescription);
    }
}
