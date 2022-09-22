package app.core.controllers;

import app.core.services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@CrossOrigin
@Controller
@RequestMapping("/trip/excel")
public class ExcelController {

    @Autowired
    ExcelService fileService;

    @GetMapping("/download/{tripId}")
    public ResponseEntity<Resource> getFile(@PathVariable int tripId, @RequestHeader String token) {
        String filename = "budgetgo-trip-" + tripId + ".xlsx";

        System.out.println("excel controller");
        System.out.println("tripId: " + tripId);
//        System.out.println("token: " + token);

        try {

            InputStreamResource file = new InputStreamResource(fileService.load(tripId));

            HttpHeaders headers = new HttpHeaders();


            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, filename)
                    .header(HttpHeaders.FROM, "server")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}