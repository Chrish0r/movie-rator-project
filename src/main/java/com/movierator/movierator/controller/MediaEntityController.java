package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.movierator.movierator.controller.formObjects.SearchResult;
import com.movierator.movierator.controller.formObjects.SearchTerm;
import com.movierator.movierator.model.MediaEntity;
import com.movierator.movierator.repository.MediaEntityRepository;

@Controller
public class MediaEntityController {
  private MediaEntityRepository mediaEntityRepository;
  
  public MediaEntityController(MediaEntityRepository mediaEntityRepository) {
    this.mediaEntityRepository = mediaEntityRepository;
  }
  
  @PostMapping("/search")
  public String searchMedia(@ModelAttribute SearchTerm searchTerm, Model model) {
    model.addAttribute("searchTerm", searchTerm);
    
    List<MediaEntity> foundMedia = this.mediaEntityRepository.findByNameContaining(searchTerm.getValue());
    // TODO
    List<SearchResult> results = new ArrayList<>(foundMedia.size());
    
    for (MediaEntity media : foundMedia) {
      results.add(new SearchResult(media.getName()));
    }
    model.addAttribute("results", results);

    return "search-result";
  }
}
