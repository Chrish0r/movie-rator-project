package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movierator.movierator.controller.formObjects.SearchResult;
import com.movierator.movierator.controller.formObjects.SearchTerm;
import com.movierator.movierator.model.Media;
import com.movierator.movierator.repository.MediaEntityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MediaEntityController {
  private MediaEntityRepository mediaEntityRepository;
  
  public MediaEntityController(MediaEntityRepository mediaEntityRepository) {
    this.mediaEntityRepository = mediaEntityRepository;
  }
  
  @PostMapping("/search")
  public String searchMedia(@ModelAttribute SearchTerm searchTerm, Model model) {
    model.addAttribute("searchTerm", searchTerm);
    
    List<Media> foundMedia = this.mediaEntityRepository.findByNameContaining(searchTerm.getValue());
    List<SearchResult> results = new ArrayList<>(foundMedia.size());
    
    for (Media media : foundMedia) {
      results.add(new SearchResult(media.getId(), media.getName()));
    }
    model.addAttribute("results", results);

    return "search-result";
  }

  @GetMapping(value="/media/{id}")
  public String showMedia(@PathVariable long id, Model model) {
    Optional<Media> media = this.mediaEntityRepository.findById(id);
    
    if(media.isEmpty()) {
      return "redirect:not-found";
    }
    
    model.addAttribute("media", media.get());
    return "media-detail";
  }
  
}
