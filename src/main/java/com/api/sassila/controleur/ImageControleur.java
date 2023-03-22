package com.api.sassila.controleur;

import com.api.sassila.modele.Individu;
import com.api.sassila.service.IndividuServiceImplement;
import com.api.sassila.service.SectionServiceImplement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller @RequiredArgsConstructor
@RequestMapping(path = "/image") @Slf4j
public class ImageControleur {
    private final IndividuServiceImplement individuServiceImplement;
    private final SectionServiceImplement sectionServiceImplement;

    @GetMapping("display/{id}")
    public ResponseEntity<byte[]> displayIndividuImage(@PathVariable(name = "id") long id) {
        Optional<Individu> optionalIndividu = individuServiceImplement.recupererUnIndividu(id);
        if (optionalIndividu.isEmpty()) throw new IllegalStateException("Aucun individu trouvé.");
        Individu individu = optionalIndividu.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(individu.getImageUrl(),headers, HttpStatus.OK);
    }
}
