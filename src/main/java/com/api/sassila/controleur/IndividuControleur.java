package com.api.sassila.controleur;

import com.api.sassila.modele.Individu;
import com.api.sassila.modele.Section;
import com.api.sassila.service.IndividuServiceImplement;
import com.api.sassila.service.SectionServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/individu")
public class IndividuControleur {

    private final IndividuServiceImplement individuServiceImplement;
    private final SectionServiceImplement sectionServiceImplement;

    @GetMapping("/list")
    public List<Individu> getIndividus(){
        return individuServiceImplement.recupererIndividus();
    }

    @GetMapping("/{id}")
    public Individu getOneIndividu(@PathVariable("id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) throw new IllegalStateException("Aucun individu trouvé.");
        return optionalIndividu.get();
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveIndividus(@RequestBody Individu individu) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("enregistré", individuServiceImplement.enregistrerIndividu(individu)))
                        .message("Individu enregistré")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteIndividu(@PathVariable("id") long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("supprimé", individuServiceImplement.supprimerIndividu(id)))
                        .message("Individu supprimé")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/{id}/sections")
    public List<Section> getIndividusSections(@PathVariable("id") long id){
        return sectionServiceImplement.recupererSectionsParIndividu(id);
    }

//    public ResponseEntity<Response> getIndividus(){
//        return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(LocalDateTime.now())
//                        .data(Map.of("Individus", individuServiceImplement.recupererIndividus()))
//                        .message("Individus retrieved")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build()
//        );
//    }

}
