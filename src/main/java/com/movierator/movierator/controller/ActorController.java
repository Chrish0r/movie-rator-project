package com.movierator.movierator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.movierator.movierator.controller.formObjects.ActorSearchResult;
import com.movierator.movierator.controller.formObjects.SearchTerm;
import com.movierator.movierator.model.Actor;
import com.movierator.movierator.repository.ActorRepository;
import com.movierator.movierator.tmdbApi.TMDBActorApi;
import com.movierator.movierator.tmdbApi.TMDBApiFactory;

@Controller
public class ActorController {
  private ActorRepository actorRepository;
  private TMDBActorApi tmdbActorApi;

  public ActorController(ActorRepository actorRepository, TMDBApiFactory tmdbApiFactory) {
    this.actorRepository = actorRepository;
    this.tmdbActorApi = tmdbApiFactory.createForActors();
  }

  @GetMapping("/search-actor")
  public String searchActor(@RequestParam(name = "searchTerm") String searchTermRaw, Model model) {
    List<Actor> foundActors = this.actorRepository.findByName(searchTermRaw);
    List<ActorSearchResult> results = new ArrayList<>(foundActors.size());
    
    for (Actor actor : foundActors) {
      results.add(new ActorSearchResult(actor.getId(), actor.getName()));
    }
    model.addAttribute("results", results);

    // To show the search term on the result page it has to be passed as attribute
    SearchTerm searchTerm = new SearchTerm(searchTermRaw);
    model.addAttribute("searchTerm", searchTerm);

    return "actor-search-result";
  }

  @GetMapping(value="/actor/{id}")
  public String showActor(@PathVariable long id, Model model) {
    Optional<Actor> actor = this.actorRepository.findById(id);
    
    if(actor.isEmpty()) {
      return "actor-not-found";
    }
    
    model.addAttribute("actor", actor.get());
    model.addAttribute("movies",  tmdbActorApi.getMovieCredits(id));

    return "actor-detail";
  }
}
