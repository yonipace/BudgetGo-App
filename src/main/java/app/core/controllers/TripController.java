package app.core.controllers;

import app.core.auth.JwtUtil;
import app.core.entities.Trip;
import app.core.exceptions.TravelBudgetException;
import app.core.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/trip")
@CrossOrigin
public class TripController {

    @Autowired
    private TripService tripService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public Trip addTrip(@RequestBody Trip trip, @RequestHeader String token) throws TravelBudgetException {

        try {
            return tripService.addTrip(trip, jwtUtil.extractId(token));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping
    public Trip updateTrip(@RequestBody Trip trip, @RequestHeader String token) throws TravelBudgetException {
        try {
            return tripService.updateTrip(trip, jwtUtil.extractId(token));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping
    public void deleteTrip(@RequestParam int tripId, @RequestHeader String token) throws TravelBudgetException {
        try {
            tripService.deleteTrip(tripId, jwtUtil.extractId(token));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/get-trip")
    public Trip getOneTrip(@RequestParam int tripId, @RequestHeader String token) throws TravelBudgetException {
        try {
            return tripService.getOneTrip(tripId, jwtUtil.extractId(token));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<Trip> getAllTrips(@RequestHeader String token) {

        try {
            return tripService.getAllTrips(jwtUtil.extractId(token));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("add-user")
    public Trip addUserToTrip(@RequestParam int tripId, @RequestBody String email, @RequestHeader String token) {

        try {
            System.out.println("from controller: " + email);
            return tripService.addUserToTrip(tripId, jwtUtil.extractId(token), email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }

    }

}
