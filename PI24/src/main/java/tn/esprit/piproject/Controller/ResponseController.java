package tn.esprit.piproject.Controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.piproject.Entities.*;
import tn.esprit.piproject.Repositories.ComplaintRepository;
import tn.esprit.piproject.Repositories.UserRepository;
import tn.esprit.piproject.Services.IProjectService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static tn.esprit.piproject.Entities.Role.ADMIN;

@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api/response")
public class ResponseController {
    @Autowired
    private IProjectService iProjectService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    @GetMapping
    public ResponseEntity<List<Response>> getAllResponse() {
        List<Response> response = iProjectService.getAllResponse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // Get documents by id
    @GetMapping("/{id}")
    public ResponseEntity<Response> getComplaintById(@PathVariable int id) {
        Optional<Response> response = iProjectService.getResponseById(id);
//        if (response.isPresent()) {
//            return new ResponseEntity<>(response.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        return response.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    // Create documents
    @PostMapping()
    public ResponseEntity<Response> createResponse(@RequestBody Response response) {
        try {
            User ADMIN = userRepository.findById(response.getADMIN().getId()).orElse(null);
            Complaint complaint = complaintRepository.findById(response.getComplaint().getIdComp()).orElse(null);

            if (ADMIN == null || complaint == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            response.setResponseDate(LocalDateTime.now());
            response.setADMIN(ADMIN);
            response.setComplaint(complaint);

            Response savedResponse = iProjectService.createResponse(response);
            return new ResponseEntity<>(savedResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update documents
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateResponse(@PathVariable int id, @RequestBody Response response) {
        Optional<Response> oldResponse = iProjectService.getResponseById(id);
        if (oldResponse.isPresent()) {
            response.setIdRep(id);
            Response updatedResponse = iProjectService.updateResponse(response);
            return new ResponseEntity<>(updatedResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    // Delete documents

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable int id) {
        iProjectService.deleteResponse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
