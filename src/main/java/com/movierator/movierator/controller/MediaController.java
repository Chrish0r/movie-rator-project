package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.movierator.movierator.controller.formObjects.MediaSearchResult;
import com.movierator.movierator.controller.formObjects.SearchTerm;
import com.movierator.movierator.model.Media;
import com.movierator.movierator.repository.MediaRepository;


@Controller
public class MediaController {
  private MediaRepository mediaEntityRepository;
  
  public MediaController(MediaRepository mediaEntityRepository) {
    this.mediaEntityRepository = mediaEntityRepository;
  }
  
  @GetMapping("/search-media")
  public String searchMedia(@RequestParam(name = "searchTerm") String searchTermRaw, Model model) {
    List<Media> foundMedia = this.mediaEntityRepository.findByNameContaining(searchTermRaw);
    List<MediaSearchResult> results = new ArrayList<>(foundMedia.size());
    
    for (Media media : foundMedia) {
      results.add(new MediaSearchResult(media.getId(), media.getName()));
    }
    model.addAttribute("results", results);

    // To show the search term on the result page it has to be passed as attribute
    SearchTerm searchTerm = new SearchTerm(searchTermRaw);
    model.addAttribute("searchTerm", searchTerm);

    return "media-search-result";
  }

  @GetMapping(value="/media/{id}")
  public String showMedia(@PathVariable long id, Model model) {
    Optional<Media> media = this.mediaEntityRepository.findById(id);
    
    if(media.isEmpty()) {
      return "media-not-found";
    }
    
    model.addAttribute("media", media.get());
    return "media-detail";
  }
}
